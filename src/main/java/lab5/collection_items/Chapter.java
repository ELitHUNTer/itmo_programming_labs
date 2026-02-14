package lab5.collection_items;

public class Chapter {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String parentLegion;
    private Integer marinesCount; //Поле может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 1000

    public Chapter(String name, String parentLegion, Integer marinesCount){
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("name mustn't be empty");
        if (marinesCount < 0 || marinesCount > 1000)
            throw new IllegalArgumentException("marines count must be in range [0..1000] or null");

        this.name = name;
        this.parentLegion = parentLegion;
        this.marinesCount = marinesCount;
    }
}