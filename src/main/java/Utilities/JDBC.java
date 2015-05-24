package Utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Models.Marker;
/**
 * A {@code JDBC} osztály segítségével tudunk kapcsolódni a távoli adatbázishoz.
 * Ennek az osztálynak a segítségével tudjuk elmenteni a játékot, illetve betölteni egy elmentett játékot.
 * @author gaboreszenyi
 *
 */
public class JDBC {
    
    /**
     * A {@code logger} változó segítségével fogunk tudni naplózni.
     */
    private static Logger logger = LoggerFactory.getLogger(JDBC.class);
    
    /**
     * A {@code username} változó tárolja a távoli adatbázishoz a felhasználónevet.
     */    
    public static String username = "";
    
    /**
     * A {@code password} változó tárolja a távoli adatbázishoz a jelszót.
     */
    public static String password = "";
    
 	/**
 	 * Az {@code url} változó tárolja a távoli adatbázis elérési útját.
 	 */
    public static String url = "";
    
 	/**
 	 * A {@code JDBC()} egy konstruktor, ami betölti a távoli adatbázis eléréséhez szükséges beállításokat.
 	 */
    public JDBC() {
    	try {
			Properties properties = new Properties();
			properties.load(getClass().getClassLoader().getResourceAsStream("project.properties"));

			username = properties.getProperty("jdbc.username");
			password = properties.getProperty("jdbc.password");
			url = properties.getProperty("jdbc.url");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	isTableExist(Constants.tableName);
    }
    
    

    /**
     * A {@code loadGame()} metódus végzi el elmentett játékállás betöltését a távoli adatbázisból.
     * @return Az elmetett játék állapota.
     */
    public static ArrayList<Marker> loadGame() {
        ArrayList<Marker> markers = new ArrayList<Marker>();
        
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
           //isTableExist(Constants.tableName);
                
                
                try (Statement stmt = conn.createStatement()) {
                    try (ResultSet rset = stmt.executeQuery("SELECT * FROM " + Constants.tableName)) {
                        while (rset.next()) {
                            //logger.debug(rset.getInt(1) +", "+ rset.getInt(2) +", "+rset.getInt(3) +", "+rset.getInt(4));
                            Marker marker = new Marker(0,0);
                            marker.row = rset.getInt(2);
                            marker.column = rset.getInt(3);
                            marker.markerState = rset.getInt(4);
                            markers.add(marker); 
                        }
                    }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return markers;
    }
    
    /**
     * A {@code saveGame()} metódus végzi el a játék metését.
     * Csatlakozik az adatbázishoz és feltölti adatokkal.
     * @param markers A játék jelenlegi állapotát reprezentáló markerek.
     */
    public static void saveGame(ArrayList<Marker> markers) {
        removeAllRecordsFromTable(Constants.tableName);
        //isTableExist(Constants.tableName);
        
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
                try (Statement stmt = conn.createStatement()) {
                    for (int i = 0; i < markers.size(); i++) {
                        Marker marker = markers.get(i);
                        try (ResultSet rset = stmt.executeQuery("INSERT INTO "+Constants.tableName+" (AZON, SOR, OSZLOP, ALLAPOT) VALUES ('"+(i+1)+"','"+marker.row+"', '"+marker.column+"', '"+marker.markerState+"')")) {
                            
                        }
                    }
                }
                
        } catch (SQLException e) {
           e.printStackTrace();
        }    
        
    }
    
    /**
     * A {@code removeAllRecordsFromTable()} metódus törli a paraméterként megadott táblából az összes sort.
     * @param table	Az üríteni kívánt tábla neve.
     */
    private static void removeAllRecordsFromTable(String table) {
    	//isTableExist(table);
    	try (Connection conn = DriverManager.getConnection(url, username, password)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rset = stmt.executeQuery("TRUNCATE TABLE "+table)) {
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * A {@code isTableExist()} metódus elelnőrzi, hogy a paraméterként megadott tábla létezik-e.
     * @param table Az ellenőrizni kívánt tábla neve.
     * @return Igaz, ha a tábla létezik.
     */
    private static void isTableExist(final String table) {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet res = meta.getTables(null, null, table,new String[] {"TABLE"});
            Boolean exists = false;
            while (res.next()) {
            	exists = true;
            }
            if (!exists) {
            	logger.debug(table + " nevű tábla nem létezik.");
                createTable(table);
            } else {
            	logger.debug(table + " nevű tábla létezik.");
            }
            
            //JOptionPane.showMessageDialog(null, table + " nevű tábla nem létezik.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //return false;
    }
    
   /**
    * A {@code createTable()} létrehozza paraméterben megadott táblát.
    * @param table A létrehozni kívánt tábla.
    */
    private static void createTable(String table) {
        logger.warn(table + " nevű tábla létrehozása.");
        //isTableExist(table);
    	try (Connection conn = DriverManager.getConnection(url, username, password)) {
    		String q = "CREATE TABLE "+table+" ( AZON NUMBER NOT NULL ENABLE, SOR NUMBER, OSZLOP NUMBER, ALLAPOT NUMBER, CONSTRAINT "+table+"_PK PRIMARY KEY (AZON) )";
    		try (Statement stmt = conn.createStatement()) {
                try (ResultSet rset = stmt.executeQuery(q)) {
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
}