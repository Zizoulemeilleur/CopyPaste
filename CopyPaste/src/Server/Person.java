package Server;
import java.io.Serializable;

/**
 * Die Klasse speichert Personenobjekte mit den dazugehˆrigen Attributen
 * @author Andreas Hess, Romeo Martinovic, Philip Wangler
 * @version 1.0 ADRELI_GUI
 * 
 */




//Klasse Person speichert Personen mit den Attributen
@SuppressWarnings("serial")
public class Person implements Serializable {
	String name, vname, anrede, strasse, plz, ort, telefon, fax, bem;
	public Person(	String name, String vname, String anrede, 
					String strasse,String plz, String ort,
					String telefon, String fax, String bem){
		this.name = name;
		this.vname = vname;
		this.anrede = anrede;
		this.strasse = strasse;
		this.plz = plz;
		this.ort = ort;
		this.telefon = telefon;
		this.fax = fax;
		this.bem = bem;
	}
}
