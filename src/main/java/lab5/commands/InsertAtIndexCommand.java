package lab5.commands;

import lab5.CollectionController;
import lab5.IOHelper;
import lab5.collection_items.SpaceMarine;
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
            SpaceMarine marine = IOHelper.readMarine();
            controller.insertAt(id, marine);
            IOHelper.printlnIfUsingConsole(String.format("Элемент %s добавлен на позицию %d",
                    marine,
                    id));
        } catch (NumberFormatException ex){
            IOHelper.printlnIfUsingConsole(String.format("id должен быть целым числом в пределах от 0 до %d включительно", controller.getCollectionSize()-1));
        }
    }

    @Override
    public String getDescription() {
        return "insert_at index {element} : добавить новый элемент в заданную позицию";
    }
}
