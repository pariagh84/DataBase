package todo.service;

import db.Entity;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

import java.util.Date;

import static db.Database.*;

public class StepService {
    public static Step saveStep(int taskRef, String title) throws InvalidEntityException {
        Step step = new Step();
        step.taskRef = taskRef;
        step.title = title;
        step.status = Step.Status.NotStarted;
        step.creationDate = new java.util.Date();
        add(step);
        return step;
    }

    public static void deleteStep(int taskRef) throws InvalidEntityException {
        for (Entity e : entities) {
            Step step = (Step) e;
            if (step.taskRef == taskRef) {
                entities.remove(step);
                return;
            }
        }
        throw new InvalidEntityException("Step not found");
    }

    public static void updateStep(int id, String title, Step.Status status) throws InvalidEntityException {
        for (Entity e : entities) {
            if (e instanceof Step step) {
                if (step.id == id) {
                    step.title = title;
                    step.status = status;
                    update(step);
                    return;
                }
            }
        }
    }
}