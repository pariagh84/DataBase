package todo.service;

import db.Database;
import db.Entity;
import db.exception.InvalidEntityException;
import todo.entity.Task;
import todo.entity.Step;

import java.util.ArrayList;

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
            if (e instanceof Step step && step.taskRef == taskId) {
                step.status = Step.Status.Completed;
                update(step);
            }
        }
    }

    public static void setAsInProgress(int taskId) throws InvalidEntityException {
        for (Entity e : entities) {
            if (e instanceof Task task && e.id == taskId) {
                task.status = Task.Status.InProgress;
                update(task);
                return;
            }
        }
        throw new InvalidEntityException("Task with ID=" + taskId + " not found");
    }

    public static void setAsNotStarted(int taskId) throws InvalidEntityException {
        for (Entity e : entities) {
            if (e instanceof Task task && e.id == taskId) {
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
            } else return e instanceof Step && ((Step) e).taskRef == taskId;
        });
    }

    public static void deleteAllTasks() throws InvalidEntityException {
        entities.removeIf(e -> e instanceof Task || e instanceof Step);
    }

    public static void updateTask(Task task) throws InvalidEntityException {
        for (Entity e : entities) {
            if (e instanceof Task task2 && e.id == task.id) {
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

    public static Task getTaskById(int id) {
        return (Task) Database.get(id);
    }

    public static ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        for (Entity e : entities) {
            if (e instanceof Task) {
                tasks.add((Task) e);
            }
        }
        return tasks;
    }

    public static ArrayList<Task> getIncompleteTasks() {
        ArrayList<Task> incompleteTasks = new ArrayList<>();
        for (Entity e : entities) {
            if (e instanceof Task && (((Task) e).status == Task.Status.InProgress || ((Task) e).status == Task.Status.NotStarted)) {
                incompleteTasks.add((Task) e);
            }
        }
        return incompleteTasks;
    }

    public static ArrayList<Step> getStepsForTask(int id) {
        ArrayList<Step> steps = new ArrayList<>();
        for (Entity e : entities) {
            if (e instanceof Step && ((Step) e).taskRef == id && e.getEntityCode() == 2) {
                steps.add((Step) e);
            }
        }
        return steps;
    }
}