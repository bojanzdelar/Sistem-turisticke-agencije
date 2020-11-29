/**
 * 
 */
package rezervacije;

/**
 * Interfejs zakupljivo sadrzi apstraktnu metodu zakup koja vraca cenu zakupa i apstraktnu metodu
 * otkazi koja vraca novac koji se vraca u slucaju otkazivanja
 *  
 * @author Bojan Zdelar
 */
public interface Zakupljivo {
	/**
	 * Metoda zakup vraca cenu zakupa
	 * 
	 * @return cena zakupa
	 */
	public int zakup();
	
	/**
	 * Metoda otkazi vraca novac koji se vraca u slucaju otkazivanja
	 * 
	 * @param povracajNovca decimalna vrednost od 0 do 1 kojom se mnozi cena zakupa
	 * @return novac koji se vraca u slucaju otkazivanja
	 */
	public float otkazi(float povracajNovca); 
}