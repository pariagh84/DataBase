import db.exception.*;
import example.Human;
import db.*;

public class Main {
    public static void main(String[] args) {
        Human ali = new Human("Ali");
        Human bob = new Human("Bob");

        Database.add(ali);
        Database.add(bob);

        ali.name = "Ali Hosseini";
        bob.name = "Bob Ross";

        Human aliFromTheDatabase = (Human) Database.get(ali.id);
        Human bobFromTheDatabase = (Human) Database.get(bob.id);

        System.out.println("ali's name in the database: " + aliFromTheDatabase.name);
        System.out.println("bob's name in the database: " + bobFromTheDatabase.name);
        System.out.println("\nali's id in the database: " + aliFromTheDatabase.id);
        System.out.println("bob's id in the database: " + bobFromTheDatabase.id);
    }
}