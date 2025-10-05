package main;

import lab1.Lab1Main;
import lab2.Lab2Main;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println("Please provide work's id");
            System.exit(0);
        }
        if (args.length > 1){
            System.out.println("Too many args. Please provide only work's id");
            System.exit(0);
        }
        try {
            Solution solution = getWork(Integer.parseInt(args[0]));
            solution.solve();
        } catch (NumberFormatException ex){
            System.out.printf("Can't parse \"%s\" to integer", args[0]);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static Solution getWork(int id){
        return switch (id) {
            case 1 -> new Lab1Main();
            case 2 -> new Lab2Main();
            default -> throw new IllegalArgumentException(String.format("Work with id = %d doesn't exist", id));
        };
    }
}