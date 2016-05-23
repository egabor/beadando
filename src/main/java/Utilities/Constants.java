package Utilities;
/**
 * A {@code Constants} osztályban kaptak helyet a konstansok.
 * @author gaboreszenyi
 *
 */
public class Constants {
    
    /**
     * A {@code kNumberOfRows} konstans értéke (jelenleg: {@value}) azt mondja meg, hogy a játéktér hány sorból áll.
     */
    public static final int kNumberOfRows = 4;
    
    /**
     * A {@code kNumberOfColumns} konstans értéke (jelenleg: {@value}) azt mondja meg, hogy a játéktér hány oszlopból áll.
     */
    public static final int kNumberOfColumns = 10;
    
    /**
     * A {@code kElementSize} konstans értéke (jelenleg: {@value}) azt mondja meg, hogy a játéktér elemeinek mekkora a szélessége, illetve a magassága.
     */
    public static final int kElementSize = 60;
    
    
    
    /**
     * A {@code kFieldStateEmpty} konstans értéke (jelenleg: {@value}) a játéktér azon elemeihez tartozik, amelyek üresek, azaz egyik játékos sem áll ezeken a mezőkön.
     */
    public static final int kFieldStateEmpty = 0;
    
    /**
     * A {@code kFieldStateBlue} konstans értéke (jelenleg: {@value}) a játéktér azon elemeihez tartozik, amelyeken a kék játékos áll.
     */
    public static final int kFieldStateBlue = 1;
    
    /**
     * A {@code kFieldStateRed} konstans értéke (jelenleg: {@value}) a játéktér azon elemeihez tartozik, amelyeken a piros játékos áll.
     */
    public static final int kFieldStateRed = 2;
    
    /**
     * A {@code kFieldStateSelectable} konstans értéke (jelenleg: {@value}) a játéktér azon elemeihez tartozik, amelyre a soron következő játékos léphet.
     */
    public static final int kFieldStateSelectable = 3;
    
    
    
    /**
     * A játék indulásakor használjuk ezt az értéket, valamit akkor ha a játék már véget ért.
     * A {@code kPlayerNone} konstans értéke: {@value}.
     */
    public static final int kPlayerNone = -1;
    
    /**
     * A {@code kPlayerBlue} konstans értéke (jelenleg: {@value}) a játék során a kék játékost fogja reprezentálni.
     * Értéke mindig megegyezik a {@code kFieldStateBlue} kostans értékével.
     */
    public static final int kPlayerBlue = kFieldStateBlue;
    
    /**
     * A {@code kPlayerRed} konstans értéke (jelenleg: {@value}) a játék során a kék játékost fogja reprezentálni.
     * Értéke mindig megegyezik a {@code kFieldStateRed} kostans értékével.
     */
    public static final int kPlayerRed = kFieldStateRed;
    
    
    // A következő négy konstans a játéktér elemeinek a kinézetéért felelős fájlok (képek) neveit tartalmazza.
    
    /**
     * A {@code kEmptyFieldImageName} konstans értéke (jelenleg: {@value}) azt mondja meg, hogy hogyan nézzen ki egy üres mező a játéktéren.
     */
    public static final String kEmptyFieldImageName = "empty.png";
    
    /**
     * A {@code kBlueFieldImageName} konstans értéke (jelenleg: {@value}) azt mondja meg, hogy hogyan nézzen ki egy a kék játékoshoz tartozó mező.
     */
    public static final String kBlueFieldImageName = "blue.png";
    
    /**
     * A {@code kRedFieldImageName} konstans értéke (jelenleg: {@value}) azt mondja meg, hogy hogyan nézzen ki egy a piros játékoshoz tartozó mező.
     */
    public static final String kRedFieldImageName = "red.png";
    
    /**
     * A {@code kSelectableFieldImageName} konstans értéke (jelenleg: {@value}) azt mondja meg, hogy hogyan nézzen ki egy olyan mező ahova lépni tudunk. (szabad mező)
     */
    public static final String kSelectableFieldImageName = "gray.png";
    
    /**
     * A {@code kWinnerRedText} konstans tárolja azt a szöveget, ami kiírásra kerül ha a piros játékos nyer.
     */
    public static final String kWinnerRedText = "A piros játékos nyert.";
    
    /**
     * A {@code kWinnerBlueText} konstans tárolja azt a szöveget, ami kiírásra kerül ha a kék játékos nyer.
     */
    public static final String kWinnerBlueText = "A kék játékos nyert.";
    
    /**
     * A {@code redTurnText} konstans tárolja azt a szöveget, ami kiírásra kerül ha a piros játékos következik.
     */
    public static final String redTurnText = "Piros jön.";
    
    /**
     * A {@code blueTurnText} konstans tárolja azt a szöveget, ami kiírásra kerül ha a kék játékos következik.
     */
    public static final String blueTurnText = "Kék jön.";
    
    /**
     * A {@code tableName} konstans tárolja annak a táblának a nevét amelynek szerepelnie kell a adatbázisban.
     * Ide fogjuk menteni a játékállást.
     */
    public static final String tableName = "JATEKALLAS";
    
    /**
     * A {@code kSaveLoadOptionJDBC} konstanssal tudjuk jelezni az erre alkalmas metódusnak, hogyha a távoli adatbázisba akarunk menteni.
     */
    public static final int kSaveLoadOptionJDBC = 0;
    
    /**
     * A {@code kSaveLoadOptionXML} konstanssal tudjuk jelezni az erre alkalmas metódusnak, hogyha a xml fájlba akarunk menteni.
     */ 
    public static final int kSaveLoadOptionXML = 1;
    
}
