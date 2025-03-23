package example;

import db.Entity;

public class Human extends Entity {
    public String name;

    public Human(String name) {
        this.name = name;
    }

    @Override
    public Human clone() {
        try {
            Human clonedHuman = (Human) super.clone();
            clonedHuman.id = this.id;
            return clonedHuman;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
