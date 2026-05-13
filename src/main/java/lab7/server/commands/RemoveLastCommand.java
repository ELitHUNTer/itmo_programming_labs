package lab7.server.commands;

import lab7.server.CollectionController;
import lab7.collectionItems.SpaceMarine;
import lab7.server.commands.base.CollectionCommand;

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
