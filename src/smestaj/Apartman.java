/**
 * 
 */
package smestaj;

import java.util.Arrays;

import lokacija.Adresa;

/** 
 * Klasa apartman nasledjuje klasu smestaj
 * 
 * @author Bojan Zdelar
 */
public class Apartman extends Smestaj {
	public Apartman() {}
	
	public Apartman(float povrsina, int brojKreveta, TipPansiona tipPansiona, Adresa adresa) {
		super(povrsina, brojKreveta, tipPansiona, adresa);
	}
	
	@Override
	public String serijalizuj() {
		return "apartman," + super.serijalizuj();
	}
	
	@Override
	public Apartman deserijalizuj(String CSV) {
		String[] podaci = CSV.split(",");

		String podaciAdrese = String.join(",", Arrays.copyOfRange(podaci, 4, 7));
		Adresa adresa = (new Adresa()).deserijalizuj(podaciAdrese);
		
		return new Apartman(Float.parseFloat(podaci[1]), Integer.parseInt(podaci[2]), TipPansiona.valueOf(podaci[3]), adresa);
	}
	
	@Override
	public String toString() {
		return "Apartman [" + super.toString() + "]";
	}
}
