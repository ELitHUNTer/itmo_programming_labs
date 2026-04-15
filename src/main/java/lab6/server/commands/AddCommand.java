package lab6.server.commands;

import lab6.server.CollectionController;
import lab6.server.IOHelper;
import lab6.server.MyGsonFactory;
import lab6.server.collection_items.SpaceMarine;
import lab6.server.commands.base.CollectionCommand;

public class AddCommand extends CollectionCommand {

    public AddCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public String execute(String... args) {
        try {
            SpaceMarine marine = new SpaceMarine(MyGsonFactory.get().fromJson(args[0], SpaceMarine.class));
            controller.addElement(marine);
            //IOHelper.printlnIfUsingConsole(String.format("Добавлен элемент %s", marine));
            return String.format("Добавлен элемент %s", marine);
        } catch (ArrayIndexOutOfBoundsException ex){
            throw new IllegalArgumentException("add должна иметь 1 параметр SpaceMarine");
        }
    }

    @Override
    public String getDescription() {
        return "add : добавить новый элемент в коллекцию";
    }
}
