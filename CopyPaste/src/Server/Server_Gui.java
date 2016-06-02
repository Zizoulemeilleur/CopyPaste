package Server;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;


/**
 * Die Klasse Server_GUI beinhaltet die Modellierung der serverseitigen
 * grafischen Oberfl‰che und die Verbindung zur Klasse Server und derer 
 * Funktionalit‰t 
 * @author Andreas Hess, Romeo Martinovic, Philip Wangler
 * @version 1.0 ADRELI_GUI 
 */




public class Server_Gui extends Frame {
	private static final long serialVersionUID = 1L;
	
	static boolean start = false; //Server ist nicht aktiviert
	private static int anzahl_clients = 0;
	
	// MenueLeiste
	private MenuBar bar;
	private Menu server;
	private MenuItem starten, stoppen;
	
	static Label clients_lbl;
	static TextArea log;
	
	private static int port;
	public static boolean datenbank;
	
	public static String directory;
	public static String file;
	
	
	/**
	 * Der Konstruktor regelt die Formatierung und 
	 * erstellt die grafischen Oberfl‰che
	 * und f¸gt ein Icon hinzu
	 */
	//Konstruktor
	public Server_Gui(){
 
		super("ADRELI - Server");
		this.setLocation(400, 300); // Fenster zentrieren 
		setSize(600, 400); // Fenstergrˆﬂe   
		this.setResizable(false); //feste Fenstergroesse
		this.setLayout(null); // Layout deaktivieren 
		//Icon links oben an der GUI
		Image icon = this.getToolkit().getImage("./hfu.png");   
		this.setIconImage(icon); // Icon hinzuf¸gen
		this.setBackground(Color.LIGHT_GRAY);
		
		
		/**
		 * Diese Methode l‰sst das schlieﬂen der Server-GUI nur zu, wenn der 
		 * Server bereits gestoppt ist
		 */
		//Bei Klick auf Schlieﬂ-Button der GUI
		this.addWindowListener( new WindowAdapter() {    
			public void windowClosing(WindowEvent we) {
				if(start == false){
					beenden();
				}
				else{
					new OK_Dialog(null, "Warnung", "Der Server muss gestoppt"
							+ " werden!");
				}
				
			}   
		});
		
		/*---------------Anfang MenueLeiste---------------*/
		// Init - Menuleiste
		this.bar = new MenuBar();
		
		// Init - Menuepunkte
		this.server = new Menu("Server");
		
		// Init - MenuItems
		this.starten = new MenuItem("Starten");
		this.stoppen = new MenuItem("Stoppen");
		
		// Components der Menuleiste hinzufuegen
		server.add(starten);
		server.addSeparator();
		server.add(stoppen);
		bar.add(server);
		
		// Leiste erzeugen  
		this.setMenuBar(bar);
		/*---------------Ende MenueLeiste---------------*/
		
		// Funktionen der Menuleiste sperren
		if (start == false) { // Server aus 
			stoppen.setEnabled(false); //Stoppen kann nicht ausgewawhlt werden
		} else { // Server an    
			starten.setEnabled(false);
		}  
			
		
		/**
		 * Die Items werden mit Listeners hinterlegt. Beim Klick auf die Items
		 * werden die jeweiligen Methoden ausgef¸hrt
		 */
		/*---------------Listeners---------------*/
		//Start_Listener
		starten.addActionListener(
				new ActionListener() {    
				  public void actionPerformed(ActionEvent ae) {     
					  	starten();    
					  }   
				}
		);	
		
		//Stopp_Listener
		stoppen.addActionListener(
				new ActionListener() {    
				  public void actionPerformed(ActionEvent ae) {     
					  	stoppen();    
					  }   
				}
		);
	}

	
	/**
	 * Die Main-Methode erstellt ein Objekt der Klasse und ruft darauf die 
	 * Startmethode auf
	 * @param args
	 */
	///////////////////////MAIN METHODE
	public static void main(String[] args){
		Server_Gui s = new Server_Gui();
		s.start();		 
	}
	
	
	/**
	 * Der alte Frame wird geschlossen und ein neuer erstellt.
	 * Dies wird beispielsweise f¸r das Sperren einer Funktion benˆtigt.
	 */
	public void start(){
		this.dispose();		
		Server_Gui server = new Server_Gui();
		server.setVisible(true);
		server.anzeige();
	}
	///////////////////////MAIN METHODE

	
	/**
	 * ÷ffnet ein Dialog ob wirklich geschlossen werden soll. Wenn ja, wird 
	 * sowohl das Programm als auch der Frame geschlossen
	 */
	public void beenden() {  
		  Entscheidungs_Dialog dlg = new Entscheidungs_Dialog(null, "Schlieﬂen",
				  "Wirklich Schlieﬂen?"); 
		  
		  if (dlg.antwort() == true) {
			  dispose(); // Frame schlieﬂen   
			  System.exit(0); // Programm beenden   
		}  
	}
	
	
	/**
	 * Verwirft vorherige Anzeigen und zeigt verbundene Clients an.
	 * Auﬂerdem werden die verschiedenen Men¸punkte angezeigt und per Listener 
	 * mit den Methoden verbunden. 
	 * Wenn der Server noch nicht gestartet wurde, wird dies ausgegeben
	 */
	public void anzeige(){
		this.removeAll(); // Fenster leeren   
		headline("Status"); // ‹berschrift  
		
		if (start == true) {   
			// Anzahl verbundener Clients    
			clients_lbl = new Label("Verbundene Clients: " + anzahl_clients);    
			clients_lbl.setBounds(50, 100, 150, 20);    
			clients_lbl.setVisible(true); 
			this.add(clients_lbl);    // TextArea Protokoll    
			 
			log = new TextArea();    
			log.setBounds(50, 130, 500, 200);    
			Font schrift = new Font("Monospaced", Font.PLAIN, 11);    
			log.setFont(schrift);    
			log.setEditable(false);   
			log.setForeground(Color.black);    
			log.setBackground(new Color(255, 064, 064));    
			this.add(log);
			//Satus an Log von Server Gui
			 try {
				Server_Gui.logUpdate("Server gestartet\nPort: " + port + 
						"\tIP: " + InetAddress.getLocalHost() 
						+ "\nWarten auf Client\n");
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			 
			//PopupMenu
			final JPopupMenu pmnu = new JPopupMenu();
			
			// Inhalt
			// Init - MenuItems
			JMenuItem stoppen = new JMenuItem("trennen");
		
			//In PopupMenu einfuegen
			pmnu.add(stoppen);
			
			// MouseListener f¸r PopupMenu
			addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent me) {
					if (me.isPopupTrigger())
						pmnu.show(me.getComponent(), me.getX(), me.getY());
					}    
				});

			
			// ActionListener Person_Aufnehmen    
			stoppen.addActionListener(new ActionListener() { 
			    public void actionPerformed(ActionEvent ae) {     
			    	stoppen();     
			    }    
			});
		 
		} else {    
			Label text = new Label("Server ist deaktiviert.");    
			text.setBounds(50, 80, 400, 50);    
			text.setVisible(true);    
			this.add(text); 
			
			//PopupMenu
			final JPopupMenu pmnu = new JPopupMenu();
			
			// Inhalt
			// Init - MenuItems
			JMenuItem starten = new JMenuItem("starten");
		
			//In PopupMenu einfuegen
			pmnu.add(starten);
			
			// MouseListener f¸r PopupMenu
			addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent me) {
					if (me.isPopupTrigger())
						pmnu.show(me.getComponent(), me.getX(), me.getY());
					}    
				});

			
			// ActionListener Person_Aufnehmen    
			starten.addActionListener(new ActionListener() { 
			    public void actionPerformed(ActionEvent ae) {     
			    	starten();     
			    }    
			});
		}
	}
	
	
	/**
	 * Aktualisiert das TextField mit der aktuellen Auswahl des Clients
	 */
	public static void logUpdate(String update){
		String oldLog = log.getText();
		log.setText(oldLog + update);
	}
	
	
	/**
	 * Setzt eine ‹berschrift und regelt deren Formatierung
	 * @param s
	 */
	//‹berschrift setzten
	public void headline(String s){
		Label headline = new Label(s); // Label   
		headline.setBounds(50, 70, this.getWidth(), 20); // Position   
		Font headfont = new Font("Arial", Font.BOLD, 16);   
		headline.setFont(headfont); // Schriftart   
		headline.setForeground(Color.RED); // Schriftfarbe   
		headline.setVisible(true);   
		this.add(headline); 
	}
	
	
	/**
	 * Verwirft vorherige Fenster. ÷ffnet ein neues und gibt Eigenschaften des
	 * Servers in einem Textfeld an. 
	 * Stattet Buttons mit Funktionen ¸ber Listeners aus.
	 * Hier ist die Auswahl ob in CSV oder Datenbank gespeichert werden soll
	 */
	public void starten(){
		this.removeAll();
		
		headline("Server starten");
		
		/*---------------TextFeld Port---------------*/
		final Label port_lbl = new Label("Port des Servers: ");
		port_lbl.setBounds(50, 120, 100, 20);
		port_lbl.setBackground(new Color(220,220,220)); // Hintergrundfarbe
		port_lbl.setVisible(true);
		this.add(port_lbl);
		
		final TextField port_txt = new TextField();
		port_txt.setBackground(Color.white); // Hintergrundfarbe
		port_txt.setForeground(Color.BLACK); // Schriftfarbe
		port_txt.setBounds(260, 120, 130, 20);
		port_txt.setText("56789"); // Default-Wert
		port_txt.setVisible(true);
		this.add(port_txt);
		/*---------------TextFeld Port---------------*/
		
		/*---------------TextFeld Datenbasis---------------*/
		Label daten_lbl = new Label("Datenbasis: ");
		daten_lbl.setBounds(50, 150, 100, 20);
		daten_lbl.setBackground(new Color(220,220,220)); // Hintergrundfarbe
		daten_lbl.setVisible(true);
		this.add(daten_lbl);
		
		final Choice daten_choice = new Choice();
		daten_choice.setBackground(Color.white); // Hintergrundfarbe
		daten_choice.setForeground(Color.BLACK); // Schriftfarbe
		daten_choice.setBounds(260, 150, 130, 20);
		daten_choice.add("CSV-Datei"); // Default-Wert
		daten_choice.add("MySql-Datenbank"); // Default-Wert
		daten_choice.setVisible(true);
		this.add(daten_choice);
		/*---------------TextFeld Datenbasis---------------*/
		
		/*---------------Label Info bei Choice---------------*/
		final Label choiceStatus_lbl = new Label("Bitte Datei ausw‰hlen!");
		choiceStatus_lbl.setBounds(260, 180, 230, 20);
		choiceStatus_lbl.setForeground(Color.BLACK); // Schriftfarbe
		add(choiceStatus_lbl);
		choiceStatus_lbl.setVisible(true);
		/*---------------Label Info bei Choice---------------*/
		
		/*---------------Button laden---------------*/
		final Button csv_btn = new Button("Datei ausw‰hlen");
		csv_btn.setBounds(400, 150, 130, 20);
		csv_btn.setBackground(Color.white); // Hintergrundfarbe
		csv_btn.setForeground(Color.BLACK); // Schriftfarbe
		csv_btn.setVisible(true);
		this.add(csv_btn);
		/*---------------Button laden---------------*/
		
		/*---------------Button Start---------------*/
		final Button start_btn = new Button("Server starten");
		start_btn.setBounds(50, 200, 130, 30);
		start_btn.setBackground(Color.white); // Hintergrundfarbe
		start_btn.setForeground(Color.BLACK); // Schriftfarbe
		start_btn.setVisible(true);
		this.add(start_btn);
		start_btn.setEnabled(false);
		/*---------------Button Start---------------*/	
	
		/*---------------Listeners---------------*/
		//Item_Listener
		daten_choice.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent arg0){
				
				if(daten_choice.getSelectedItem().equals("MySql-Datenbank")){
					csv_btn.setEnabled(false);
					start_btn.setEnabled(true);
					choiceStatus_lbl.setText("Datenbank noch nicht "
							+ "implementiert!");
				}
				if(daten_choice.getSelectedItem().equals("CSV-Datei")){
					csv_btn.setEnabled(true);
					start_btn.setEnabled(false);
					choiceStatus_lbl.setText("Bitte Datei ausw‰hlen!");
				}
			}
		});
		
		//LadenButton_Listener
		final FileDialog fileDg = new FileDialog(this, "CSV-Datei laden", 
				FileDialog.LOAD);
		fileDg.setSize(200, 200);// Grˆﬂe
		fileDg.setResizable(false);
		
		csv_btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				fileDg.setVisible(true);
				
				String dir = fileDg.getDirectory();
				String f = fileDg.getFile();
				
				//Wenn eine File ausgew‰hlt wurde
				if(f != null){
					setFile(dir, f);
					choiceStatus_lbl.setText("Datei: " + f + " wurde gewh‰lt!");
					start_btn.setEnabled(true);
				}
			}
		});
		
		//StartButton_Listener
		final Label portStatus_lbl = new Label("Eingabe falsch!");
		
		start_btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					
					boolean fehler = false; 
					datenbank = false;
					portStatus_lbl.setVisible(false);
					
				  	if(port_txt.getText().matches("[1-9][0-9]{4}") == false){
				  		portStatus_lbl.setBounds(400, 120, 100, 20);
				  		portStatus_lbl.setForeground(Color.BLACK); 
				  		// Schriftfarbe
				  		portStatus_lbl.setBackground(Color.RED); 
				  		// Hintergrundfarbe
				  		add(portStatus_lbl);
				  		portStatus_lbl.setVisible(true);
				  		fehler = true;
				  	}
				  	
					if (daten_choice.getSelectedItem().equals("MySql-Datenbank")
							) {      
						datenbank = true;
						fehler = true;
					} 
					
					if (daten_choice.getSelectedItem().equals("CSV-Datei")) {      
						datenbank = false;
					} 
					
					if(fehler == false){ // Wenn alle Eingaben korrekt sind und 
										// Datei ausgew‰hlt wurde
						//Server starten
						Server_Controler sc = new Server_Controler();
						setPort(Integer.parseInt(port_txt.getText())); 
						// eingegebenen Port uebergeben
						sc.start(); //Thread starten
						start = true;
						start(); // Fenster wird neu aufgebaut
					}
				}   
			}
		);  
	}
	
	
	/**
	 * Wenn Clients verbunden sind wird eine Meldung ausgegeben ob wirklich
	 * geschlossen werden soll.
	 * Sonst wird nur abgefragt, ob geschlossen werden soll 
	 */
	public void stoppen(){
		Entscheidungs_Dialog dlg = null;
		if(anzahl_clients > 0){
			dlg = new Entscheidungs_Dialog(null, "Stoppen", "Server stoppen? "
					+ "Noch " + anzahl_clients + " Client(s) verbunden!"); 
		}
		else{
			dlg = new Entscheidungs_Dialog(null, "Stoppen", "Wirklich den "
					+ "Server stoppen?");
		}
		
		if (dlg.antwort() == true) {
			  start = false;
			  System.exit(-1);
		}  
	}
	
	
	/**
	 * Addiert zur Anzahl der Clients einen dazu 
	 * und gibt die Anzahl aus
	 */
	public static void addClient(){
		anzahl_clients++;
		clients_lbl.setText("Verbundene Clients: " + anzahl_clients);
	}
	
	
	/**
	 * Zieht von der Anzahl der Clients eins ab
	 * und gibt die Anzahl aus
	 */
	public static void minClient(){
		anzahl_clients--;
		clients_lbl.setText("Verbundene Clients: " + anzahl_clients);
	}
	
	
	/**
	 * Get-Methode zum angeben des jeweiligen Serverports
	 * @return
	 */
	public static int getPort(){
		return port;
	}
	
	
	/**
	 * Set-Methode f¸r die Serverports
	 * @param port
	 */
	public void setPort(int port){
		Server_Gui.port = port;
	}
	
	
	/**
	 * Set-Methode f¸r den FileDialog
	 * @param d
	 * @param f
	 */
	public void setFile(String d, String f){
		Server_Gui.directory = d;
		Server_Gui.file = f;
	}
	
	
	/**
	 * Gibt den verketteten String zur¸ck
	 * @return
	 */
	public static String getFile(){
		//verketteter String wird zur¸ckgegeben
		return (directory + file);
	}
	
	
	/**
	 * Zeigt in der Fuﬂzeile die Autoren an 
	 */
	public void paint(Graphics g) {  
		// Footer   
		g.drawString("© Andreas Hess, Romeo Martinovic, Philip Wangler", 
				this.getWidth() - 300,this.getHeight() - 20); 
	} 
	
}
