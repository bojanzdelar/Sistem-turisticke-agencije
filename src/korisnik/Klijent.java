/**
 * 
 */
package korisnik;

import java.util.ArrayList;

import podaci.Unos;

/** 
 * Klasa klijent nasledjuje klasu korisnik i opisana je raspolozivim stanjem novca
 * Agent sadrzi metodu ucitaj koja vraca listu agenta definisanih u datoteci,
 * metodu prijava koja uporedjuje unete podatke korisnika sa postojecim agentima
 * i metode koje menjaju raspolozivo stanje novca
 * 
 * @author Bojan Zdelar
 */
public class Klijent extends Korisnik {
	private float raspolozivoStanje; 
	
	public Klijent() {}
	
	public Klijent(String korisnickoIme, String lozinka, String ime, String prezime, float raspolozivoStanje) {
		super(korisnickoIme, lozinka, ime, prezime);
		this.raspolozivoStanje = raspolozivoStanje;
	} 

	public float getRaspolozivoStanje() {
		return raspolozivoStanje;
	}
	
	public void setRaspolozivoStanje(float raspolozivoStanje) {
		this.raspolozivoStanje = raspolozivoStanje;
	}

	@Override
	public String serijalizuj() {
		return getKorisnickoIme() + "," + getLozinka() + "," + getIme() + "," + getPrezime() + "," + raspolozivoStanje;
 	}

	@Override
	public Klijent deserijalizuj(String CSV) {
		String[] podaci = CSV.split(",");
		return new Klijent(podaci[0], podaci[1], podaci[2], podaci[3], Float.parseFloat(podaci[4]));
	}
	
	/**
	 * Staticka metoda prijava uporedjuje podatke unete od strane korisnika sa svim klijentima iz date liste
	 * i vraca prijavljenog klijenta ili null
	 * 
	 * @param korisnickoIme uneto od strane korisnika
	 * @param lozinka uneta od strane korisnika
	 * @param klijenti lista klijenata 
	 * @return prijavljeni klijent ili null
	 */
	public static Klijent prijava(String korisnickoIme, String lozinka, ArrayList<Klijent> klijenti) {
		for (Klijent klijent : klijenti) {
			if (klijent.getKorisnickoIme().equals(korisnickoIme) && klijent.getLozinka().equals(lozinka)) {
				return klijent;
			}
		}
		return null;
	}
	
	/**
	 * Metoda uplati novac povecava raspolozivno stanje klijenta nad kojim je pozvana za odredjenu vrednost
	 * 
	 * @param novac vrednost za koju se uvecava raspolozivo stanje
	 */
	public void uplatiNovac(float novac) {
		if (novac > 0) {
			raspolozivoStanje += novac;
			System.out.println("- Uspesno ste uplatili novac, trenutno stanje je " + raspolozivoStanje);
		} else if (novac == 0) {
			System.out.println("- Vase raspolozivo stanje je ostalo isto");
		} else {
			System.out.println("- Novac koji zelite da uplatite mora da bude pozitivan");
		}
	}

	/**
	 * Metoda uplati novac zahteva od korisnika unos svote novca koju zeli da uplati
	 */
	public void uplatiNovac() {
		System.out.println("\nUplata novca");
		float novac = Unos.unesiDecimalanBroj("Unesite svotu novca koju zelite da uplatite", 100000);
		uplatiNovac(novac);
	}
	
	/**
	 * Metoda potrosi novac umanjuje raspolozivo stanje klijenta nad kojim je pozvana za odredjenu vrednost
	 * 
	 * @param novac vrednost za koju se umanjuje raspolozivo stanje
	 */
	public void potrosiNovac(float novac) {
		if (novac > 0) {
			raspolozivoStanje -= novac;
			System.out.println("- Uspesno ste potrosili novac, trenutno stanje je " + raspolozivoStanje);
		} else if (novac == 0) {
			System.out.println("- Vase raspolozivo stanje je ostalo isto");
		} else {
			System.out.println("- Novac koji zelite da potrosite mora da pozitivan");
		}
	}
}