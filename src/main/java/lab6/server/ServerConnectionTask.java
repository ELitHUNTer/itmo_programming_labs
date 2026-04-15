package lab6.server;

import lab6.server.commands.base.CommandManager;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerConnectionTask implements Runnable{

    private final DatagramPacket receivePacket;
    private final DatagramSocket serverSocket;
    private CommandManager manager;

    public ServerConnectionTask(DatagramSocket serverSocket, DatagramPacket receivePacket, CommandManager manager){
        this.receivePacket = receivePacket;
        this.serverSocket = serverSocket;
        this.manager = manager;
    }

    @Override
    public void run() {
        try {
            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();

            String receivedData = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Поток " + Thread.currentThread().getName() +
                    ": Получено от " + clientAddress + ":" + clientPort + " -> " + receivedData);

            Request request = MyGsonFactory.get().fromJson(receivedData, Request.class);
            String responseData = manager.executeCommand(request.getRawCommand());

            byte[] sendBuffer = responseData.getBytes();


            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
            serverSocket.send(sendPacket);

            System.out.println("Поток " + Thread.currentThread().getName() + ": Ответ отправлен.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
