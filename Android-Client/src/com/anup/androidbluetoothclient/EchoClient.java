package com.anup.androidbluetoothclient;

import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

public class EchoClient extends AsyncTask<Void, Void, Void>{
	
	private static final String UUID_STRING = "11111111111111111111111111111111"; // 32 hex digits
	
//	private final BluetoothSocket mmSocket;
//	private final InputStream mmInStream;
//	private final OutputStream mmOutStream;
	
	
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
	
	
}
