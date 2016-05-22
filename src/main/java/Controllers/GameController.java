package Controllers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Views.GameScene;
import Models.Marker;
import Utilities.Constants;
import Utilities.XML;

 
/**
 * A {@code GameController} osztályban foglal helyet a játéklogika.
 * Itt lesznek feldolgozva a felhasználói interakciók és innen lesz módosítva a felhasználói felület és a játéktér is.
 * @author gaboreszenyi
 */
public class GameController extends Application {
	
	/**
     * A {@code logger} változó segítségével fogunk tudni naplózni.
     */
    private static Logger logger = LoggerFactory.getLogger(GameController.class);
    
    /**
     * A {@code gs} változó segítségével érhetjük el azokat a metódusokat, amelyekkel a játéktér kinézete módosítható.
     */
    static GameScene gs;
    
    /**
     * A {@code markers} tömb tárolja a játéktér összes mezőjét.
     */
    public ArrayList<Marker> markers;
    
    /**
     * A {@code freeMarkers} tömb tárolja azokat a mezőket, ahova lépni lehet.
     */
    public ArrayList<Marker> freeMarkers;
    
    /**
     * A {@code lastMarker} változó tárolja a jelenleg aktív játékos által utoljára kattintott mezőt.
     */
    public Marker lastMarker;
    
    /**
     * A {@code currentPlayer} változó tárjolja a jelenleg aktív játékost.
     */
    public static int currentPlayer = Constants.kPlayerNone;
    
    
	
    //TODO dokumentálás
    public static void main(String[] args) {
        launch(args);
    }
    
    //TODO dokumentálás
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        logger.debug("Controller felépítése.");
        gs = new GameScene();
        gs.setGC(this);

        primaryStage.setScene(new Scene(gs, 700, 400));
        primaryStage.show();
        
        freeMarkers = new ArrayList<Marker>();
        lastMarker = null; 
        initEmptyMarkers();
        initBluePlayer();
        initRedPlayer();
        currentPlayer = changePlayer(Constants.kPlayerNone);
    }
    
    //TODO dokumentálás
    public void reset() {
    	freeMarkers = new ArrayList<Marker>();
        lastMarker = null; 
        initEmptyMarkers();
        initBluePlayer();
        initRedPlayer();
        currentPlayer = changePlayer(Constants.kPlayerNone);
    }
    
    /**
     * A {@code GameController()} egy konstruktor, ami egy {@code GameScene} típusú paramétert vár az inicializáláshoz.
     * @param scene	{@code GameScene} típusú. A {@code scene} paraméter a felhasználói felületért felel.
     */
    /*public GameController(GameScene scene) {
        
    }*/
    
    /**
     * A {@code initEmptyMarkers()} metódus az üres {@code Marker}eket fogja inicializálni.
     */
    public void initEmptyMarkers() {
        if (markers != null) markers.clear();
        markers = new ArrayList<Marker>();
        for (int i = 0; i < Constants.kNumberOfRows; i++) {
            for (int j = 0; j < Constants.kNumberOfColumns; j++) {
                Marker marker = new Marker(i,j,Constants.kFieldStateEmpty);
                markers.add(marker);
                modifyFieldImageAndState(marker,
                                         Constants.kEmptyFieldImageName,
                                         Constants.kFieldStateEmpty);
            }
        }
    }
    
    /**
     * Az {@code initBluePlayer()} metódus a kék játékos inicializálásáért felel.
     */
    public void initBluePlayer() {
        logger.debug("Kék játékos betöltése:");
        ArrayList<Marker> blueMarkers = new ArrayList<Marker>();
        blueMarkers.add(new Marker(3,9));
        blueMarkers.add(new Marker(2,9));
        blueMarkers.add(new Marker(1,9));
        blueMarkers.add(new Marker(2,4));
        blueMarkers.add(new Marker(1,4));
        blueMarkers.add(new Marker(0,4));
        
        for (int i = 0; i < blueMarkers.size(); i++) {
            Marker blueMarker = blueMarkers.get(i);
            Marker marker = markers.get(blueMarker.getIndex());
            ((Marker)markers.get(marker.getIndex())).markerState = Constants.kFieldStateBlue;

            
            logger.debug("(" + marker.row + ", " + marker.column + ")");
            modifyFieldImageAndState(marker,
                                     Constants.kBlueFieldImageName,
                                     Constants.kFieldStateBlue);
        }
        logger.debug("hely(ek)re.");
    }
    
    /**
     * Az {@code initRedPlayer()} metódus a piros játékos inicializálásáért felel.
     */
    public void initRedPlayer() {
        logger.debug("Kék játékos betöltése:");
        ArrayList<Marker> redMarkers = new ArrayList<Marker>();
        redMarkers.add(new Marker(0,0));
        redMarkers.add(new Marker(1,0));
        redMarkers.add(new Marker(2,0));
        redMarkers.add(new Marker(1,5));
        redMarkers.add(new Marker(2,5));
        redMarkers.add(new Marker(3,5));
        
        for (int i = 0; i < redMarkers.size(); i++) {
            Marker redMarker = redMarkers.get(i);
            Marker marker = markers.get(redMarker.getIndex());
            ((Marker)markers.get(marker.getIndex())).markerState = Constants.kFieldStateRed;
            logger.debug("(" + marker.row + ", " + marker.column + ")");
            modifyFieldImageAndState(marker,
                                     Constants.kRedFieldImageName,
                                     Constants.kFieldStateRed);
        }
        logger.debug("hely(ek)re.");
    }
    
    /**
     * A játéktér egy mezőjére kattintva fog végrehajtódni ez a metódus, ahol vizsgálni fogjuk, hogy a kattintott mezőnek vannak-e elérhető szomszédjai, valamint itt fogunk dönteni a mozgatásról is.
     * @param ae {@code ActionEvent} típusú. Ebből fog kiderülni, hogy melyik mezőre kattintottunk.
     */
    public void buttonPressed(Marker pressedMarker) {
        //Marker pressedMarker = (Marker)((Field)event.getSource()).marker;
        
        String filename = Constants.kSelectableFieldImageName;
        String playerLogString = null;
        if (currentPlayer == Constants.kPlayerBlue) {
            filename = Constants.kBlueFieldImageName;
            playerLogString = "Kék";
        } else if (currentPlayer == Constants.kPlayerRed) {
            filename = Constants.kRedFieldImageName;
            playerLogString = "Piros";
        }
        
        Boolean willMove = false;
        int winner = Constants.kPlayerNone;
        
        if (!freeMarkers.isEmpty()) {
            // Ez az ág hajtódik végre, ha a játékos már választott egy mezőt ahonnan lépni akar.
            for (int i = 0; i <freeMarkers.size(); i++) {
                Marker marker = freeMarkers.get(i);
                modifyFieldImage(marker, Constants.kEmptyFieldImageName);
                if (pressedMarker.getIndex() == marker.getIndex()) {
                    willMove = true;
                    if (lastMarker != null) {
                        Marker markerToEmpty = markers.get(lastMarker.getIndex());
                        
                        pressedMarker.markerState = currentPlayer;
                        modifyFieldImageAndState(pressedMarker, filename, pressedMarker.markerState);
                        
                        markerToEmpty.markerState = Constants.kFieldStateEmpty;
                        modifyFieldImageAndState(markerToEmpty, Constants.kEmptyFieldImageName, markerToEmpty.markerState);
                        
                        logger.debug(playerLogString + " játékos kiválasztja, hogy hova lép. ("+pressedMarker.row+", "+pressedMarker.column+")");
                        
                        
                        
                        winner = checkWinner(markers);
                        if (winner == Constants.kPlayerRed || winner == Constants.kPlayerBlue) {
                            // Nyertes esetén letiltjuk, hogy ne lehessen mozogni.
                            currentPlayer = Constants.kPlayerNone;
                        }
                        
                    }
                }
                
            }
            if (!willMove) {
                // Ha a játékos MEGVÁLTOZTATJA, hogy HONNAN akar lépni.
                // Nem szabad mezőt választott.
                freeMarkers = getFreeNeighborsForMarker(pressedMarker.getIndex(), markers, currentPlayer);
                for (int i = 0; i <freeMarkers.size(); i++) {
                    Marker freeMarker = freeMarkers.get(i);
                    modifyFieldImage(freeMarker, Constants.kSelectableFieldImageName);
                }
                
                if (pressedMarker.markerState == currentPlayer)
                    logger.debug(playerLogString + " játékos kiválasztja, hogy honnan lép. ("+pressedMarker.row+", "+pressedMarker.column+")");
                
            } else {
                // Ha a játékos KIVÁLASZTJA, hogy HOVA akar lépni.
                lastMarker = null;
                freeMarkers.clear();
                // Ha van nyertes, akkor kíirjuk.
                if (winner != Constants.kPlayerNone) {
                    if (winner == Constants.kPlayerRed ) {
                        modifyViewTitle(Constants.kWinnerRedText);
                    } else if (winner == Constants.kPlayerBlue ) {
                        modifyViewTitle(Constants.kWinnerBlueText);
                    }
                    logger.debug(playerLogString + " játékos nyert. (Játék vége.)");
                } else {
                    currentPlayer = changePlayer(currentPlayer);
                }
                return;
            }
        } else {
            // Ha a játékos KIVÁLASZTJA, hogy HONNAN akar lépni.
            // Kijelöljük a szabad mezőket.
            freeMarkers = getFreeNeighborsForMarker(pressedMarker.getIndex(), markers, currentPlayer);
            for (int i = 0; i <freeMarkers.size(); i++) {
                Marker freeMarker = freeMarkers.get(i);
                modifyFieldImage(freeMarker, Constants.kSelectableFieldImageName);
            }
            if (pressedMarker.markerState == currentPlayer)
                logger.debug(playerLogString + " játékos kiválasztja, hogy honnan lép. ("+pressedMarker.row+", "+pressedMarker.column+")");
        }
        
        
        if (!willMove) {
            // Ha a játékos KIVÁLASZTJA, hogy HONNAN akar lépni.
            // Eltároljuk, mert ha lép, akkor ezt a mezőt üres állapotúra kell állítani.
            lastMarker = pressedMarker;
        }
        
    }
    
    /**
     * A {@code changePlayer()} metódus dönti el, hogy melyik játékos következik.
     * @param current	A jelenlegi játékos értéke.
     * @return	Az új játékos értékével tér vissza.
     */
    public static int changePlayer(int current) {
        if (current == Constants.kPlayerBlue) {
            modifyViewTitle(Constants.redTurnText);
            return Constants.kPlayerRed;
        } else if (current == Constants.kPlayerRed) {
            modifyViewTitle(Constants.blueTurnText);
            return Constants.kPlayerBlue;
        }
        modifyViewTitle(Constants.blueTurnText);
        return Constants.kPlayerBlue;
    }
    
    
    /**
     * A {@code getFreeNeighborsForMarker()} függvény a kattintott mező szabad szomszédait keresi meg és azzal tér vissza.
     * @param index	A kattintott mező indexe.
     * @param currentGameState	A játéktér jelenlegi állapota.
     * @param player	Az aktuális játékos.
     * @return	Azoknak a mezőknek a listájával tér vissza, melyekre lehet lépni.
     */
    public static ArrayList<Marker> getFreeNeighborsForMarker(int index, ArrayList<Marker> currentGameState, int player) {
        ArrayList<Marker> free = new ArrayList<Marker>();
        Marker marker = currentGameState.get(index);
        if (marker.markerState != player) {
            return free;
        }
        Marker neighbor;
        if (marker.hasAbove()) {
            neighbor = currentGameState.get(getMarkerIndex(marker.row-1,marker.column));
            if (neighbor.markerState == Constants.kFieldStateEmpty) {
                free.add(neighbor);
            }
        }
        if (marker.hasBelow()) {
            neighbor = currentGameState.get(getMarkerIndex(marker.row+1,marker.column));
            if (neighbor.markerState == Constants.kFieldStateEmpty) {
                free.add(neighbor);
            }
        }
        if (marker.hasRight()) {
            neighbor = currentGameState.get(getMarkerIndex(marker.row,marker.column+1));
            if (neighbor.markerState == Constants.kFieldStateEmpty) {
                free.add(neighbor);
            }
        }
        if (marker.hasLeft()) {
            neighbor = currentGameState.get(getMarkerIndex(marker.row,marker.column-1));
            if (neighbor.markerState == Constants.kFieldStateEmpty) {
                free.add(neighbor);
            }
        }
        return free;
    }
    
    /**
     * A {@code getMarkerIndex()} függvény a játéktér egy mezőjének indexét állítja elő.
     * @param row	A játéktér egy sorának sorszáma.
     * @param column	A játéktér egy oszlopának sorszáma.
     * @return	A sorszámból és oszlopszámból előállított index, amellyel egy mezőt egyértelműen tudunk azonosítani.
     */
    public static int getMarkerIndex(int row, int column) {
        return row*Constants.kNumberOfColumns+column;
    }
    
    /**
     * A {@code checkWinner()} függvény ellenőrzi, hogy nyert-e valamelyik játékos.
     * @param currentGameState	A játéktér jelenlegi állapota.
     * @return Visszatér a győztes játékossal.
     */
    public static int checkWinner(ArrayList<Marker> currentGameState) {
        
        int index = 0;
        int redCounter = 0;
        int blueCounter = 0;
        // Sorok ellenőrzése
        for (int i = 0; i < Constants.kNumberOfRows; i++) {
            redCounter = 0;
            blueCounter = 0;
            for (int j = 0; j < Constants.kNumberOfColumns; j++) {
                index = i*Constants.kNumberOfColumns+j;
                Marker marker = currentGameState.get(index);
                if (marker.markerState == Constants.kFieldStateRed) {
                    redCounter++;
                    if (redCounter == 4) {
                        return Constants.kPlayerRed;
                    }
                } else redCounter = 0;
                
                if (marker.markerState == Constants.kFieldStateBlue) {
                    blueCounter++;
                    if (blueCounter == 4) {
                        return Constants.kPlayerBlue;
                    }
                } else blueCounter = 0;
            }
        }
        
        
        // Oszlopok ellenőrzése
        for (int i = 0; i < Constants.kNumberOfColumns; i++) {
            redCounter = 0;
            blueCounter = 0;
            for (int j = 0; j < Constants.kNumberOfRows; j++) {
                index = j*Constants.kNumberOfColumns+i;
                Marker marker = currentGameState.get(index);
                if (marker.markerState == Constants.kFieldStateRed) {
                    redCounter++;
                    if (redCounter == 4) {
                        return Constants.kPlayerRed;
                    }
                } else redCounter = 0;
                
                if (marker.markerState == Constants.kFieldStateBlue) {
                    blueCounter++;
                    if (blueCounter == 4) {
                        return Constants.kPlayerBlue;
                    }
                } else blueCounter = 0;
            }
        }
        return Constants.kPlayerNone;
    }
    
    /**
     * A {@code saveGame()} metódus végzi el a játék mentését.
     * @param option Itt lehet megadni, hogy az adatokat xml-be legyenek mentve vagy egy távoli adatbázisba.
     * @throws SQLException 
     */
    public void saveGame(int option) {
        logger.debug("Játék mentése.");
        ArrayList<Marker> markersToSave = new ArrayList<Marker>();
        for (int i = 0; i < markers.size(); i++) {
            Marker marker = markers.get(i);
            if (marker.markerState == currentPlayer) {
                markersToSave.add(marker);
            }
        }
        currentPlayer = changePlayer(currentPlayer);
        for (int i = 0; i < markers.size(); i++) {
            Marker marker = markers.get(i);
            if (marker.markerState == currentPlayer) {
                markersToSave.add(marker);
            }
        }
        currentPlayer = changePlayer(currentPlayer);
        
        if (option == Constants.kSaveLoadOptionJDBC) {
        	//JDBC jdbcManager = new JDBC(Constants.tableName);
        	//jdbcManager.saveGame(markersToSave);
        } else if (option == Constants.kSaveLoadOptionXML) {
            XML.saveGame(markersToSave);
        }
        
        
    }
    
    /**
     * A {@code loadGame()} metódus végzi el az elmentett játék betöltését.
     * @param option Itt lehet megadni, hogy az adatokat xml-ből legyenek betöltve vagy egy távoli adatbázisból.
     * @throws SQLException 
     */
    public void loadGame(int option) {
        logger.debug("Játék betöltése az adatbázisból.");
        
        ArrayList<Marker> loadedMarkers = new ArrayList<Marker>();
        
        if (option == Constants.kSaveLoadOptionJDBC) {
        	//JDBC jdbcManager = new JDBC(Constants.tableName);
            //loadedMarkers = jdbcManager.loadGame();
        } else if (option == Constants.kSaveLoadOptionXML) {
            loadedMarkers = XML.loadGame();
        }
        // Ha nem kapjuk vissza mindet, akkor ne tegyük tönkre a felületet.
        if (loadedMarkers.size() != 12) {
        	logger.warn("Nem sikerült betölteni a játékállást.");
        	return;
        }
        logger.debug("Sikeres betöltés.");
        
        for (int i = 0; i < markers.size(); i++) {
            Marker marker = markers.get(i);
            marker.markerState = Constants.kFieldStateEmpty;
            modifyFieldImage(marker, Constants.kEmptyFieldImageName);
        }
        
        freeMarkers = new ArrayList<Marker>();
        lastMarker = null;
        
        initEmptyMarkers();
        for (int i = 0; i < loadedMarkers.size(); i++) {
            Marker marker = loadedMarkers.get(i);
            if (i == loadedMarkers.size()-1) currentPlayer = marker.markerState;
            ((Marker)markers.get(marker.getIndex())).markerState = marker.markerState;
            
            modifyFieldImage(marker, null);
        }
        currentPlayer = changePlayer(currentPlayer);
    }
    
    /**
     * A {@code modifyViewTitle()} metódus segítségével lehet módosítani a játéktér címkéjén megjelenő szöveget.
     * @param title A címkén megjelenő új szöveg.
     */
    public static void modifyViewTitle(String title) {
        if (gs != null) {
            gs.setTitle(title);
            logger.debug(title);
        }
    }
    
    /**
     * A {@code modifyFieldImage()} metódus segítségével lehet módosítani a játéktér egy {@code Field}jének a kinézetét.
     * @param marker A módosítani kívánt {@code Field}hez tartozó {@code Marker}.
     * @param imageName Az új kép elérése.
     */
    public void modifyFieldImage(Marker marker, String imageName) {
        if (gs != null) {
            if (imageName != null) {
                gs.changeIcon(marker.getIndex(),  imageName);
            } else {
                gs.changeIcon(marker.getIndex(), (marker.markerState == Constants.kPlayerBlue ? Constants.kBlueFieldImageName : Constants.kRedFieldImageName));
            }
        }
    }
    
    /**
     * A {@code modifyFieldImageAndState()} metódus segítségével lehet módosítani a játéktér egy {@code Field}jének a kinézetét.
     * @param marker A módosítani kívánt {@code Field}hez tartozó {@code Marker}.
     * @param imageName Az új kép elérése.
     * @param state Az új állapot.
     */
    public void modifyFieldImageAndState(Marker marker, String imageName, int state) {
        ((Marker)markers.get(marker.getIndex())).markerState = state;
        if (gs != null) {
            if (imageName != null) {
                gs.changeField(marker.getIndex(),  imageName, state);
            } else {
                gs.changeField(marker.getIndex(), (marker.markerState == Constants.kPlayerBlue ? Constants.kBlueFieldImageName : Constants.kRedFieldImageName), state);
            }
        }
    }
}
