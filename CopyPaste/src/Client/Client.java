package Client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


/**
 * Die Klasse Client beinhaltet die Funktionen des Clients und stellt 
 * die Verbindung zum Server her
 * @author Andreas Hess, Romeo Martinovic, Philip Wangler
 * @version 1.0 ADRELI_GUI
 * 
 */




public class Client{
	
	static Socket client;
	
	//Streams
	static InputStream in;
	static OutputStream out;
	static DataInputStream dataIn;
	static DataOutputStream dataOut;
	static ObjectOutputStream oos;
	static ObjectInputStream ois;
	

	static Scanner scanString = new Scanner(System.in);
	static Scanner scanInt = new Scanner(System.in);
	
	boolean error = false;
	boolean verbindungsError = false;
	
	static String serverIP;
	static int port;
	
	
	//Daten
	static ArrayList<Person> daten = new ArrayList<Person>();
	
	
	/**
	 * Stellt ¸ber Sockets eine Verbindung mit dem Server her.
	 * Kann die Verbindung nicht erstellt werden, 
	 * wird eine Fehlermeldung ausgegeben.
	 * @param p
	 * @param server
	 */
	public void verbinden(int p, String server){
		
		Client.serverIP = server;
		Client.port = p;
		
		try{
			client = new Socket(serverIP, port);
			
			
			in = client.getInputStream();
			out = client.getOutputStream();
			
			dataIn = new DataInputStream(client.getInputStream());
			dataOut = new DataOutputStream(client.getOutputStream());
			
		}
		catch(Exception e){
			error = true;
		}
	}
	
	
	/**
	 * Trennt die Verbindung mit dem Server. Schlieﬂt das Programm und die 
	 * Streams und vermittelt dem Server, dass das Programm beendet wurde.
	 * Kann die Verbindung nicht getrennt werden, wird eine Fehlermeldung 
	 * ausgegeben. Auch der Klasse Client_GUI wird vermittelt, dass die 
	 * Verbindung getrennt wurde.
	 */
	public static void trennen(){
		try {
			dataOut.write(7); //Server senden, dass Programm beendet wurde
			dataOut.flush();
			
			dataIn.close();
			dataOut.close();
		} catch (IOException e) {
			System.out.println("Fehler: " + e.getMessage());
		}
		Client_Gui.verbunden = false;
	}
	
	
	/**
	 * ‹bermittelt dem Server welcher Men¸punkt ausgef¸hrt werden soll. 
	 * @param i Parameter zur Auswahl der Men¸punkte
	 */
	public void senden(int i){
		try{
			dataOut.write(i);
			dataOut.flush();
		}catch(Exception e){
		}
	}
	
	
	/**
	 * Erstellt neues Personen Objekt und nimmt Datens‰tze zu diesem auf. 
	 * H‰ngt das neue Objekt an die Arrayliste daten an.
	 * @param name
	 * @param vname
	 * @param anrede
	 * @param strasse
	 * @param plz
	 * @param ort
	 * @param telefon
	 * @param fax
	 * @param bem
	 */
	public static void aufnehmen(	String name, String vname, String anrede, 
									String strasse,String plz, String ort,
									String telefon, String fax, String bem){
		
		
		Person p = new Person(name, vname, anrede, strasse, plz, ort, telefon, 
				fax, bem);
		daten.add(p);
	}
	
	
	/**
	 * Speichert die aufgenommenen Datens‰tze ¸ber Streams auf dem Server
	 */
	public static void speichern(){
		try {
			
			oos = new ObjectOutputStream(out);
			oos.writeObject(daten);
			oos.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Ladet die Datens‰tze ¸ber Inputstreams in die Arrayliste daten. Wenn es
	 * nicht funtioniert wird eine Fehlermeldung ausgegeben. 
	 */
	@SuppressWarnings("unchecked")
	public static void laden(){
		try {
			
			ois = new ObjectInputStream(in);
			daten = (ArrayList<Person>) ois.readObject();
		
		}catch(Exception e){
			e.getMessage();
		}
	}
	
	
	
	
	/**
	 * Die innere Klasse Sortieren vergleicht Eintr‰ge und ruft die Methode 
	 * sort() auf, welche die Eintr‰ge nach Namen sortiert
	 */
	//ArrayList sortieren
	//Innere Klasse Sortieren
	static class Sortieren implements Comparator<Person>{
		public int compare(Person p1, Person p2){
			return p1.name.compareTo(p2.name);
		}
	}

	
	/**
	 * Sortiert die Eintr‰ge alphabetisch nach Namen.
	 */
	//Methode Sortieren
	public static void sort(){
		
		try{
			//Sortieren aufrufen
			Collections.sort(daten, new Sortieren());
			System.out.println();//Abstand
		}
		catch(NullPointerException npex){
			npex.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}//Ende Sortieren
}
