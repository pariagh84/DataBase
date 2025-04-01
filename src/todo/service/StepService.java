package todo.service;

import db.Entity;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

import static db.Database.*;

public class StepService {
    public static void saveStep(int taskRef, String title) throws InvalidEntityException {
        Step step = new Step();
        step.taskRef = taskRef;
        step.title = title;
        step.status = Step.Status.NotStarted;
        add(step);
    }

    public static void deleteStep(int stepId) throws InvalidEntityException {
        for (Entity e : entities) {
            if (e instanceof Step && e.id == stepId) {
                entities.remove(e);
                return;
            }
        }
        throw new InvalidEntityException("Step with ID=" + stepId + " not found");
    }

    public static void updateStep(int stepId, String title, Step.Status status) throws InvalidEntityException {
        Step targetStep = null;
        for (Entity e : entities) {
            if (e instanceof Step && e.id == stepId) {
                targetStep = (Step) e;
                targetStep.title = title;
                targetStep.status = status;
                update(targetStep);
                break;
            }
        }
        if (targetStep == null) {
            throw new InvalidEntityException("Step with ID=" + stepId + " not found");
        }
        Task task = null;
        for (Entity e : entities) {
            if (e instanceof Task && e.id == targetStep.taskRef) {
                task = (Task) e;
                break;
            }
        }
        if (task != null) {
            if (status == Step.Status.Completed && task.status == Task.Status.NotStarted) {
                task.status = Task.Status.InProgress;
                update(task);
            }
            boolean allCompleted = true;
            for (Entity e : entities) {
                if (e instanceof Step && ((Step) e).taskRef == task.id) {
                    if (((Step) e).status != Step.Status.Completed) {
                        allCompleted = false;
                        break;
                    }
                }
            }
            if (allCompleted && task.status != Task.Status.Completed) {
                task.status = Task.Status.Completed;
                update(task);
            }
        }
    }
}