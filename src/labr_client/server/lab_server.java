/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.server;
import java.io.*;
import java.net.*;

/**
 *
 * @author familie
 */
public class lab_server {
    
	server_instance[] servers = new server_instance[1]; 
	int clientNumber = 0;
	
	public void server_connect(){
		
		servers[clientNumber] = new server_instance("localhost",9898, clientNumber);
		servers[clientNumber].start();
		clientNumber++;
	}
	public String server_send(String data){
		String answer = servers[clientNumber-1].server_send(data);		
		return answer;
	}
	
	private static class server_instance extends Thread {
		
	    Socket dataSocket = null;  
	    DataOutputStream os = null;
	    BufferedReader d = null;
	    
		public server_instance(String host, int port, int ServerNumber){			
            try {
				dataSocket = new Socket(host, port);
				os = new DataOutputStream(dataSocket.getOutputStream());
				d = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public String server_send(String data){
			String answer = null;			
			if (dataSocket != null && os != null && d != null) {		
				try {					
					os.writeBytes(data);					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
		    }			
			return answer;
		}
		public void server_close(){
			
	        try {
				os.close();
				d.close();
			    dataSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       
	 
			
		}
		
	}
}
