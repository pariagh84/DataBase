package example;

import db.*;
import db.exception.*;

public class HumanValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Human)) {
            throw new InvalidEntityException("Entity is not a Human");
        }
        if (((Human) entity).age < 0) {
            throw new InvalidEntityException("Age cannot be negative");
        }
        if (((Human) entity).name == null || !((Human) entity).name.isBlank()) {
            throw new InvalidEntityException("Name cannot be null or blank");
        }
    }
}
