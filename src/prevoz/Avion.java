/**
 * 
 */
package prevoz;

import java.util.Arrays;

import lokacija.Adresa;
import podaci.Unos;

/** 
 * Klasa avion nasledjuje klasu prevozno sredstvo i opisana je klasom avionskog mesta
 * Avion sadrzi metodu kreiraj koja vraca njen konstruisani objekat 
 * 
 * @author Bojan Zdelar
 */
public class Avion extends PrevoznoSredstvo {
	public enum KlasaAvionskogMesta {
		BIZNIS("biznis", 1000), EKONOMSKA("ekonomska", 250);
		
		private final String ime;
		private final int cena;
		
		private KlasaAvionskogMesta(final String ime, final int cena) {
			this.ime = ime;
			this.cena = cena;
		}
		
		@Override
		public String toString() {
			return ime;
		}
		
		public int getCena() {
			return cena;
		}
	}
	
	private KlasaAvionskogMesta klasaAvionskogMesta;
	
	public Avion() {}

	public Avion(boolean obrok, Adresa polaznaAdresa, Adresa odredisnaAdresa, KlasaAvionskogMesta klasaAvionskogMesta) {
		super(obrok, polaznaAdresa, odredisnaAdresa);
		this.klasaAvionskogMesta = klasaAvionskogMesta;
	}
	
	public KlasaAvionskogMesta getKlasaAvionskogMesta() {
		return klasaAvionskogMesta;
	}
	
	public void setKlasaAvionskogMesta(KlasaAvionskogMesta klasaAvionskogMesta) {
		this.klasaAvionskogMesta = klasaAvionskogMesta;
	}
	
	@Override
	public String toString() {
		return "Avion [klasa: " + klasaAvionskogMesta + "; " + super.toString() + "]";
	}
	
	@Override
	public String serijalizuj() {
		return "avion," + super.serijalizuj() + "," + klasaAvionskogMesta.name();
	}
	
	@Override
	public Avion deserijalizuj(String CSV) {
		String[] podaci = CSV.split(",");

		String podaciPolaznaAdresa = String.join(",", Arrays.copyOfRange(podaci, 2, 5)); 
		String podaciOdredisnaAdresa = String.join(",", Arrays.copyOfRange(podaci, 6, 9));
		
		Adresa polaznaAdresa = (new Adresa()).deserijalizuj(podaciPolaznaAdresa);
		Adresa odredisnaAdresa = (new Adresa()).deserijalizuj(podaciOdredisnaAdresa);

		return new Avion(Boolean.parseBoolean(podaci[1]), polaznaAdresa, odredisnaAdresa, KlasaAvionskogMesta.valueOf(podaci[10]));
	}
	
	/**
	 * Staticka metoda kreiraj zahteva unos klase avionskog mesta i vraca objekat klase avion 
	 * 
	 * @param obrok da li je ukljucen
	 * @param polaznaAdresa 
	 * @param odredisnaAdresa
	 * @return objekat klase avion
	 */
	public static Avion kreiraj(boolean obrok, Adresa polaznaAdresa, Adresa odredisnaAdresa) {
		String[] opcije = new String[] {"Biznis", "Ekonomska"};
		int izbor = Unos.izaberiOpciju("Izaberite klasu avionskog mesta", opcije);
		KlasaAvionskogMesta klasaAvionskogMesta = KlasaAvionskogMesta.values()[--izbor]; // 0-based indexing
		return new Avion(obrok, polaznaAdresa, odredisnaAdresa, klasaAvionskogMesta);
	}
	
	@Override
	public int zakup() {
		return super.zakup() + klasaAvionskogMesta.getCena();
	}
}