package lab5.commands;

import lab5.CollectionController;
import lab5.IOHelper;
import lab5.commands.base.CollectionCommand;

public class InsertAtIndexCommand extends CollectionCommand {

    public InsertAtIndexCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        if (args.length == 0) return;
        try {
            int id = Integer.parseInt(args[0]);
            if (id < 0 || id > controller.getCollectionSize()) return;
            controller.insertAt(id, IOHelper.readMarine());
        } catch (NumberFormatException ex){}
    }

    @Override
    public String getDescription() {
        return "insert_at index {element} : добавить новый элемент в заданную позицию";
    }
}
