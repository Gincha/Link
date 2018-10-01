package com.easyLink.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.derby.tools.sysinfo;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easyLink.database.EasyLinkDatabaseManager;


/** 
 * This application allows you to shorten your links and name them by specified Id.
 * To run the program successfully it is necessary to have MySQL database server installed on your machine.
 * User interface can be found entering "http://localhost:8080/" in your Web Browser address bar.
 * 
 * 
 * @author Kristaps
 *
 */

public class EasyLinkControllerTest {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(5);

	private Logger logger = LoggerFactory.getLogger(EasyLinkDatabaseManager.class);

	private static Connection conn = Mockito.mock(Connection.class);

	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule(); 
	
	
	@Test 
	public void testGetLink() throws SQLException, ClassNotFoundException {
		EasyLinkDatabaseManager controller = new EasyLinkDatabaseManager();
		controller.setConn(conn);
		
		String name = "labi";
		String url = "www.google.com";
		
		String name2 = " ";
		String url2 = "www.yandex.ua";
		
		PreparedStatement testPS = Mockito.mock(PreparedStatement.class);
		when(conn.prepareStatement(anyString())).thenReturn(testPS);
		doNothing().when(testPS).setString(anyInt(), anyString());
		when(testPS.executeUpdate()).thenReturn(1);
	    doNothing().when(conn).commit();
	      
	    ResultSet resSet = Mockito.mock(ResultSet.class);
        when(testPS.executeQuery()).thenReturn(resSet);
        when(resSet.next()).thenReturn(true);
        assertEquals(true, resSet.next());
        
	    controller.insertLink(name, url); 
	    controller.insertLink(name2, url2);
	    when(controller.getLink(name)).thenReturn("redirect:" + url);
	    when(controller.getLink(name2)).thenReturn("Wrong entry! Try again\n");

	    assertEquals("redirect:" + url, controller.getLink(name));
	    assertEquals("Wrong entry! Try again\n", controller.getLink(name2));
	}

}
