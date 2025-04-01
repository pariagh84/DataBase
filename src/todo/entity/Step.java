package todo.entity;

import db.Entity;

import java.util.Date;

public class Step extends Entity {
    public static final int ENTITY_CODE = 2;
    public enum Status {
        NotStarted, Completed
    }

    public String title;
    public Status status;
    public int taskRef;

    @Override
    public Entity copy() {
        Step copyStep = new Step();
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