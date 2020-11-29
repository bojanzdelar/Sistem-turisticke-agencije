/**
 * 
 */
package rezervacije;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

import lokacija.Adresa;
import podaci.CSV;
import podaci.Datoteke;
import podaci.Unos;
import prevoz.Avion;
import prevoz.PrevoznoSredstvo;
import prevoz.Voz;
import smestaj.Apartman;
import smestaj.Hotel;
import smestaj.Smestaj;

/** 
 * Klasa aranzman implementira interfejse zakupljvo i CSV, a opisana je informacijom da li je osiguran, da li je grupni, 
 * datomom polaska i povratka i prevoznim sredstvom i smestajem
 * Aranzman sadrzi metodu kreiraj koja vraca objekat njene klase i metodu obrisi koja iz datoteke brise zadani aranzman
 * 
 * @author Bojan Zdelar
 */
public class Aranzman implements Zakupljivo, CSV<Aranzman> {
	private boolean osiguran;
	private boolean grupni;
	private LocalDate polazak;
	private LocalDate povratak; 
	private PrevoznoSredstvo prevoznoSredstvo;
	private Smestaj smestaj;
	
	public Aranzman() {}
		
	public Aranzman(boolean osiguran, boolean grupni, LocalDate polazak, LocalDate povratak, PrevoznoSredstvo prevoznoSredstvo, Smestaj smestaj) {
		this.osiguran = osiguran;
		this.grupni = grupni;
		this.polazak = polazak;
		this.povratak = povratak;
		this.prevoznoSredstvo = prevoznoSredstvo;
		this.smestaj = smestaj;
	}
	
	public boolean isOsiguran() {
		return osiguran;
	}
	
	public void setOsiguran(boolean osiguran) {
		this.osiguran = osiguran;
	}
	
	public boolean isGrupni() {
		return grupni;
	}
	
	public void setGrupni(boolean grupni) {
		this.grupni = grupni;
	}
	
	public LocalDate getPolazak() {
		return polazak;
	}
	
	public void setPolazak(LocalDate polazak) {
		this.polazak = polazak;
	}
	
	public LocalDate getPovratak() {
		return povratak;
	}
	
	public void setPovratak(LocalDate povratak) {
		this.povratak = povratak;
	}
	
	public PrevoznoSredstvo getPrevoznoSredstvo() {
		return prevoznoSredstvo;
	}
	
	public void setPrevoznoSredstvo(PrevoznoSredstvo prevoznoSredstvo) {
		this.prevoznoSredstvo = prevoznoSredstvo;
	}
	
	public Smestaj getSmestaj() {
		return smestaj;
	}
	
	public void setSmestaj(Smestaj smestaj) {
		this.smestaj = smestaj;
	}
	
	@Override
	public String toString() {
		return "Aranzman [" + (osiguran ? "osiguran; " : "nije osiguran; ") + (grupni ? "grupni; " : "nije grupni; ") 
				+ "polazak: " + polazak.toString() + "; povratak: " + povratak.toString()
				+ ",\n\t" + prevoznoSredstvo.toString() + ",\n\t" + smestaj.toString() + "]";
	}
	
	@Override
	public String serijalizuj() {
		return osiguran + "," + grupni + "," + polazak + "," + povratak + "," + prevoznoSredstvo.serijalizuj() + "," + smestaj.serijalizuj();
	}
	
	@Override
	public Aranzman deserijalizuj(String CSV) {
		String[] podaci = CSV.split(",");
				
		String podaciPrevoznoSredstvo = String.join(",", Arrays.copyOfRange(podaci, 4, 15)); 
		String podaciSmestaj = String.join(",", Arrays.copyOfRange(podaci, 15, podaci.length));
		
		PrevoznoSredstvo prevoznoSredstvo = (podaci[4].equals("avion")) ? (new Avion()).deserijalizuj(podaciPrevoznoSredstvo)
																   		: (new Voz()).deserijalizuj(podaciPrevoznoSredstvo);
		
		Smestaj smestaj = (podaci[15].equals("apartman")) ? (new Apartman()).deserijalizuj(podaciSmestaj)
														  : (new Hotel()).deserijalizuj(podaciSmestaj);
			
		return new Aranzman(Boolean.parseBoolean(podaci[0]), Boolean.parseBoolean(podaci[1]),
				LocalDate.parse(podaci[2]), LocalDate.parse(podaci[3]), prevoznoSredstvo, smestaj);		
	}
	
	/**
	 * Metoda vraca broj dana izmedju polaska i povratka 
	 * @return broj dana
	 */
	public int brojDana() { 
		return (int) ChronoUnit.DAYS.between(polazak, povratak);
	}
	
	@Override
	public int zakup() {			
		return (smestaj.zakup() * brojDana()) + prevoznoSredstvo.zakup();
	}
	
	@Override
	public float otkazi(float povracajNovca) {
		float povracajPrevoza = prevoznoSredstvo.otkazi(povracajNovca);
		float povracajSmestaja = smestaj.otkazi(povracajNovca) * brojDana();
		System.out.println("Povracaj novca za aranzman");
		System.out.println("- Troskovi prevoza su umanjeni za " + povracajPrevoza);
		System.out.println("- Troskovi smestaja su umanjeni za " + povracajSmestaja);
		return povracajPrevoza + povracajSmestaja;
	}
	
	/**
	 * Staticka metoda kreiraj zahteva od korisnika unos podataka koji opisuju aranzman,
	 * kreira objekat klase aranzman i dodaje ga u odgovarajucu datoteku
	 */
	public static void kreiraj() {
		System.out.println("\nKreiranje aranzmana");
		boolean osiguran = Unos.unesiBoolean("Da li je aranzman osiguran");
		boolean grupni = Unos.unesiBoolean("Da li je aranzman grupni");
		Adresa polaznaAdresa = Adresa.baza(); 
		if (polaznaAdresa == null) {
			return;
		}
		System.out.println("- Svi aranzmani polaze od turisticke agencije");
		System.out.println(polaznaAdresa);
		/* Adresa odredista mora biti razlicita od adrese polaska */
		System.out.println("Izbor odredisne adrese");
		Adresa odredisnaAdresa = polaznaAdresa;
		do {
			odredisnaAdresa = Adresa.izaberi();
			if (odredisnaAdresa.equals(polaznaAdresa)) {
				System.out.println("- Adresa odredista mora biti razlicita od adrese polaska");
			}
		} while (odredisnaAdresa.equals(polaznaAdresa));
		/* Datum polaska mora da bude nakon trenutnog datuma */		
		LocalDate polazak = null;
		do {
			polazak = Unos.unesiDatum("Unesite datum polaska");
			if (polazak.compareTo(LocalDate.now()) < 1) { 
				polazak = null;
				System.out.println("- Datum povratka mora biti nakon danasnjeg datuma");
			}
		} while (polazak == null);
		/* Datum povratka mora da bude nakon datuma polaska */
		LocalDate povratak = null;
		do {
			povratak = Unos.unesiDatum("Unesite datum povratka");
			if (povratak.compareTo(polazak) < 1) { 												
				povratak = null;
				System.out.println("- Datum povratka mora biti nakon polaska");
			}
		} while (povratak == null);
		/* Korisnik moze da sam da kreira prevozno sredstvo ili da izabere neko od postojecih */
		boolean kreiranje = Unos.unesiBoolean("Da li zelite da sami kreirate prevozno sredstvo");
		PrevoznoSredstvo prevoznoSredstvo = (kreiranje) ? PrevoznoSredstvo.kreiraj(polaznaAdresa, odredisnaAdresa) 
														: PrevoznoSredstvo.izaberi(polaznaAdresa, odredisnaAdresa);
		if (prevoznoSredstvo == null) {
			return;
		}
		/* Korisnik moze sam da kreira smestaj ili da izaberi neki od postojecih */
		kreiranje = Unos.unesiBoolean("Da li zelite da sami kreirate smestaj");
		Smestaj smestaj = (kreiranje) ? Smestaj.kreiraj(odredisnaAdresa) : Smestaj.izaberi(odredisnaAdresa);
		if (smestaj == null) {
			return;
		}
		/* Kreiranje novog aranzmana sa izabranim atributima */
		System.out.println("- Uspesno ste kreirali aranzman");
		Datoteke.<Aranzman>dodaj((new Aranzman(osiguran, grupni, polazak, povratak, prevoznoSredstvo, smestaj)), "aranzmani.csv");
	}
	
	/**
	 * Staticka metoda obrisi prikazuje korisinku sve aranzmane definisane u datoteci
	 * i omogucava mu brisanje jednog od tih objekata
	 */
	public static void obrisi() {
		System.out.println("\nBrisanje aranzmana");
		ArrayList<Aranzman> aranzmani = Datoteke.<Aranzman>ucitaj((new Aranzman()), "aranzmani.csv");
		if (aranzmani.size() == 0) {
			System.out.println("- Ne mozete da obrisete aranzman jer nijedan ne postoji");
			return;
		}	
		int izbor = Unos.izaberiOpciju("Izaberite aranzman koji zelite da obrisete", aranzmani) - 1; // 0-based indexing
		aranzmani.remove(izbor);
		Datoteke.<Aranzman>sacuvaj(aranzmani, "aranzmani.csv");
		System.out.println("- Uspesno ste obrisali aranzman");
	}
}