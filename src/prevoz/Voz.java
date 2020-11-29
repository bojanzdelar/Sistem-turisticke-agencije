/**
 * 
 */
package prevoz;

import java.util.Arrays;

import lokacija.Adresa;
import podaci.Unos;

/** 
 * Klasa voz nasledjuje klasu prevozno sredstvo i opisana je klasom voznog mesta
 * VOz sadrzi metodu kreiraj koja vraca njen konstruisani objekat 
 * 
 * @author Bojan Zdelar
 */
public class Voz extends PrevoznoSredstvo {
	public enum KlasaVoznogMesta {
		PRVI_RAZRED("prvi razred", 210), DRUGI_RAZRED("drugi razred", 0), SPAVACA_KOLA("spavaca kola", 1000);
		
		private final String ime;
		private final int cena;
		
		private KlasaVoznogMesta(final String ime, final int cena) {
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
	
	private KlasaVoznogMesta klasaVoznogMesta;

	public Voz() {}
	
	public Voz(boolean obrok, Adresa polaznaAdresa, Adresa odredisnaAdresa, KlasaVoznogMesta klasaVoznogMesta) {
		super(obrok, polaznaAdresa, odredisnaAdresa);
		this.klasaVoznogMesta = klasaVoznogMesta;
	}
	
	public KlasaVoznogMesta getKlasaVoznogMesta() {
		return klasaVoznogMesta;
	}
	
	public void setKlasaVoznogMesta(KlasaVoznogMesta klasaVoznogMesta) {
		this.klasaVoznogMesta = klasaVoznogMesta;
	}
	
	@Override
	public String toString() {
		return "Voz [klasa: " + klasaVoznogMesta + "; " + super.toString() + "]";
	}
	
	@Override
	public String serijalizuj() {
		return "voz," + super.serijalizuj() + "," + klasaVoznogMesta.name();
	}
	
	@Override
	public Voz deserijalizuj(String CSV) {
		String[] podaci = CSV.split(",");
		
		String podaciPolaznaAdresa = String.join(",", Arrays.copyOfRange(podaci, 2, 5)); 
		String podaciOdredisnaAdresa = String.join(",", Arrays.copyOfRange(podaci, 6, 9));
		
		Adresa polaznaAdresa = (new Adresa()).deserijalizuj(podaciPolaznaAdresa);
		Adresa odredisnaAdresa = (new Adresa()).deserijalizuj(podaciOdredisnaAdresa);
																													
		return new Voz(Boolean.parseBoolean(podaci[1]), polaznaAdresa, odredisnaAdresa, KlasaVoznogMesta.valueOf(podaci[10]));
	}
	
	/**
	 * Staticka metoda kreiraj zahteva unos klase voznog mesta i vraca objekat klase voz 
	 * 
	 * @param obrok da li je ukljucen
	 * @param polaznaAdresa
	 * @param odredisnaAdresa
	 * @return objekat klase voz
	 */
	public static Voz kreiraj(boolean obrok, Adresa polaznaAdresa, Adresa odredisnaAdresa) {
		String[] opcije = new String[] {"Prvi razred", "Drugi razred", "Spavaca kola"};
		int izbor = Unos.izaberiOpciju("Izaberite klasu voznog mesta", opcije);
		KlasaVoznogMesta klasaVoznogMesta = KlasaVoznogMesta.values()[--izbor]; // 0-based indexing
		return new Voz(obrok, polaznaAdresa, odredisnaAdresa, klasaVoznogMesta);
	}
	
	@Override
	public int zakup() {
		return super.zakup() + klasaVoznogMesta.getCena();
	}	
}