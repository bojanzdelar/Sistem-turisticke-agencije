/**
 * 
 */
package podaci;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/** 
 * Utility klasa unos sadrzi staticke metode za unos raznih tipova podataka uz otpornost na greske u radu
 * 
 * @author Bojan Zdelar
 */
public final class Unos {
	private static Scanner scanner = new Scanner(System.in);
	
	private Unos() {
        throw new UnsupportedOperationException();
	}
	
	/**
	 * Staticka metoda unesi boolean vraca vrednost true ili false u zavisnosti od unosa korisnika
	 * 
	 * @param pitanje koje se postavlja korisniku pri unosu
	 * @return true ili false
	 */
	public static boolean unesiBoolean(String pitanje) {
		String unos;
		do {
			System.out.print(pitanje + " (da/ne)? ");
			unos = scanner.nextLine().toLowerCase();
		} while ((!unos.equals("da")) && (!unos.equals("ne")));
		return (unos.equals("da")) ? true : false;
	}
	
	/**
	 * Staticka metoda unesi string vraca neprazan string
	 * 
	 * @param zahtev koji se postavlja korisniku pri unosu
	 * @return neprazan string
	 */
	public static String unesiString(String zahtev) {
		String unos;
		do {
			System.out.print(zahtev + ": ");
			unos = scanner.nextLine();
		} while (unos.equals(""));
		return unos;
	}
	
	/**
	 * Staticka metoda unesi prirodan broj vraca ceo broj iz raspona od 1 do zadatog limita
	 * 
	 * @param zahtev koji se postavlja korisniku pri unosu
	 * @param limit maksimalna vrednost koja moze da se unese
	 * @return ceo broj izmedju 1 i zadatog limita
	 */
	public static int unesiPrirodanBroj(String zahtev, int limit) {
		int unos = -1;
		do {
			System.out.print(zahtev + " (1-" + limit + "): ");
			try {
				unos = scanner.nextInt();
			} catch (InputMismatchException e){
				// Ponavljamo unos ukoliko je neodgovarajuci
			}
			if (scanner.hasNextLine()) {
				scanner.nextLine();
			}
		} while (unos < 1 || unos > limit);
		return unos;
	}
	
	/**
	 * Staticka metoda unesi decimalan broj vraca decimalan broj iz raspona od 0 do zadatog limita
	 * 
	 * @param zahtev koji se postavlja korisniku pri unosu
	 * @param limit maksmimalna vrednost koja moze da se unese
	 * @return decimalan broj izmedju 0 i zadatog limita
	 */
	public static float unesiDecimalanBroj(String zahtev, float limit) {
		float unos = -1;
		do {
			System.out.print(zahtev + " (0-" + limit + "): ");
			try {
				unos = scanner.nextFloat();
			} catch (InputMismatchException e){
				// Ponavljamo unos ukoliko je neodgovarajuci
			}
			if (scanner.hasNextLine()) {
				scanner.nextLine();
			}
		} while (unos < 0 || unos > limit);
		return unos;
	}
	
	/**
	 * Staticka metoda izaberi opciju prikazuje korisniku sve opcije koje moze da izabere i vraca
	 * izabranu opciju u vidu celog broja u rasponu od 1 do broj opcija
	 * 
	 * @param zahtev koji se postavlja korisniku pri unosu
	 * @param opcije ponudjene za izbor
	 * @return ceo broj (izabrana opcija) u rasponu od 1 do broj opcija
	 */
	public static int izaberiOpciju(String zahtev, String[] opcije) {
		for (int i = 0; i < opcije.length; i++) {
			System.out.println((i + 1) + ". " + opcije[i]);
		}
		int unos = -1;
		do {
			System.out.print(zahtev + " (1-" + opcije.length + "): ");
			try {
				unos = scanner.nextInt();
			} catch (InputMismatchException e){
				// Ponavljamo unos ukoliko je neodgovarajuci
			}
			if (scanner.hasNextLine()) {
				scanner.nextLine();
			}
		} while (unos < 1 || unos > opcije.length);
		return unos;
	}
	
	/**
	 * Staticka metoda izaberi opciju prikazuje korisniku sve opcije koje moze da izabere i vraca
	 * izabranu opciju u vidu celog broja u rasponu od 1 do broj opcija
	 * 
	 * @param <T> genericki tip liste opcija
	 * @param zahtev koji se postavlja korisniku pri unosu
	 * @param opcije ponudjene za izbor
	 * @return ceo broj (izabrana opcija) u rasponu od 1 do broj opcija
	 */
	public static <T> int izaberiOpciju(String zahtev, ArrayList<T> opcije) {
		for (int i = 0; i < opcije.size(); i++) {
			System.out.println((i + 1) + ". " + opcije.get(i));
		}
		int unos = -1;
		do {
			System.out.print(zahtev + " (1-" + opcije.size() + "): ");
			try {
				unos = scanner.nextInt();
			} catch (InputMismatchException e){
				// Ponavljamo unos ukoliko je neodgovarajuci
			}
			if (scanner.hasNextLine()) {
				scanner.nextLine();
			}
		} while (unos < 1 || unos > opcije.size());
		return unos;
	}
	
	/**
	 * Staticka metoda unesi datum vraca ispravan datum
	 * 
	 * @param zahtev koji se postavlja korisniku pri unosu
	 * @return datum
	 */
	public static LocalDate unesiDatum(String zahtev) {
		LocalDate datum = null;
		do {
			System.out.print(zahtev + " (GGGG-MM-DD): ");
			String unos = scanner.nextLine();
			try {
				datum = LocalDate.parse(unos);	
			} catch (DateTimeException e) {
				// Ponavljamo unos ukoliko je neodgovarajuci
			}
		} while (datum == null);
		return datum;
	}	
}