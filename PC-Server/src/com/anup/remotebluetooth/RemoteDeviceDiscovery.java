package src.com.anup.remotebluetooth;

import java.io.IOException;
import java.util.Vector;
import javax.bluetooth.*;

public class RemoteDeviceDiscovery {
	public static final Vector<RemoteDevice> devicesDiscovered = new Vector<RemoteDevice>();
	
	public static void main(String[] args) throws IOException, InterruptedException {
		final Object inquiryCompletedEvent = new Object();
		
		devicesDiscovered.clear();
		
		DiscoveryListener listener = new DiscoveryListener() {
			
			@Override
			public void servicesDiscovered(int arg0, ServiceRecord[] arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void serviceSearchCompleted(int arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void inquiryCompleted(int discType) {
				if(discType == INQUIRY_COMPLETED) {
					System.out.println("Device Inquiry completed");
					synchronized (inquiryCompletedEvent) {
						inquiryCompletedEvent.notifyAll();
					}
				}
				else {
					System.out.println("Inquiry completion failed");
				}
			}
			
			@Override
			public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
				System.out.println("Device " + btDevice.getBluetoothAddress() + " found");
				devicesDiscovered.add(btDevice);
				try {
					System.out.println("	name " + btDevice.getFriendlyName(false));
				} catch (IOException cantGetDeviceName) {
					cantGetDeviceName.printStackTrace();
				}
				
			}
		};
	
		synchronized(inquiryCompletedEvent) {
			System.out.println("This machine has mac address: " + LocalDevice.getLocalDevice().getBluetoothAddress());
			boolean started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, listener);
			if(started) {
				System.out.println("wait for device inquiry to complete...");
				inquiryCompletedEvent.wait();
				System.out.println(devicesDiscovered.size() + " devices found");
			}
		}
	
	}

}
