package lab5.commands.base;

import lab5.CollectionController;

public abstract class CollectionCommand implements Command{

    protected CollectionController controller;

    public CollectionCommand(CollectionController controller){
        this.controller = controller;
    }
}
