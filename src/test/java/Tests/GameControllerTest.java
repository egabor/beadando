package Tests;

import java.util.ArrayList;

import org.junit.Test;

import Controllers.GameController;
import Models.Marker;
import Utilities.Constants;
import static org.junit.Assert.*;

public class GameControllerTest {

	@Test
	public void testPlayerChangeRedToBlue() {
		assertEquals(null, Constants.kPlayerBlue,  GameController.changePlayer(Constants.kPlayerRed));
	}
	
	@Test
	public void testPlayerChangeBlueToRed() {
		assertEquals(null, Constants.kPlayerRed,  GameController.changePlayer(Constants.kPlayerBlue));
	}
	
	@Test
	public void testPlayerChangeNoneToBlue() {
		assertEquals(null, Constants.kPlayerBlue,  GameController.changePlayer(Constants.kPlayerNone));
	}
	
	@Test
	public void testMarkerIndex() {
		assertEquals(null, GameController.getMarkerIndex(2,9), 29 );
	}
	
	@Test
	public void testWhenRedWin() {
		ArrayList<Marker> gameState = new ArrayList<Marker>();
		for (int i = 0; i < Constants.kNumberOfRows; i++) {
            for (int j = 0; j < Constants.kNumberOfColumns; j++) {
            	gameState.add(new Marker(i,j));
            }
		}
		for (int i = 0; i < Constants.kNumberOfColumns; i++) {
			((Marker)gameState.get(i)).markerState = Constants.kPlayerRed;
		}
		assertEquals(null, Constants.kPlayerRed,  GameController.checkWinner(gameState));
	}
	
	@Test
	public void testWhenBlueWin() {
		ArrayList<Marker> gameState = new ArrayList<Marker>();
		for (int i = 0; i < Constants.kNumberOfRows; i++) {
            for (int j = 0; j < Constants.kNumberOfColumns; j++) {
            	gameState.add(new Marker(i,j));
            }
		}
		for (int i = 0; i < Constants.kNumberOfRows; i++) {
			((Marker)gameState.get(i*Constants.kNumberOfColumns)).markerState = Constants.kPlayerBlue;
		}
		assertEquals(null, Constants.kPlayerBlue,  GameController.checkWinner(gameState));
	}
	
	@Test
	public void testFreeNeighbors1() {
		ArrayList<Marker> gameState = new ArrayList<Marker>();
		for (int i = 0; i < Constants.kNumberOfRows; i++) {
            for (int j = 0; j < Constants.kNumberOfColumns; j++) {
            	gameState.add(new Marker(i,j));
            }
		}
		((Marker)gameState.get(0)).markerState = Constants.kPlayerBlue;
		
		
		ArrayList<Marker> freeNeighbors = GameController.getFreeNeighborsForMarker(0, gameState, Constants.kPlayerBlue);
		ArrayList<Marker> testNeighbors = new ArrayList<Marker>();
		testNeighbors.add(new Marker(0,1,Constants.kFieldStateEmpty));
		testNeighbors.add(new Marker(1,0,Constants.kFieldStateEmpty));


		if (testNeighbors.size() != freeNeighbors.size()) { 
			assertTrue(false);
		}
		
		
		for (int i = 0; i < testNeighbors.size(); i++) {
			Marker m1 = testNeighbors.get(i);
			Boolean isEqual = false;
			for (int j = 0; j < testNeighbors.size(); j++) {
				Marker m2 = freeNeighbors.get(j);
				if (m1.equals(m2)) {
					isEqual = true;
				}
			}
			if (isEqual == false) {
				assertTrue(false);
			}
		}
		assertTrue(true);
	}
	
	@Test
	public void testFreeNeighbors2() {
		ArrayList<Marker> gameState = new ArrayList<Marker>();
		for (int i = 0; i < Constants.kNumberOfRows; i++) {
            for (int j = 0; j < Constants.kNumberOfColumns; j++) {
            	gameState.add(new Marker(i,j));
            }
		}
		((Marker)gameState.get(15)).markerState = Constants.kPlayerBlue;
		
		
		ArrayList<Marker> freeNeighbors = GameController.getFreeNeighborsForMarker(15, gameState, Constants.kPlayerBlue);
		ArrayList<Marker> testNeighbors = new ArrayList<Marker>();
		testNeighbors.add(new Marker(0,5,Constants.kFieldStateEmpty));
		testNeighbors.add(new Marker(1,4,Constants.kFieldStateEmpty));
		testNeighbors.add(new Marker(1,6,Constants.kFieldStateEmpty));
		testNeighbors.add(new Marker(2,5,Constants.kFieldStateEmpty));
		//System.out.println(testNeighbors.size() +", "+freeNeighbors.size());
		if (testNeighbors.size() != freeNeighbors.size()) { 
			assertTrue(false);
		}
		
		for (int i = 0; i < testNeighbors.size(); i++) {
			Marker m1 = testNeighbors.get(i);
			Boolean isEqual = false;
			for (int j = 0; j < testNeighbors.size(); j++) {
				Marker m2 = freeNeighbors.get(j);
				//System.out.println(j);
				if (m1.equals(m2)) {
					isEqual = true;
				}
			}
			if (isEqual == false) {
				assertTrue(false);
			}
		}
		assertTrue(true);
	}
	
}
