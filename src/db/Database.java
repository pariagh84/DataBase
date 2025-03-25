package db;

import db.exception.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private static final ArrayList<Entity> entities = new ArrayList<>();
    private static int newID = 1;
    private static HashMap<Integer, Validator> validators = new HashMap<>();

    private Database() {}

    public static void add(Entity e) throws InvalidEntityException {
        Validator validator = validators.get(e.getEntityCode());
        validator.validate(e);
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

        public static void update(Entity e) throws EntityNotFoundException, InvalidEntityException {
            Validator validator = validators.get(e.getEntityCode());
            validator.validate(e);
            for (Entity entity : entities) {
                if (entity.id == e.id) {
                    int index = entities.indexOf(entity);
                    entities.set(index, e.copy());
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
}
