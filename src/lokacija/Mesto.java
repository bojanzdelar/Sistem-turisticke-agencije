/**
 * 
 */
package lokacija;

import java.util.ArrayList;

import podaci.CSV;
import podaci.Datoteke;

/** 
 * Klasa mesto implementira interfejs CSV, 
 * opisana je njenim imemom, kao i drzavom u kojoj se nalazi
 * Mesto sadrzi metodu pronadji koja prema imenu pronalazi opis mesta u datotetci
 * 
 * @author Bojan Zdelar
 */
public class Mesto implements CSV<Mesto> {
	private String ime;
	private Drzava drzava;
	
	public Mesto() {}
	
	public Mesto(String ime, Drzava drzava) {
		this.ime = ime;
		this.drzava = drzava;
	}
	
	public String getIme() {
		return ime;
	}
	
	public void setIme(String ime) {
		this.ime = ime;
	}
	
	public Drzava getDrzava() {
		return drzava;
	}
	
	public void setDrzava(Drzava drzava) {
		this.drzava = drzava;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (!(obj instanceof Mesto))) {
			return false;
		}
		
		Mesto mesto = (Mesto) obj;
		
		if ((ime.equals(mesto.ime)) && (drzava.equals(mesto.drzava))) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return ime + ", " + drzava.toString();
	}

	@Override
	public String serijalizuj() {
		return ime + "," + drzava.getIme();
 	}

	@Override
	public Mesto deserijalizuj(String CSV) {
		String[] podaci = CSV.split(",");
		Drzava drzava = Drzava.pronadji(podaci[1],podaci[0]);		
		return (drzava != null) ? new Mesto(podaci[0], drzava) : null; 
	}
	
	/**
	 * Staticka metoda pronadji vraca mesto kojem odgovara zadato ime ili null
	 * 
	 * @param ime mesta
	 * @return pronadjeno mesto ili null
	 */
	public static Mesto pronadji(String ime) {
		ArrayList<Mesto> mesta = Datoteke.<Mesto>ucitaj((new Mesto()), "mesta.csv");
		for (Mesto mesto : mesta) {
			if (mesto.ime.equals(ime)) {
				return mesto;
			}
		}
		System.out.println("- Nije pronadjeno trazeno mesto u datoteci");
		return null;
	}
}