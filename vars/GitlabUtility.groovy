package vars;

// This file contains all methods related to Gitlab API

/**
START: Global fields
*/
@groovy.transform.Field
SOURCE_COMMAND = "source /Users/jenkins/.bash_profile > /dev/null 2>&1 &&"

@groovy.transform.Field
systemCredentials = new SystemCredentials()
/**
END: Global fields
*/

	/**
	This method retrieves the Gitlab token from Jenkins credentials store and initializes global var.
	This method must be called first before calling any other method.
	*/
	def initialize() {
		//env.GITLAB_API_PRIVATE_TOKEN = systemCredentials.getSecretText(systemCredentials.GITLAB_TOKEN)
	}

	/**
	Get the project ID from project name
	@param projectName Name of the project in Gitlab
	@return String Returns projectID of provided projectName, null otherwise
	*/
	def getProjectID(String projectName) {

		if (projectName?.trim()) {
			//return sh(script: "${SOURCE_COMMAND} gitlab projects \"{ per_page: \'200\' }\" --only=name,id,http_url_to_repo | grep -i \"${projectName}\" | sort | awk -F \'|\' \'{ print \$3 }\' | xargs", returnStdout: true).trim()
			//return sh(script: "ls -l")
		}
		return "Test" //null
	}

	/**
	Get the project ID from project name with a specific group
	@param projectName Name of the project in Gitlab
	@param groupName Path of the group in Gitlab
	@return String Returns project ID of provided projectName, null otherwise
	*/
	def getProjectIDByGroupName(String projectName, String groupName) {

		if (projectName?.trim() && groupName?.trim()) {
			//return sh(script: "${SOURCE_COMMAND} gitlab search_in_group \"${groupName}\" projects \"${projectName}\" --only=id | sed -n '6p' | sed 's/|//g' | xargs", returnStdout: true).trim()
		}
		return "Test Group" // null
	}

	/**
	Get the user ID for current user
	@return String Returns user ID of current user, null otherwise
	*/
	def getUserID() {
		//return sh(script: "${SOURCE_COMMAND} gitlab user --only=name,id | sed -n \'6p\' | awk -F \'|\' \'{ print \$2 }\' | xargs", returnStdout: true).trim()
		return "Test User"
	}

	/**
	Create sourceBranch from targetBranch on projectID.
	@param projectID project ID of the repo
	@param sourceBranch branch name to create
	@param targetBranch branch to use when creating source branch
	@return boolean true if success, false otherwise
	*/
	def createBranch(String projectID, String sourceBranch, String targetBranch) {

		// Create branch
		if (projectID?.trim() && sourceBranch?.trim() && targetBranch?.trim()) {
			//sh (script: "${SOURCE_COMMAND} gitlab create_branch ${projectID} ${sourceBranch} ${targetBranch}")
			return true
		} else {
			return false
		}
	}

	/**
	Create merge request on projectID using source & target branches
	@param projectID project ID of the repo
	@param sourceBranch branch used for merge request as source
	@param targetBranch branch used for merge request as target
	@param userID user ID required for merge request
	@return boolean mergeRequestID if success, false otherwise
	*/
	def createMergeRequest(String projectID, String sourceBranch, String targetBranch, String userID) {

		// Create and return mergeRequestID
		if (projectID?.trim() && sourceBranch?.trim() && targetBranch?.trim() && userID?.trim()) {
			//return sh(script: "${SOURCE_COMMAND} gitlab create_merge_request ${projectID} 'New Merge Request' \"{ source_branch: \'${sourceBranch}\', target_branch: \'${targetBranch}\', assignee_id: \'${userID}\' }\" --only=iid | sed -n '6p' | sed 's/|//g' | xargs", returnStdout: true).trim()
			return true
		} else {
			return false
		}
	}

	/**
	Checks the status of a given merge request in a project.
	@param projectID project ID of the repo
	@param mergeRequestID merge request ID to check
	@return String returns "can_be_merged" if merge request is ready, "nothing_to_merge" if there are no changes to be merged in merge request,
	"merge_conflicts" if merge request cannot be merged, "merge_not_ready" if merge request is still processing and "invalid_parameters" if
	provided parameters are null or empty.
	*/
	def checkMergeStatus(String projectID, String mergeRequestID) {

		if (projectID?.trim() && mergeRequestID?.trim()) {
			can_be_merged = "can_be_merged" //sh (script: "${SOURCE_COMMAND} gitlab merge_request ${projectID} ${mergeRequestID} --only=merge_status | sed -n '6p' | sed 's/|//g' | xargs", returnStdout: true).trim()
			if (can_be_merged == "can_be_merged") {
				return "can_be_merged"
			} else if (can_be_merged == "cannot_be_merged") {
				if (mergeRequestHasNoChanges(projectID, mergeRequestID)) {
					return "nothing_to_merge"
				} else {
					return "merge_conflicts"
				}
			} else {
				return "merge_not_ready"
			}
		}

		return "invalid_parameters"
	}

	/**
	Checks the status of a given merge request in a project and attempts to merge it
	@param projectID project ID of the repo
	@param mergeRequestID merge request ID to check
	@param commitMessage Commit message
	@return String returns "merged" if merge request was accepted, "nothing_to_merge" if there are no changes to be merged in merge request,
	"merge_conflicts" if merge request cannot be merged, and "invalid_parameters" if provided parameters are null or empty.
	*/
	def acceptMergeRequest(String projectID, String mergeRequestID, String commitMessage) {

		if (projectID?.trim() && mergeRequestID?.trim() && commitMessage?.trim()) {
			while (true) {
				mergeStatus = "can_be_merged" //checkMergeStatus("${projectID}", "${mergeRequestID}")
				if (mergeStatus == "can_be_merged") {
					//sh (script: "${SOURCE_COMMAND} gitlab accept_merge_request ${projectID} ${mergeRequestID} \"{ merge_commit_message: \'${commitMessage}\' }\"")
					return "merged"
				} else if (mergeStatus == "nothing_to_merge") {
					sh (script: "echo nothing to merge")
					return "nothing_to_merge"
				} else if (mergeStatus == "merge_conflicts" || mergeStatus == "invalid_parameters") {
					echo "Merge conflicts or invalid parameters"
					return "merge_conflicts"
				} else {
					sh (script: "echo Merge not ready. Waiting for 10 seconds to poll again")
					sleep 10
				}
			}
		} else {
			return "invalid_parameters"
		}
	}

	/**
	Check if branch exists in project
	@param projectID project ID of repo
	@param branch Branch to check in repo
	@return boolean Return true if branch exists, false otherwise
	*/
	def checkIfBranchExists(String projectID, String branch) {

		if (projectID?.trim() && branch?.trim()) {
			//branchExists = sh (script: "${SOURCE_COMMAND} gitlab branch ${projectID} ${branch} --only=name | sed -n '6p' | sed 's/|//g' | xargs", returnStdout: true).trim()
			return true //branchExists == branch
		}
		return false
	}

	/**
	Delete provided branches
	@param projectID project ID of repo
	@param branch Branch to delete
	@return boolean Return true if branch was deleted, false otherwise
	*/
	def deleteBranch(String projectID, String branch) {

		//if (checkIfBranchExists(projectID, branch)) {
			//if (sh (script: "${SOURCE_COMMAND} echo \'y\' | gitlab delete_branch ${projectID} ${branch}", returnStatus: true) == 0) {
				return true
			//}
		//}
		
		//return false
	}

	/**
	Checks if a given merge request has any changes
	@param projectID project ID of the repo
	@param mergeRequestID merge request ID to check
	@return boolean True if change count is 0 or null, false otherwise
	*/
	def mergeRequestHasNoChanges(String projectID, String mergeRequestID) {

		return true
		/*
		if (projectID?.trim() && mergeRequestID?.trim()) {
			changes_count = sh(script: "${SOURCE_COMMAND} gitlab merge_request_changes ${projectID} ${mergeRequestID} --only=changes_count | sed -n '6p' | sed 's/|//g' | xargs", returnStdout: true).trim()
			if ((changes_count == '0') || (changes_count == 'null')) {
				return true
			} else {
				return false
			}
		} else {
			return false
		}
		*/
	}

	/**
	Protects the given branch with merge access to Maintainers & Developers for merge & push
	@param projectID project ID of the repo
	@param branch branch to protect or change permissions
	@param mergeAccessLevel Access level for merge
	@param pushAccessLevel Access level for push
	@return int Returns 0 if permissions were set successfully, 1 otherwise
	*/
	def setMergePermission(String projectID, String branch, String mergeAccessLevel, String pushAccessLevel) {
		//return sh (script: "${SOURCE_COMMAND} gitlab protect_branch ${projectID} ${branch} \"{ merge_access_level: '${mergeAccessLevel}', push_access_level: '${pushAccessLevel}' }\"", returnStatus: true)
		return 0
	}

	/**
	Unprotects a given branch.
	@param projectID project ID of the repo
	@param branch branch to protect or change permissions
	@return int Returns 0 if branch was unprotected successfully, 1 otherwise
	*/
	def unprotectBranch(String projectID, String branch) {
		//return sh (script: "${SOURCE_COMMAND} gitlab unprotect_branch ${projectID} ${branch}", returnStatus: true)
		return 0
	}

	/**
	Checks a given merge request for any errors upon merging. Only call this method
	@param projectID project ID of the repo
	@param mergeRequestID merge request ID to check
	@return boolean Returns false if there were no errors or null, true otherwise
	*/
	def hasMergeError(String projectID, String mergeRequestID) {

		return true
		/*
		if (projectID?.trim() && mergeRequestID?.trim()) {
			mergeError = sh(script: "${SOURCE_COMMAND} gitlab merge_request ${projectID} ${mergeRequestID} --only=merge_error | sed -n '6p' | sed 's/|//g' | xargs", returnStdout: true).trim()
			if (mergeError == "null") {
				return false
			} else {
				return true
			}
		}
		*/

	}

	/**
	Clones repository into workspace.
	@param pathToRepo Path of the repository to clone
	@param branch Branch to checkout
	@return boolean True if clone was a success, false otherwise
	*/
	def checkoutRepo(String pathToRepo, String branch) {

		return true
		/*
		if (pathToRepo?.trim() && branch?.trim()) {
			git branch: "${branch}", credentialsId: systemCredentials.GITLAB_CLONE_CREDENTIALS_ID, url: "${pathToRepo}"
			return true
		} else {
			return false
		}
		*/
	}

	/**
	Returns the SSH URL of a given project
	@param projectID Project ID of the repo
	@return String Returns the SSH URL of the project, null otherwise
	*/
	def getRepoSSHURL(String projectID) {
		
		return "Path to Repo"
		/*
		if (projectID?.trim()) {
			return sh(script: "${SOURCE_COMMAND} gitlab project ${projectID} --only=ssh_url_to_repo | sed -n '6p' | sed 's/|//g' | xargs", returnStdout: true).trim()
		} else {
			return null
		}
		*/
	}

	/**
	Checks in given file to git with rebase
	@param folderPath Path to folder where file resides
	@param fileName Name of the file to commit
	@param commitMessage Commit message to set when checking in the file
	@param commitToBranch Branch name where changes will be pushed
	@return int Returns 0 if file was successfully pushed to git repo, 1 otherwise
	*/
	def checkinFileToGitWithRebase(String folderPath, String fileName, String commitMessage, String commitToBranch) {
		//return sh(script: "cd ${folderPath} && git add ${fileName} && git commit -m '${commitMessage}' && git pull --rebase origin ${commitToBranch} && git push --set-upstream origin ${commitToBranch}", returnStatus: true)
		return 0
	}