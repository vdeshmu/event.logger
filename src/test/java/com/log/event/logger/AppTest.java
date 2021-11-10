package com.log.event.logger;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import com.log.event.logger.exception.EventException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */

public class AppTest 
extends TestCase
{
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public AppTest( String testName )
	{
		super( testName );
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite( AppTest.class );
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp()
	{
		assertTrue( true );
	}
	public void testEventLogWithCorrectSequence() {
		String input = "logs"+File.separator+"logWithCorrectOrder.txt";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		App.eventLogger();
		assertEquals(new Double(3),  new Double(App.getEventCount()));

	}
	public void testEventLogWithIncorrectSequence() {
		String input = "logs"+File.separator+"logWithIncorrectOrder.txt";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		App.eventLogger();
	}

	/*
	 * public void testCount() { Assert.assertEquals(new Double(0), new
	 * Double(App.getEventCount())); }
	 */
	public void testEventLogWithMissingID() {
		String input = "logs"+File.separator+"logWithMissingId.txt";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		assertThrows(EventException.class, ()->{ App.eventLogger();});

	}
	public void testEventLogWithMissingTime() {
		String input = "logs"+File.separator+"logWithMissingTime.txt";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		assertThrows(EventException.class, ()->{ App.eventLogger();});

	}
	public void testEventLogWithMissingState() {
		String input = "logs"+File.separator+"logWithMissingState.txt";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		assertThrows(EventException.class, ()->{ App.eventLogger();});

	}
}
