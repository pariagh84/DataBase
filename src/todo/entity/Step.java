package todo.entity;

import db.Entity;
import db.Trackable;

import java.util.Date;

public class Step extends Entity implements Trackable {
    public static final int ENTITY_CODE = 2;

    @Override
    public void setCreationDate(Date date) {
        creationDate = date;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        lastModificationDate = date;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public enum Status {
        NotStarted, Completed
    }

    public String title;
    public Status status;
    public int taskRef;

    @Override
    public Entity copy() {
        Step copyStep = new Step();
        copyStep.id = id;
        copyStep.taskRef = this.taskRef;
        copyStep.title = this.title;
        copyStep.status = this.status;
        if (this.creationDate != null) {
            copyStep.creationDate = (Date) this.creationDate.clone();
        }
        if (this.lastModificationDate != null) {
            copyStep.lastModificationDate = (Date) this.lastModificationDate.clone();
        }
        return copyStep;
    }

    @Override
    public int getEntityCode() {
        return ENTITY_CODE;
    }
}