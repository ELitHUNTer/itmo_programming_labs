package lab6.server.commands;

import lab6.server.CollectionController;
import lab6.server.commands.base.CollectionCommand;

public class InfoCommand extends CollectionCommand {

    public InfoCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public String execute(String... args) {
        //System.out.println(controller.getCollectionInfo());
        return controller.getCollectionInfo();
    }

    @Override
    public String getDescription() {
        return "info: вывести в стандартный поток вывода информацию о коллекции";
    }
}
