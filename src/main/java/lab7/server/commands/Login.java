package lab7.server.commands;

import lab7.server.DataBaseManager;
import lab7.server.commands.base.Command;

public class Login implements Command {

    @Override
    public String execute(String userName, String... args) {
        if (args.length != 2)
            return "Команда должна содержать 2 параметра - логин и пароль";
        return DataBaseManager.getInstance().hasUser(args[0], args[1]) ?
                "Добро пожаловать!" :
                "Нет такого пользователя";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
