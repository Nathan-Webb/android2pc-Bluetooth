package com.anup.androidbluetoothclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.d(TAG, "onCreate");

		editText = (EditText) findViewById(R.id.edit_msg);
		textView = (TextView) findViewById(R.id.rcv_msg);
		
		if(BluetoothAdapter.getDefaultAdapter().isEnabled()) {
			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(intent, RESULT_OK);
		}
		
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

			try {
				// UUID string same used by server 
				clientSocket = mmDevice.createRfcommSocketToServiceRecord(UUID
								.fromString("00001101-0000-1000-8000-00805F9B34FB"));

				mBluetoothAdapter.cancelDiscovery(); 	// Cancel, discovery slows connection

				clientSocket.connect();

				DataInputStream in = new DataInputStream(clientSocket.getInputStream());
				DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

				out.writeUTF(msg[0]); 			// Send message to server
				Log.d(TAG, "Message Successfully sent to server");
				return in.readUTF();            // Read response from server
			} catch (Exception e) {
				System.err.println(e.getMessage());
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
