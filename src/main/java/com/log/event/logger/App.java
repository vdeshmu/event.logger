package com.log.event.logger;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import com.log.event.logger.entity.Event;
import com.log.event.logger.exception.EventException;
import com.log.event.logger.service.EventService;
import com.log.event.logger.service.FileReadingService;

/**
 * Hello world!
 *C:\CREDIT\log.txt
 */
public class App 
{
	private static final Logger logger = Logger.getLogger(App.class.getName());

	public static void main( String[] args ) throws IOException, EventException
	{
		logger.info("Application started...");

		eventLogger();
	}

	/**
	 * @ 
	 * 
	 */
	public static void eventLogger()  {

		FileReadingService service = new FileReadingService();
		List<JSONObject>  rawJsonObjects = service.scanFile();

		List<Event> alertEvents = 	EventService.setAlerts(rawJsonObjects);

		logger.info(alertEvents.toString());

		EventsRepo.save(alertEvents);

	}
	public static int getEventCount()  {
		return EventsRepo.eventCount();
	}
}
