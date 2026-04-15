package lab5.commands;

import lab5.CollectionController;
import lab5.IOHelper;
import lab5.collection_items.SpaceMarine;
import lab5.commands.base.CollectionCommand;

public class UpdateIdCommand extends CollectionCommand {

    public UpdateIdCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        if (args.length == 0) return;
        try {
            int id = Integer.parseInt(args[0]);
            //if (id < 0 || id > controller.getCollectionSize()) throw new NumberFormatException("");
            if (id < 0 || !controller.containsElementWithId(id)) throw new NumberFormatException("");
            SpaceMarine marine = IOHelper.readMarine();
            controller.updateElement(id, marine);
            IOHelper.printlnIfUsingConsole(String.format("Элемент %s обновлен на позиции %d",
                    controller.getCollectionElements().stream().filter(x -> x.getID() == id).findFirst(),
                    id));
        } catch (NumberFormatException ex) {
            IOHelper.printlnIfUsingConsole(String.format("id должен быть целым числом > 0 и присутствовать в коллекции", controller.getCollectionSize()-1));
        }
    }

    @Override
    public String getDescription() {
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }
}
