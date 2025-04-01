package db;

import db.exception.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Database {
    public static final ArrayList<Entity> entities = new ArrayList<>();
    private static int newID = 1;
    private static final HashMap<Integer, Validator> validators = new HashMap<>();

    private Database() {}

    public static void add(Entity e) throws InvalidEntityException {
        //Validator validator = validators.get(e.getEntityCode());
        //validator.validate(e);
        e.id = newID++;
        Entity copiedEntity = e.copy();
        Date now = new Date();
        if (copiedEntity instanceof Trackable t) {
            t.setCreationDate(now);
            t.setLastModificationDate(now);
        }
        if (e instanceof Trackable original) {
            original.setCreationDate(now);
            original.setLastModificationDate(now);
        }
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

        public static void update(Entity e) throws EntityNotFoundException, InvalidEntityException {
            //Validator validator = validators.get(e.getEntityCode());
            //validator.validate(e);
            for (Entity entity : entities) {
                if (entity.id == e.id) {
                    int index = entities.indexOf(entity);
                    Entity copiedEntity = e.copy();
                    Date now = new Date();
                    if (copiedEntity instanceof Trackable t) {
                        t.setLastModificationDate(now);
                    }
                    if (e instanceof Trackable original) {
                        original.setLastModificationDate(now);
                    }
                    entities.set(index, copiedEntity);
                    return;
                }
            }
            throw new EntityNotFoundException(e.id);
        }

    public static void registerValidator(int entityCode, Validator validator) {
        if (validators.containsKey(entityCode)) {
            throw new IllegalArgumentException("Entity with code " + entityCode + " already exists");
        }
        validators.put(entityCode, validator);
    }

    public static ArrayList<Entity> getAll(int entityCode) {
        ArrayList<Entity> wanted = new ArrayList<>();
        for (Entity e : entities) {
            if (e.id == entityCode) {
                wanted.add(e.copy());
            }
        }
        return wanted;
    }
}
