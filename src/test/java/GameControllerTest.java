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
	public void testWhenRedWinRow() {
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
	public void testWhenRedWinColumn() {
		ArrayList<Marker> gameState = new ArrayList<Marker>();
		for (int i = 0; i < Constants.kNumberOfRows; i++) {
            for (int j = 0; j < Constants.kNumberOfColumns; j++) {
            	gameState.add(new Marker(i,j));
            }
		}
		for (int i = 0; i < Constants.kNumberOfRows; i++) {
			((Marker)gameState.get(i*Constants.kNumberOfColumns)).markerState = Constants.kPlayerRed;
		}
		assertEquals(null, Constants.kPlayerRed,  GameController.checkWinner(gameState));
	}
	
	@Test
	public void testWhenBlueWinRow() {
		ArrayList<Marker> gameState = new ArrayList<Marker>();
		for (int i = 0; i < Constants.kNumberOfRows; i++) {
            for (int j = 0; j < Constants.kNumberOfColumns; j++) {
            	gameState.add(new Marker(i,j));
            }
		}
		for (int i = 0; i < Constants.kNumberOfColumns; i++) {
			((Marker)gameState.get(i)).markerState = Constants.kPlayerBlue;
		}
		assertEquals(null, Constants.kPlayerBlue,  GameController.checkWinner(gameState));
	}
	
	@Test
	public void testWhenBlueWinColumn() {

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
	public void testWhenNoWinner() {
		ArrayList<Marker> gameState = new ArrayList<Marker>();
		for (int i = 0; i < Constants.kNumberOfRows; i++) {
            for (int j = 0; j < Constants.kNumberOfColumns; j++) {
            	gameState.add(new Marker(i,j));
            }
		}
		
		assertEquals(null, Constants.kPlayerNone,  GameController.checkWinner(gameState));
	}
	
	/*@Test
	public void testRedInit() {
		ArrayList<Marker> player = GameController.initRedPlayer(); 
		ArrayList<Marker> redMarkers = new ArrayList<Marker>();
        redMarkers.add(new Marker(0,0));
        redMarkers.add(new Marker(1,0));
        redMarkers.add(new Marker(2,0));
        redMarkers.add(new Marker(1,5));
        redMarkers.add(new Marker(2,5));
        redMarkers.add(new Marker(3,5));
        
        for (int i = 0; i < player.size(); i++) {
        	if (!player.get(i).equals(redMarkers.get(i))) {
        		assertTrue(false);
        	}
        }
        assertTrue(true);
	}
	
	@Test
	public void testBlueInit() {
		ArrayList<Marker> player = GameController.initBluePlayer(); 
		ArrayList<Marker> blueMarkers = new ArrayList<Marker>();
        blueMarkers.add(new Marker(3,9));
        blueMarkers.add(new Marker(2,9));
        blueMarkers.add(new Marker(1,9));
        blueMarkers.add(new Marker(2,4));
        blueMarkers.add(new Marker(1,4));
        blueMarkers.add(new Marker(0,4));
        
        for (int i = 0; i < player.size(); i++) {
        	if (!player.get(i).equals(blueMarkers.get(i))) {
        		assertTrue(false);
        	}
        }
        assertTrue(true);
	}*/
	
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
	
	@Test
	public void testFreeNeighbors3() {
		ArrayList<Marker> gameState = new ArrayList<Marker>();
		for (int i = 0; i < Constants.kNumberOfRows; i++) {
            for (int j = 0; j < Constants.kNumberOfColumns; j++) {
            	gameState.add(new Marker(i,j));
            }
		}
		((Marker)gameState.get(10)).markerState = Constants.kPlayerBlue;
		
		
		ArrayList<Marker> freeNeighbors = GameController.getFreeNeighborsForMarker(10, gameState, Constants.kPlayerBlue);
		ArrayList<Marker> testNeighbors = new ArrayList<Marker>();
		testNeighbors.add(new Marker(0,0,Constants.kFieldStateEmpty));
		testNeighbors.add(new Marker(1,1,Constants.kFieldStateEmpty));
		testNeighbors.add(new Marker(2,0,Constants.kFieldStateEmpty));
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
	
	@Test
	public void testFreeNeighbors4() {
		ArrayList<Marker> gameState = new ArrayList<Marker>();
		for (int i = 0; i < Constants.kNumberOfRows; i++) {
            for (int j = 0; j < Constants.kNumberOfColumns; j++) {
            	gameState.add(new Marker(i,j));
            }
		}
		((Marker)gameState.get(30)).markerState = Constants.kPlayerBlue;
		
		
		ArrayList<Marker> freeNeighbors = GameController.getFreeNeighborsForMarker(30, gameState, Constants.kPlayerBlue);
		ArrayList<Marker> testNeighbors = new ArrayList<Marker>();
		testNeighbors.add(new Marker(2,0,Constants.kFieldStateEmpty));
		testNeighbors.add(new Marker(3,1,Constants.kFieldStateEmpty));
		
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
	
	@Test
	public void testFreeNeighbors5() {
		ArrayList<Marker> gameState = new ArrayList<Marker>();
		for (int i = 0; i < Constants.kNumberOfRows; i++) {
            for (int j = 0; j < Constants.kNumberOfColumns; j++) {
            	gameState.add(new Marker(i,j));
            }
		}
		((Marker)gameState.get(39)).markerState = Constants.kPlayerBlue;
		
		
		ArrayList<Marker> freeNeighbors = GameController.getFreeNeighborsForMarker(39, gameState, Constants.kPlayerBlue);
		ArrayList<Marker> testNeighbors = new ArrayList<Marker>();
		testNeighbors.add(new Marker(2,9,Constants.kFieldStateEmpty));
		testNeighbors.add(new Marker(3,8,Constants.kFieldStateEmpty));
		
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
	
	@Test
	public void testFreeNeighbors6() {
		ArrayList<Marker> gameState = new ArrayList<Marker>();
		for (int i = 0; i < Constants.kNumberOfRows; i++) {
            for (int j = 0; j < Constants.kNumberOfColumns; j++) {
            	gameState.add(new Marker(i,j));
            }
		}
		((Marker)gameState.get(9)).markerState = Constants.kPlayerBlue;
		
		
		ArrayList<Marker> freeNeighbors = GameController.getFreeNeighborsForMarker(9, gameState, Constants.kPlayerBlue);
		ArrayList<Marker> testNeighbors = new ArrayList<Marker>();
		testNeighbors.add(new Marker(0,8,Constants.kFieldStateEmpty));
		testNeighbors.add(new Marker(1,9,Constants.kFieldStateEmpty));
		
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
	
	/*@Test
	public void saveLoad() {
		
		ArrayList<Marker> gameToSave = GameController.initBluePlayer(); 
		ArrayList<Marker> loadedGame = new ArrayList<Marker>();
        
        
        for (int i = 0; i < gameToSave.size(); i++) {
        	if (!gameToSave.get(i).equals(loadedGame.get(i))) {
        		assertTrue(false);
        	}
        }
        assertTrue(true);
		
	}*/
	

}
