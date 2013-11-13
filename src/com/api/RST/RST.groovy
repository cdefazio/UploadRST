package com.api.RST
import com.mongodb.*

class RST extends BasicDBObject {

	def uploadToDB(fileText)
	{
		populateData(fileText)
		def config = new ConfigSlurper().parse(new File('dbconfig.groovy').toURL())

		Mongo connection = new Mongo(config.server.toString(),config.port.toInteger().value)
		DB mongoDB = connection.getDB(config.name)
		
		insertRST(mongoDB,this.get("release").value[0].toString())
		//TODO: grab sprint name from directory
		insertRST(mongoDB,"sprint-1")
	}
	
	def insertRST(DB db,String collectionName)
	{
		if (!collectionName.equals("null"))
		{
			DBCollection collection = db.getCollection(collectionName)
			collection.setObjectClass(RST.class)
			collection.insert(this)
			println "inserted into ${collectionName}"
		}
		else
		{
			println "unable to insert ${this.get("jira").value[0]} , no #RELEASE tag in file"
		}
	}
	
	def populateData(fileText)
	{

		put("bugs",splitListByHashTags(createListOf(fileText,"#BUG")))
		put("jira",splitListByHashTags(createListOf(fileText,"#JIRA")))
		put("date",splitListByHashTags(createListOf(fileText,"#DATE")))
		put("browser",splitListByHashTags(createListOf(fileText,"#BROWSER")))
		put("module",splitListByHashTags(createListOf(fileText,"#MODULE")))
		put("issue",splitListByHashTags(createListOf(fileText,"#ISSUE")))
		put("observation",splitListByHashTags(createListOf(fileText,"#OBSERVATION")))
		put("actionItems",splitListByHashTags(createListOf(fileText,"#ACTION ITEMS")))
		put("testingTime",splitListByHashTags(createListOf(fileText,"#TESTING TIME")))
		put("setupTime",splitListByHashTags(createListOf(fileText,"#SETUP TIME")))
		put("tangentTime",splitListByHashTags(createListOf(fileText,"#TANGENT TIME")))
		put("release",splitListByHashTags(createListOf(fileText,"#RELEASE")))
		
	}
	
	//parse the RST and create a list for each line associated to the given hashtag
	def createListOf(fileText, hashTag)
	{
		def list = []

		def insideHashTag = false
		
		fileText.eachLine { line->
			
			if (line.contains(hashTag))
			{
				insideHashTag = true
			}
			else if (line.startsWith("#"))
			{
				insideHashTag = false
			}
			
			if ((insideHashTag) 
				&& !line.trim ().equals(""))
			{
				list.add(line)
			}
		}
		
		return list
	}
	
	//given a list of RST line data, potentially containing multiple hashtags
	//split the list into a new list for each hashtag present
	def splitListByHashTags(listOfLines){
		
		if (listOfLines != [])
		{
			def hashtag = listOfLines.first()
			def expandedList = []
			def text = ""
						
			listOfLines.eachWithIndex { line, index->
				
				if (line.contains(hashtag)){
					if (!text.equals("")){
						expandedList.add(text)
						text = ""
					}
				}
				else{
					text += line
					
					if (index == listOfLines.size() - 1){
						expandedList.add(text)
					}
				}
			}
			return expandedList
		}
	
		return listOfLines
	}
	
}
