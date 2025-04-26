import db.*;
import db.exception.*;
import todo.entity.*;
import todo.service.*;
import todo.validator.StepValidator;
import todo.validator.TaskValidator;


import java.text.*;
import java.util.*;


public class Main {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        Validator stepValidator = new StepValidator();
        Validator taskValidator = new TaskValidator();
        Database.registerValidator(Task.ENTITY_CODE, taskValidator);
        Database.registerValidator(Step.ENTITY_CODE, stepValidator);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            String command = scanner.nextLine().trim();
            try {
                switch (command) {
                    case "add task":
                        handleAddTask(scanner);
                        break;
                    case "add step":
                        handleAddStep(scanner);
                        break;
                    case "delete":
                        handleDelete(scanner);
                        break;
                    case "update task":
                        handleUpdateTask(scanner);
                        break;
                    case "update step":
                        handleUpdateStep(scanner);
                        break;
                    case "get task-by-id":
                        handleGetTaskById(scanner);
                        break;
                    case "get all-tasks":
                        handleGetAllTasks();
                        break;
                    case "get incomplete-tasks":
                        handleGetIncompleteTasks();
                        break;
                    case "exit":
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid command.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("******************************************************");
        System.out.println("Welcome to your to-do list! What would you like to do?");
        System.out.println("1. Add a new task (add task)");
        System.out.println("2. Add a new step (add step)");
        System.out.println("3. Delete an entity (delete)");
        System.out.println("4. Update a task (update task)");
        System.out.println("5. Update a step (update step)");
        System.out.println("6. Get task by id (get task-by-id)");
        System.out.println("7. Get all tasks (get all-tasks)");
        System.out.println("8. Get incomplete tasks (get incomplete-tasks)");
        System.out.println("9. Exit (exit)");
        System.out.println("******************************************************");
    }

    private static void handleAddTask(Scanner scanner) throws InvalidEntityException, ParseException {
        System.out.println("Title:");
        String title = scanner.nextLine();
        System.out.println("Description:");
        String description = scanner.nextLine();
        System.out.println("Due date (yyyy-MM-dd):");
        String dueDateStr = scanner.nextLine();
        Date dueDate = sdf.parse(dueDateStr);
        Task task = new Task();
        task.title = title;
        task.description = description;
        task.dueDate = dueDate;
        TaskService.addTask(task);
        System.out.println("Task saved successfully.");
        System.out.println("ID: " + task.id);
    }

    private static void handleAddStep(Scanner scanner) throws InvalidEntityException {
        System.out.println("TaskID:");
        int taskId = Integer.parseInt(scanner.nextLine());
        System.out.println("Title:");
        String title = scanner.nextLine();
        Step step = StepService.saveStep(taskId, title);
        System.out.println("Step saved successfully.");
        System.out.println("ID: " + step.id);
        System.out.println("Creation Date: " + step.getCreationDate());
    }

    private static void handleDelete(Scanner scanner) throws InvalidEntityException {
        System.out.println("ID:");
        int id = Integer.parseInt(scanner.nextLine());
        Entity removed = null;
        for (Entity e : Database.entities) {
            if (e.id == id) {
                removed = e;
                Database.entities.remove(e);
                break;
            }
        }
        if (removed == null) {
            throw new InvalidEntityException("Entity with ID=" + id + " not found");
        }
        if (removed instanceof Task) {
            TaskService.deleteTask(id);
        }
        System.out.println("Entity with ID=" + id + " successfully deleted.");
    }

    private static void handleUpdateTask(Scanner scanner) throws InvalidEntityException, ParseException {
        System.out.println("ID:");
        int id = Integer.parseInt(scanner.nextLine());
        Task task = TaskService.getTaskById(id);
        if (task == null) {
            throw new InvalidEntityException("Task with ID=" + id + " not found");
        }
        System.out.println("Field:");
        String field = scanner.nextLine();
        System.out.println("New Value:");
        String newValue = scanner.nextLine();
        String oldValue;
        switch (field.toLowerCase()) {
            case "title":
                oldValue = task.title;
                task.title = newValue;
                break;
            case "description":
                oldValue = task.description;
                task.description = newValue;
                break;
            case "due date":
                oldValue = sdf.format(task.dueDate);
                task.dueDate = sdf.parse(newValue);
                break;
            case "status":
                oldValue = task.status.toString();
                Task.Status newStatus = Task.Status.valueOf(newValue);
                if (newStatus == Task.Status.Completed) {
                    TaskService.setAsCompleted(id);
                } else if (newStatus == Task.Status.InProgress) {
                    TaskService.setAsInProgress(id);
                } else {
                    TaskService.setAsNotStarted(id);
                }
                task.status = newStatus;
                break;
            default:
                throw new InvalidEntityException("Invalid field: " + field);
        }
        Database.update(task);
        System.out.println("Successfully updated the task.");
        System.out.println("Field: " + field);
        System.out.println("Old Value: " + oldValue);
        System.out.println("New Value: " + newValue);
        System.out.println("Modification Date: " + task.getLastModificationDate());
    }

    private static void handleUpdateStep(Scanner scanner) throws InvalidEntityException {
        System.out.println("ID:");
        int id = Integer.parseInt(scanner.nextLine());
        Step step = null;
//        for (Entity e : Database.entities) {
//            if (e instanceof Step && e.id == id) {
//                step = (Step) e;
//                break;
//            }
//        }
        step = (Step) Database.get(id);
//        if (step == null) {
//            throw new InvalidEntityException("Step with ID=" + id + " not found");
//        }
        System.out.println("Field:");
        String field = scanner.nextLine();
        System.out.println("New Value:");
        String newValue = scanner.nextLine();
        String oldValue;
        switch (field.toLowerCase()) {
            case "title":
                oldValue = step.title;
                step.title = newValue;
                Database.update(step);
                break;
            case "status":
                oldValue = step.status.toString();
                Step.Status newStatus = Step.Status.valueOf(newValue);
                StepService.updateStep(id, step.title, newStatus);
                break;
            default:
                throw new InvalidEntityException("Invalid field: " + field);
        }

        System.out.println("Successfully updated the step.");
        System.out.println("Field: " + field);
        System.out.println("Old Value: " + oldValue);
        System.out.println("New Value: " + newValue);
        System.out.println("Modification Date: " + step.getLastModificationDate());
    }

    private static void handleGetTaskById(Scanner scanner) {
        System.out.println("ID:");
        int id = Integer.parseInt(scanner.nextLine());
        Task task = TaskService.getTaskById(id);
        if (task == null) {
            System.out.println("Cannot find task with ID=" + id + ".");
            return;
        }
        printTask(task);
    }

    private static void handleGetAllTasks() {
        List<Task> tasks = TaskService.getAllTasks();
        for (Task task : tasks) {
            printTask(task);
            System.out.println();
        }
    }

    private static void handleGetIncompleteTasks() {
        List<Task> tasks = TaskService.getIncompleteTasks();
        for (Task task : tasks) {
            printTask(task);
            System.out.println();
        }
    }

    private static void printTask(Task task) {
        System.out.println("ID: " + task.id);
        System.out.println("Title: " + task.title);
        System.out.println("Due Date: " + sdf.format(task.dueDate));
        System.out.println("Status: " + task.status);
        List<Step> steps = TaskService.getStepsForTask(task.id);
        if (!steps.isEmpty()) {
            System.out.println("Steps:");
            for (Step step : steps) {
                System.out.println("    + " + step.title + ":");
                System.out.println("        ID: " + step.id);
                System.out.println("        Status: " + step.status);
            }
        }
    }
}