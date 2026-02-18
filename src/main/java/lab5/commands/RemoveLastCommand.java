package lab5.commands;

import lab5.CollectionController;
import lab5.commands.base.CollectionCommand;

public class RemoveLastCommand extends CollectionCommand {

    public RemoveLastCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        controller.remove_last();
    }

    @Override
    public String getDescription() {
        return "remove_last : удалить последний элемент из коллекции";
    }
}
