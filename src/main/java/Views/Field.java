package Views;
import Models.Marker;
import javafx.scene.control.Button;


/**
 * Egy {@code Field} osztályú egyed fogja reprezentálni a játéktér egy elemét.
 * A {@code Field} osztály a {@code JButton} osztály leszármazottja.
 * @author gaboreszenyi
 */
public class Field extends Button {

	/**
	 * A {@code marker} változón keresztül érhetjük el a {@code Field}hez tartozó markert.
	 */
	public Marker marker;
	
	/**
	 * A {@code Field()} egy kostruktor, amely a {@code Field}et inicializálja.
	 * @param	row		A játéktér egy sorának sorszáma.
	 * @param	column	A játéktér egy oszlopának sorszáma.
	 */
	public Field(int row, int column) {
		this.marker = new Marker(row, column);
	}
}
