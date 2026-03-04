package lab5.commands;

import lab5.CollectionController;
import lab5.IOHelper;
import lab5.commands.base.CollectionCommand;

public class AddCommand extends CollectionCommand {

    public AddCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        controller.addElement(IOHelper.readMarine());
    }

    @Override
    public String getDescription() {
        return "add : добавить новый элемент в коллекцию";
    }
}
