/**
 * 
 */
package lokacija;

import java.util.ArrayList;

import podaci.CSV;
import podaci.Datoteke;
import podaci.Unos;

/**
 * Klasa adresa implementira interfejs CSV, 
 * opisana je imenom ulice, brojem objekta u ulici i mestom u kojem se nalazi
 * Adresa sadrzi metodu izaberi za izbor adrese definisanih u datoteci
 *  
 * @author Bojan Zdelar
 */
public class Adresa implements CSV<Adresa> {
	private String ulica;
	private int broj;
	private Mesto mesto;
	
	public Adresa() {}
	
	public Adresa(String ulica, int broj, Mesto mesto) {
		this.ulica = ulica;
		this.broj = broj;
		this.mesto = mesto;
	}
	
	public String getUlica() {
		return ulica;
	}
	
	public void setUlica(String ulica) {
		this.ulica = ulica;
	}
	
	public int getBroj() {
		return broj;
	}
	
	public void setBroj(int broj) {
		this.broj = broj;
	}
	
	public Mesto getMesto() {
		return mesto;
	}
	
	public void setMesto(Mesto mesto) {
		this.mesto = mesto;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (!(obj instanceof Adresa))) {
			return false;
		}
		
		Adresa adresa = (Adresa) obj;
		
		if ((ulica.equals(adresa.ulica)) && (broj == adresa.broj) && (mesto.equals(adresa.mesto))) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return ulica + " " + ((broj != 0) ? broj : "bb")  + ", " + mesto.toString();
	}
	
	@Override
	public String serijalizuj() {
		return ulica + "," + broj + "," + mesto.serijalizuj();
 	}

	@Override
	public Adresa deserijalizuj(String CSV) {
		String[] podaci = CSV.split(",");
		Mesto mesto = Mesto.pronadji(podaci[2]);		
		return (mesto != null) ? new Adresa(podaci[0], Integer.parseInt(podaci[1]), mesto) : null; 
	}
	
	/**
	 * Staticka metoda baza vraca prvu adresu definisanu u datoteci koja predstavlja adresu turisticke agencije
	 * i tacku od koje sva prevozna sredstva polaze
	 * 
	 * @return
	 */
	public static Adresa baza() {
		ArrayList<Adresa> adrese = Datoteke.<Adresa>ucitaj((new Adresa()), "adrese.csv");
		if (adrese.size() <= 1) {
			System.out.println("- Nedovoljan broj adresa je definisan u datoteci"); 
			return null;
		} 
		return adrese.get(0);
	}
	
	/**
	 * Staticka metoda izaberi ucitava adrese definisane u datoteci i pruza korisniku mogucnost izbora jedne od njih
	 * 
	 * @return izabrana adresa
	 */
	public static Adresa izaberi() {
		ArrayList<Adresa> adrese = Datoteke.<Adresa>ucitaj((new Adresa()), "adrese.csv");
		if (adrese.size() <= 1) {
			System.out.println("- Nedovoljan broj adresa je definisan u datoteci"); 
			return null;
		}
		int izbor = Unos.izaberiOpciju("Izaberite redni broj adrese", adrese) - 1; // 0-based indexing
		return adrese.get(izbor); 
	}
}