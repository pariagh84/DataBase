package db;

import db.exception.*;

import java.util.ArrayList;

public class Database {
    private static final ArrayList<Entity> entities = new ArrayList<>();
    private static int newID = 1;

    private Database() {}

    public static void add(Entity e) {
        e.id = newID++;
        Entity copiedEntity = e.copy();
        entities.add(copiedEntity);

    }

    public static Entity get(int id) throws EntityNotFoundException {
        for (Entity e : entities) {
            if (e.id == id) {
                return e.copy();
            }
        }
        throw new EntityNotFoundException(id);
    }

        public static void update(Entity e) throws EntityNotFoundException {
            for (Entity entity : entities) {
                if (entity.id == e.id) {
                    int index = entities.indexOf(entity);
                    entities.set(index, e.copy());
                    return;
                }
            }
            throw new EntityNotFoundException(e.id);
        }
    }
