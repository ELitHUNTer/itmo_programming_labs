package lab6.server.commands;

import lab6.server.CollectionController;
import lab6.server.IOHelper;
import lab6.server.collection_items.SpaceMarine;
import lab6.server.commands.base.CollectionCommand;

public class RemoveGreaterCommand extends CollectionCommand {

    public RemoveGreaterCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public String execute(String... args) {
        SpaceMarine origin = IOHelper.readMarine();
        controller.removeGreater(origin);
        //IOHelper.printlnIfUsingConsole("Элементы удалены");
        return "Элементы удалены";
    }

    @Override
    public String getDescription() {
        return "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный";
    }
}
