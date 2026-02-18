package lab5.commands;

import lab5.CollectionController;
import lab5.commands.base.CollectionCommand;

public class InsertAtIndexCommand extends CollectionCommand {

    public InsertAtIndexCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        // TODO
    }

    @Override
    public String getDescription() {
        return "insert_at index {element} : добавить новый элемент в заданную позицию";
    }
}
