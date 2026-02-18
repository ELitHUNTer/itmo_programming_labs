package lab5.commands;

import lab5.commands.base.Command;

import java.util.HashMap;

public class HelpCommand implements Command {

    private HashMap<String, Command> commands;

    public HelpCommand(HashMap<String, Command> commands){
        this.commands = commands;
    }

    @Override
    public void execute(String... args) {
        StringBuilder builder = new StringBuilder();
        for (var x: commands.entrySet()) {
            builder.append(x.getKey());
            builder.append(" ");
            builder.append(x.getValue().getDescription());
            builder.append("\n");
        }
        System.out.println(builder);
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }
}
