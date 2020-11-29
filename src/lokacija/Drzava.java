/**
 * 
 */
package lokacija;

import java.util.ArrayList;

import podaci.CSV;
import podaci.Datoteke;

/**
 * Klasa drzava implementira interfejs CSV, 
 * opisana je njenim imenom i listom mesta koji se u njoj nalaze
 * Drzava sadrzi metodu pronadji koja prema imenu pronalazi opis drzave u datotetci
 *  
 * @author Bojan Zdelar
 */
public class Drzava implements CSV<Drzava> {
	private String ime;
	private ArrayList<String> mesta; 
	
	public Drzava() {}
	
	public Drzava(String ime, ArrayList<String> mesta) {
		this.ime = ime;
		this.mesta = mesta;
	}
	
	public String getIme() {
		return ime;
	}
	
	public void setIme(String ime) {
		this.ime = ime;
	}
	
	public ArrayList<String> getMesta() {
		return mesta;
	}
	
	public void setMesta(ArrayList<String> mesta) {
		this.mesta = mesta;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (!(obj instanceof Drzava))) {
			return false;
		}
		
		Drzava drzava = (Drzava) obj;
		
		if (ime.equals(drzava.ime)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return ime;
	}
	
	@Override
	public String serijalizuj() {
		String rezultat = ime;
		for (int i = 0; i < mesta.size(); i++) {
			if (i < mesta.size() - 1) {
				rezultat += ",";
			}
			rezultat += mesta.get(i);
		}
		return rezultat;
	}

	@Override
	public Drzava deserijalizuj(String CSV) {
		String[] podaci = CSV.split(",");
		ArrayList<String> mesta = new ArrayList<String>();
		// Preskacemo prvi element niza podaci jer se na njemu nalazi naziv drzave
		for (int i = 1; i < podaci.length; i++) {
			mesta.add(podaci[i]);
		}
		return new Drzava(podaci[0], mesta);
	}
	
	/**
	 * Staticka metoda pronadji vraca drzavu kojoj odgovara zadato ime drzave 
	 * i koja sadrzi zadato ime mesta, ili null
	 * 
	 * @param imeDrzave koja se trazi
	 * @param imeMesta koje treba da sadrzi
	 * @return pronadjena drzava ili null
	 */
	public static Drzava pronadji(String imeDrzave, String imeMesta) {
		ArrayList<Drzava> drzave = Datoteke.<Drzava>ucitaj((new Drzava()), "drzave.csv");
		for (Drzava drzava : drzave) {
			if ((drzava.ime.equals(imeDrzave)) && (drzava.mesta.contains(imeMesta))) {
				return drzava;
			}
		}
		System.out.println("- Nije pronadjena trazena drzava u datoteci");
		return null;
	}
}