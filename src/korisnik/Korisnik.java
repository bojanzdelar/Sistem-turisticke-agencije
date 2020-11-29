/**
 * 
 */
package korisnik;

import aplikacija.TuristickaAgencija;
import podaci.CSV;
import podaci.Unos;

/** 
 * Abstraktna klasa Korisnik implementira interfejs CSV,
 * a opisana je korisnickim imenom, lozinkom, imenom i prezimenom
 * Korisnik sadrzi metodu prijava u turisticku agenciju
 * 
 * @author Bojan Zdelar
 */
public abstract class Korisnik implements CSV<Korisnik> { 
	private String korisnickoIme;
	private String lozinka;
	private String ime;
	private String prezime;
	
	public Korisnik() {}
	
	public Korisnik(String korisnickoIme, String lozinka, String ime, String prezime) {
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
	}
	
	protected String getKorisnickoIme() {
		return korisnickoIme;
	} 
	
	protected void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	
	protected String getLozinka() {
		return lozinka;
	}
	
	protected void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	
	public String getIme() {
		return ime;
	}
	
	public void setIme(String ime) {
		this.ime = ime;
	}
	
	public String getPrezime() {
		return prezime;
	}
	
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	
	public String toString() {
		return ime + " " + prezime;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (!(obj instanceof Klijent))) {
			return false;
		}
		
		Korisnik korisnik = (Korisnik) obj;
		
		if (korisnickoIme.equals(korisnik.korisnickoIme)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Metoda prijava omogucuje korisniku prijavu u turisticku agenciju
	 * 
	 * @param agencija u koju korisnik zeli da se prijavi
	 * @return prijavljen korisnik ili null 
	 */
	public static Korisnik prijava(TuristickaAgencija agencija) {
		System.out.println("\nPrijava korisnika");
		String korisnickoIme = Unos.unesiString("Unesite vase korisnicko ime");
		String lozinka = Unos.unesiString("Unesite vasu lozinku");
		
		/* Pokusaj prijavljavinja korisnika kao agent */
		Korisnik korisnik = Agent.prijava(korisnickoIme, lozinka, agencija.getAgenti());
		/* Ukoliko korisnik nije prijavljen kao agent, korisnik pokusava da se prijavi kao klijent */
		if (korisnik == null) {
			korisnik = Klijent.prijava(korisnickoIme, lozinka, agencija.getKlijenti());
		}
		
		System.out.println((korisnik == null) ? "- Prijava nije uspela" : "- Uspesno ste se prijavili");
		return korisnik;
	}
}