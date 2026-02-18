package lab5.commands.base;

import lab5.CollectionController;
import lab5.commands.*;

import java.util.Arrays;
import java.util.HashMap;

public class CommandManager {

    private HashMap<String, Command> commands;

    public CommandManager(CollectionController collection){
        commands = new HashMap<>();
        commands.put("help", new HelpCommand(commands));
        commands.put("info", new InfoCommand(collection));
        commands.put("show", new ShowCommand(collection));
        commands.put("add", new AddCommand(collection));
        commands.put("clear", new ClearCommand(collection));
        commands.put("count_less_than_chapter", new CountLessThanChapterCommand(collection));
        commands.put("execute_script", new ExecuteScriptCommand());
        commands.put("exit", new ExitCommand());
        commands.put("filter_starts_with_name", new FilterStartsWithNameCommand(collection));
        commands.put("insert_at", new InsertAtIndexCommand(collection));
        commands.put("print_field_descending_health", new PrintFieldDescendingHealthCommand(collection));
        commands.put("remove_by_id", new RemoveByIdCommand(collection));
        commands.put("remove_greater", new RemoveGreaterCommand(collection));
        commands.put("remove_last", new RemoveLastCommand(collection));
        commands.put("save", new SaveCommand(collection));
        commands.put("update", new UpdateIdCommand(collection));
    }

    /**
     * Starts execution of a command by its name and args
     * @param commandName name of command
     * @param commandArgs argument to pass for a command
     */
    public void executeCommand(String commandName, String... commandArgs){
        if (!commands.containsKey(commandName))
            throw new IllegalArgumentException("No command found for name " + commandName + ". Please type help for help");

        try {
            commands.get(commandName).execute(commandArgs);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Parses line and starts execution of a command
     * @param unparsedLine command and args in unparsed format
     */
    public void executeCommand(String unparsedLine){
        String[] arr = unparsedLine.trim().split(" ");
        if (arr.length == 0)
            return;
        executeCommand(arr[0], Arrays.copyOfRange(arr, 1, arr.length));
    }

}
