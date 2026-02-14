package lab5.collection_items;

public class Coordinates {
    private Long x; //Значение поля должно быть больше -201, Поле не может быть null
    private long y;

    public Coordinates(Long x, long y){
        if (x == null || x <= -201)
            throw new IllegalArgumentException("x mustn't be null and be greater than -201");

        this.x = x;
        this.y = y;
    }
}