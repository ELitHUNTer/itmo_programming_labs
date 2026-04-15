package lab6.server.commands;

import lab6.server.commands.base.Command;

public class ExitCommand implements Command {
    @Override
    public String execute(String... args) {
        System.exit(-1);
        return "";
    }

    @Override
    public String getDescription() {
        return "exit : завершить программу (без сохранения в файл)";
    }
}
