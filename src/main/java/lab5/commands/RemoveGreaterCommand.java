package lab5.commands;

import lab5.CollectionController;
import lab5.IOHelper;
import lab5.collection_items.SpaceMarine;
import lab5.commands.base.CollectionCommand;

public class RemoveGreaterCommand extends CollectionCommand {

    public RemoveGreaterCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        SpaceMarine origin = IOHelper.readMarine();
        controller.removeGreater(origin);
    }

    @Override
    public String getDescription() {
        return "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный";
    }
}
