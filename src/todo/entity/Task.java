package todo.entity;

import db.Entity;
import db.Trackable;

import java.util.Date;

public class Task extends Entity implements Trackable {
    public static final int ENTITY_CODE = 1;
    public enum Status {
        NotStarted, Completed, InProgress
    }

    public String title;
    public String description;
    public Date dueDate;
    public Status status;

    @Override
    public Entity copy() {
        Task copyTask = new Task();
        copyTask.id = id;
        copyTask.title = title;
        copyTask.description = description;
        copyTask.dueDate = dueDate;
        copyTask.status = status;
        if (this.creationDate != null) {
            copyTask.creationDate = (Date) this.creationDate.clone();
        }
        if (this.lastModificationDate != null) {
            copyTask.lastModificationDate = (Date) this.lastModificationDate.clone();
        }
        return copyTask;
    }

    @Override
    public int getEntityCode() {
        return ENTITY_CODE;
    }

    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }
}