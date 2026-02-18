package lab5;

import lab5.commands.base.CommandManager;
import main.Solution;

import java.util.Scanner;

public class Lab5Main implements Solution {

    private CommandManager manager;

    /**
     * static void main(String... args) for this package
     */
    @Override
    public void solve() {
        CollectionController cc = new CollectionController();
        manager = new CommandManager(cc);

        while (true){
            manager.executeCommand(IOHelper.readConsoleLine());
        }
    }
}
