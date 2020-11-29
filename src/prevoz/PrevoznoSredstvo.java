/**
 * 
 */
package prevoz;

import java.util.ArrayList;

import lokacija.Adresa;
import podaci.CSV;
import podaci.Datoteke;
import podaci.Unos;
import rezervacije.Zakupljivo;

/** 
 * Abstraktna klasa prevozno sredstvo implementira interfejse zakupljivo i CSV,
 * a opisana je informacijom da li sadrzi obrok, polaznom i odredisnom adresom
 * Prevozno sredstvo sadrzi metodu izaberi i kreiraj koje vracaju objekat aviona ili voza
 * 
 * @author Bojan Zdelar
 */
public abstract class PrevoznoSredstvo implements Zakupljivo, CSV<PrevoznoSredstvo> {
	private boolean obrok;
	private Adresa polaznaAdresa;
	private Adresa odredisnaAdresa;
	
	public PrevoznoSredstvo() {}
	
	public PrevoznoSredstvo(boolean obrok, Adresa polaznaAdresa, Adresa odredisnaAdresa) {
		this.obrok = obrok;
		this.polaznaAdresa = polaznaAdresa;
		this.odredisnaAdresa = odredisnaAdresa;
	}
	
	public boolean isObrok() {
		return obrok;
	}
	
	public void setObrok(boolean obrok) {
		this.obrok = obrok;
	}
	
	public Adresa getPolaznaAdresa() {
		return polaznaAdresa;
	}
	
	public void setPolaznaAdresa(Adresa polaznaAdresa) {
		this.polaznaAdresa = polaznaAdresa;
	}
	
	public Adresa getOdredisnaAdresa() {
		return odredisnaAdresa;
	}
	
	public void setOdredisnaAdresa(Adresa odredisnaAdresa) {
		this.odredisnaAdresa = odredisnaAdresa;
	}
		
	@Override
	public String toString() {
		return (obrok ? "ima obrok; " : "nema obrok; ") + "polazna adresa: " + polaznaAdresa + "; " + "odredisna adresa: " + odredisnaAdresa;
	}

	@Override
	public String serijalizuj() {
		return obrok + "," + polaznaAdresa.serijalizuj() + "," + odredisnaAdresa.serijalizuj();
	}
	
	/**
	 * Staticka metoda izaberi daje izbor izmedju odgovarajucih prevoznih sredstava koji su definisani u datoteci
	 * i vraca odabrani objekat klase koja nasledjuje prevozno sredstvo (avion ili voz)
	 * 
	 * @param polaznaAdresa
	 * @param odredisnaAdresa
	 * @return objekat prevoznog sredstva (avion ili voz) ili null
	 */
	public static PrevoznoSredstvo izaberi(Adresa polaznaAdresa, Adresa odredisnaAdresa) {
		ArrayList<PrevoznoSredstvo> prevoznaSredstva = new ArrayList<PrevoznoSredstvo>();
		ArrayList<PrevoznoSredstvo> odgovarajucaPrevoznaSredstva = new ArrayList<PrevoznoSredstvo>();
		ArrayList<Avion> avioni = Datoteke.<Avion>ucitaj((new Avion()), "avioni.csv");
		ArrayList<Voz> vozovi = Datoteke.<Voz>ucitaj((new Voz()), "vozovi.csv");
		if (avioni != null) {
			prevoznaSredstva.addAll(avioni);
		}
		if (vozovi != null) {
			prevoznaSredstva.addAll(vozovi);
		}
		if (prevoznaSredstva.size() == 0) {
			System.out.println("- Ne postoji nijedno prevozno sredstvo u datotekama");
			return null;
		}
		for (PrevoznoSredstvo prevoznoSredstvo : prevoznaSredstva) {
			if ((prevoznoSredstvo.polaznaAdresa.equals(polaznaAdresa)) && (prevoznoSredstvo.odredisnaAdresa.equals(odredisnaAdresa))) {
				odgovarajucaPrevoznaSredstva.add(prevoznoSredstvo);
			}
		}
		if (odgovarajucaPrevoznaSredstva.size() == 0) {
			System.out.println("- Ne postoji nijedno prevozno sredstvo u datotekama koje odgovara unetim adresama");
			return null;
		}
		int izbor = Unos.izaberiOpciju("Izaberite prevozno sredstvo koje zelite da iskoristite", odgovarajucaPrevoznaSredstva) - 1; // 0-based indexing
		return odgovarajucaPrevoznaSredstva.get(izbor);
	}
	
	/**
	 * Staticka metoda kreiraj zahteva od korisnika podatke potrebne za konstruisanje objekta koji 
	 * nasledjuje klasu prevozno sredstvo i vraca ga nakon kreiran
	 * 
	 * @param polaznaAdresa
	 * @param odredisnaAdresa
	 * @return objekat prevoznog sredstva (avion ili voz) ili null
	 */
	public static PrevoznoSredstvo kreiraj(Adresa polaznaAdresa, Adresa odredisnaAdresa) {
		String[] opcije = new String[] {"Avion", "Voz"}; 
		int izbor = Unos.izaberiOpciju("Izaberite prevozno sredstvo", opcije);
		boolean obrok = Unos.unesiBoolean("Da li prevozno sredstvo ukljucuje obrok");
		PrevoznoSredstvo prevoznoSredstvo = null;
		/* Avion */
		if (izbor == 1) {
			prevoznoSredstvo = Avion.kreiraj(obrok, polaznaAdresa, odredisnaAdresa);
			Datoteke.<PrevoznoSredstvo>dodaj(prevoznoSredstvo, "avioni.csv");
		}
		/* Voz */
		else if (izbor == 2) {
			prevoznoSredstvo = Voz.kreiraj(obrok, polaznaAdresa, odredisnaAdresa);
			Datoteke.<PrevoznoSredstvo>dodaj(prevoznoSredstvo, "vozovi.csv");
		}
		return prevoznoSredstvo;
	}
	
	@Override 
	public int zakup() {
		return 540 * (obrok ? 1 : 0) + 640;
	}
	
	@Override
	public float otkazi(float povracajNovca) {
		return povracajNovca * zakup();
	}
}