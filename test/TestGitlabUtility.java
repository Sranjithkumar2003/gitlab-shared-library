package test;

import org.testng.annotations.Test;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import vars.GitlabUtility;

import org.testng.annotations.BeforeTest;
import org.codehaus.groovy.control.CompilationFailedException;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

import java.io.File;
import java.io.IOException;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import vars.GitlabUtility;


public class TestGitlabUtility {
	
	GitlabUtility gitlabUtility;
	GroovyShell groovyShell;
	Class groovy;
	GroovyObject groovyObj;
	final GroovyClassLoader classLoader = new GroovyClassLoader();
  
	@BeforeTest
	public void beforeTest() {
		
		try {
			groovyShell = new GroovyShell();
			groovy = classLoader.parseClass(new File("vars/GitlabUtility.groovy"));
			gitlabUtility = new GitlabUtility();
			groovyObj = (GroovyObject) groovy.newInstance();
			gitlabUtility.initialize();
		} catch (CompilationFailedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
  
	@Test(priority = 0)
	public void testGetProjectID() {
		System.out.println("Testing method getProjectID");
		
		Object result = groovyObj.invokeMethod("getProjectID", new Object[] { "Test Project" });
		//System.out.println(result);
		Assert.assertEquals(result, "Test");
	}
	
	@Test(priority = 1)
	public void testGetProjectIDByGroupName() {
		System.out.println("Testing method getProjectIDByGroupName");
		Object result = groovyObj.invokeMethod("getProjectIDByGroupName", new Object[] { "Test Project", "Test Group" });
		//System.out.println(result);
		Assert.assertEquals(result, "Test Group");
		
	}
	
	@Test(priority = 2)
	public void testGetUserID() {
		System.out.println("Testing method getUserID");
		Object result = groovyObj.invokeMethod("getUserID", new Object[] { });
		//System.out.println(result);
		Assert.assertEquals(result, "Test User");
		
	}

	@Test(priority = 4)
	public void testCreateBranch() {
		System.out.println("Testing method createBranch");
		Object result = groovyObj.invokeMethod("createBranch", new Object[] {  "Project ID", "Source Branch", "Target Branch" });
		//System.out.println(result);
		Assert.assertEquals(result, true);
		
	}
	
	@Test(priority = 5)
	public void testCreateMergeRequest() {
		System.out.println("Testing method createMergeRequest");
		Object result = groovyObj.invokeMethod("createMergeRequest", new Object[] {  "Project ID", "Source Branch", "Target Branch", "User ID" });
		//System.out.println(result);
		Assert.assertEquals(result, true);
		
	}
	
	
	@Test(priority = 6)
	public void testCheckMergeStatus() {
		System.out.println("Testing method checkMergeStatus");
		Object result = groovyObj.invokeMethod("checkMergeStatus", new Object[] {  "Project ID", "Merge Request ID" });
		//System.out.println(result);
		Assert.assertEquals(result, "can_be_merged");
		
	}
	
	
	@Test(priority = 7)
	public void testAcceptMergeRequest() {
		System.out.println("Testing method acceptMergeRequest");
		Object result = groovyObj.invokeMethod("acceptMergeRequest", new Object[] {  "Project ID", "Merge Request ID", "Commit Message" });
		//System.out.println(result);
		Assert.assertEquals(result, "merged");
		
	}
	
	@Test(priority = 8)
	public void testCheckIfBranchExists() {
		System.out.println("Testing method checkIfBranchExists");
		Object result = groovyObj.invokeMethod("checkIfBranchExists", new Object[] {  "Project ID", "Branch" });
		//System.out.println(result);
		Assert.assertEquals(result, true);
		
	}
	
	
	@Test(priority = 9)
	public void testDeleteBranch() {
		System.out.println("Testing method deleteBranch");
		Object result = groovyObj.invokeMethod("deleteBranch", new Object[] {  "Project ID", "Branch" });
		//System.out.println(result);
		Assert.assertEquals(result, true);
		
	}
	
	
	@Test(priority = 10)
	public void testMergeRequestHasNoChanges() {
		System.out.println("Testing method mergeRequestHasNoChanges");
		Object result = groovyObj.invokeMethod("mergeRequestHasNoChanges", new Object[] {  "Project ID", "Merge Request ID" });
		//System.out.println(result);
		Assert.assertEquals(result, true);
		
	}
	
	@Test(priority = 11)
	public void testSetMergePermission() {
		System.out.println("Testing method setMergePermission");
		Object result = groovyObj.invokeMethod("setMergePermission", new Object[] {  "Project ID", "Branch", "Merge Access Level", "Push Access Level" });
		//System.out.println(result);
		Assert.assertEquals(result, 0);
		
	}
	
	
	@Test(priority = 12)
	public void testUnprotectBranch() {
		System.out.println("Testing method unprotectBranch");
		Object result = groovyObj.invokeMethod("unprotectBranch", new Object[] {  "Project ID", "Branch" });
		//System.out.println(result);
		Assert.assertEquals(result, 0);
		
	}
	
	
	@Test(priority = 13)
	public void testHasMergeError() {
		System.out.println("Testing method hasMergeError");
		Object result = groovyObj.invokeMethod("hasMergeError", new Object[] {  "Project ID", "Merge Request ID" });
		//System.out.println(result);
		Assert.assertEquals(result, true);
		
	}
	
	
	@Test(priority = 14)
	public void testCheckoutRepo() {
		System.out.println("Testing method checkoutRepo");
		Object result = groovyObj.invokeMethod("checkoutRepo", new Object[] {  "Path to Repo", "Branch" });
		//System.out.println(result);
		Assert.assertEquals(result, true);
		
	}
	
	@Test(priority = 15)
	public void testGetRepoSSHURL() {
		System.out.println("Testing method getRepoSSHURL");
		Object result = groovyObj.invokeMethod("getRepoSSHURL", new Object[] {  "Project ID"});
		//System.out.println(result);
		Assert.assertEquals(result, "Path to Repo");
		
	}
	
	@Test(priority = 16)
	public void testCheckinFileToGitWithRebase() {
		System.out.println("Testing method checkinFileToGitWithRebase");
		Object result = groovyObj.invokeMethod("checkinFileToGitWithRebase", new Object[] {  "Folder Path", "File Name", "Commit Message", "Commit To Branch"});
		//System.out.println(result);
		Assert.assertEquals(result, 0);
		
	}
	
	@AfterTest
	public void afterTest() {
	}

}
