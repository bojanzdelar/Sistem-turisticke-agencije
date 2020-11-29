/**
 * 
 */
package korisnik;

import java.util.ArrayList;

/** 
 * Klasa agent nasledjuje klasu korisnik 
 * Agent sadrzi metodu ucitaj koja vraca listu agenta definisanih u datoteci
 * i metodu prijava koja uporedjuje unete podatke korisnika sa postojecim agentima
 * 
 * @author Bojan Zdelar
 */
public class Agent extends Korisnik {	
	public Agent() {}
	
	public Agent(String korisnickoIme, String lozinka, String ime, String prezime) {
		super(korisnickoIme, lozinka, ime, prezime);
	}

	@Override
	public String serijalizuj() { 
		return getKorisnickoIme() + "," + getLozinka() + "," + getIme() + "," + getPrezime();
 	}

	@Override
	public Agent deserijalizuj(String CSV) {
		String[] podaci = CSV.split(",");
		return new Agent(podaci[0], podaci[1], podaci[2], podaci[3]);
	}
		
	/**
	 * Staticka metoda prijava uporedjuje podatke unete od strane korisnika sa svim agentima iz date liste
	 * i vraca prijavljenog agenta ili null
	 * 
	 * @param korisnickoIme uneto od strane korisnika
	 * @param lozinka uneta od strane korisnika
	 * @param agenti lista agenata 
	 * @return prijavljeni agent ili null
	 */
	public static Agent prijava(String korisnickoIme, String lozinka, ArrayList<Agent> agenti) {
		for (Agent agent : agenti) {
			if (agent.getKorisnickoIme().equals(korisnickoIme) && agent.getLozinka().equals(lozinka)) {
				return agent;
			}
		}
		return null;
	}
}