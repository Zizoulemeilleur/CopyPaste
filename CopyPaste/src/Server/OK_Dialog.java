package Server;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Diese Klasse definiert ein Dialogfenster mit OK Dialog
 * @author Andreas Hess, Romeo Martinovic, Philip Wangler
 * @version 1.0 ADRELI_GUI
 * 
 */




@SuppressWarnings("serial")
public class OK_Dialog extends Dialog implements ActionListener {
	
	Button btnOk;
	boolean gewaehlt;

	
	/**
	 * Definiert ein Dialogfenster mit dem Button OK
	 * @param owner
	 * @param titel
	 * @param meldung
	 */
	public OK_Dialog(Dialog owner, String titel, String meldung) {
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
			
		 btnOk = new Button("OK"); // JA Button   
		 btnOk.setSize(70, 25);  
		 btnOk.setLocation(115, 80);  
		 this.add(btnOk);  
		 btnOk.addActionListener(this);  
		 
		 this.setVisible(true);

	}


	/**
	 * Schlieﬂt das Dialogfenster, wenn der OK Button angeklickt wird.
	 */
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if(object.equals(btnOk)){
			gewaehlt = true;
		}
		setVisible(false);
		dispose();
	}	
}
