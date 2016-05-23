package Views;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Utilities.Constants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import Controllers.GameController;

/**
 * A {@code GameScene} osztály felel a játék felületének a kinézetéért, az interakciók kezeléséért és az interakciókra reagálásért.
 * @author gaboreszenyi
 */
public class GameScene extends StackPane {

    
    /**
     * A {@code logger} változó segítségével fogunk tudni naplózni.
     */
    private static Logger logger = LoggerFactory.getLogger(GameScene.class);
    
    /**
     * A {@code gc} változón keresztül érjük el a játéklogikáért felelős függvényeket.
     */
    private GameController gc;
    
    /**
     * A {@code textLabel} címkén fog megjelenni, hogy éppen melyik játékos következik, vagy hogy ki a nyertes.
     */
    public Label textLabel;

    /**
     * A {@code resetButton} gomb alaphelyzetbe állítja vissza a játékot.
     */
    public Button resetButton;

    /**
     * A {@code saveXMLButton} gomb elmenti a jelenlegi játékállást.
     */
    public Button saveXMLButton;

    /**
     * A {@code loadXMLButton} gomb betölti az elmentett játékállást.
     */
    public Button loadXMLButton;
    
    
    /**
     * A {@code horizontalControlBox} felület fogja tartalmazni a képernyőn megjelenő gombokat.
     */
    private HBox horizontalControlBox;
	   
    /**
     * A {@code verticalContainerBox} felület fogja tartalmazni a felületen megjelenő összes elemet.
     */
    private VBox verticalContainerBox;

    
    /**
     * A {@code fields} tömb tárolja a játéktér összes mezőjét.
     */
    public ArrayList<Field> fields;
    
    
    
    /**
     * A {@code GameScene()} egy konstruktor, ami az alkalmazás indulását követően kerül meghívásra.
     */
    public GameScene() {
    	init();
    }
    
    /**
     * Az {@code init()} metódus összerakja a felületet.
     */
    private void init() {     
        this.getChildren().add(getVerticalContainerBox());
        initFields();
	}
    
    /**
     * Az {@code initialize()} metódus összefoglalja az inicializáló metódusokat.
     */
    public void initialize() {
        logger.debug("Új játék");
        initFields();   
    }
    
    /**
     * Előállítja a játék alaphelyzetbe állítására szolgáló gombot.
     * @return Visszaadja a játék alaphelyzetbe állítására szolgáló gombot.
     */
    private Button getResetButton() {
    	if (resetButton == null) {
    		resetButton = new Button();
        	resetButton.setText("Alaphelyzet");
        	resetButton.setOnAction(new EventHandler<ActionEvent>() {
     
                @Override
                public void handle(ActionEvent event) {
                	textLabel.setText("");
                    gc.reset();
                }
            });
    	}
    	return resetButton;
    }
    
    /**
     * Előállítja a játékmentésre szolgáló gombot.
     * @return Visszaadja a játékmentésre szolgáló gombot.
     */
    private Button getSaveXMLButton() {
    	if (saveXMLButton == null) {
    		saveXMLButton = new Button();
    		saveXMLButton.setText("Mentés");
    		saveXMLButton.setOnAction(new EventHandler<ActionEvent>() {
     
                @Override
                public void handle(ActionEvent event) {
                	gc.saveGame(gc.markers, gc.currentPlayer);
                }
            });
    	}
    	return saveXMLButton;
    }
    
    /**
     * Előállítja a játék betöltésére szolgáló gombot.
     * @return Visszaadja a játék betöltésére szolgáló gombot.
     */
    private Button getLoadXMLButton() {
    	if (loadXMLButton == null) {
    		loadXMLButton = new Button();
    		loadXMLButton.setText("Betöltés");
    		loadXMLButton.setOnAction(new EventHandler<ActionEvent>() {
     
                @Override
                public void handle(ActionEvent event) {
                	gc.loadGame();
                }
            });
    	}
    	return loadXMLButton;
    }
    
    /**
     * Előállítja az összes felületi elemet tartalmazó elemet.
     * @return Visszaadja az összes felületi elemet tartalmazó {@code VBox}-ot.
     */
    private VBox getVerticalContainerBox() {
    	if (verticalContainerBox == null) {
    		verticalContainerBox = new VBox(getTextLabel(),getHorizontalControlBox());
    		verticalContainerBox.setAlignment(Pos.TOP_CENTER);
    		verticalContainerBox.setSpacing(15.0f);
    		horizontalControlBox.setAlignment(Pos.TOP_CENTER);    		
    	}
    	return verticalContainerBox;
    }
    
    /**
     * Előállítja a gombokat tartalmazó felületi elemet.
     * @return Visszaadja a gonbokat tartalmazó {@code HBox}-ot.
     */
    private HBox getHorizontalControlBox() {
    	if (horizontalControlBox == null) {
    		horizontalControlBox = new HBox(getResetButton(),getSaveXMLButton(),getLoadXMLButton());
    		horizontalControlBox.setSpacing(5.0f);
    	}
    	return horizontalControlBox;
    }

    /**
     * Előállítja a címkét, ami a játékállást fogja jelezni.
     * @return Visszaad egy címkét.
     */
    private Label getTextLabel() {
    	if (textLabel == null) {
    		textLabel = new Label();
    		textLabel.setText("új");
    	}
    	return textLabel;
    }
    
    
    
    
    
    
    
    
    
    
    /**
     * Az {@code initFields()} metódus a kattintható mezők inicializálását végzi.
     */
    public void initFields() {
        fields = new ArrayList<Field>();
        
        for (int i = 0; i < Constants.kNumberOfRows; i++) {
        	HBox fieldBox = new HBox();
        	fieldBox.setSpacing(5.0f);
            for (int j = 0; j < Constants.kNumberOfColumns; j++) {
                final Field field = new Field(i,j);
                
                field.setStyle("-fx-background-image: url('"+getClass().getClassLoader().getResource(Constants.kEmptyFieldImageName)+"')");
                field.setMinSize(Constants.kElementSize, Constants.kElementSize); 
                field.setMaxSize(Constants.kElementSize, Constants.kElementSize);

                field.setOnAction(new EventHandler<ActionEvent>() {
                    
                    @Override
                    public void handle(ActionEvent event) {
                    	gc.buttonPressed(field.marker);
                    }
                });
                field.marker.markerState = Constants.kFieldStateEmpty;
                fields.add(field);
                fieldBox.getChildren().add(0,field);
            }
            fieldBox.setAlignment(Pos.TOP_CENTER);
            
            verticalContainerBox.getChildren().add(fieldBox);
        }
        //containerPanel.add(fieldContainerPanel,BorderLayout.SOUTH);

    }
    
    
    /**
     * A {@code changeIcon()} metódus a játéktér egy elemének beállítja a hátterét a megadott paraméterek szerint, illetve változtatja a játéktér egy eleméhez tartozó markernek az állapotát.
     * @param index	A játéktér elemének az azonosítója.
     * @param iconName	A megjeleníteni kívánt kép neve.
     * @param fieldState	A beállítani kívánt új állapot
     */
    public void changeField(int index, String iconName, int fieldState) {
        Field fieldToChange = fields.get(index);
        fieldToChange.setStyle("-fx-background-image: url('"+getClass().getClassLoader().getResource(iconName)+"')");
        fieldToChange.marker.markerState = fieldState;
        /*fieldContainerPanel.revalidate();
        fieldContainerPanel.repaint();*/
    }
    
    /**
     * A {@code changeIcon()} metódus a játéktér egy elemének beállítja a hátterét a megadott paraméterek szerint.
     * @param index	A játéktér elemének az azonosítója.
     * @param iconName	A megjeleníteni kívánt kép neve.
     */
    public void changeIcon(int index, String iconName) {
        Field fieldToChange = fields.get(index);
        fieldToChange.setStyle("-fx-background-image: url('"+getClass().getClassLoader().getResource(iconName)+"')");
        /*fieldContainerPanel.revalidate();
        fieldContainerPanel.repaint();*/

    }
    
    /**
     * A {@code setTitle()} metódus a {@code textLabel}en megjelenő szöveget állítja a bemenő paraméter szerint.
     * @param	text	Az a szöveg, ami a {@code textLabel}en fog megjelenni.
     */
    public void setTitle(String text) {
        textLabel.setText(text);
    }
    
    /**
     * Beállítja a view-nak a játéklogikáért felelős controllert.
     * @param gameController A játéklogikáért felelős controller.
     */
    public void setGC(GameController gameController) {
    	this.gc = gameController;
    }
    
    

}
