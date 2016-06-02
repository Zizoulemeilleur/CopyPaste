package Server;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Diese Klasse definiert ein Entscheidungsfenster mit Ja Nein Dialog
 * @author Andreas Hess, Romeo Martinovic, Philip Wangler
 * @version 1.0 ADRELI_GUI
 * 
 */




@SuppressWarnings("serial")
public class Entscheidungs_Dialog extends Dialog implements ActionListener {
	
	Button btnJa, btnNein;
	boolean gewaehlt;

	/**
	 * Erstellt ein Enztscheidungsfenster, mit einem Ja und einem Nein Button
	 * @param owner
	 * @param titel
	 * @param meldung
	 */
	public Entscheidungs_Dialog(Dialog owner, String titel, String meldung) {
		super(owner, titel, true); 
		
		this.setSize(300, 150); // Fenstergroesse   
		this.setLocationRelativeTo(null); // Fenster zentrieren   
		this.setResizable(false); // feste Fenstergrˆﬂe   
		this.setLayout(null); // Layout deaktivieren  
		//Icon links oben an der GUI
		Image icon = this.getToolkit().getImage("./hfu.png");   
		this.setIconImage(icon); // Icon hinzuf¸gen
		
		//Schrift
		Font schrift = new Font("Calibri", Font.TRUETYPE_FONT, 11);   
		this.setFont(schrift); // Schrift hinzufuegen
		
		this.setForeground(Color.black); // Schriftfarbe
		
		Label lblMldg = new Label(meldung); // Meldung   
		lblMldg.setSize(260, 25);   
		lblMldg.setLocation(20, 40);  
		lblMldg.setAlignment(Label.CENTER);  
		add(lblMldg);  
			
		 btnJa = new Button("Ja"); // JA Button   
		 btnJa.setSize(70, 25);  
		 btnJa.setLocation(70, 80);  
		 this.add(btnJa);  
		 btnJa.addActionListener(this);  
		  
		 btnNein = new Button("Nein"); // NEIN Button  
		 btnNein.setSize(70, 25);   
		 btnNein.setLocation(160, 80);   
		 this.add(btnNein);  
		 btnNein.addActionListener(this);
		 
		 this.setVisible(true);

	}


	/**
	 * Schlieﬂt das Fenster und returned true oder false, je nachdem
	 * was angeklickt wird
	 */
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if(object.equals(btnJa)){
			gewaehlt = true;
		}
		setVisible(false);
		dispose();
	}
	
	public boolean antwort(){
		return gewaehlt;
	}
	
}
