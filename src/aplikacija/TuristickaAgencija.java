/**
 * 
 */
package aplikacija;

import java.util.ArrayList;

import korisnik.Agent;
import korisnik.Klijent;
import korisnik.Korisnik;
import podaci.Datoteke;
import podaci.Unos;
import rezervacije.Aranzman;
import rezervacije.Paket;

/** 
 * Klasa turisticka agencija opisana je nazivom, listom klijenata, agenata i paketa
 * Turisticka agencija sadrzi metode koje predstavljaju meni, a korisniku pruzaju
 * mogucnost koriscenja funkcionalnosti aplikacije
 * 
 * @author Bojan Zdelar
 */
public class TuristickaAgencija {
	private String naziv;
	private ArrayList<Klijent> klijenti;
	private ArrayList<Agent> agenti;
	private ArrayList<Paket> paketi;
		
	public TuristickaAgencija() {}
	
	public TuristickaAgencija(String naziv) {
		this.naziv = naziv;
		this.klijenti = Datoteke.<Klijent>ucitaj(new Klijent(), "klijenti.csv"); 
		this.agenti = Datoteke.<Agent>ucitaj(new Agent(), "agenti.csv");
		this.paketi = new ArrayList<Paket>();
	}
		
	public String getNaziv() {
		return naziv;
	}
	
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	public ArrayList<Klijent> getKlijenti() {
		return klijenti;
	}
	
	public void setKlijenti(ArrayList<Klijent> klijenti) {
		this.klijenti = klijenti;
	}
	
	public ArrayList<Agent> getAgenti() {
		return agenti;
	}
	
	public void setAgenti(ArrayList<Agent> agenti) {
		this.agenti = agenti;
	}
		
	public void dobroDosli() {
		System.out.println("Dobro dosli u turisticku agenciju " + naziv);
	}
	
	public void dovidjenja() {
		System.out.println("\nHvala vam na poverenju!");
	}
	
	/**
	 * Metoda meni klijenta prijavljenom klijentu omogucava uplatu novca, 
	 * kreiranje paketa, zakupljivanje paketa i odjavu korisnika
	 * 
	 * @param klijent prijavljeni
	 */
	public void meniKlijent(Klijent klijent) {
		System.out.println("\nMeni klijenta");
		String[] opcije = new String[] {"Uplata novca", "Kreiranje paketa", "Zakupljivanje paketa", "Otkazivanje paketa", "Odjava korisnika"};
		int izbor = Unos.izaberiOpciju("Izaberite opciju", opcije);
		switch (izbor) {
		/* Uplata novca */
		case 1:
			klijent.uplatiNovac();
			Datoteke.<Klijent>sacuvaj(klijenti, "klijenti.csv");
			break;
		/* Kreiranje paketa */
		case 2:
			Paket paket = Paket.kreiraj(Datoteke.<Aranzman>ucitaj((new Aranzman()), "aranzmani.csv"), klijent);
			if (paket != null) {
				paketi.add(paket);
			}
			break;
		/* Zakupljivanje paketa */
		case 3:
			Paket.zakupi(paketi, klijent);
			Datoteke.<Klijent>sacuvaj(klijenti, "klijenti.csv");
			break;
		/* Otkazivanje paketa */
		case 4:
			Paket.otkazi(paketi, klijent);
			Datoteke.<Klijent>sacuvaj(klijenti, "klijenti.csv");
			break;
		/* Odjava korisnika */
		case 5:
			return;
		}
		/* Rekurzija omogucava ostanak u meniju dokle god korisnik ne zahteva odjavu */
		meniKlijent(klijent);
	}
	
	/**
	 * Metoda meni agenta prijavljenom agentu omogucava kreiranje aranzmana, brisanje aranzmana, 
	 * kreiranje paketa, brisanje paketa i odjavu korisnika
	 * 
	 * @param agent prijavljeni
	 */
	public void meniAgent(Agent agent) {
		System.out.println("\nMeni agenta");
		String[] opcije = new String[] { "Kreiranje aranzmana", "Brisanje aranzmana", "Kreiranje paketa", "Brisanje paketa", "Odjava korisnika"};
		int izbor = Unos.izaberiOpciju("Izaberite opciju", opcije);
		switch (izbor) {
		/* Kreiranje aranzmana */
		case 1:
			Aranzman.kreiraj();
			break;
		/* Brisanje aranzmana */
		case 2:
			Aranzman.obrisi();
			break;
		/* Kreiranje paketa */
		case 3:
			Paket paket = Paket.kreiraj(Datoteke.<Aranzman>ucitaj((new Aranzman()), "aranzmani.csv"), agent);
			if (paket != null) {
				paketi.add(paket);
			}
			break;
		/* Brisanje paketa */
		case 4:
			Paket.obrisi(paketi);
			break;
		/* Odjava korisnika */
		case 5:
			return; 
		}
		/* Rekurzija omogucava ostanak u meniju dokle god korisnik ne zahteva odjavu */
		meniAgent(agent);
	}
	
	/**
	 * Metoda meni omogucava korisniku prijavu u turisticku agenciju kao 
	 * klijent ili agent, i izlazak iz aplikacije
	 */
	public void meni() {
		System.out.println("\nKorisnicki meni");
		String[] opcije = new String[] { "Prijava korisnika", "Izlazak iz aplikacije"};
		int izbor = Unos.izaberiOpciju("Izaberite opciju", opcije);
		/* Prijava korisnika */
		if (izbor == 1) {
			Korisnik korisnik = Korisnik.prijava(this);
			if (korisnik instanceof Klijent) {
				meniKlijent((Klijent) korisnik);
			} 
			else if (korisnik instanceof Agent) {
				meniAgent((Agent) korisnik);
			}
		} 
		/* Izlazak iz aplikacije */
		else if (izbor == 2) {
			return;
		}
		/* Rekurzija omogucava ostanak u meniju dokle god korisnik ne zahteva izlaz iz aplikacije */
		meni();
	}
 }