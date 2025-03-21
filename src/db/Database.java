package db;

import java.util.ArrayList;

public class Database {

    private static final ArrayList<Entity> entities = new ArrayList<>();

    public static void add(Entity e) {
        entities.add(e);
        e.id = entities.indexOf(e) + 1;
    }

    public static Entity get(int id) throws EntityNotFoundException {
        for (Entity e : entities) {
            if (e.id == id) {
                return e;
            }
        }
        throw new EntityNotFoundException(id);
    }

    public static void delete(int id) throws EntityNotFoundException {
        boolean flag = entities.removeIf(e -> e.id == id);
        if (!flag) {
            throw new EntityNotFoundException(id);
        } else {
            entities.removeIf(e -> e.id == id);
        }
    }

    public static void update(Entity e) throws EntityNotFoundException {
        for (Entity entity : entities) {
            if (entity.id == e.id) {
                int index = entities.indexOf(entity);
                entities.set(index, e);
                return;
            }
        }
        throw new EntityNotFoundException(e.id);
    }
}
