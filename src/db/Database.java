package db;

import db.exception.*;

import java.util.ArrayList;

public class Database {
    private static final ArrayList<Entity> entities = new ArrayList<>();
    private static int newID = 1;

    private Database() {}

    public static void add(Entity e) {
        e.id = newID++;
        try {
            Entity clonedEntity = (Entity) e.clone();
            entities.add(clonedEntity);
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cloning failed", ex);
        }
    }

    public static Entity get(int id) throws EntityNotFoundException {
        for (Entity e : entities) {
            if (e.id == id) {
                try {
                    return (Entity) e.clone();
                } catch (CloneNotSupportedException ex) {
                    throw new RuntimeException("Cloning failed", ex);
                }
            }
        }
        throw new EntityNotFoundException(id);
    }

    public static void delete(int id) throws EntityNotFoundException {
        boolean flag = entities.removeIf(e -> e.id == id);
        if (!flag) {
            throw new EntityNotFoundException(id);
        }
    }

    public static void update(Entity e) throws EntityNotFoundException {
        for (Entity entity : entities) {
            if (entity.id == e.id) {
                int index = entities.indexOf(entity);
                try {
                    entities.set(index, (Entity) e.clone());
                } catch (CloneNotSupportedException ex) {
                    throw new RuntimeException("Cloning failed", ex);
                }
                return;
            }
        }
        throw new EntityNotFoundException(e.id);
    }
}
