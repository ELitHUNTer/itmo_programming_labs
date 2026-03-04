package lab5.commands;

import lab5.CollectionController;
import lab5.IOHelper;
import lab5.commands.base.CollectionCommand;
import lab5.commands.base.Command;
import lab5.commands.base.CommandManager;

import java.io.*;
import java.util.HashSet;

public class ExecuteScriptCommand extends CollectionCommand implements Command {

    private HashSet<String> openedFiles = new HashSet<>();

    public ExecuteScriptCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        if (args.length == 0) return;
        if (openedFiles.contains(args[0])) {
            System.err.println("Попытка открыть уже открытый файл");
            return;
        }
        InputStream old = IOHelper.defaultIn;
        try (FileInputStream reader = new FileInputStream(args[0])) {
            openedFiles.add(args[0]);
            IOHelper.defaultIn = reader;
            CollectionController newController = new CollectionController();
            // TODO fix reading from file reads only one line
            CommandManager manager = new CommandManager(newController);
            String command = IOHelper.readFileLine(reader);
            while (!command.isEmpty()) {
                System.out.println(command);
                command = IOHelper.readFileLine(reader);
            }
            controller.updateCollection(newController);
            IOHelper.consoleOut.println("Скрипт завершен");
        } catch (IOException e) {
        } finally {
            IOHelper.defaultIn = old;
        }
    }

    @Override
    public String getDescription() {
        return "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме";
    }
}
