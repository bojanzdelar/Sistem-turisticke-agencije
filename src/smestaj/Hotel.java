/**
 * 
 */
package smestaj;

import java.util.Arrays;

import lokacija.Adresa;
import podaci.Unos;

/** 
 * Klasa hotel nasledjuje klasu smestaj i opisana je brojem zvezdica
 * Hotel sadrzi metodu kreiraj koja vraca objekat ove klase
 * 
 * @author Bojan Zdelar
 */
public class Hotel extends Smestaj {
	private int brojZvezdica;
	
	public Hotel() {}

	public Hotel(float povrsina, int brojKreveta, TipPansiona tipPansiona, Adresa adresa, int brojZvezdica) {
		super(povrsina, brojKreveta, tipPansiona, adresa);
		this.brojZvezdica = brojZvezdica;
	}
	
	public int getBrojZvezdica() {
		return brojZvezdica;
	}
	
	public void setBrojZvezdica(int brojZvezdica) {
		this.brojZvezdica = brojZvezdica;
	}
	
	@Override
	public String toString() {
		return "Hotel [broj zvezdica: " + brojZvezdica + "; " + super.toString() + "]";
	}
	
	@Override
	public String serijalizuj() {
		return "hotel," + super.serijalizuj() + "," + brojZvezdica;
	}
	
	@Override
	public Hotel deserijalizuj(String CSV) {
		String[] podaci = CSV.split(",");
															
		String podaciAdrese = String.join(",", Arrays.copyOfRange(podaci, 4, 7));
		Adresa adresa = (new Adresa()).deserijalizuj(podaciAdrese);
		
		return new Hotel(Float.parseFloat(podaci[1]), Integer.parseInt(podaci[2]), 
				TipPansiona.valueOf(podaci[3]), adresa, Integer.parseInt(podaci[8]));
	}
	
	/**
	 * Staticka metoda kreiraj zahteva od korisnika unos broja zvezdica i vraca objekat klase hotel
	 * 
	 * @param povrsina 
	 * @param brojKreveta
	 * @param tipPansiona
	 * @param adresa
	 * @return kreirani hotel
	 */
	public static Hotel kreiraj(float povrsina, int brojKreveta, TipPansiona tipPansiona, Adresa adresa) {
		int brojZvezdica = Unos.unesiPrirodanBroj("Unesi broj zvezdica hotela", 5);
		return new Hotel(povrsina, brojKreveta, tipPansiona, adresa, brojZvezdica);
	}

	@Override
	public int zakup() {
		return super.zakup() + 123 * brojZvezdica;
	}
}