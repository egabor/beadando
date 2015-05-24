package Tests;

import org.junit.Test;
import Models.Marker;
import Utilities.Constants;
import static org.junit.Assert.*;

public class MarkerTest {
	
	private Boolean isGood;

	@Test
	public void testMaker1() {
		Marker marker = new Marker(0,0);
		isGood = true;
		if (marker.hasAbove()) isGood = false;
		if (!marker.hasBelow()) isGood = false;
		if (marker.hasLeft()) isGood = false;
		if (!marker.hasRight()) isGood = false;
		assertTrue(isGood);
	}
	
	@Test
	public void testMaker2() {
		Marker marker = new Marker(Constants.kNumberOfRows-1,Constants.kNumberOfColumns-1);
		isGood = true;
		if (!marker.hasAbove()) isGood = false;
		if (marker.hasBelow()) isGood = false;
		if (!marker.hasLeft()) isGood = false;
		if (marker.hasRight()) isGood = false;
		assertTrue(isGood);
	}
	
	@Test
	public void testMaker3() {
		Marker marker = new Marker(1,1);
		isGood = true;
		if (!marker.hasAbove()) isGood = false;
		if (!marker.hasBelow()) isGood = false;
		if (!marker.hasLeft()) isGood = false;
		if (!marker.hasRight()) isGood = false;
		assertTrue(isGood);
	}
	
	@Test
	public void testMaker4() {
		Marker marker = new Marker(Constants.kNumberOfRows-1,0);
		isGood = true;
		if (!marker.hasAbove()) isGood = false;
		if (marker.hasBelow()) isGood = false;
		if (marker.hasLeft()) isGood = false;
		if (!marker.hasRight()) isGood = false;
		assertTrue(isGood);
	}
	
	

}
