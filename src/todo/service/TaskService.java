package todo.service;

import db.Entity;
import db.exception.InvalidEntityException;
import todo.entity.Task;
import todo.entity.Step;

import static db.Database.*;

public class TaskService {
    public static void setAsCompleted(int taskId) throws InvalidEntityException {
        Task targetTask = null;
        for (Entity e : entities) {
            if (e instanceof Task && e.id == taskId) {
                targetTask = (Task) e;
                targetTask.status = Task.Status.Completed;
                update(targetTask);
                break;
            }
        }
        if (targetTask == null) {
            throw new InvalidEntityException("Task with ID=" + taskId + " not found");
        }
        for (Entity e : entities) {
            if (e instanceof Step && ((Step) e).taskRef == taskId) {
                Step step = (Step) e;
                step.status = Step.Status.Completed;
                update(step);
            }
        }
    }

    public static void setAsInProgress(int taskId) throws InvalidEntityException {
        for (Entity e : entities) {
            if (e instanceof Task && e.id == taskId) {
                Task task = (Task) e;
                task.status = Task.Status.InProgress;
                update(task);
                return;
            }
        }
        throw new InvalidEntityException("Task with ID=" + taskId + " not found");
    }

    public static void setAsNotStarted(int taskId) throws InvalidEntityException {
        for (Entity e : entities) {
            if (e instanceof Task && e.id == taskId) {
                Task task = (Task) e;
                task.status = Task.Status.NotStarted;
                update(task);
                return;
            }
        }
        throw new InvalidEntityException("Task with ID=" + taskId + " not found");
    }

    public static void deleteTask(int taskId) throws InvalidEntityException {
        entities.removeIf(e -> {
            if (e instanceof Task && e.id == taskId) {
                return true;
            } else if (e instanceof Step && ((Step) e).taskRef == taskId) {
                return true;
            }
            return false;
        });
    }

    public static void deleteAllTasks() throws InvalidEntityException {
        entities.removeIf(e -> e instanceof Task || e instanceof Step);
    }

    public static void updateTask(Task task) throws InvalidEntityException {
        for (Entity e : entities) {
            if (e instanceof Task && e.id == task.id) {
                Task task2 = (Task) e;
                task2.title = task.title;
                task2.status = task.status;
                update(task2);
                return;
            }
        }
        throw new InvalidEntityException("Task with ID=" + task.id + " not found");
    }

    public static void addTask(Task task) throws InvalidEntityException {
        task.status = Task.Status.NotStarted;
        add(task);
    }
}