package lab6.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lab6.client.collection_items.Chapter;
import lab6.client.collection_items.SpaceMarine;
import lab6.server.CollectionController;
import lab6.server.FileReadingException;
import lab6.server.ServerConnectionTask;
import lab6.server.commands.base.CommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

public class ClientMain implements Runnable{

    interface Command{
        void execute(String... args);
    }

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 9988;
    private static final int MAX_RETRIES = 3;
    private static final int TIMEOUT_MS = 2000;
    private DatagramChannel channel;
    private final HashSet<String> openedFiles = new HashSet<>();
    private HashMap<String, Command> clientCommands = new HashMap<>() {{
            put("exit", (args) -> System.exit(0));
            put("execute_script", (args) -> {
                if (openedFiles.contains(args[0])) {
                    System.err.println("Попытка открыть уже открытый файл");
                    return;
                }
                InputStreamReader old = IOHelper.defaultIn;
                try (FileInputStream fileReader = new FileInputStream(args[0]);
                     InputStreamReader reader = new InputStreamReader(fileReader)) {
                    openedFiles.add(args[0]);
                    IOHelper.defaultIn = reader;
                    //CollectionController newController = new CollectionController();
                    //CommandManager manager = new CommandManager(newController);
                    String command = IOHelper.readFileLine(reader);
                    while (!command.isEmpty()) {
                        String message = handleCommand(command, reader);
                        if (message == null) continue;
                        sendToServer(message);
                        //manager.executeCommand(command);
                        command = IOHelper.readFileLine(reader);
                    }
                    //controller.updateCollection(newController);
                    IOHelper.consoleOut.println("Скрипт завершен");
                    //return "Скрипт завершен";
                } catch (IOException e) {
                } catch (FileReadingException e) {
                    IOHelper.errOut.println("Ошибка при чтении файла. Изменения не будут применены");
                    //return "Ошибка при чтении файла. Изменения не будут применены";
                } finally {
                    IOHelper.defaultIn = old;
                    openedFiles.remove(args[0]);
                }
            });
    }};
    private Gson gson = new Gson();
    private Scanner sc;
    private Selector selector;
    //private Logger logger = LoggerFactory.getLogger(ClientMain.class);

    {
        gson = new GsonBuilder()
                .registerTypeAdapter(
                        LocalDate.class,
                        new TypeAdapter<LocalDate>(){

                            @Override
                            public void write(JsonWriter jsonWriter, LocalDate localDate) throws IOException {
                                jsonWriter.value(localDate.toString());
                            }

                            @Override
                            public LocalDate read(JsonReader jsonReader) throws IOException {
                                return LocalDate.parse(jsonReader.nextString());
                            }
                        }
                )
                .registerTypeAdapter(lab6.client.collection_items.SpaceMarine.class, new lab6.client.collection_items.SpaceMarine.Adapter()).create();
    }

    @Override
    public void run() {
        try {
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);
            sc = new Scanner(System.in);

            IOHelper.consoleOut.print(">>");
            while (sc.hasNextLine()) {
                //IOHelper.consoleOut.print(">>");
                String message = handleCommand(sc.nextLine(), IOHelper.consoleIn);
                if (message == null) continue;
                sendToServer(message);
                IOHelper.consoleOut.print(">>");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendToServer(String message) throws IOException {
        String json = gson.toJson(new Request(message));
        //System.out.println(json);
        ByteBuffer sendBuffer = ByteBuffer.wrap(json.getBytes());
        InetSocketAddress serverAddress = new InetSocketAddress(SERVER_HOST, SERVER_PORT);

        boolean responseReceived = false;
        int retryCount = 0;

        System.out.println("Отправка данных: " + message);
        //logger.info("Отправка данных: " + message);

        channel.send(sendBuffer, serverAddress);

        while (!responseReceived && retryCount < MAX_RETRIES) {
            int readyChannels = selector.select(TIMEOUT_MS);

            if (readyChannels == 0) {
                retryCount++;
                System.out.println("Сервер временно недоступен. Повторная попытка (" + retryCount + "/" + MAX_RETRIES + ")...");
                sendBuffer.rewind();
                channel.send(sendBuffer, serverAddress);
            } else {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                responseReceived = handleResponse(iterator);
                //return iterator;
            }
        }
        if (!responseReceived) {
            System.out.println("Ошибка: Сервер так и не ответил после " + MAX_RETRIES + " попыток.");
        }
        //return null;
    }

    private boolean canUseUpdateCommand(int id) throws IOException{
        channel.send(
                ByteBuffer.wrap("u?".getBytes()),
                new InetSocketAddress(SERVER_HOST, SERVER_PORT));
        int readyChannels = selector.select(TIMEOUT_MS);
        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            if (key.isReadable()) {
                ByteBuffer receiveBuffer = ByteBuffer.allocate(65000);
                InetSocketAddress from = (InetSocketAddress) channel.receive(receiveBuffer);

                if (from != null) {
                    receiveBuffer.flip();
                    String response = new String(receiveBuffer.array(), 0, receiveBuffer.limit(), StandardCharsets.UTF_8);
                    //System.out.println(response);
                    iterator.remove();
                    return Arrays.stream(response.split(" ")).anyMatch(x -> x.equals(String.valueOf(id)));
                    //logger.info("Получен ответ от сервера: " + response);
                }
            }
            iterator.remove();
        }
        return false;
    }

    private boolean handleResponse(Iterator<SelectionKey> iterator) throws IOException{
        boolean responseReceived = false;
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            if (key.isReadable()) {
                ByteBuffer receiveBuffer = ByteBuffer.allocate(65000);
                InetSocketAddress from = (InetSocketAddress) channel.receive(receiveBuffer);

                if (from != null) {
                    receiveBuffer.flip();
                    String response = new String(receiveBuffer.array(), 0, receiveBuffer.limit(), StandardCharsets.UTF_8);
                    //logger.info("Получен ответ от сервера: " + response);
                    System.out.println(response);
                    responseReceived = true;
                }
            }
            iterator.remove();
        }
        return responseReceived;
    }

    private String handleCommand(String command, InputStreamReader reader) throws IOException, NumberFormatException {
        //logger.info("Обработка команды: " + command);
        String[] splittedCommand = command.split(" ");
        if (clientCommands.containsKey(splittedCommand[0])){
            clientCommands.get(splittedCommand[0]).execute(Arrays.copyOfRange(splittedCommand, 1, splittedCommand.length));
            return null;
        }
        if (splittedCommand[0].equals("update")){
            if (!canUseUpdateCommand(Integer.parseInt(splittedCommand[1]))) {
                System.out.println("Не существует id " + splittedCommand[1]);
                return null;
            }
        }
        try {
            Queue<String> args = getCommandArgs(splittedCommand, reader);
            StringBuilder sb = new StringBuilder(splittedCommand[0]);
            while(!args.isEmpty())
                sb.append(" ").append(args.poll());
            return sb.toString();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private LinkedList<String> getCommandArgs(String[] parsedCommand, InputStreamReader reader) throws UnsupportedEncodingException {
        LinkedList<String> ret = new LinkedList<>();
        switch (parsedCommand[0]){
            case "count_less_than_chapter":
                Chapter chapter = IOHelper.readChapter(reader, true);
                ret.add(gson.toJson(chapter));
                //logger.info(String.format("Добавлен аргумент %s к команде %s", gson.toJson(chapter), parsedCommand[0]));
                break;
            case "filter_starts_with_name":
            case "execute_script":
                ret.add(parsedCommand[1]);
                //logger.info(String.format("Добавлен аргумент %s к команде %s", parsedCommand[1], parsedCommand[0]));
                break;
            case "remove_by_id":
                Integer.parseInt(parsedCommand[1]);
                ret.add(parsedCommand[1]);
                //logger.info(String.format("Добавлен аргумент %s к команде %s", parsedCommand[1], parsedCommand[0]));
                break;
            case "update":
            case "insert_at":
                Integer.parseInt(parsedCommand[1]);
                ret.add(parsedCommand[1]);
                //logger.info(String.format("Добавлен аргумент %s к команде %s", parsedCommand[1], parsedCommand[0]));
            case "add":
            case "remove_greater":
                SpaceMarine marine = IOHelper.readMarine(reader);
                ret.add(gson.toJson(marine));
                //logger.info(String.format("Добавлен аргумент %s к команде %s", gson.toJson(marine), parsedCommand[0]));
                break;
        }
        return ret;
    }
}
