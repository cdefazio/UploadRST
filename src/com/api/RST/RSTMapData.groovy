package com.api.RST

class RSTMapData {

	def jiras = []
	def issues = []
	def numOfIssues = 0;
	def bugs = []
	def numOfBugs = 0;
	
	def addIssue(def issue)
	{
		issues.addAll(issue)
	}
	
	def addJira(def jira)
	{
		jiras.addAll(jira)
	}
	
	def addBug(def bug)
	{
		bugs.addAll(bug)
	}
	def getNumberOfBugs()
	{
		return bugs.size()
	}
	def getNumberOfIssues()
	{
		return issues.size()
	}
	def String toString()
	{
		"#JIRA:${jiras} \r\n #ISSUES: ${issues} \r\n #NUMOFISSUES: ${getNumberOfIssues()} \r\n #BUGS: ${bugs} \r\n #NUMOFBUGS: ${getNumberOfBugs()}"
	}
}
