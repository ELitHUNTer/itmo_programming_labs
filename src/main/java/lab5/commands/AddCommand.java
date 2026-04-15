package lab5.commands;

import lab5.CollectionController;
import lab5.IOHelper;
import lab5.collection_items.SpaceMarine;
import lab5.commands.base.CollectionCommand;

public class AddCommand extends CollectionCommand {

    public AddCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        SpaceMarine marine = IOHelper.readMarine();
        controller.addElement(marine);
        IOHelper.printlnIfUsingConsole(String.format("Добавлен элемент %s", marine));
    }

    @Override
    public String getDescription() {
        return "add : добавить новый элемент в коллекцию";
    }
}
