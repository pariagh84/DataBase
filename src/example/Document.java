package example;

import db.Entity;
import db.Trackable;

import java.util.Date;

public class Document extends Entity implements Trackable {
    public String content;

    public Document(String content) {
        this.content = content;
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

    @Override
    public Entity copy() {
        Document copyDocument = new Document(this.content);
        copyDocument.id = id;
        copyDocument.content = this.content;
        copyDocument.creationDate = this.creationDate;
        copyDocument.lastModificationDate = this.lastModificationDate;
        return copyDocument;
    }

    @Override
    public int getEntityCode() {
        return this.id;
    }
}


