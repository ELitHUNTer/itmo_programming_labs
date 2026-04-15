package lab6.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lab6.client.collection_items.Chapter;
import lab6.client.collection_items.SpaceMarine;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

public class ClientMain implements Runnable{

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8080;
    private static final int MAX_RETRIES = 3;
    private static final int TIMEOUT_MS = 2000;
    private DatagramChannel channel;
    private HashMap<String, Runnable> clientCommands = new HashMap<>() {{
            put("exit", () -> System.exit(0));
    }};
    private Gson gson = new Gson();
    private Scanner sc;

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
            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);
            sc = new Scanner(System.in);


            while (true) {
                lab5.IOHelper.consoleOut.print(">>");
                String message = handleCommand(sc.nextLine());
                if (message == null) continue;
                String json = gson.toJson(new Request(message));
                //System.out.println(json);
                ByteBuffer sendBuffer = ByteBuffer.wrap(json.getBytes());
                InetSocketAddress serverAddress = new InetSocketAddress(SERVER_HOST, SERVER_PORT);

                boolean responseReceived = false;
                int retryCount = 0;

                System.out.println("Отправка данных: " + message);

                channel.send(sendBuffer, serverAddress);

                while (!responseReceived && retryCount < MAX_RETRIES) {
                    int readyChannels = selector.select(TIMEOUT_MS);

                    if (readyChannels == 0) {
                        retryCount++;
                        System.err.println("Сервер временно недоступен. Повторная попытка (" + retryCount + "/" + MAX_RETRIES + ")...");
                        sendBuffer.rewind();
                        channel.send(sendBuffer, serverAddress);
                    } else {
                        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                        responseReceived = handleResponse(iterator);
                    }
                }
                if (!responseReceived) {
                    System.err.println("Ошибка: Сервер так и не ответил после " + MAX_RETRIES + " попыток.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean handleResponse(Iterator<SelectionKey> iterator) throws IOException{
        boolean responseReceived = false;
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            if (key.isReadable()) {
                ByteBuffer receiveBuffer = ByteBuffer.allocate(2048);
                InetSocketAddress from = (InetSocketAddress) channel.receive(receiveBuffer);

                if (from != null) {
                    receiveBuffer.flip();
                    String response = new String(receiveBuffer.array(), 0, receiveBuffer.limit(), StandardCharsets.UTF_8);
                    System.out.println("Получен ответ от сервера: " + response);
                    responseReceived = true;
                }
            }
            iterator.remove();
        }
        return responseReceived;
    }

    private String handleCommand(String command) throws UnsupportedEncodingException {
        String[] splittedCommand = command.split(" ");
        if (clientCommands.containsKey(splittedCommand[0])){
            clientCommands.get(splittedCommand[0]).run();
            return null;
        }
        try {
            Queue<String> args = getCommandArgs(splittedCommand);
            StringBuilder sb = new StringBuilder(splittedCommand[0]);
            while(!args.isEmpty()){
                sb.append(" ");
                sb.append(args.poll());
            }
            return sb.toString();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private LinkedList<String> getCommandArgs(String[] parsedCommand) throws UnsupportedEncodingException {
        LinkedList<String> ret = new LinkedList<>();
        switch (parsedCommand[0]){
            case "count_less_than_chapter":
                Chapter chapter = IOHelper.readChapter(IOHelper.consoleIn, true);
                ret.add(gson.toJson(chapter));
                break;
            case "filter_starts_with_name":
            case "execute_script":
                ret.add(parsedCommand[1]);
                break;
            case "remove_by_id":
                Integer.parseInt(parsedCommand[1]);
                ret.add(parsedCommand[1]);
                break;
            case "update":
            case "insert_at":
                Integer.parseInt(parsedCommand[1]);
                ret.add(parsedCommand[1]);
            case "add":
            case "remove_greater":
                SpaceMarine marine = IOHelper.readMarine();
                ret.add(gson.toJson(marine));
                break;
        }
        return ret;
    }
}
