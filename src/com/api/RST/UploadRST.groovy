package com.api.RST

class UploadRST {

	static main(args)
	{
		//def file = new File("c:\\dev\\RST_10_14_2013 SW-6457.txt")
		//TODO pull directory from config file
		def file = new File("C:\\dev\\RSTs\\")
		if (file.isDirectory())
		{
			file.eachFile { localRSTFile ->
				def rst = new RST()
				rst.uploadToDB(localRSTFile.text)
				
			}
		}
		else if (file.isFile())
		{
			def rst = new RST()
			rst.uploadToDB(file.text)
		}
			
	}	

}
