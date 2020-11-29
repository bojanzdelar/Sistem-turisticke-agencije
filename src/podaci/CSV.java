/**
 * 
 */
package podaci;

/** 
 * Interfejs CSV sadrzi abstraktne metode za serijalizaciju i deserijalizaciju
 * 
 * @author Bojan Zdelar
 */
public interface CSV<T> {
	/**
	 * Apstraktna metoda serijalizuj pretvara objekat nad kojim je pozvana u string
	 * 
	 * @return objekat pretvoren u string
	 */
	public String serijalizuj();
	
	/**
	 * Apstraktna metoda deserijalizuj konstruise objekat iz zadatog stringa
	 * 
	 * @param CSV zadati string
	 * @return konstruisani objekat
	 */
	public T deserijalizuj(String CSV);
}