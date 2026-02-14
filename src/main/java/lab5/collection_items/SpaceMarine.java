package lab5.collection_items;

import java.time.LocalDate;

public class SpaceMarine {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double health; //Поле не может быть null, Значение поля должно быть больше 0
    private AstartesCategory category; //Поле может быть null
    private Weapon weaponType; //Поле не может быть null
    private MeleeWeapon meleeWeapon; //Поле может быть null
    private Chapter chapter; //Поле может быть null

    private static int idCounter = 0;

    public SpaceMarine(String name,
                       Coordinates coordinates,
                       Double health,
                       AstartesCategory category,
                       Weapon weaponType,
                       MeleeWeapon meleeWeapon,
                       Chapter chapter){
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("name must not be null or empty string");
        if (coordinates == null)
            throw new IllegalArgumentException("coordinates mustn't be null");
        if (health == null || health <= 0)
            throw new IllegalArgumentException("health must not be null and be > 0");
        if (category == null)
            throw new IllegalArgumentException("category mustn't be null");
        if (weaponType == null)
            throw new IllegalArgumentException("weapon mustn't be null");
        if (meleeWeapon == null)
            throw new IllegalArgumentException("melee weapon mustn't be null");
        if (chapter == null)
            throw new IllegalArgumentException("chapter mustn't be null");

        id = idCounter++;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.health = health;
        this.category = category;
        this.weaponType = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
    }
}