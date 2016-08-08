/* 
 * Copyright (C) 2016 De Mey Matthias
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
