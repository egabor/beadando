package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Models.Marker;
import Utilities.Constants;
import Utilities.JDBC;

public class JDBCTest {
	
	public String testTableName = "TESTTABLE";

	
	@Test
	public void databaseTest1() {
        ArrayList<Marker> markersToSave = new ArrayList<Marker>();
        ArrayList<Marker> loadedMarkers = new ArrayList<Marker>();
        
        for (int i = 0; i < Constants.kNumberOfRows; i++) {
            for (int j = 0; j < Constants.kNumberOfColumns; j++) {
                Marker marker = new Marker(i,j,Constants.kFieldStateEmpty);
                markersToSave.add(marker);
            }
        }

		JDBC jdbcManager = new JDBC(testTableName);
    	jdbcManager.saveGame(markersToSave);
        loadedMarkers = jdbcManager.loadGame();
        if (markersToSave.size() != loadedMarkers.size()) {
        	assertTrue(false);
        	return;
        }
        
        for (int i = 0; i < markersToSave.size(); i++) {
        	Marker marker = markersToSave.get(i);
        	if (!marker.equals(loadedMarkers.get(i))) {
        		assertTrue(false);
        		return;
        	}
        }
        assertTrue(true);
	}
	
	@Test
	public void databaseTest2() {
        ArrayList<Marker> markersToSave = new ArrayList<Marker>();
        ArrayList<Marker> loadedMarkers = new ArrayList<Marker>();
        
        for (int i = 0; i < Constants.kNumberOfRows; i++) {
            for (int j = 0; j < Constants.kNumberOfColumns; j++) {
                Marker marker = new Marker(i,j,Constants.kFieldStateEmpty);
                markersToSave.add(marker);
            }
        }
        if (markersToSave.size() >= 30 ) {
        	((Marker) markersToSave.get(0)).markerState = Constants.kFieldStateBlue;
        	((Marker) markersToSave.get(11)).markerState = Constants.kFieldStateRed;
        	((Marker) markersToSave.get(22)).markerState = Constants.kFieldStateEmpty;
        	((Marker) markersToSave.get(20)).markerState = Constants.kFieldStateRed;
        	((Marker) markersToSave.get(15)).markerState = Constants.kFieldStateBlue;
        	((Marker) markersToSave.get(8)).markerState = Constants.kFieldStateBlue;
        }

		JDBC jdbcManager = new JDBC(testTableName);
    	jdbcManager.saveGame(markersToSave);
        loadedMarkers = jdbcManager.loadGame();
        if (markersToSave.size() != loadedMarkers.size()) {
        	assertTrue(false);
        	return;
        }
        
        for (int i = 0; i < markersToSave.size(); i++) {
        	Marker marker = markersToSave.get(i);
        	if (!marker.equals(loadedMarkers.get(i))) {
        		assertTrue(false);
        		return;
        	}
        }
        assertTrue(true);
	}
}
