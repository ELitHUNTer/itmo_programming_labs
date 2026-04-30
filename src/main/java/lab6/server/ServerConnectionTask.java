package lab6.server;

import lab6.server.commands.base.CommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerConnectionTask implements Runnable{

    private final DatagramPacket receivePacket;
    private final DatagramSocket serverSocket;
    private CommandManager manager;
    private Logger logger = LoggerFactory.getLogger(ServerConnectionTask.class);

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
            if (receivedData.equals("u?")){
                byte[] sendBuffer = manager.executeCommand("u?").getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);
                return;
            }
            logger.info("Получено от " + clientAddress + ":" + clientPort + " -> " + receivedData);

            Request request = MyGsonFactory.get().fromJson(receivedData, Request.class);
            logger.info("Обработка команды: " + request.getRawCommand());
            String responseData = manager.executeCommand(request.getRawCommand());
            logger.info("Результат работы: " + responseData);

            byte[] sendBuffer = responseData.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
            serverSocket.send(sendPacket);

            logger.info("Отправлено: " + responseData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
