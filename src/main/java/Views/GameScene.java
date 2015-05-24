package Views;



import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Utilities.Constants;
import Controllers.GameController;
import Models.Marker;

/**
 * A {@code GameScene} osztály felel a játék felületének a kinézetéért, az interakciók kezeléséért és az interakciókra reagálásért.
 * @author gaboreszenyi
 */
public class GameScene extends JFrame {
    
    /**
     * A {@code logger} változó segítségével fogunk tudni naplózni.
     */
    private static Logger logger = LoggerFactory.getLogger(GameScene.class);
    
    /**
     * A {@code gc} változón keresztül érjük el a játéklogikáért felelős függvényeket.
     */
    private GameController gc;
    
    /**
     * A {@code textLabel} címkán fog megjelenni, hogy éppen melyik játékos következik, vagy hogy ki a nyertes.
     */
    public JLabel textLabel;
    
    /**
     * A {@code resetButton} gomb alaphelyzetbe állítja vissza a játékot.
     */
    public JButton resetButton;
    
    /**
     * A {@code saveXMLButton} gomb elmenti a jelenlegi játékállást.
     */
    public JButton saveXMLButton;
    
    /**
     * A {@code loadXMLButton} gomb betölti az elmentett játékállást.
     */
    public JButton loadXMLButton;
    
    /**
     * A {@code saveButton} gomb elmenti a jelenlegi játékállást.
     */
    public JButton saveButton;
    
    /**
     * A {@code loadButton} gomb betölti az elmentett játékállást.
     */
    public JButton loadButton;
    
    /**
     * A {@code controlContainerPanel} felület fogja tartalmazni a {@code textLabel}t és a {@code resetButton}t.
     */
    public JPanel controlContainerPanel;
	   
    /**
     * A {@code containerPanel} felület fogja tartalmazni a felületen megjelenő összes elemet.
     */
    private JPanel containerPanel;
    
    /**
     * A {@code fields} tömb tárolja a játéktér összes mezőjét.
     */
    public ArrayList<Field> fields;
    
    /**
     * A {@code fieldContainerPanel} felület a játéktér mezőit fogja rattalmazni.
     */
    public JPanel fieldContainerPanel;
    
    
    
    /**
     * A {@code GameScene()} egy konstruktor, ami az alkalmazás indulását követően kerül meghívásra.
     * @param	title	Az ablak tetején megjelenő szöveg.
     */
    public GameScene(String title) {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    /**
     * Az {@code initialize()} metódus összefoglalja az inicializáló metódusokat.
     */
    public void initialize() {
        logger.debug("Új játék");
        initView();
        initFields();
        
        gc = new GameController(this);
        
        setContentPane(containerPanel);
        pack();
        setVisible(true);
    }
    
    /**
     * Az {@code initView()} metódus a felületen szereplő vezérlőelemek inicializálását végzi.
     */
    public void initView() {
        textLabel = new JLabel("", JLabel.CENTER);
        
        containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        
        fieldContainerPanel = new JPanel();
        fieldContainerPanel.setLayout(new GridLayout(Constants.kNumberOfRows, Constants.kNumberOfColumns, 3, 3));
        
        controlContainerPanel = new JPanel();
        controlContainerPanel.setLayout(new BoxLayout(controlContainerPanel, BoxLayout.Y_AXIS));
        
        JPanel labelPanel = new JPanel();
        JPanel buttonLeftPanel = new JPanel();
        
        resetButton = new JButton("Alaphelyzet");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                textLabel.setText("");
                initialize();
            }
        });
        labelPanel.add(textLabel);
        buttonLeftPanel.add(resetButton);
        
        
        saveButton = new JButton("Mentés");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
					gc.saveGame(Constants.kSaveLoadOptionJDBC);
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }
        });
        buttonLeftPanel.add(saveButton);
        
        
        loadButton = new JButton("Betöltés");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
					gc.loadGame(Constants.kSaveLoadOptionJDBC);
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }
        });
        buttonLeftPanel.add(loadButton);
        
        
        saveXMLButton = new JButton("XML Mentés");
        saveXMLButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
					gc.saveGame(Constants.kSaveLoadOptionXML);
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }
        });
        buttonLeftPanel.add(saveXMLButton);
        
        loadXMLButton = new JButton("XML Betöltés");
        loadXMLButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
					gc.loadGame(Constants.kSaveLoadOptionXML);
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }
        });
        
        buttonLeftPanel.add(loadXMLButton);
        
        controlContainerPanel.add(labelPanel);
        controlContainerPanel.add(buttonLeftPanel);
        
        containerPanel.add(controlContainerPanel,BorderLayout.NORTH);
    }
    
    /**
     * Az {@code initFields()} metódus a kattintható mezők inicializálását végzi.
     */
    public void initFields() {
        fields = new ArrayList<Field>();
        
        for (int i = 0; i < Constants.kNumberOfRows; i++) {
            for (int j = 0; j < Constants.kNumberOfColumns; j++) {
                Field field = new Field(i,j);
                field.setActionCommand("("+(i*Constants.kNumberOfColumns+j)+")");
                field.setPreferredSize(new Dimension(Constants.kElementSize, Constants.kElementSize));
                field.setIcon(new ImageIcon(getClass().getClassLoader().getResource(Constants.kEmptyFieldImageName)));

                field.setBorder(BorderFactory.createEmptyBorder(0, 3/*Constants.kSquareImagePadding*/, 0, 0));
                
                field.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        gc.buttonPressed(ae);
                    }
                });
                field.marker.markerState = Constants.kFieldStateEmpty;
                fields.add(field);
                fieldContainerPanel.add(field);
            }
        }
        containerPanel.add(fieldContainerPanel,BorderLayout.SOUTH);
    }
    
    
    /**
     * A {@code changeIcon()} metódus a játéktér egy elemének beállítja a hátterét a megadott paraméterek szerint, illetve változtatja a játéktér egy eleméhez tartozó markernek az állapotát.
     * @param index	A játéktér elemének az azonosítója.
     * @param iconName	A megjeleníteni kívánt kép neve.
     * @param fieldState	A beállítani kívánt új állapot
     */
    public void changeField(int index, String iconName, int fieldState) {
        Field fieldToChange = fields.get(index);
        fieldToChange.setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconName)));
        fieldToChange.marker.markerState = fieldState;
        fieldContainerPanel.revalidate();
        fieldContainerPanel.repaint();
    }
    
    /**
     * A {@code changeIcon()} metódus a játéktér egy elemének beállítja a hátterét a megadott paraméterek szerint.
     * @param index	A játéktér elemének az azonosítója.
     * @param iconName	A megjeleníteni kívánt kép neve.
     */
    public void changeIcon(int index, String iconName) {
        Field fieldToChange = fields.get(index);
        fieldToChange.setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconName)));
        fieldContainerPanel.revalidate();
        fieldContainerPanel.repaint();
    }
    
    /**
     * A {@code setTitle()} metódus a {@code textLabel}en megjelenő szöveget állítja a bemenő paraméter szerint.
     * @param	text	Az a szöveg, ami a {@code textLabel}en fog megjelenni.
     */
    public void setTitle(String text) {
        textLabel.setText(text);
    }
    
    /**
     * A {@code getMarkers()} fügvény a játéktér összes mezőjétől elkéri a hozzá tartozó markert, amit majd egy tömbben fog tárolni.
     * @return	A játéktér összes markere.
     *
     public ArrayList<Marker> getMarkers() {
     ArrayList<Marker> markers = new ArrayList<Marker>();
     for (int i = 0; i < fields.size(); i++) {
     markers.add(((Field)fields.get(i)).marker);
     }
     return markers;
     }*/
}
