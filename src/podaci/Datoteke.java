/**
 * 
 */
package podaci;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/** 
 * Utility klasa datoteke sadrzi staticke metode za ucitavanje, dodavanje i cuvanje podataka u datotekama
 * 
 * @author Bojan Zdelar
 */
public final class Datoteke {
	private Datoteke() {
        throw new UnsupportedOperationException();
	}
	
	/**
	 * Staticka genericka metoda ucitaj vracu listu objekata ucitanih iz fajla 
	 * sa zadatim imenom ili praznu listu
	 * 
	 * @param <T> genericki tip koji implementira interfejs CSV
	 * @param t objekat nad kojim se poziva metoda deserijalizacije
	 * @param imeFajla koji se ucivata
	 * @return lista ucitanih objekata ili prazna lista
	 */
	public static <T extends CSV<?>> ArrayList<T> ucitaj(T t, String imeFajla) {
		ArrayList<T> lista = new ArrayList<T>();
		try {			
			BufferedReader reader = new BufferedReader(new FileReader("data/" + imeFajla));
			String linija = reader.readLine();
			while(linija != null) {
				@SuppressWarnings("unchecked")
				T element = (T) t.deserijalizuj(linija);
				if (element != null) {
					lista.add(element); 
				}
			    linija = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("- Datoteka " + imeFajla + " nije pronadjena");
		} catch (Exception e) {
			System.out.println("- Doslo je do greske pri ucitavanju datoteke " + imeFajla);
		}
		return lista;
	}
	
	/**
	 * Staticka genericka metoda dodaj dodaje zadati element u datoteku sa zadatim imenom fajla
	 * 
	 * @param <T> genericki tip koji implementira interfejs CSV
	 * @param element objekat koji se serijalizuje
	 * @param imeFajla u koji se serijalizovani objekat dodaje
	 */
	public static <T extends CSV<?>> void dodaj(T element, String imeFajla) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("data/" + imeFajla, true));
			writer.write(element.serijalizuj() + "\n");
			writer.close();
		} catch (Exception e) { 
			System.out.println("- DOslo je do greske pri dodavanju podataka u datoteku " + imeFajla);
		} 
	}
	
	/**
	 * Staticka genericka metoda sacuvaj zapisuje sve elemente zadate liste u fajl sa zadatim imenom
	 *  
	 * @param <T> genericki tip koji implementira interfejs CSV
	 * @param lista ciji se elementi serijalizuj i cuvaju u datoteku
	 * @param imeFajla u koji se cuvaju elementi liste
	 */
	public static <T extends CSV<?>> void sacuvaj(ArrayList<T> lista, String imeFajla) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("data/" + imeFajla));
			for (T element : lista) {
				writer.write(element.serijalizuj() + "\n");
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("- Doslo je do greske pri cuvanju podataka u datoteku "  + imeFajla);
		}
	}
}
