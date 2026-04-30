package lab6.server;

import lab5.IOHelper;
import lab6.server.commands.base.CommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerMain implements Runnable {

    private ThreadPoolExecutor executor;
    private ExecutorService serverConsoleExecutor;
    private final int PORT = 9988;

    private CommandManager manager;

    private Logger logger = LoggerFactory.getLogger(ServerMain.class);

    public ServerMain(){
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        CollectionController cc = new CollectionController();
        manager = new CommandManager(cc);
        serverConsoleExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void run() {
        serverConsoleExecutor.submit(() -> {
            while (true) {
                try {
                    lab5.IOHelper.consoleOut.print(">>");
                    String consoleLine = IOHelper.readConsoleLine();
                    if (consoleLine.equals("save")) {
                        //logger.info("save command");
                        logger.info(manager.executeSaveForServer());
                        continue;
                    }
                    logger.info(manager.executeCommand(consoleLine));
                } catch (IllegalArgumentException ex){
                    IOHelper.consoleOut.println(ex.getMessage());
                } catch (Exception e){
                    System.err.println(e.getMessage());
                }
            }
        });
        try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
            logger.info("Сервер запущен на порту " + PORT);

            byte[] receiveBuffer = new byte[65000];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);
                executor.submit(new ServerConnectionTask(serverSocket, receivePacket, manager));
            }
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            executor.shutdown();
        }
    }
}
