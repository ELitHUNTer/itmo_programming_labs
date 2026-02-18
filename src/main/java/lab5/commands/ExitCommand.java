package lab5.commands;

import lab5.commands.base.Command;

public class ExitCommand implements Command {
    @Override
    public void execute(String... args) {
        System.exit(-1);
    }

    @Override
    public String getDescription() {
        return "exit : завершить программу (без сохранения в файл)";
    }
}
