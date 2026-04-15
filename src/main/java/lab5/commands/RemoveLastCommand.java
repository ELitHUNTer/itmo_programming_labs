package lab5.commands;

import lab5.CollectionController;
import lab5.IOHelper;
import lab5.collection_items.SpaceMarine;
import lab5.commands.base.CollectionCommand;

public class RemoveLastCommand extends CollectionCommand {

    public RemoveLastCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        SpaceMarine marine = controller.remove_last();
        IOHelper.printlnIfUsingConsole(String.format("Элемент %s удален", marine));
    }

    @Override
    public String getDescription() {
        return "remove_last : удалить последний элемент из коллекции";
    }
}
