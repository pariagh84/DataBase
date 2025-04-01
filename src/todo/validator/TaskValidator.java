package todo.validator;

import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Task;

public class TaskValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Task task)) {
            throw new InvalidEntityException("Entity must be a task");
        }
        if (task.title == null || task.title.isBlank()) {
            throw new InvalidEntityException("Task must have a title");
        }
    }
}
