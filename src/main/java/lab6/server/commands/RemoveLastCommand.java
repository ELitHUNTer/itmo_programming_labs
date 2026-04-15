package lab6.server.commands;

import lab6.server.CollectionController;
import lab6.server.IOHelper;
import lab6.server.collection_items.SpaceMarine;
import lab6.server.commands.base.CollectionCommand;

public class RemoveLastCommand extends CollectionCommand {

    public RemoveLastCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public String execute(String... args) {
        SpaceMarine marine = controller.remove_last();
        //IOHelper.printlnIfUsingConsole(String.format("Элемент %s удален", marine));
        return String.format("Элемент %s удален", marine);
    }

    @Override
    public String getDescription() {
        return "remove_last : удалить последний элемент из коллекции";
    }
}
