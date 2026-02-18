package lab5.commands;

import lab5.CollectionController;
import lab5.commands.base.CollectionCommand;

public class InfoCommand extends CollectionCommand {

    public InfoCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        System.out.println(controller.getCollectionInfo());
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода информацию о коллекции";
    }
}
