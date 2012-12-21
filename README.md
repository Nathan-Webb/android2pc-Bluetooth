android2pc-Bluetooth
====================

A sample program to transfer data from android to pc over bluetooth. 
The pc acts as a server and the android phone as a client.

Requirements:
PC: Java SE 1.6 or above
    Bluecove 2.1 Java bluetooth library
    
Phone: Android 2.2 or higher.

Note:
Currently the program only support transfer of message from phone to pc.

Compilation Instructions.

1. To compile server code.
   javac -classpath .:libs/bluecove-2.1.0.jar src/com/anup/remotebluetooth/Main.java
   java -d32 -classpath .:libs/bluecove-2.1.0.jar src/com/anup/remotebluetooth/Main