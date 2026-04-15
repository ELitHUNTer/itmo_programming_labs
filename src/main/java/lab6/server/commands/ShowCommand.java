package lab6.server.commands;

import lab6.server.CollectionController;
import lab6.server.IOHelper;
import lab6.server.commands.base.CollectionCommand;

public class ShowCommand extends CollectionCommand {

    public ShowCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public String execute(String... args) {
        StringBuilder sb = new StringBuilder();
        //controller.getCollectionElements().forEach(IOHelper.consoleOut::println);
        controller.getCollectionElements().forEach(sb::append);
        return sb.toString();
    }

    @Override
    public String getDescription() {
        return "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
