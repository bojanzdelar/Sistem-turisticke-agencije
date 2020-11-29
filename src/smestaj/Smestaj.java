/**
 * 
 */
package smestaj;

import java.util.ArrayList;

import lokacija.Adresa;
import podaci.CSV;
import podaci.Datoteke;
import podaci.Unos;
import rezervacije.Zakupljivo;

/** 
 * Apstraktna klasa smestaj implementira interfejse zakupljivo i CSV, a opisana je povrsinom smestaja, brojem
 * kreveta, tipom pansiona i adresom na koja se nalazi
 * Smestaj sadrzi metode izaberi i kreiraj koje vracaju objekte ove klase
 * 
 * @author Bojan Zdelar
 */
public abstract class Smestaj implements Zakupljivo, CSV<Smestaj> {
	public enum TipPansiona { 
		NOCENJE("nocenje", 1200), NOCENJE_SA_DORUCKOM("nocenje sa doruckom", 1700), POLUPANSION("polupansion", 2100), 
		PUN_PANSION("pun pansion", 2800), ALL_INCLUSIVE("all inclusive", 3600);
		
		private final String ime;
		private final int cena;

		private TipPansiona(final String ime, final int cena) {
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
	
	private float povrsina;
	private int brojKreveta;
	private TipPansiona tipPansiona;
	private Adresa adresa;
	
	public Smestaj() {}
	
	public Smestaj(float povrsina, int brojKreveta, TipPansiona tipPansiona, Adresa adresa) {
		this.povrsina = povrsina;
		this.brojKreveta = brojKreveta;
		this.tipPansiona = tipPansiona;
		this.adresa = adresa;
	}
	
	public float getPovrsina() {
		return povrsina;
	}
	
	public void setPovrsina(float povrsina) {
		this.povrsina = povrsina;
	}
	
	public int getBrojKreveta() {
		return brojKreveta;
	}
	
	public void setBrojKreveta(int brojKreveta) {
		this.brojKreveta = brojKreveta;
	}
	
	public TipPansiona getTipPansiona() {
		return tipPansiona;
	}
	
	public void setTipPansiona(TipPansiona tipPansiona) {
		this.tipPansiona = tipPansiona;
	}
	
	public Adresa getAdresa() {
		return adresa;
	}
	
	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}
	
	@Override
	public String toString() {
		return "povrsina: " + povrsina + "; broj kreveta:" + brojKreveta 
				+ "; tip pansiona: " + tipPansiona + "; adresa: " + adresa;
	}
	
	@Override
	public String serijalizuj() {
		return povrsina + "," + brojKreveta + "," + tipPansiona.name() + "," + adresa.serijalizuj();
	}
	
	/**
	 * Staticka metoda izaberi nudi korisniku izbor odgovarajucih smestaja definisanih u datoteci i vraca izabrani smestaj
	 * 
	 * @param adresa
	 * @return izabrani smestaj (apartman ili hotel) ili null
	 */
	public static Smestaj izaberi(Adresa adresa) {
		ArrayList<Smestaj> smestaji = new ArrayList<Smestaj>();
		ArrayList<Smestaj> odgovarajuciSmestaji = new ArrayList<Smestaj>();
		ArrayList<Apartman> apartmani = Datoteke.<Apartman>ucitaj((new Apartman()), "apartmani.csv");
		ArrayList<Hotel> hoteli = Datoteke.<Hotel>ucitaj((new Hotel()), "hoteli.csv");
		if (apartmani != null) {
			smestaji.addAll(apartmani);
		}
		if (hoteli != null) {
			smestaji.addAll(hoteli);
		}
		if (smestaji.size() == 0) {
			System.out.println("- Ne postoji nijedan smestaj u datotekama");
			return null;
		} 				      
		for (Smestaj smestaj : smestaji) {
			if (smestaj.adresa.equals(adresa)) {
				odgovarajuciSmestaji.add(smestaj);
			}
		}
		if (odgovarajuciSmestaji.size() == 0) {
			System.out.println("- Ne postoji nijedan smestaj u datotekama koji odgovara unetoj adresi");
			return null;
		}
		int izbor = Unos.izaberiOpciju("Izaberite prevozno sredstvo koje zelite da iskoristite", odgovarajuciSmestaji) - 1;  // 0-based indexing
		return odgovarajuciSmestaji.get(izbor);
	}
	
	/**
	 * Staticka metoda kreiraj zahteva od korisnika unos podataka koji opisuju smestaj i vraca kreirani smestaj
	 * 
	 * @return kreirani smestaj
	 */
	public static Smestaj kreiraj(Adresa adresa) {
		String[] opcije = new String[] {"Apartman", "Hotel"}; 
		int izborSmestaj = Unos.izaberiOpciju("Izaberite tip smestaja", opcije);
		float povrsina = Unos.unesiDecimalanBroj("Unesi povrsinu(kvadratni metri) smestaja", 200);
		int brojKreveta = Unos.unesiPrirodanBroj("Unesi broj kreveta u smestaju", 8);
		opcije = new String[] {"Nocenje", "Nocenje sa doruckom", "Polupansion", "Pun pansion", "All inclusive"};
		int izborPansion = Unos.izaberiOpciju("Izaberite tip pansiona", opcije) - 1; // 0-based indexing
		TipPansiona tipPansiona = TipPansiona.values()[izborPansion];
		Smestaj smestaj = null;
		/* Apartman */
		if (izborSmestaj == 1) {
			smestaj = new Apartman(povrsina, brojKreveta, tipPansiona, adresa);
			Datoteke.<Smestaj>dodaj(smestaj, "apartmani.csv");
		}
		/* Hotel */
		else if (izborSmestaj == 2) {
			smestaj = Hotel.kreiraj(povrsina, brojKreveta, tipPansiona, adresa);
			Datoteke.<Smestaj>dodaj(smestaj, "hoteli.csv");
		}
		return smestaj;
	}
	
	@Override 
	public int zakup() {
		return tipPansiona.getCena();
	}
	
	@Override 
	public float otkazi(float povracajNovca) {
		return povracajNovca * zakup();
	}
}