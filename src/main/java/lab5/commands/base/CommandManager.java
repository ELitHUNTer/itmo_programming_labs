package lab5.commands.base;

import lab5.CollectionController;
import lab5.commands.HelpCommand;
import lab5.commands.InfoCommand;

import java.util.Arrays;
import java.util.HashMap;

public class CommandManager {

    private HashMap<String, Command> commands;

    public CommandManager(CollectionController collection){
        commands = new HashMap<>();
        commands.put("help", new HelpCommand(commands));
        commands.put("info", new InfoCommand(collection));
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
