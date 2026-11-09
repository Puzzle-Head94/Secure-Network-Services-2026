package p;

import java.io.*;
import java.net.*;

public class QuoteThreadHandler extends Thread {

    private DatagramPacket packet;
    private final DatagramSocket socket;
    
    public QuoteThreadHandler(DatagramPacket packet, DatagramSocket socket) {
        this.packet = packet;
        this.socket = socket;
    }
    public void run () {

        try {
            Thread.sleep(5000);
            byte[] buffer = new byte[256];

            String message = new String(packet.getData(), 0, packet.getLength());
            String[] message_list = message.split(";");
            String function = message_list[0];
            int num1 = Integer.parseInt(message_list[1]);
            int num2 = Integer.parseInt(message_list[2]);
            int value = calculate(function, num1, num2);
            String str_value = String.valueOf(value);

            String returned_message = "Value: " + str_value;
            buffer = returned_message.getBytes();
            // Send the packet out
            
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }

    protected int calculate(String function, int num1, int num2) {
        int return_val = -1;
        switch (function.toUpperCase()) {
            case "ADD":
                return_val = num1 + num2;
        }
        return return_val;
    }
}
