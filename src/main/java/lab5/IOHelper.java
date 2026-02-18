package lab5;

import lab5.collection_items.Coordinates;
import lab5.collection_items.SpaceMarine;

import java.io.*;
import java.util.Scanner;

public class IOHelper {

    private static Scanner sc = new Scanner(System.in);

    public static final PrintStream consoleOut = System.out;

    public static String readConsoleLine(){
        return sc.nextLine();
    }

    public static SpaceMarine readMarine(String name, Double health, InputStream readFrom) throws UnsupportedEncodingException {
        InputStreamReader reader = new InputStreamReader(readFrom, "UTF-8");
        if (readFrom == System.in)
            System.out.print("Введите координаты {x, y}: ");
        Coordinates coordinates;
        while (true) {
            Coordinates coordinates = new Coordinates();
        }
    }

}
