package lab6.server;

import lab6.server.commands.base.CommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerMain implements Runnable {

    private ThreadPoolExecutor executor;
    private final int PORT = 8080;

    private CommandManager manager;

    private Logger logger = LoggerFactory.getLogger(ServerMain.class);

    public ServerMain(){
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        CollectionController cc = new CollectionController();
        manager = new CommandManager(cc);
    }

    @Override
    public void run() {
        try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
            logger.info("Сервер запущен на порту " + PORT);

            byte[] receiveBuffer = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);
                executor.submit(new ServerConnectionTask(serverSocket, receivePacket, manager));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
