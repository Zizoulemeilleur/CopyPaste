package Client;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;


/**
 * Die Klasse Client_GUI beinhaltet die Modelierung der clientseitigen
 * grafischen Oberfl‰che und die Verbindung zur Klasse Client und derer 
 * Funktionalit‰t 
 * @author Andreas Hess, Romeo Martinovic, Philip Wangler
 * @version 1.0 ADRELI_GUI 
 */




public class Client_Gui extends Frame{
	private static final long serialVersionUID = 1L;
	
	// MenueLeiste
	private MenuBar bar;
	private Menu verb, funkt, autor;
	private MenuItem verbinden, trennen, aufnehmen, anzeigen, speichern, laden, 
	sortieren, loeschen, autoren;
	
	public static boolean verbunden = false;
	public static boolean error = false;
	
	int zaehler;
	Image hgBild;
	boolean showBild = false;
	
	
	/**
	 * Der Konstruktor regelt die Formatierung und 
	 * erstellt die grafischen Oberfl‰che
	 * und f¸gt ein Icon hinzu
	 */
	//Konstruktor
	public Client_Gui(){
 
		super("ADRELI - Client");
		this.setLocation(400, 300); // Fenster zentrieren 
		setSize(800, 600); // Fenstergrˆﬂe   
		this.setResizable(false); //feste Fenstergroesse
		this.setLayout(null); // Layout deaktivieren 
		//Icon links oben an der GUI
		Image icon = this.getToolkit().getImage("./hfu.png");   
		this.setIconImage(icon); // Icon hinzuf¸gen
		this.setBackground(Color.LIGHT_GRAY);
		
		
		/**
		 * Diese Methode l‰sst das schlieﬂen der Client-GUI nur zu, wenn die 
		 * Verbindung zum Server bereits getrennt ist
		 */
		//Bei Klick auf Schlieﬂ-Button der GUI
		this.addWindowListener( new WindowAdapter() {  
			public void windowClosing(WindowEvent we) {
				
				if(verbunden == false){
					beenden();
				}
				else{
					new OK_Dialog(null, "Warnung", "Die Verbindung muss erst "
							+ "getrennt werden!");
				}
			}   
		});
		
		/*---------------Anfang MenueLeiste---------------*/
		// Init - Menuleiste
		this.bar = new MenuBar();
		
		// Init - Menuepunkte
		this.verb = new Menu("Verbindung");
		this.funkt = new Menu("Funktionen");
		this.autor = new Menu("Autoren");
		
		// Init - MenuItems
		this.verbinden = new MenuItem("verbinden");
		this.trennen = new MenuItem("trennen");
		this.aufnehmen = new MenuItem("Person aufnehmen");
		this.anzeigen = new MenuItem("Records anzeigen");
		this.speichern = new MenuItem("Records speichern");
		this.laden = new MenuItem("Records laden");
		this.sortieren = new MenuItem("Records sortieren");
		this.loeschen = new MenuItem("Datei loeschen");
		this.autoren = new MenuItem("Autoren");
		
		// Components der Menuleiste hinzufuegen
		verb.add(verbinden);
		verb.addSeparator();
		verb.add(trennen);
		funkt.add(aufnehmen);
		funkt.add(anzeigen);
		funkt.add(speichern);
		funkt.add(laden);
		funkt.add(sortieren);
		funkt.add(loeschen);
		autor.add(autoren);
		
		bar.add(verb);
		bar.add(funkt);
		bar.add(autor);
		
		this.setMenuBar(bar);
		/*---------------Anfang MenueLeiste---------------*/
		
		// Funktionen der Menuleiste sperren
		if (verbunden == false) { // Server aus 
			trennen.setEnabled(false); //Stoppen kann nicht ausgewawhlt werden
			aufnehmen.setEnabled(false);
			anzeigen.setEnabled(false);
			speichern.setEnabled(false);
			laden.setEnabled(false);
			sortieren.setEnabled(false);
			loeschen.setEnabled(false);
		} else { // Server an    
			verbinden.setEnabled(false);
		}		
		
		
		/**
		 * Die Items werden mit Listeners hinterlegt. Beim Klick auf die Items
		 * werden die jeweiligen Methoden ausgef¸hrt
		 */
		/*---------------Listeners---------------*/
		//Verbinden_Listener
		verbinden.addActionListener(
			new ActionListener() {    
				public void actionPerformed(ActionEvent ae) {     
					verbinden();    
				}   
			}
		);
		
		
		//Verbinden_Listener
		trennen.addActionListener(
			new ActionListener() {    
				public void actionPerformed(ActionEvent ae) {     
					trennen();    
				}   
			}
		);
		
		
		//Aufnehmen_Listener
		aufnehmen.addActionListener(
			new ActionListener() {    
				public void actionPerformed(ActionEvent ae) {     
					person_aufnehmen();    
				}   
			}
		);
		
		
		//Anzeigen_Listener
		anzeigen.addActionListener(
			new ActionListener() {    
				public void actionPerformed(ActionEvent ae) {     
					person_anzeigen();    
				}   
			}
		);
		
		
		//Speichern_Listener
		speichern.addActionListener(
			new ActionListener() {    
				public void actionPerformed(ActionEvent ae) {     
					safe();    
				}   
			}
		);
		
		
		//Laden_Listener
		laden.addActionListener(
			new ActionListener() {    
				public void actionPerformed(ActionEvent ae) {     
					laden();    
				}   
			}
		);
		
		
		//Sortieren_Listener
		sortieren.addActionListener(
			new ActionListener() {    
				public void actionPerformed(ActionEvent ae) {     
					sortieren();    
				}   
			}
		);
		
		
		//loeschen_Listener
		loeschen.addActionListener(
			new ActionListener() {    
				public void actionPerformed(ActionEvent ae) {     
					loeschen();    
				}   
			}
		);		
		
		
		//loeschen_Listener
		autoren.addActionListener(
			new ActionListener() {    
				public void actionPerformed(ActionEvent ae) {     
					new OK_Dialog(null, "Autoren", "© Andreas Hess, Romeo "
							+ "Martinovic, Philip Wangler");    
				}   
			}
		);
	}
	
	
	/**
	 * Die Main-Methode erstellt ein Objekt der Klasse und rauft darauf die 
	 * Startmethode auf
	 * @param args
	 */
	///////////////////////MAIN METHODE
	public static void main(String[] args) {
		Client_Gui cgui = new Client_Gui();
		cgui.start();
	}
	
	
	/**
	 * Der alte Frame wird geschlossen und ein neuer erstellt.
	 * Dies wird beispielsweise f¸r das Sperren einer Funtion benˆtigt.
	 */
	public void start(){
		this.dispose();
		Client_Gui cgui = new Client_Gui();
		cgui.setVisible(true);
		cgui.anzeige();
	}
	///////////////////////MAIN METHODE
	
	
	/**
	 * Verwirft die vorherige Anzeige und zeigt die eingegebene Server-IP und 
	 * Port an, wenn verbunden. Wenn nicht verbunden, wird angezeigt, dass 
	 * noch keine Verbindung besteht.
	 * Auﬂerdem werden die verschiedenen Men¸punkte als PopUpMenu 
	 * zur Verf¸gung gestellt und angezeigt und per Listener mit den Methoden 
	 * verbunden. 
	 */
	public void anzeige(){
		this.removeAll();
		
		if(verbunden == true){
			headline("Verbunden mit Server");
			Label text = new Label("Im Men¸punkt 'Funktionen' kˆnnen Sie "
					+ "verschiedene Funktionen aufrufen.");    
			text.setBounds(50, 80, 500, 50);    
			text.setVisible(true);    
			this.add(text);	
			
			Label ip = new Label("IP des Servers: " + Client.serverIP);    
			ip.setBounds(50, 120, 500, 50);    
			ip.setVisible(true);    
			this.add(ip);
			
			Label port = new Label("Port des Servers: " + Client.port);    
			port.setBounds(50, 160, 500, 50);    
			port.setVisible(true);    
			this.add(port);
			
			//PopupMenu
			final JPopupMenu pmnu = new JPopupMenu();
			
			// Inhalt
			// Init - MenuItems
			JMenuItem aufnehmen = new JMenuItem("Person aufnehmen");
			JMenuItem anzeigen = new JMenuItem("Records anzeigen");
			JMenuItem speichern = new JMenuItem("Records speichern");
			JMenuItem laden = new JMenuItem("Records laden");
			JMenuItem sortieren = new JMenuItem("Records sortieren");
			JMenuItem loeschen = new JMenuItem("Datei loeschen");
			
			//In PopupMenu einfuegen
			pmnu.add(aufnehmen);
			pmnu.add(anzeigen);
			pmnu.add(speichern);
			pmnu.add(laden);
			pmnu.add(sortieren);
			pmnu.add(loeschen);
	
			
			// MouseListener f¸r PopupMenu
			addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent me) {
					if (me.isPopupTrigger())
						pmnu.show(me.getComponent(), me.getX(), me.getY());
					}    
				});

			
			// ActionListener Person_Aufnehmen    
			aufnehmen.addActionListener(new ActionListener() { 
			    public void actionPerformed(ActionEvent ae) {     
			    	person_aufnehmen();     
			    }    
			}); 
			
			
			// ActionListener Person_Aufnehmen    
			anzeigen.addActionListener(new ActionListener() { 
			    public void actionPerformed(ActionEvent ae) {     
			    	person_anzeigen();     
			    }    
			}); 
			
			
			// ActionListener Person_Aufnehmen    
			speichern.addActionListener(new ActionListener() { 
			    public void actionPerformed(ActionEvent ae) {     
			    	safe();     
			    }    
			}); 
			
			
			// ActionListener Person_Aufnehmen    
			laden.addActionListener(new ActionListener() { 
			    public void actionPerformed(ActionEvent ae) {     
			    	laden();     
			    }    
			}); 
			
			
			// ActionListener Person_Aufnehmen    
			sortieren.addActionListener(new ActionListener() { 
			    public void actionPerformed(ActionEvent ae) {     
			    	sortieren();     
			    }    
			}); 
			
			
			// ActionListener Person_Aufnehmen    
			loeschen.addActionListener(new ActionListener() { 
			    public void actionPerformed(ActionEvent ae) {     
			    	loeschen();     
			    }    
			}); 
			
			
		}
		else{
			headline("Willkommen");
			Label text = new Label("... nicht verbunden.");    
			text.setBounds(50, 80, 400, 50);    
			text.setVisible(true);    
			this.add(text);   			
		}
		
	}
	
	
	/**
	 * Setz die ‹berschrift und regelt deren Formatierung
	 * @param s ‹bergabe eines Strings
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
	 * ÷ffnet ein Dialog ob wirklich geschlossen werden soll. Wenn ja, wird 
	 * sowohl das Programm sowie auch der Frame geschlossen
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
	 * Lˆscht den vorherigen Inhalt und fragt die Server-IP und Port ab. 
	 * F‰ngt falsche Eingaben ab und gibt eine Fehlermeldung dazu aus. Bei 
	 * korrekter Eingabe wird eine Verbindung hergestellt
	 */
	public void verbinden(){
		this.removeAll();
		
		headline("Verbindung herstellen");
		
		/*---------------TextFeld Port---------------*/
		final Label port_lbl = new Label("Port des Servers: ");
		port_lbl.setBounds(50, 120, 140, 20);
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
		
		/*---------------TextFeld IP---------------*/
		final Label ip_lbl = new Label("IP-Adresse des Servers: ");
		ip_lbl.setBounds(50, 150, 140, 20);
		ip_lbl.setBackground(new Color(220,220,220)); // Hintergrundfarbe
		ip_lbl.setVisible(true);
		this.add(ip_lbl);
		
		final TextField ip_txt = new TextField();
		ip_txt.setBackground(Color.white); // Hintergrundfarbe
		ip_txt.setForeground(Color.BLACK); // Schriftfarbe
		ip_txt.setBounds(260, 150, 130, 20);
		ip_txt.setVisible(true);
		this.add(ip_txt);
		/*---------------TextFeld IP---------------*/
		
		/*---------------Button Start---------------*/
		Button verbinden_btn = new Button("Verbindung herstellen");
		verbinden_btn.setBounds(50, 200, 130, 30);
		verbinden_btn.setBackground(Color.white); // Hintergrundfarbe
		verbinden_btn.setForeground(Color.BLACK); // Schriftfarbe
		verbinden_btn.setVisible(true);
		this.add(verbinden_btn);
		/*---------------Button Start---------------*/
		
		/*---------------Listeners---------------*/
		//StartButton_Listener
		final Label portStatus_lbl = new Label("Eingabe falsch!");
		final Label ipStatus_lbl = new Label("Eingabe falsch!");
		
		verbinden_btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					
					boolean fehler = false;
					portStatus_lbl.setVisible(false);
					ipStatus_lbl.setVisible(false);
					
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
				  	
				  	
					if (ip_txt.getText().matches("(([1-9][0-9]?[0-9]?)."
							+ "([1-9][0-9]?[0-9]?).([1-9][0-9]?[0-9]?)."
							+ "([1-9][0-9]?[0-9]?))|localhost") == false){      
						ipStatus_lbl.setBounds(400, 150, 100, 20);
						ipStatus_lbl.setForeground(Color.BLACK); 
						// Schriftfarbe
						ipStatus_lbl.setBackground(Color.RED); 
						// Hintergrundfarbe
						add(ipStatus_lbl);
						ipStatus_lbl.setVisible(true);
						fehler = true;
					}  
					
					if(fehler == false){ // Wenn alle Eingaben korrekt sind
						
						Client c = new Client();
						c.verbinden(Integer.valueOf(port_txt.getText()), 
								ip_txt.getText());
						
						if(c.error == false){
							verbunden = true;
							start(); // Fenster wird neu aufgebaut
						}
						else{
							new OK_Dialog(null, "Verbindungsfehler", 
									"Verbindung konnte nicht aufgebaut werden");
							verbinden();
						}
						
						
					}
				}   
			}
		);
	}
	
	
	/**
	 * Trennt die Verbindung zum Server und fragt in einem Dialogfenster nach 
	 * einer Best‰tigung des Verbindungsabbaus 
	 */
	public void trennen(){
		Entscheidungs_Dialog dlg = null;
		
		dlg = new Entscheidungs_Dialog(null, "Verbindungsabbau", "Wirklich die "
				+ "Verbindung zum Server trennen?");
		
		if (dlg.antwort() == true) {
			  Client.trennen();
			  start();
		}  
	}
	
	
	/**
	 * Bildet das Fenster zu Datens‰tze aufnehmen ab und pr¸ft die Eingaben auf 
	 * regul‰re Ausdr¸cke. Bei falschen Eingaben wird eine Fehlermeldung 
	 * ausgegeben. 
	 * Auﬂerdem wird ¸ber die Client Methode "senden" dem Server signalisiert,
	 * dass der erste Men¸punkt, Datens‰tze aufnehmen, ausgef¸hrt werden soll.
	 */
	public void person_aufnehmen(){
		
		new Client().senden(1);
		
		this.removeAll();
		headline("Person aufnehmen");
		
		/*---------------TextFeld Name---------------*/
		final Label name_lbl = new Label("Name: ");
		name_lbl.setBounds(50, 120, 140, 20);
		name_lbl.setBackground(new Color(220,220,220)); // Hintergrundfarbe
		name_lbl.setVisible(true);
		this.add(name_lbl);
		
		final TextField name_txt = new TextField();
		name_txt.setBackground(Color.white); // Hintergrundfarbe
		name_txt.setForeground(Color.BLACK); // Schriftfarbe
		name_txt.setBounds(260, 120, 160, 20);
		name_txt.setVisible(true);
		this.add(name_txt);
		/*---------------TextFeld Ende Name---------------*/
		
		/*---------------TextFeld Vorname---------------*/
		final Label vname_lbl = new Label("Vorname: ");
		vname_lbl.setBounds(50, 150, 140, 20);
		vname_lbl.setBackground(new Color(220,220,220)); // Hintergrundfarbe
		vname_lbl.setVisible(true);
		this.add(vname_lbl);
		
		final TextField vname_txt = new TextField();
		vname_txt.setBackground(Color.white); // Hintergrundfarbe
		vname_txt.setForeground(Color.BLACK); // Schriftfarbe
		vname_txt.setBounds(260, 150, 160, 20);
		vname_txt.setVisible(true);
		this.add(vname_txt);
		/*---------------TextFeld Ende Vorname---------------*/
		
		/*---------------TextFeld Anrede---------------*/
		final Label anrede_lbl = new Label("Anrede: ");
		anrede_lbl.setBounds(50, 180, 140, 20);
		anrede_lbl.setBackground(new Color(220,220,220)); // Hintergrundfarbe
		anrede_lbl.setVisible(true);
		this.add(anrede_lbl);
		
		final TextField anrede_txt = new TextField();
		anrede_txt.setBackground(Color.white); // Hintergrundfarbe
		anrede_txt.setForeground(Color.BLACK); // Schriftfarbe
		anrede_txt.setBounds(260, 180, 160, 20);
		anrede_txt.setVisible(true);
		this.add(anrede_txt);
		/*---------------TextFeld Ende Anrede---------------*/
		
		/*---------------TextFeld Strasse---------------*/
		final Label strasse_lbl = new Label("Strasse: ");
		strasse_lbl.setBounds(50, 210, 140, 20);
		strasse_lbl.setBackground(new Color(220,220,220)); // Hintergrundfarbe
		strasse_lbl.setVisible(true);
		this.add(strasse_lbl);
		
		final TextField strasse_txt = new TextField();
		strasse_txt.setBackground(Color.white); // Hintergrundfarbe
		strasse_txt.setForeground(Color.BLACK); // Schriftfarbe
		strasse_txt.setBounds(260, 210, 160, 20);
		strasse_txt.setVisible(true);
		this.add(strasse_txt);
		/*---------------TextFeld Ende Strasse---------------*/
		
		/*---------------TextFeld PLZ---------------*/
		final Label plz_lbl = new Label("PLZ: ");
		plz_lbl.setBounds(50, 240, 140, 20);
		plz_lbl.setBackground(new Color(220,220,220)); // Hintergrundfarbe
		plz_lbl.setVisible(true);
		this.add(plz_lbl);
		
		final TextField plz_txt = new TextField();
		plz_txt.setBackground(Color.white); // Hintergrundfarbe
		plz_txt.setForeground(Color.BLACK); // Schriftfarbe
		plz_txt.setBounds(260, 240, 160, 20);
		plz_txt.setVisible(true);
		this.add(plz_txt);
		/*---------------TextFeld Ende PLZ---------------*/
		
		/*---------------TextFeld Ort---------------*/
		final Label ort_lbl = new Label("Ort: ");
		ort_lbl.setBounds(50, 270, 140, 20);
		ort_lbl.setBackground(new Color(220,220,220)); // Hintergrundfarbe
		ort_lbl.setVisible(true);
		this.add(ort_lbl);
		
		final TextField ort_txt = new TextField();
		ort_txt.setBackground(Color.white); // Hintergrundfarbe
		ort_txt.setForeground(Color.BLACK); // Schriftfarbe
		ort_txt.setBounds(260, 270, 160, 20);
		ort_txt.setVisible(true);
		this.add(ort_txt);
		/*---------------TextFeld Ende Ort---------------*/
		
		/*---------------TextFeld Telefon---------------*/
		final Label tel_lbl = new Label("Telefon: ");
		tel_lbl.setBounds(50, 300, 140, 20);
		tel_lbl.setBackground(new Color(220,220,220)); // Hintergrundfarbe
		tel_lbl.setVisible(true);
		this.add(tel_lbl);
		
		final TextField tel_txt = new TextField();
		tel_txt.setBackground(Color.white); // Hintergrundfarbe
		tel_txt.setForeground(Color.BLACK); // Schriftfarbe
		tel_txt.setBounds(260, 300, 160, 20);
		tel_txt.setVisible(true);
		this.add(tel_txt);
		/*---------------TextFeld Ende Telefon---------------*/
		
		/*---------------TextFeld Fax---------------*/
		final Label fax_lbl = new Label("Fax: ");
		fax_lbl.setBounds(50, 330, 140, 20);
		fax_lbl.setBackground(new Color(220,220,220)); // Hintergrundfarbe
		fax_lbl.setVisible(true);
		this.add(fax_lbl);
		
		final TextField fax_txt = new TextField();
		fax_txt.setBackground(Color.white); // Hintergrundfarbe
		fax_txt.setForeground(Color.BLACK); // Schriftfarbe
		fax_txt.setBounds(260, 330, 160, 20);
		fax_txt.setVisible(true);
		this.add(fax_txt);
		/*---------------TextFeld Ende Fax---------------*/
		
		/*---------------TextFeld Bemerkung---------------*/
		final Label bem_lbl = new Label("Bemerkung: ");
		bem_lbl.setBounds(50, 360, 140, 20);
		bem_lbl.setBackground(new Color(220,220,220)); // Hintergrundfarbe
		bem_lbl.setVisible(true);
		this.add(bem_lbl);
		
		final TextField bem_txt = new TextField();
		bem_txt.setBackground(Color.white); // Hintergrundfarbe
		bem_txt.setForeground(Color.BLACK); // Schriftfarbe
		bem_txt.setBounds(260, 360, 160, 20);
		bem_txt.setVisible(true);
		this.add(bem_txt);
		/*---------------TextFeld Ende Bemerkung---------------*/
		
		/*---------------Button Person aufnehmen---------------*/
		Button aufnehmen_btn = new Button("Person aufnehmen");
		aufnehmen_btn.setBounds(50, 390, 140, 30);
		aufnehmen_btn.setBackground(Color.white); // Hintergrundfarbe
		aufnehmen_btn.setForeground(Color.BLACK); // Schriftfarbe
		aufnehmen_btn.setVisible(true);
		this.add(aufnehmen_btn);
		/*---------------Button Person aufnehmen---------------*/
		
		//Aufnehmen_Listener
		final Label nameStatus_lbl = new Label("Falsche Eingabe. Bsp.: "
				+ "Mustermann");
		final Label vnameStatus_lbl = new Label("Falsche Eingabe. Bsp.: Max");
		final Label anredeStatus_lbl = new Label("Falsche Eingabe. Bsp.: "
				+ "Herr oder Frau");
		final Label strasseStatus_lbl = new Label("Falsche Eingabe. Bsp.: "
				+ "Strasse. 2");
		final Label plzStatus_lbl = new Label("Falsche Eingabe. Bsp.: 78120");
		final Label ortStatus_lbl = new Label("Falsche Eingabe. Bsp.: "
				+ "Furtwangen");
		final Label telStatus_lbl = new Label("Falsche Eingabe. Bsp.: 123456");
		final Label faxStatus_lbl = new Label("Falsche Eingabe. Bsp.: 234567");
		final Label bemStatus_lbl = new Label("Falsche Eingabe. Bsp.: "
				+ "keine Bemerkung");
		
		aufnehmen_btn.addActionListener(
			new ActionListener() {    
				public void actionPerformed(ActionEvent ae) {     
					boolean error = false;
					
					nameStatus_lbl.setVisible(false);
					vnameStatus_lbl.setVisible(false);
					anredeStatus_lbl.setVisible(false);
					strasseStatus_lbl.setVisible(false);
					plzStatus_lbl.setVisible(false);
					ortStatus_lbl.setVisible(false);
					telStatus_lbl.setVisible(false);
					faxStatus_lbl.setVisible(false);
					bemStatus_lbl.setVisible(false);
					
					// Name ueberpruefen
					Matcher name_ok;
					Pattern pattern_name = Pattern.compile("[A-Zƒ÷‹]"
							+ "[a-z‰ˆ¸ﬂ]+");
					name_ok = pattern_name.matcher(name_txt.getText());
					if(name_ok.matches() == false){ //wenn falsch
						error = true;
						nameStatus_lbl.setBounds(430, 120, 220, 20);
						nameStatus_lbl.setForeground(Color.BLACK); 
						// Schriftfarbe
						nameStatus_lbl.setBackground(Color.RED); 
						// Hintergrundfarbe
				  		add(nameStatus_lbl);
				  		nameStatus_lbl.setVisible(true);
					}
					
					// Vorname ueberpruefen
					Matcher vname_ok;
					Pattern pattern_vname = Pattern.compile("[A-Zƒ÷‹]"
							+ "[a-z‰ˆ¸ﬂ]+");
					vname_ok = pattern_vname.matcher(vname_txt.getText());
					if(vname_ok.matches() == false){ //wenn falsch
						error = true;
						vnameStatus_lbl.setBounds(430, 150, 220, 20);
						vnameStatus_lbl.setForeground(Color.BLACK); 
						// Schriftfarbe
						vnameStatus_lbl.setBackground(Color.RED); 
						// Hintergrundfarbe
				  		add(vnameStatus_lbl);
				  		vnameStatus_lbl.setVisible(true);
					}
					
					// Anrede ueberpruefen
					Matcher anrede_ok;
					Pattern pattern_anrede = Pattern.compile("Herr|Frau");
					anrede_ok = pattern_anrede.matcher(anrede_txt.getText());
					if(anrede_ok.matches() == false){ //wenn falsch
						error = true;
						anredeStatus_lbl.setBounds(430, 180, 220, 20);
						anredeStatus_lbl.setForeground(Color.BLACK); 
						// Schriftfarbe
						anredeStatus_lbl.setBackground(Color.RED); 
						// Hintergrundfarbe
				  		add(anredeStatus_lbl);
				  		anredeStatus_lbl.setVisible(true);
					}
					
					// Strasse ueberpruefen
					Matcher strasse_ok;
					Pattern pattern_strasse = Pattern.compile
							("[a-zA-Z‰ˆ¸ƒ÷‹ \\.]+ [0-9]+");
					strasse_ok = pattern_strasse.matcher(strasse_txt.getText());
					if(strasse_ok.matches() == false){ //wenn falsch
						error = true;
						strasseStatus_lbl.setBounds(430, 210, 220, 20);
						strasseStatus_lbl.setForeground(Color.BLACK); 
						// Schriftfarbe
						strasseStatus_lbl.setBackground(Color.RED); 
						// Hintergrundfarbe
				  		add(strasseStatus_lbl);
				  		strasseStatus_lbl.setVisible(true);
					}
					
					// PLZ ueberpruefen
					Matcher plz_ok;
					Pattern pattern_plz = Pattern.compile("[1-9][0-9]{4}");
					plz_ok = pattern_plz.matcher(plz_txt.getText());
					if(plz_ok.matches() == false){ //wenn falsch
						error = true;
						plzStatus_lbl.setBounds(430, 240, 220, 20);
						plzStatus_lbl.setForeground(Color.BLACK); 
						// Schriftfarbe
						plzStatus_lbl.setBackground(Color.RED); 
						// Hintergrundfarbe
				  		add(plzStatus_lbl);
				  		plzStatus_lbl.setVisible(true);
					}
					
					// Ort ueberpruefen
					Matcher ort_ok;
					Pattern pattern_ort = Pattern.compile("[A-Zƒ÷‹]"
							+ "[a-z‰ˆ¸ƒ÷‹ﬂ]+");
					ort_ok = pattern_ort.matcher(ort_txt.getText());
					if(ort_ok.matches() == false){ //wenn falsch
						error = true;
						ortStatus_lbl.setBounds(430, 270, 220, 20);
						ortStatus_lbl.setForeground(Color.BLACK); 
						// Schriftfarbe
						ortStatus_lbl.setBackground(Color.RED); 
						// Hintergrundfarbe
				  		add(ortStatus_lbl);
				  		ortStatus_lbl.setVisible(true);
					}
					
					// Telefon ueberpruefen
					Matcher tel_ok;
					Pattern pattern_tel = Pattern.compile("[0-9]{3,14}");
					tel_ok = pattern_tel.matcher(tel_txt.getText());
					if(tel_ok.matches() == false){ //wenn falsch
						error = true;
						telStatus_lbl.setBounds(430, 300, 220, 20);
						telStatus_lbl.setForeground(Color.BLACK); 
						// Schriftfarbe
						telStatus_lbl.setBackground(Color.RED); 
						// Hintergrundfarbe
				  		add(telStatus_lbl);
				  		telStatus_lbl.setVisible(true);
					}
					
					// Fax ueberpruefen
					Matcher fax_ok;
					Pattern pattern_fax = Pattern.compile("[0-9]{3,14}");
					fax_ok = pattern_fax.matcher(fax_txt.getText());
					if(fax_ok.matches() == false){ //wenn falsch
						error = true;
						faxStatus_lbl.setBounds(430, 330, 220, 20);
						faxStatus_lbl.setForeground(Color.BLACK); 
						// Schriftfarbe
						faxStatus_lbl.setBackground(Color.RED); 
						// Hintergrundfarbe
				  		add(faxStatus_lbl);
				  		faxStatus_lbl.setVisible(true);
					}
					
					// Bemerkung ueberpruefen
					Matcher bem_ok;
					Pattern pattern_bem = Pattern.compile("[^;]{4,300}");
					bem_ok = pattern_bem.matcher(bem_txt.getText());
					if(bem_ok.matches() == false){ //wenn falsch
						error = true;
						bemStatus_lbl.setBounds(430, 360, 220, 20);
						bemStatus_lbl.setForeground(Color.BLACK); 
						// Schriftfarbe
						bemStatus_lbl.setBackground(Color.RED); 
						// Hintergrundfarbe
				  		add(bemStatus_lbl);
				  		bemStatus_lbl.setVisible(true);
					}
					
					if(error == false){
						
						Entscheidungs_Dialog korrekt = new Entscheidungs_Dialog
						(null, "Eingabe pr¸fen", "Sind Ihre Eingaben korrekt?");
						if(korrekt.antwort() == true){
							
							Client.aufnehmen(	
				
				name_txt.getText(), vname_txt.getText(), anrede_txt.getText(), 
				strasse_txt.getText(), plz_txt.getText(), ort_txt.getText(), 
				tel_txt.getText(), fax_txt.getText(), bem_txt.getText());
							
				Entscheidungs_Dialog weitere = new Entscheidungs_Dialog(null, 
						"Person aufnehmen", "Person aufgenommen! Noch eine "
								+ "Person aufnehmen?");
							
							if(weitere.antwort() == true){
								person_aufnehmen();
							}
							else{
								anzeige();
							}
						}
					}
				}   
			}
		);	
	}
	
	
	/**
	 * Dem Server wird signalisiert, dass der zweite Men¸punkt, Datens‰tze
	 * anzeigen, ausgef¸hrt werden soll. Alte Anzeigen werden gelˆscht.
	 * Falls keine Datens‰tze vorhanden sind wird dies per Dialog angezeigt, 
	 * ansonsten wird die Ausgabe modelliert und mitgez‰hlt.
	 */
	public void person_anzeigen(){

		new Client().senden(2);
		
		this.removeAll();
		headline("Records anzeigen");
		
		zaehler = 0;
		
		if(Client.daten.isEmpty()){
			new OK_Dialog(null, "Achtung", "Noch keine Personen aufgenommen "
					+ "oder geladen");
			anzeige();
		}
		else{
			
			/*---------------TextFeld Name---------------*/
			final Label name_lbl = new Label("Name: ");
			name_lbl.setBounds(50, 120, 140, 20);
			name_lbl.setBackground(new Color(220,220,220)); // Hintergrundfarbe
			name_lbl.setVisible(true);
			this.add(name_lbl);
			
			final TextField name_txt = new TextField();
			name_txt.setBackground(Color.white); // Hintergrundfarbe
			name_txt.setForeground(Color.BLACK); // Schriftfarbe
			name_txt.setBounds(260, 120, 160, 20);
			name_txt.setVisible(true);
			this.add(name_txt);
			/*---------------TextFeld Ende Name---------------*/
			
			/*---------------TextFeld Vorname---------------*/
			final Label vname_lbl = new Label("Vorname: ");
			vname_lbl.setBounds(50, 150, 140, 20);
			vname_lbl.setBackground(new Color(220,220,220)); // Hintergrundfarbe
			vname_lbl.setVisible(true);
			this.add(vname_lbl);
			
			final TextField vname_txt = new TextField();
			vname_txt.setBackground(Color.white); // Hintergrundfarbe
			vname_txt.setForeground(Color.BLACK); // Schriftfarbe
			vname_txt.setBounds(260, 150, 160, 20);
			vname_txt.setVisible(true);
			this.add(vname_txt);
			/*---------------TextFeld Ende Vorname---------------*/
			
			/*---------------TextFeld Anrede---------------*/
			final Label anrede_lbl = new Label("Anrede: ");
			anrede_lbl.setBounds(50, 180, 140, 20);
			anrede_lbl.setBackground(new Color(220,220,220)); 
			// Hintergrundfarbe
			anrede_lbl.setVisible(true);
			this.add(anrede_lbl);
			
			final TextField anrede_txt = new TextField();
			anrede_txt.setBackground(Color.white); // Hintergrundfarbe
			anrede_txt.setForeground(Color.BLACK); // Schriftfarbe
			anrede_txt.setBounds(260, 180, 160, 20);
			anrede_txt.setVisible(true);
			this.add(anrede_txt);
			/*---------------TextFeld Ende Anrede---------------*/
			
			/*---------------TextFeld Strasse---------------*/
			final Label strasse_lbl = new Label("Strasse: ");
			strasse_lbl.setBounds(50, 210, 140, 20);
			strasse_lbl.setBackground(new Color(220,220,220)); 
			// Hintergrundfarbe
			strasse_lbl.setVisible(true);
			this.add(strasse_lbl);
			
			final TextField strasse_txt = new TextField();
			strasse_txt.setBackground(Color.white); // Hintergrundfarbe
			strasse_txt.setForeground(Color.BLACK); // Schriftfarbe
			strasse_txt.setBounds(260, 210, 160, 20);
			strasse_txt.setVisible(true);
			this.add(strasse_txt);
			/*---------------TextFeld Ende Strasse---------------*/
			
			/*---------------TextFeld PLZ---------------*/
			final Label plz_lbl = new Label("PLZ: ");
			plz_lbl.setBounds(50, 240, 140, 20);
			plz_lbl.setBackground(new Color(220,220,220)); // Hintergrundfarbe
			plz_lbl.setVisible(true);
			this.add(plz_lbl);
			
			final TextField plz_txt = new TextField();
			plz_txt.setBackground(Color.white); // Hintergrundfarbe
			plz_txt.setForeground(Color.BLACK); // Schriftfarbe
			plz_txt.setBounds(260, 240, 160, 20);
			plz_txt.setVisible(true);
			this.add(plz_txt);
			/*---------------TextFeld Ende PLZ---------------*/
			
			/*---------------TextFeld Ort---------------*/
			final Label ort_lbl = new Label("Ort: ");
			ort_lbl.setBounds(50, 270, 140, 20);
			ort_lbl.setBackground(new Color(220,220,220)); // Hintergrundfarbe
			ort_lbl.setVisible(true);
			this.add(ort_lbl);
			
			final TextField ort_txt = new TextField();
			ort_txt.setBackground(Color.white); // Hintergrundfarbe
			ort_txt.setForeground(Color.BLACK); // Schriftfarbe
			ort_txt.setBounds(260, 270, 160, 20);
			ort_txt.setVisible(true);
			this.add(ort_txt);
			/*---------------TextFeld Ende Ort---------------*/
			
			/*---------------TextFeld Telefon---------------*/
			final Label tel_lbl = new Label("Telefon: ");
			tel_lbl.setBounds(50, 300, 140, 20);
			tel_lbl.setBackground(new Color(220,220,220)); // Hintergrundfarbe
			tel_lbl.setVisible(true);
			this.add(tel_lbl);
			
			final TextField tel_txt = new TextField();
			tel_txt.setBackground(Color.white); // Hintergrundfarbe
			tel_txt.setForeground(Color.BLACK); // Schriftfarbe
			tel_txt.setBounds(260, 300, 160, 20);
			tel_txt.setVisible(true);
			this.add(tel_txt);
			/*---------------TextFeld Ende Telefon---------------*/
			
			/*---------------TextFeld Fax---------------*/
			final Label fax_lbl = new Label("Fax: ");
			fax_lbl.setBounds(50, 330, 140, 20);
			fax_lbl.setBackground(new Color(220,220,220)); // Hintergrundfarbe
			fax_lbl.setVisible(true);
			this.add(fax_lbl);
			
			final TextField fax_txt = new TextField();
			fax_txt.setBackground(Color.white); // Hintergrundfarbe
			fax_txt.setForeground(Color.BLACK); // Schriftfarbe
			fax_txt.setBounds(260, 330, 160, 20);
			fax_txt.setVisible(true);
			this.add(fax_txt);
			/*---------------TextFeld Ende Fax---------------*/
			
			/*---------------TextFeld Bemerkung---------------*/
			final Label bem_lbl = new Label("Bemerkung: ");
			bem_lbl.setBounds(50, 360, 140, 20);
			bem_lbl.setBackground(new Color(220,220,220)); // Hintergrundfarbe
			bem_lbl.setVisible(true);
			this.add(bem_lbl);
			
			final TextField bem_txt = new TextField();
			bem_txt.setBackground(Color.white); // Hintergrundfarbe
			bem_txt.setForeground(Color.BLACK); // Schriftfarbe
			bem_txt.setBounds(260, 360, 160, 20);
			bem_txt.setVisible(true);
			this.add(bem_txt);
			/*---------------TextFeld Ende Bemerkung---------------*/
			
			/*---------------Button Datensatz anzeigen---------------*/
			final Button naechster_btn = new Button("Naechster Datensatz");
			naechster_btn.setBounds(50, 390, 140, 30);
			naechster_btn.setBackground(Color.white); // Hintergrundfarbe
			naechster_btn.setForeground(Color.BLACK); // Schriftfarbe
			naechster_btn.setVisible(true);
			this.add(naechster_btn);
			/*---------------Button Datensatz anzeigen---------------*/
			
			// Label Person    
			final Label person;    
			person = new Label((zaehler + 1) + " / " + Client.daten.size());
			person.setBounds(300, 395, 50, 20);    
			person.setVisible(true);    
			this.add(person);
			
			name_txt.setText(Client.daten.get(0).name);
			vname_txt.setText(Client.daten.get(0).vname);
			anrede_txt.setText(Client.daten.get(0).anrede);
			strasse_txt.setText(Client.daten.get(0).strasse);
			plz_txt.setText(Client.daten.get(0).plz);
			ort_txt.setText(Client.daten.get(0).ort);
			tel_txt.setText(Client.daten.get(0).telefon);
			fax_txt.setText(Client.daten.get(0).fax);
			bem_txt.setText(Client.daten.get(0).bem);
			
			//naechster_Listener
			naechster_btn.addActionListener(
				new ActionListener() {    
					public void actionPerformed(ActionEvent ae) { 
						
						if ((zaehler + 1) < Client.daten.size()) {       
							addZaehler(); // zaehler um 1 erhˆhen       
							person.setText((zaehler + 1) + " / " 
							+ Client.daten.size());      
						}  
						
					     // TextFields mit Personendaten f¸llen
						name_txt.setText(Client.daten.get(zaehler).name);
						vname_txt.setText(Client.daten.get(zaehler).vname);
						anrede_txt.setText(Client.daten.get(zaehler).anrede);
						strasse_txt.setText(Client.daten.get(zaehler).strasse);
						plz_txt.setText(Client.daten.get(zaehler).plz);
						ort_txt.setText(Client.daten.get(zaehler).ort);
						tel_txt.setText(Client.daten.get(zaehler).telefon);
						fax_txt.setText(Client.daten.get(zaehler).fax);
						bem_txt.setText(Client.daten.get(zaehler).bem);
						
						// Button bei keinen weiteren Personen sperren      
						if ((zaehler + 1) == Client.daten.size()){
							naechster_btn.setEnabled(false);
						}
					}   
				}
			);	
		}
	}
	
	/**
	 * Die Funktion erhˆht den Zaehler um 1. Ist notwendig um die n‰chsten 
	 * Datens‰tze anzuzeigen
	 */
	public void addZaehler(){
		zaehler++;
	}
	
	
	/**
	 * Die Funktion "speichern" wird in der Klasse Client ausgef¸hrt und dem 
	 * Server ¸ber senden(3) weitergegeben. Alte Fenster werden gelˆscht. Das 
	 * Fenster zum Speichern wird modelliert und zeigt an, dass die Records 
	 * gespeichert wurden
	 */
	public void safe(){
		
		new Client().senden(3);
		Client.speichern();
		
		this.removeAll();
		headline("Records speichern");
		
		// Label Speichern    
		final Label speichern;    
		speichern = new Label("Die Records wurden auf dem Server gespeichert");    
		speichern.setBounds(50, 80, 500, 50);    
		speichern.setVisible(true);    
		this.add(speichern);
	}
	

	/**
	 * Lˆscht alte Fenster. Zeigt die bereits vorhandenen Datens‰tze an. 
	 * Signalisiert dem Server den Men¸punkt laden auszuf¸hren. Ruft die Methode
	 * laden() auf dem Klassenobjekt Client auf.
	 */
	public void laden(){

		new Client().senden(4);
		Client.laden();
		
		this.removeAll();
		headline("Records laden");
		
		// Label Laden    
		final Label laden;    
		laden = new Label("Die Records wurden, falls vorhanden, geladen.");    
		laden.setBounds(50, 80, 500, 50);    
		laden.setVisible(true);    
		this.add(laden);
	}
	
	
	/**
	 * Die Methode sortieren wird ausgef¸hrt und 
	 * ein Fenster zur Best‰tigung der Sortierung erstellt. 
	 * Alte Fenster werden gelˆscht. 
	 * Dem Server wird signalisiert, dass die Methode sortieren ausgef¸hrt wird
	 */
	public void sortieren(){
		
		new Client().senden(5);
		Client.sort();
		
		this.removeAll();
		headline("Records sortieren");
		
		// Label Sortieren    
		final Label sort;    
		sort = new Label("Die Records wurden sortiert");    
		sort.setBounds(50, 80, 500, 50);    
		sort.setVisible(true);    
		this.add(sort);
	}
	
	
	/**
	 * Dem Server wird signalisiert die 
	 * ausgew‰hlte Dateei zu lˆschen. Ein Fenster zum Lˆschen der Datei
	 * und der dazugehˆrigen Best‰tigung wird erstellt.
	 */
	public void loeschen(){
		
		new Client().senden(6);
		
		this.removeAll();
		headline("Datei lˆschen");
		
		// Label Loeschen    
		final Label delete;    
		delete = new Label("Die Datei wurde geloescht");    
		delete.setBounds(50, 80, 500, 50);    
		delete.setVisible(true);    
		this.add(delete);
	}
	
	
	/**
	 * Zeigt in der Fuﬂzeile der GUI die Autoren an.
	 */
	public void paint(Graphics g) {  
		// Footer   
		g.drawString("© Andreas Hess, Romeo Martinovic, Philip Wangler", 
				this.getWidth() - 300,this.getHeight() - 20);
	} 
}
