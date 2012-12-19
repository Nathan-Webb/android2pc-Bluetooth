package com.anup.remotebluetooth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class RFCOMMServer {
	public static void main(String[] args) {
		try {
			StreamConnectionNotifier service = (StreamConnectionNotifier) Connector.open("btspp://localhost:"
					+ new UUID("0000110100001000800000805F9B34FB", false).toString()
					+ ";name=helloService");
			
			StreamConnection conn = service.acceptAndOpen();
			
			System.out.println("Connected");
			
			DataInputStream in = new DataInputStream(conn.openInputStream());
			DataOutputStream out = new DataOutputStream(conn.openDataOutputStream());
			
			String received = in.readUTF();
			out.writeUTF("Echo: " + received); 
			
			conn.close();
			service.close();
		} catch (IOException e) {
			System.err.print(e.toString());
		} 
	}
}
