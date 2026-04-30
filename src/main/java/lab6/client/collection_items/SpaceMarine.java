package lab6.client.collection_items;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import lab5.IOHelper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.HashSet;

public class SpaceMarine implements Comparable<SpaceMarine> {
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

    public void update(SpaceMarine other){
        this.name = other.name;
        this.coordinates = other.coordinates;
        this.creationDate = other.creationDate;
        this.health = other.health;
        this.category = other.category;
        this.weaponType = other.weaponType;
        this.meleeWeapon = other.meleeWeapon;
        this.chapter = other.chapter;
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
                "}";
    }

    private static HashSet<Integer> usedID = new HashSet<>();
    public static class Adapter extends TypeAdapter<SpaceMarine> {

        @Override
        public void write(JsonWriter out, SpaceMarine spaceMarine) throws IOException {
            if (spaceMarine == null) {
                out.nullValue();
                return;
            }

            out.beginObject();

            out.name("id").value(spaceMarine.id);
            out.name("name").value(spaceMarine.name);

            out.name("coordinates");
            writeCoordinates(out, spaceMarine.coordinates);

            out.name("creationDate").value(spaceMarine.creationDate.toString());
            out.name("health").value(spaceMarine.health);

            out.name("category");
            if (spaceMarine.category == null) {
                out.nullValue();
            } else {
                out.value(spaceMarine.category.name());
            }

            out.name("weaponType").value(spaceMarine.weaponType.name());

            out.name("meleeWeapon");
            if (spaceMarine.meleeWeapon == null) {
                out.nullValue();
            } else {
                out.value(spaceMarine.meleeWeapon.name());
            }

            out.name("chapter");
            if (spaceMarine.chapter == null) {
                out.nullValue();
            } else {
                writeChapter(out, spaceMarine.chapter);
            }

            out.endObject();
        }

        private void writeCoordinates(JsonWriter out, Coordinates coordinates) throws IOException {
            if (coordinates == null) {
                out.nullValue();
                return;
            }
            out.beginObject();
            try {
                Field xField = Coordinates.class.getDeclaredField("x");
                xField.setAccessible(true);
                out.name("x").value((Long) xField.get(coordinates));

                Field yField = Coordinates.class.getDeclaredField("y");
                yField.setAccessible(true);
                out.name("y").value((Long) yField.get(coordinates));
            } catch (Exception e) {
                throw new IOException("Ошибка сериализации Coordinates", e);
            }
            out.endObject();
        }

        private void writeChapter(JsonWriter out, Chapter chapter) throws IOException {
            if (chapter == null) {
                out.nullValue();
                return;
            }
            out.beginObject();
            try {
                Field nameField = Chapter.class.getDeclaredField("name");
                nameField.setAccessible(true);
                out.name("name").value((String) nameField.get(chapter));

                Field parentField = Chapter.class.getDeclaredField("parentLegion");
                parentField.setAccessible(true);
                out.name("parentLegion").value((String) parentField.get(chapter));

                Field countField = Chapter.class.getDeclaredField("marinesCount");
                countField.setAccessible(true);
                Integer count = (Integer) countField.get(chapter);
                out.name("marinesCount");
                if (count == null) {
                    out.nullValue();
                } else {
                    out.value(count);
                }
            } catch (Exception e) {
                throw new IOException("Ошибка сериализации Chapter", e);
            }
            out.endObject();
        }


        @Override
        public SpaceMarine read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }

            in.beginObject();

            Integer id = null;
            String name = null;
            Coordinates coordinates = null;
            LocalDate creationDate = null;
            Double health = null;
            AstartesCategory category = null;
            Weapon weaponType = null;
            MeleeWeapon meleeWeapon = null;
            Chapter chapter = null;

            while (in.hasNext()) {
                String fieldName = in.nextName();
                switch (fieldName) {
                    case "id":
                        if (in.peek() == JsonToken.NULL) {
                            in.nextNull();
                            id = null;
                        } else {
                            id = in.nextInt();
                            if (usedID.contains(id)){
                                IOHelper.errOut.println(String.format("Повторяющееся id %d", id));
                                System.exit(0);
                            }
                            usedID.add(id);
                        }
                        break;
                    case "name":
                        if (in.peek() == JsonToken.NULL) {
                            in.nextNull();
                            name = null;
                        } else {
                            name = in.nextString();
                        }
                        break;
                    case "coordinates":
                        coordinates = readCoordinates(in);
                        break;
                    case "creationDate":
                        if (in.peek() == JsonToken.NULL) {
                            in.nextNull();
                            creationDate = null;
                        } else {
                            creationDate = LocalDate.parse(in.nextString());
                        }
                        break;
                    case "health":
                        if (in.peek() == JsonToken.NULL) {
                            in.nextNull();
                            health = null;
                        } else {
                            health = in.nextDouble();
                        }
                        break;
                    case "category":
                        if (in.peek() == JsonToken.NULL) {
                            in.nextNull();
                            category = null;
                        } else {
                            category = AstartesCategory.valueOf(in.nextString());
                        }
                        break;
                    case "weaponType":
                        if (in.peek() == JsonToken.NULL) {
                            in.nextNull();
                            weaponType = null;
                        } else {
                            weaponType = Weapon.valueOf(in.nextString());
                        }
                        break;
                    case "meleeWeapon":
                        if (in.peek() == JsonToken.NULL) {
                            in.nextNull();
                            meleeWeapon = null;
                        } else {
                            meleeWeapon = MeleeWeapon.valueOf(in.nextString());
                        }
                        break;
                    case "chapter":
                        chapter = readChapter(in);
                        break;
                    default:
                        in.skipValue();
                        break;
                }
            }

            in.endObject();

            // === ВАЛИДАЦИЯ ОГРАНИЧЕНИЙ ИЗ КОММЕНТАРИЕВ К ПОЛЯМ ===
            if (id == null || id < 0) {
                throw new IllegalArgumentException("id must not be null and be greater than 0");
            }
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("name must not be null or empty string");
            }
            if (coordinates == null) {
                throw new IllegalArgumentException("coordinates mustn't be null");
            }
            if (creationDate == null) {
                throw new IllegalArgumentException("creationDate mustn't be null");
            }
            if (health == null || health <= 0) {
                throw new IllegalArgumentException("health must not be null and be > 0");
            }
            if (weaponType == null) {
                throw new IllegalArgumentException("weapon mustn't be null");
            }
            // category, meleeWeapon, chapter — могут быть null (по комментариям)
            // marinesCount в Chapter — может быть null или [0..1000]

            // Создаём объект через конструктор (он проверит свои ограничения)
            SpaceMarine spaceMarine = new SpaceMarine(name, coordinates, health,
                    category, weaponType, meleeWeapon, chapter);

            // Перезаписываем авто-генерируемые поля из JSON + обновляем счётчик id
            try {
                Field idField = SpaceMarine.class.getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(spaceMarine, id);

                Field dateField = SpaceMarine.class.getDeclaredField("creationDate");
                dateField.setAccessible(true);
                dateField.set(spaceMarine, creationDate);

                // Обновляем статический счётчик, чтобы следующие созданные объекты не конфликтовали
                Field counterField = SpaceMarine.class.getDeclaredField("idCounter");
                counterField.setAccessible(true);
                int currentCounter = counterField.getInt(null);
                if (id > currentCounter) {
                    counterField.setInt(null, id + 1);
                }
            } catch (Exception e) {
                throw new IOException("Не удалось установить id/creationDate", e);
            }

            return spaceMarine;
        }

        private Coordinates readCoordinates(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            in.beginObject();
            Long x = null;
            long y = 0L;
            while (in.hasNext()) {
                String fn = in.nextName();
                switch (fn) {
                    case "x":
                        if (in.peek() == JsonToken.NULL) {
                            in.nextNull();
                            x = null;
                        } else {
                            x = in.nextLong();
                        }
                        break;
                    case "y":
                        y = in.nextLong();
                        break;
                    default:
                        in.skipValue();
                }
            }
            in.endObject();

            if (x == null || x <= -201) {
                throw new IllegalArgumentException("x mustn't be null and be greater than -201");
            }
            return new Coordinates(x, y);
        }

        private Chapter readChapter(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            in.beginObject();
            String name = null;
            String parentLegion = null;
            Integer marinesCount = null;
            while (in.hasNext()) {
                String fn = in.nextName();
                switch (fn) {
                    case "name":
                        name = in.nextString();
                        break;
                    case "parentLegion":
                        if (in.peek() == JsonToken.NULL) {
                            in.nextNull();
                            parentLegion = null;
                        } else {
                            parentLegion = in.nextString();
                        }
                        break;
                    case "marinesCount":
                        if (in.peek() == JsonToken.NULL) {
                            in.nextNull();
                            marinesCount = null;
                        } else {
                            marinesCount = in.nextInt();
                        }
                        break;
                    default:
                        in.skipValue();
                }
            }
            in.endObject();

            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("name mustn't be empty");
            }
            if (marinesCount != null && (marinesCount < 0 || marinesCount > 1000)) {
                throw new IllegalArgumentException("marines count must be in range [0..1000] or null");
            }

            return new Chapter(name, parentLegion, marinesCount);
        }
    }
}