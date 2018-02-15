package com.anup.androidbluetoothclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Set;
import java.util.UUID;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	
	EditText editText;
	TextView textView;

	String send_msg;
	String rcv_msg;
	
	private static final String UUID_STRING = "00000000-0000-0000-0000-00000000ABCD"; // 32 hex digits

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.d(TAG, "onCreate");

		editText = (EditText) findViewById(R.id.edit_msg);
		textView = (TextView) findViewById(R.id.rcv_msg);
		
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		
		if(adapter == null) {
			textView.append("Bluetooth NOT Supported!");
			return;
		}
		
		// Request user to turn ON Bluetooth
		if(!adapter.isEnabled()) {
			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(intent, RESULT_OK);
		}
		
		// Discover devices and display them
//		Set<BluetoothDevice> devices = adapter.getBondedDevices();
//		textView.setText("");
//		for (BluetoothDevice device: devices) {
//			textView.append(device.getName().toString());
//		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onClick(View view) {
		Log.d(TAG, "onClick");
		new SendMessageToServer().execute(send_msg);
	}

	private class SendMessageToServer extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... msg) {
			Log.d(TAG, "doInBackground");
			BluetoothSocket clientSocket = null;
			BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			mBluetoothAdapter.enable();
			// Client knows the server MAC address 
			BluetoothDevice mmDevice = mBluetoothAdapter.getRemoteDevice("00:25:00:C3:1C:FE");
			Log.d(TAG, "got hold of remote device");
			try {
				// UUID string same used by server 
				clientSocket = mmDevice.createInsecureRfcommSocketToServiceRecord(UUID
								.fromString(UUID_STRING));

				Log.d(TAG, "bluetooth socket created");
				
				mBluetoothAdapter.cancelDiscovery(); 	// Cancel, discovery slows connection

				clientSocket.connect();
				Log.d(TAG, "connected to server");
				
				DataInputStream in = new DataInputStream(clientSocket.getInputStream());
				DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

				out.writeUTF(msg[0]); 			// Send message to server
				Log.d(TAG, "Message Successfully sent to server");
				return "done";            // Allows the task to end
			} catch (Exception e) {
				
				Log.d(TAG, "Error creating bluetooth socket");
				Log.d(TAG, e.getMessage());
				return "";
			}
			
		}

		@Override
		protected void onPostExecute(String result) {
			Log.d(TAG, "onPostExecute");
			rcv_msg = result;
			textView.setText(rcv_msg);
		}

	}

}
