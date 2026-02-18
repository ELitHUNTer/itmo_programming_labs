package lab5.commands;

import lab5.CollectionController;
import lab5.IOHelper;
import lab5.collection_items.Chapter;
import lab5.commands.base.CollectionCommand;

public class CountLessThanChapterCommand extends CollectionCommand {

    public CountLessThanChapterCommand(CollectionController controller) {
        super(controller);
    }

    @Override
    public void execute(String... args) {
        try {
            Chapter base = new Chapter(args[0], args[1], Integer.parseInt(args[2]));
            Long ans = controller.getCollectionElements().stream().filter(x -> x.getChapter().compareTo(base) < 0).count();
            IOHelper.consoleOut.println(ans);
        } catch (ArrayIndexOutOfBoundsException ex){
            throw new IllegalArgumentException("Chapter должна иметь 3 параметра name, parentLegion, marinesCount");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("marinesCount - целое число");
        }
    }

    @Override
    public String getDescription() {
        return "count_less_than_chapter chapter : вывести количество элементов, значение поля chapter которых меньше заданного";
    }
}
