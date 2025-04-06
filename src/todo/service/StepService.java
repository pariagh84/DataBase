package todo.service;

import db.Entity;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

import static db.Database.*;

public class StepService {
    private static int nextId = 1;

    public static Step saveStep(int taskRef, String title) throws InvalidEntityException {
        Step step = new Step();
        step.id = nextId++;
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

    public static void updateStep(int taskRef, String title, Step.Status status) throws InvalidEntityException {
        for (Entity e : entities) {
            Step step = (Step) e;
            if (step.taskRef == taskRef) {
                step.title = title;
                step.status = status;
                update(step);
                return;
            }
        }
    }
}