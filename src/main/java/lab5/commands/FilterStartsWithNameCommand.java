package lab5.commands;

import lab5.CollectionController;
import lab5.IOHelper;
import lab5.commands.base.CollectionCommand;

public class FilterStartsWithNameCommand extends CollectionCommand {

    public FilterStartsWithNameCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        try {
            controller.getCollectionElements().stream()
                    .filter(x -> x.getName().startsWith(args[0]))
                    .forEach(IOHelper.consoleOut::println);
        } catch (ArrayIndexOutOfBoundsException ex){
            throw new IllegalArgumentException("name - строка и обязательный параметр");
        }
    }

    @Override
    public String getDescription() {
        return "filter_starts_with_name name : вывести элементы, значение поля name которых начинается с заданной подстроки";
    }
}
