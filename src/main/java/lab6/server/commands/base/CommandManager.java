package lab6.server.commands.base;

import lab6.server.CollectionController;
import lab6.server.collection_items.SpaceMarine;
import lab6.server.commands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;

public class CommandManager {

    private HashMap<String, Command> commands;
    private final Logger logger = LoggerFactory.getLogger(CommandManager.class);

    public CommandManager(CollectionController collection){
        commands = new HashMap<>();
        commands.put("help", new HelpCommand(commands));
        commands.put("info", new InfoCommand(collection));
        commands.put("show", new ShowCommand(collection));
        commands.put("add", new AddCommand(collection));
        commands.put("clear", new ClearCommand(collection));
        commands.put("count_less_than_chapter", new CountLessThanChapterCommand(collection));
        //commands.put("execute_script", new ExecuteScriptCommand(collection));
        commands.put("exit", new ExitCommand());
        commands.put("filter_starts_with_name", new FilterStartsWithNameCommand(collection));
        commands.put("insert_at", new InsertAtIndexCommand(collection));
        commands.put("print_field_descending_health", new PrintFieldDescendingHealthCommand(collection));
        commands.put("remove_by_id", new RemoveByIdCommand(collection));
        commands.put("remove_greater", new RemoveGreaterCommand(collection));
        commands.put("remove_last", new RemoveLastCommand(collection));
        commands.put("save", new SaveCommand(collection));
        commands.put("update", new UpdateIdCommand(collection));
        commands.put("u?", new Command() {
            @Override
            public String execute(String... args) {
                StringBuilder sb = new StringBuilder();
                collection.getCollectionElements().stream().map(SpaceMarine::getID).forEach(x -> sb.append(x).append(" "));
                return sb.toString();
            }

            @Override
            public String getDescription() {
                return "";
            }
        });
        StringBuilder sb = new StringBuilder();
        commands.keySet().stream().filter(x -> !x.equals("u?")).forEach(x -> sb.append(x).append(", "));
        logger.info("Зарегистрированы команды: " + sb);
    }

    /**
     * Starts execution of a command by its name and args
     * @param commandName name of command
     * @param commandArgs argument to pass for a command
     */
    public String executeCommand(String commandName, String... commandArgs){
        if (!commands.containsKey(commandName) || commandName.equals("save"))
            //throw new IllegalArgumentException("No command found for name " + commandName + ". Please type help for help");
            return "No command found for name " + commandName + ". Please type help for help";
        logger.info("Provided command: " + commandName);
        try {
            return commands.get(commandName).execute(commandArgs);
        } catch (Exception ex){
            //ex.printStackTrace();
            return ex.getMessage();
        }
    }

    public String executeSaveForServer(String... commandArgs){
        return commands.get("save").execute(commandArgs);
    }

    /**
     * Parses line and starts execution of a command
     * @param unparsedLine command and args in unparsed format
     */
    public String executeCommand(String unparsedLine){
        String[] arr = unparsedLine.trim().split(" ");
        if (arr.length == 0)
            return "";
        return executeCommand(arr[0], Arrays.copyOfRange(arr, 1, arr.length));
    }

}
