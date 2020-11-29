/**
 * 
 */
package aplikacija;

/** 
 * Klasa aplikacija sadrzi metodu main od koje pocinje izvrsavanje programa
 * 
 * @author Bojan Zdelar
 */
public class Aplikacija {
	/**
	 * Metoda main kreira objekat klase turisticka agencija i poziva njen meni
	 * 
	 * @param args 
	 */
	public static void main(String[] args) {
		TuristickaAgencija agencija = new TuristickaAgencija("ZDELAR TOURS");
		agencija.dobroDosli();
		agencija.meni();
		agencija.dovidjenja();
	}
}
