package lab5.commands;

import lab5.CollectionController;
import lab5.commands.base.CollectionCommand;

public class RemoveGreaterCommand extends CollectionCommand {

    public RemoveGreaterCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        // TODO
    }

    @Override
    public String getDescription() {
        return "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный";
    }
}
