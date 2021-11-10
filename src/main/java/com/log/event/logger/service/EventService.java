package com.log.event.logger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.log.event.logger.entity.Event;
import com.log.event.logger.entity.enums.State;
import com.log.event.logger.exception.EventException;

public class EventService {

	public static List<Event> setAlerts(List<JSONObject> rawJsonObjects) {

		return collectAlertEvents(
				extractEvents(rawJsonObjects, new Gson()).parallelStream()
				.collect(
						Collectors.groupingBy(Event::getState)
						)
				);
			
	}
	private static List<Event> extractEvents(List<JSONObject> rawJsonObjects, Gson gson) {
		List<Event> eventList = new ArrayList<>();
		rawJsonObjects.forEach(e->{
			Event event = gson.fromJson(e.toString(),Event.class);
			if(event.getState()==null) throw new EventException("State can not be empty");
			eventList.add(event);
		});
		return eventList;
	}
	private static List<Event> collectAlertEvents(final Map<State, List<Event>> eventMap) {
		List<Event> alertEvents = new ArrayList<>();
		eventMap.get(State.FINISHED).parallelStream().forEach((t)->{
			if(t.getTimeStamp()==null) throw new EventException("Timestamp field can not be null.");
			t.setTimeDifference(new Long(t.getTimeStamp())- new Long(
					eventMap.get(State.STARTED).parallelStream().filter(e->{
						if(t.getId()==null || e.getId()==null) {
							throw new EventException("ID field can not be null");
						}
						return t.getId().equalsIgnoreCase(e.getId());
					}).collect(Collectors.toList()).get(0).getTimeStamp()));
			alertEvents.add(t);
		} );
		return alertEvents;
	}

}
