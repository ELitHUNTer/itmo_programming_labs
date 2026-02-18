package lab5.commands;

import lab5.CollectionController;
import lab5.commands.base.CollectionCommand;

public class ShowCommand extends CollectionCommand {

    public ShowCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        controller.getCollectionElements().forEach(System.out::println);
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
