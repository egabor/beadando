package Models;
import Utilities.Constants;


/**
 * Egy {@code Marker}t a játéktér egy mezőjéhez rendeljük, így tároljuk, hogy a mező szabad-e vagy foglalt. Ha foglalt, akkor melyik játékos által foglalt.
 * @author gaboreszenyi
 */
public class Marker {
    
    
    /**
     * A {@code row} változó tárolja, hogy az elem a játéktér hanyadik sorában foglal helyet.
     */
    public int row;
    
    /**
     * A {@code column} változó tárolja, hogy az elem a játéktér hanyadik oszlopában foglal helyet.
     */
    public int column;
    
    /**
     * A {@code markerState} változó tárolja a {@code Marker} szabad-e, vagy, hogy melyik játékos birtokában áll.
     */
    public int markerState;
    
    
    /**
     * A {@code Marker()} egy kostruktor, amely a {@code Marker}t inicializálja.
     * @param	row		A játéktér egy sorának sorszáma.
     * @param	column	A játéktér egy oszlopának sorszáma.
     */
    public Marker(int row, int column) {
        this.row = row;
        this.column = column;
        this.markerState = Constants.kFieldStateEmpty;
    }
    
    /**
     * A {@code Marker()} egy kostruktor, amely a {@code Marker}t inicializálja.
     * @param	row		A játéktér egy sorának sorszáma.
     * @param	column	A játéktér egy oszlopának sorszáma.
     * @param	state	A {@code Marker} állapota.
     */
    public Marker(int row, int column, int state) {
        this.row = row;
        this.column = column;
        this.markerState = state;
    }
    
    /**
     * A {@code getIndex()} metódus a makerhez tarzozó {@code row} és {@code column} értékekból előállítja a {@code Marker}hez tartozó indexet, amely egyértelműen azonosítja a {@code Marker}t.
     * @return	A {@code Marker}hez tartozó index.
     */
    public int getIndex() {
        return this.row*Constants.kNumberOfColumns+this.column;
    }
    
    /**
     * Az {@code hasLeft()} függvény megmondja, hogy a {@code Marker}nek van-e baloldali szomszédja.
     * @return Igaz, ha a mezőnek van baloldali szomszédja, egyébként hamis.
     */
    public Boolean hasLeft() {
        if (this.column > 0) {
            return true;
        }
        return false;
    }
    
    /**
     * Az {@code hasRight()} függvény megmondja, hogy a {@code Marker}nek van-e jobboldali szomszédja.
     * @return Igaz, ha a mezőnek van jobboldali szomszédja, egyébként hamis.
     */
    public Boolean hasRight() {
        if (this.column < Constants.kNumberOfColumns-1) {
            return true;
        }
        return false;
    }
    
    /**
     * Az {@code hasAbove()} függvény megmondja, hogy a {@code Marker}nek van-e északi szomszédja.
     * @return Igaz, ha a mezőnek van északi szomszédja, egyébként hamis.
     */
    public Boolean hasAbove() {
        if (this.row > 0) {
            return true;
        }
        return false;
    }
    
    /**
     * Az {@code hasBelow()} függvény megmondja, hogy a {@code Marker}nek van-e déli szomszédja.
     * @return Igaz, ha a mezőnek van déli szomszédja, egyébként hamis.
     */
    public Boolean hasBelow() {
        if (this.row < Constants.kNumberOfRows-1) {
            return true;
        }
        return false;
    }
    
    /**
     * Az {@code equals()} függvénnyel két {@code Marker}ről lehet eldönteni, hogy egyformák-e.
     * @param m	Az összehasinlítani kívánt {@code Marker}.
     * @return Igaz, ha egyformák.
     */
    public Boolean equals(Marker m) {
        if (this.row == m.row && 
            this.column == m.column &&
            this.markerState == m.markerState) {
            return true;
        }
        return false;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + markerState;
		result = prime * result + row;
		return result;
	}

	@Override
	public String toString() {
		return "Marker [row=" + row + ", column=" + column + ", markerState=" + markerState + "]";
	}

	
    
    
}
