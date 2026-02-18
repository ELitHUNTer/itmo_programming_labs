package lab5.collection_items;

import com.google.gson.annotations.Expose;

import java.time.LocalDate;

public class SpaceMarine implements Comparable<SpaceMarine> {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    @Expose
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

    /**
     * comparing by id with default Integer comparison
     * @param o other marine
     * @return 0 if equal. <0 if this less than other. >0 if this larger that other
     */
    @Override
    public int compareTo(SpaceMarine o) {
        return Integer.compare(id, o.id);
    }

    /**
     *
     * @return chapter of the current SpaceMarine
     */
    public Chapter getChapter() {
        return chapter;
    }

    /**
     *
     * @return name of the current SpaceMarine
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return health of the current SpaceMarine
     */
    public Double getHealth() {
        return health;
    }

    /**
     *
     * @return id of the current SpaceMarine
     */
    public Integer getID(){
        return id;
    }

    @Override
    public String toString() {
        return "SpaceMarine{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", health=" + health +
                ", category=" + category +
                ", weaponType=" + weaponType +
                ", meleeWeapon=" + meleeWeapon +
                ", chapter=" + chapter +
                '}';
    }
}