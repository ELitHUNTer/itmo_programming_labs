package lab5.commands;

import lab5.CollectionController;
import lab5.commands.base.CollectionCommand;

import java.util.Arrays;

public class SaveCommand extends CollectionCommand {

    public SaveCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        Arrays.stream(args).forEach(System.out::println);
        if (args.length != 0)
            controller.save(args[0]);
        else
            controller.save();
    }

    @Override
    public String getDescription() {
        return "save : сохранить коллекцию в файл";
    }
}
