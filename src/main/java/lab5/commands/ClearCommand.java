package lab5.commands;

import lab5.CollectionController;
import lab5.commands.base.CollectionCommand;

public class ClearCommand extends CollectionCommand {

    public ClearCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        controller.clear();
    }

    @Override
    public String getDescription() {
        return "clear : очистить коллекцию";
    }
}
