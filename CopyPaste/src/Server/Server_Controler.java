package Server;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Erstellt Streams und Sockets um eine Verbindung zwischen Server und Client 
 * herzustellen.
 * @author Andreas Hess, Romeo Martinovic, Philip Wangler
 * @version 1.0 ADRELI_GUI
 * 
 */




public class Server_Controler extends Thread{
	
	
	/**
	 * Erstellt Server-Threads und startet sie, ¸bergibt in und out Streams und
	 * die IP des Clients. Erstellt log-file.
	 */
	public void run(){

		ServerSocket server;
		
		try{
			 //Serversocket
			 server = new ServerSocket(Server_Gui.getPort());			 
			 
			 while(true){
				//Client annehmen
				Socket client = server.accept();
				//neuer Client hat sich verbunden
				Server_Gui.addClient();
				
				//IP Adresse des Clients
				InetAddress clientIP = client.getInetAddress();
				
				OutputStream out = client.getOutputStream();
				InputStream in = client.getInputStream(); 

				/*
				 * Server-Thread erstellen und starten, ¸bergibt 
				 * in out Streams und IP des Clients
				 */
				Server s = new Server(in, out, clientIP); 
				s.start();
				
				File log = new File("log.csv");
				
				if(!log.exists()){
					try{
						log.createNewFile();
					}catch(Exception e){
						System.out.println("log-File konnte nicht "
								+ "erzeugt werden!");
					}
				}
			 }
			
		}catch(Exception e){
			System.out.println("Fehler bei Controller");
			e.printStackTrace();
		}
	}
}
