package lab5.commands;

import lab5.commands.base.Command;

public class ExecuteScriptCommand implements Command {
    @Override
    public void execute(String... args) {
        // TODO
    }

    @Override
    public String getDescription() {
        return "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме";
    }
}
