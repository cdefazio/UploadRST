package com.api.RST
import com.mongodb.*
class RSTReport {

	def createReportForRelease() 
	{
		//connect to the db
		//get collections by release
		//grab jira #, module, and # of issues
		//Print
		
		def reportMap=[:]
		
		def config = new ConfigSlurper().parse(new File('dbconfig.groovy').toURL())
		Mongo connection = new Mongo(config.server.toString(),config.port.toInteger().value)
		DB mongoDB = connection.getDB(config.name)
		
		DBCollection releaseCollection = mongoDB.getCollection("6.8.0.0")
		def listOfModules = releaseCollection.distinct("module")
		
		DBCursor cursorOfAllRSTDocuments = releaseCollection.find()
		
		cursorOfAllRSTDocuments.each{rstDoc ->
		
			if (reportMap[rstDoc.module] == null)
			{
				reportMap.put(rstDoc.module, new RSTMapData())
			}
			
			def value = reportMap[rstDoc.module]
			value.addJira(rstDoc.jira)
			value.addIssue(rstDoc.issue)
			value.addBug(rstDoc.bugs)
			
			reportMap[rstDoc.module] = value
		}
		
		reportMap.each{k,v ->
			println "${k} : ${v}"
		}
	}
	static main(args){
		def report = new RSTReport()
		report.createReportForRelease()
	}
	
}
