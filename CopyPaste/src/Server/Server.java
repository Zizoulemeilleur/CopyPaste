package Server;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Die Klasse Server erstellt virtuelle Server
 * und enth‰lt die Serverfunktionen
 * @author Andreas Hess, Romeo Martinovic, Philip Wangler
 * @version 1.0 ADRELI_GUI
 * 
 */




public class Server extends Thread {
	
	//Streams
	OutputStream out;
	InputStream in; 
	DataInputStream dataIn;
	DataOutputStream dataOut; 
	ObjectOutputStream oos;
	ObjectInputStream ois;

	//Array Person
	public ArrayList<Person> daten;
	
	//Menue-Eingabe einlesen
	int auswahl;
	
	//IP-Adresse des Clients
	InetAddress ip;	
	
	
	/**
	 * Konstruktor nimmt In- und Outputstreams von der Klasse Server_Controller
	 * entgegen
	 * @param in
	 * @param out
	 * @param ip
	 */
	public Server(InputStream in, OutputStream out, InetAddress ip){
		this.in = in;
		this.out = out;
		this.ip = ip;
	}

	
	/**
	 * Zeigt Client an zu dem eine Verbindung aufgebaut wurde
	 * und empf‰ngt vom Client die Auswahl des Men¸s
	 */
	public void run() {
		
		Server_Gui.logUpdate("Verbindung zu Client " + ip + " " 
		+ ip.getHostName() +" aufgebaut\n");
		
		dataIn = new DataInputStream(in);
		dataOut = new DataOutputStream(out);
	
		get();
		
	}
	
	
	/**
	 * Ruft ¸ber eine Switch-Case-Verzweigung die Men¸punkte auf, die Auswahl 
	 * wird von dem Client empfangen
	 */
	@SuppressWarnings("unchecked")
	public void get(){
				
		try{		
			auswahl = dataIn.read();
			protokoll();
		}
		catch(Exception e){}
		
		
		//Nur bei Auswahl von 3, 4 oder 6 ruft der Write-Thread 
		//seine Methoden auf
		//Es wird immer wieder von der View-Klasse die Auswahl empfangen
		switch(auswahl){
			case 1:	
				get();
				break; 
				
			case 2: 
				get();
				break;
				
			case 3: 
				try{
					ois = new ObjectInputStream(in);
					daten = (ArrayList<Person>) ois.readObject();
					
				}catch(Exception e){}
				
				// Wenn in CSV abgespeichert werden soll
				if(Server_Gui.datenbank == false){
					safeDatei();
				}
				// Sonst in Datenbank
				else{
					//safeDb();
				}
				
				Server_Gui.logUpdate("speichern\n");
				get();
				break;
				
			case 4:					
				laden();
				try{
					
					oos = new ObjectOutputStream(out);
					oos.writeObject(daten);
					oos.flush();
					
				}catch(Exception e){}
				
				Server_Gui.logUpdate("laden\n");
		
				get();
				break;
				
			case 5: 
				get();
				break;
				
			case 6:
				loeschen();
				get();
				break;
				
			case 7: 
				Server_Gui.logUpdate("Verbindung zu Client " + ip 
						+ " abgebaut\n");
				Server_Gui.minClient();
				Thread.interrupted();
				break;
				
			default: 
				get();
				break;
		}
	}//Ende Get Methode
	
	
	/**
	 * Erstellt ein Protokoll von welchem Client wann welche Befehle 
	 * ausgef¸hrt werden, generiert logfile Eintr‰ge
	 */
	public void protokoll(){
		SimpleDateFormat formatter = new SimpleDateFormat
				("yyyy.MM.dd - HH:mm:ss "); 
        Date currentTime = new Date(); 
        
        if(auswahl < 7 || auswahl > 1){
	        //in TextArea schreiben von GUI
	        Server_Gui.logUpdate(formatter.format(currentTime) + ip 
	        		+ " Auswahl: " + auswahl + "\n");
	        
	        //in log File schreiben
			FileWriter out;
			try{
				out = new FileWriter("log.csv", true);
				out.write(formatter.format(currentTime) + " ; Ger‰t ; " 
				+ ip + " ; Auswahl ; " + auswahl);
				out.append(System.getProperty("line.separator"));
				out.close();
			}catch(Exception e){
				e.printStackTrace();
			}
        }
        
	}
	
	
	/**
	 * Schreibt und speichert alle Daten der ArrayList untereinander in eine 
	 * Datei
	 */
	public void safeDatei(){		
		
		//In Datei schreiben
		try{
			FileWriter datei; 
	    	/*Alle Daten (bzw. Personen) der ArraysList 
	    	 * in die Datei untereinander speichern
	    	 */
	    	for(Person p : daten)
	    	{	   
	    		datei = new FileWriter(Server_Gui.getFile(), true);
	    		datei.write(	p.name + ";" + p.vname + ";" 
	    					+ p.anrede + ";" + p.strasse + ";" 
	    					+ p.plz + ";" + p.ort + ";" 
	    					+ p.telefon + ";" + p.fax + ";" 
	    					+ p.bem );
	    		datei.append(System.getProperty("line.separator"));
	    		datei.close();//Writer-Stream wieder schliessen
			}
		}
		catch(Exception e){
			System.out.println("Fehler: " + e.getMessage());
		}

		//Ende in Datei schreiben
		
	}//Ende safe()-Methode
	
	
	/**
	 * Ruft bereits vorhandene Datei auf und ¸bergibt sie an den Client
	 */
	public void laden(){	
		try{			
			try
		    {			    	
		    	//eindim. Array deklarieren als Zwischenspeicher
				String[] liste =  null;
				
				//ArrayList wird gefuellt
	            daten = new ArrayList<Person>();
				
				FileReader fr = new FileReader(Server_Gui.getFile()); 
				BufferedReader br = new BufferedReader(fr);
				
		    	String zeile = null;
		    	while((zeile = br.readLine()) != null){
		    		liste = zeile.split(";");
		    		Person p = new Person(	liste[0], liste[1], 
							liste[2], liste[3], 
							liste[4], liste[5], 
							liste[6], liste[7], 	
							liste[8]);
		    		daten.add(p);
		    	}
		    	
	            fr.close();
	            br.close();			    
				
		    }
		    catch(IOException e){
		    	e.printStackTrace();
		    }
		}
		catch (Exception e){
			Server_Gui.logUpdate("Datei ist leer\n");
		}
		
	}//Ende laden()-Methode
	
	
	/**
	 * Diese Methode lˆscht eine vorhande Adressliste bzw. die Datei in der sie
	 * gespeichert ist
	 */
	public void loeschen(){
	
		try{			
			File datei = new File(Server_Gui.getFile());
			if (datei.exists()) {
			      datei.delete();//Datei loeschen
			}
		}
		catch(Exception e){
			System.out.println("Fehler: " + e.getMessage());
		}
	}//Ende Loeschen()-Methode
	
}
