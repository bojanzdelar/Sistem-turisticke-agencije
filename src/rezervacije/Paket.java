/**
 * 
 */
package rezervacije;

import java.time.LocalDate;
import java.util.ArrayList;

import korisnik.Agent;
import korisnik.Klijent;
import korisnik.Korisnik;
import podaci.Unos;

/**
 * Klasa paket implementira interfejs zakupljvio, a opisana je decimalnom vrednoscu povracaj novca u slucaju otkazivanja,
 * listom aranzmana koji su ukljuceni u paket, korisnikom koji je kreirao paket i klijentom koji ga je zakupio
 * Paket sadrzi metode kreiraj, zakupi, otkazi i obrisi koje kreiraju, menjaju ili brisu paket
 *  
 * @author Bojan Zdelar
 */
public class Paket implements Zakupljivo {
	private float povracajNovca; 
	private ArrayList<Aranzman> aranzmani;
	private Korisnik korisnik; 
	private Klijent klijent;
	
	public Paket() {}
	
	public Paket(float povracajNovca, ArrayList<Aranzman> aranzmani, Korisnik korisnik, Klijent klijent) {
		this.povracajNovca = povracajNovca;
		this.aranzmani = aranzmani;
		this.korisnik = korisnik;
		this.klijent = klijent;
	}
	
	public float getpovracajNovca() {
		return povracajNovca;
	}
	
	public void setpovracajNovca(float povracajNovca) {
		this.povracajNovca = povracajNovca;
	}
	
	public ArrayList<Aranzman> getAranzmani() {
		return aranzmani;
	}
	
	public void setAranzmani(ArrayList<Aranzman> aranzmani) {
		this.aranzmani = aranzmani;
	}
	
	public Korisnik getKorisnik() {
		return korisnik;
	}
	
	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}
	
	public Klijent getKlijent() {
		return klijent;
	}
	
	public void setKlijent(Klijent klijent) {
		this.klijent = klijent;
	}
	
	@Override
	public String toString() {
		String opis = "Paket: povracaj novca: " + povracajNovca * 100 + "%, paket kreirao: " 
					+ korisnik + ", paket zakupio: " + ((klijent == null) ? "niko" : klijent + ",");
		for (Aranzman aranzman : aranzmani) {
			opis += "\n" + aranzman;
		}
		return opis + "\n"; 
	}
	
	/**
	 * Staticka metoda kreiraj zahteva od korisnika da izabere aranzmane koje zeli da 
	 * uvrsti u paket koji kreira i vraca objekat klase paket
	 * 
	 * @param aranzmani ponudjeni
	 * @param korisnik koji kreira paket
	 * @return kreirani paket ili null
	 */
	public static Paket kreiraj(ArrayList<Aranzman> aranzmani, Korisnik korisnik) {
		System.out.println("\nKreiranje paketa");
		if (aranzmani.size() == 0) {
			System.out.println("- Ne mozete da kreirate paket jer nije definisan nijedan aranzman");
			return null;
		}
		/* 
		 * Ukoliko agent kreira paket on bira i procenat povratka novca u slucaju otkazivanja, 
		 * a ako klijent kreira paket onda je ta ta vrednost uvek 50%  
		 */
		float povracajNovca = (korisnik instanceof Agent) 
							  ? Unos.unesiDecimalanBroj("Unesite decimalnu vrednost povratka novca u slucaju otkazivanja", 1f)
							  : 0.5f;
		ArrayList<Aranzman> izabraniAranzmani = new ArrayList<Aranzman>();
		ArrayList<Aranzman> ponudjeniAranzmani = new ArrayList<Aranzman>(aranzmani);
		
		boolean unos = true;
		do {																								   
			int izbor = Unos.izaberiOpciju("Izaberite redni broj aranzman koji zelite da dodate u paket", ponudjeniAranzmani) - 1; // 0-based indexing
			Aranzman aranzman = ponudjeniAranzmani.get(izbor);
			izabraniAranzmani.add(aranzman);
			ponudjeniAranzmani.remove(izbor);
			if (ponudjeniAranzmani.size() == 0) {
				break;
			}
			unos = Unos.unesiBoolean("Da li zelite da izaberete jos aranzmana");
		} while (unos);
		
		System.out.println("- Uspesno ste kreirali paket");
		return new Paket(povracajNovca, izabraniAranzmani, korisnik, null);	
	}
	
	@Override
	public int zakup() {
		int cena = 0;
		for (Aranzman aranzman : aranzmani) {				
			cena += aranzman.zakup();
		}
		return cena;
	}
	
	
	/**
	 * Staticka metoda zakupi daje korisniku izbor izmedju nezakupljenih paketa i zakupljuje
	 * izabrani paket ukoliko korisnik ima dovoljno novca da ga kupi
	 * 
	 * @param paketi 
	 * @param klijent koji zakupljuje paket
	 * @return cena paketa ili 0
	 */
	public static void zakupi(ArrayList<Paket> paketi, Klijent klijent) {
		System.out.println("\nZakupljivanje paketa");
		if (paketi.size() == 0) {
			System.out.println("- Nijedan paket nije kreiran");
			return;
		}
		ArrayList<Paket> slobodniPaketi = new ArrayList<Paket>();
		ArrayList<Integer> indeksiPaketa = new ArrayList<Integer>();
		for (int i = 0; i < paketi.size(); i++) {
			if (paketi.get(i).klijent == null) {
				slobodniPaketi.add(paketi.get(i));
				indeksiPaketa.add(i);
			}
		}
		if (slobodniPaketi.size() == 0) {
			System.out.println("- Nijedan paket nije slobodan");
			return;
		}																		  
		int izbor = Unos.izaberiOpciju("Izaberite paket koji zelite da zakupite", slobodniPaketi) - 1; // 0-based indexing
		Paket paket = slobodniPaketi.get(izbor);
		int cena = paket.zakup();
		if (klijent.getRaspolozivoStanje() >= cena) {
			boolean potvrda = Unos.unesiBoolean("Cena paketa je " + cena + ". Da li potvrdjujete zakup paketa?");
			if (potvrda) {
				paketi.get(indeksiPaketa.get(izbor)).klijent = klijent; 
				klijent.potrosiNovac(cena);
				System.out.println("- Uspesno ste zakupili paket");
			}
		} else {
			System.out.println("- Nemate dovoljno novca da zakupite paket cija je cena " + cena);
		}
	}
	
	@Override
	public float otkazi(float povracajNovca) {
		float cena = 0;
		for (Aranzman aranzman : aranzmani) {
			cena += aranzman.otkazi(povracajNovca);
		}
		return cena;
	}
	
	/**
	 * Staticka metoda otkazi daje korisniku izbor da otkaze jedan od paketa koji je prethodno zakupio
	 * 
	 * @param paketi
	 * @param klijent koji otkazuje paket
	 */
	public static void otkazi(ArrayList<Paket> paketi, Klijent klijent) {
		System.out.println("\nOtkazivanje paketa");
		if (paketi.size() == 0) {
			System.out.println("- Nijedan paket nije zakupljen");
			return;
		}
		ArrayList<Paket> korisnikoviPaketi = new ArrayList<Paket>();
		ArrayList<Integer> indeksiPaketa = new ArrayList<Integer>();
		for (int i = 0; i < paketi.size(); i++) {
			boolean paketZapocet = false;
			Paket paket = paketi.get(i);
			if (paket.klijent != null && paket.klijent.equals(klijent) ) {
				for (Aranzman aranzman : paketi.get(i).aranzmani) {
					if (LocalDate.now().compareTo(aranzman.getPolazak()) >= 0) {
						paketZapocet = true;
						break;
					}
				}
				/* Ukoliko je u paketu pronadjen aranzman koji je vec poceo iii prosa njega je nemoguce otkazati */
				if (paketZapocet) {
					continue;
				}
				korisnikoviPaketi.add(paketi.get(i));
				indeksiPaketa.add(i);
			}
		}
		if (korisnikoviPaketi.size() == 0) {
			System.out.println("- Nemate nijedan zakupljeni paket koji mozete da otkazete");
			return;
		}																			// 0-based indexing
		int izbor = Unos.izaberiOpciju("Izaberite paket koji zelite da otkazete", korisnikoviPaketi) - 1;
		Paket paket = korisnikoviPaketi.get(izbor);
		float cena = paket.otkazi(paket.povracajNovca);
		klijent.uplatiNovac(cena);
		paketi.get(indeksiPaketa.get(izbor)).klijent = null;
		System.out.println("- Uspesno ste otkazali paket");
	}
	
	/**
	 * Staticka metoda obrisi daju korisniku mogucnost da izbrise neki od prethodno definisanih paketa koji nisu zakupljeni
	 * 
	 * @param paketi
	 */
	public static void obrisi(ArrayList<Paket> paketi) {
		System.out.println("\nBrisanje paketa");
		if (paketi.size() == 0) {
			System.out.println("- Ne postoji nijedan paket");
			return;
		}
		ArrayList<Paket> slobodniPaketi = new ArrayList<Paket>();
		for (Paket paket : paketi) {
			if (paket.klijent == null) {
				slobodniPaketi.add(paket);
			}
		}
		if (slobodniPaketi.size() == 0) {
			System.out.println("- Nijedan paket nije slobodan, moguce je obrisati samo nezakupljene pakete");
			return;
		}																		  // 0-based indexing
		int izbor = Unos.izaberiOpciju("Izaberite paket koji zelite da obrisete", slobodniPaketi) - 1;
		paketi.remove(slobodniPaketi.get(izbor));
		System.out.println("- Uspesno ste obrisali paket");
	}
}