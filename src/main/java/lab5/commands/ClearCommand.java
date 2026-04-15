package lab5.commands;

import lab5.CollectionController;
import lab5.IOHelper;
import lab5.commands.base.CollectionCommand;

public class ClearCommand extends CollectionCommand {

    public ClearCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        controller.clear();
        IOHelper.printlnIfUsingConsole("Коллекция очищена");
    }

    @Override
    public String getDescription() {
        return "clear : очистить коллекцию";
    }
}
