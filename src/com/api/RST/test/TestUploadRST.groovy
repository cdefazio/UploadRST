package com.api.RST.test;
import com.api.RST.*;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestUploadRST {

	@Test
	public void testCreateListOfJIRA() {
		def rst = new RST()
		
		def fileText = "#JIRA\nSW-2560"
		def hashTag = "#JIRA"
		
		def list = rst.createListOf(fileText, hashTag)
		
		assertEquals(list, ["#JIRA","SW-2560"])
	}
	
	@Test
	public void testCreateListOfBugs() {
		def rst = new RST()
		
		def fileText = "#BUG\nhi brent\n#BUG\nhi kirk"
		def hashTag = "#BUG"
		
		def list = rst.createListOf(fileText, hashTag)
		
		assertEquals(list, ["#BUG","hi brent","#BUG","hi kirk"])
	}
	@Test
	public void testSplitListOfBugs()
	{
		def rst = new RST()

		def listOfLines = ["#BUG","This is bug 1 sentence 1."," This is bug 1 sentence 2.","#BUG","This is bug 2 sentence 1."," This is bug 2 sentence 2."," This is bug 2 sentence 3."]	
	
		def list = rst.splitListByHashTags(listOfLines)
		
		def expectedList = ["This is bug 1 sentence 1. This is bug 1 sentence 2.","This is bug 2 sentence 1. This is bug 2 sentence 2. This is bug 2 sentence 3."]
		
		assertEquals(list, expectedList)
	}

}
