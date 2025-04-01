package todo.validator;

import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

import static db.Database.entities;

public class StepValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Step step)) {
            throw new InvalidEntityException("Entity must be a step");
        }
        if (step.title == null || step.title.isBlank()) {
            throw new InvalidEntityException("Step must have a title");
        }
        boolean taskFound = false;
        for (Entity e : entities) {
            if (e instanceof Task && e.id == step.taskRef) {
                taskFound = true;
                break;
            }
        }
        if (!taskFound) {
            throw new InvalidEntityException("Step must have a valid task reference");
        }
    }
}