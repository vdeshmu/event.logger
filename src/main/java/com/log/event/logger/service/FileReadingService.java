package com.log.event.logger.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.log.event.logger.exception.EventException;

public class FileReadingService {
	static Logger logger = Logger.getLogger(FileReadingService.class.getName());

	@SuppressWarnings("unchecked")
	public List<JSONObject> scanFile() {
		Object rawObject = null;
		String filePath = "";
		try {
			System.out.println("Please enter the log file path	:	");
			filePath = new BufferedReader(new InputStreamReader(System.in)).readLine();//"C:\\CREDIT\\log.txt";
			logger.info("file path : "+filePath);
			JSONParser parser = new JSONParser();
			logger.info("Scanning input file for events");
			rawObject = parser.parse(new FileReader(filePath));
		} catch (IOException | ParseException e) {
			logger.info("Failed to read event log input file from "+filePath);			
			EventException ee = new EventException("Failed to read even log input file from "+filePath,e);
			//ee.printStackTrace();
			throw ee;
		}

		return (List<JSONObject>)rawObject;
	}

}
