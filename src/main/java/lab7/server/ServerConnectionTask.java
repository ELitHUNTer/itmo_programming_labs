package lab7.server;

import com.google.gson.Gson;
import lab7.server.commands.base.CommandManager;
import lab7.utils.DatagramChunk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerConnectionTask implements Runnable{

    private final DatagramPacket receivePacket;
    private final DatagramSocket serverSocket;
    private CommandManager manager;
    private String receivedData;
    private Logger logger = LoggerFactory.getLogger(ServerConnectionTask.class);

    public ServerConnectionTask(DatagramSocket serverSocket, DatagramPacket receivePacket, CommandManager manager, String receivedData){
        this.receivePacket = receivePacket;
        this.serverSocket = serverSocket;
        this.manager = manager;
        this.receivedData = receivedData;
    }

    @Override
    public void run() {
        Gson gson = MyGsonFactory.get();
        try {
            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();

            if (receivedData.equals("u?")){
                byte[] sendBuffer = manager.executeCommand("u?").getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);
                return;
            }
            logger.info("Получено от " + clientAddress + ":" + clientPort + " -> " + receivedData);

            String[] arr = receivedData.split(" ");
            String login =
            String command = ;
            logger.info("Обработка команды: " + command);
            String responseData = manager.executeCommand(command);
            logger.info("Результат работы: " + responseData);

            DatagramChunk[] responseChunks = DatagramChunk.split(responseData);
            //System.out.println(responseChunks.length);

            for (DatagramChunk chunk : responseChunks) {
                byte[] sendBuffer = gson.toJson(chunk).getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
                logger.info("Отправлено: " + chunk);
                serverSocket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
