package Lesson1;

public class Main {
    public static void main(String[] args) {
        Person[] person = new Person[5];
        person[0] = new Person("Ivanov Ivan", "Engineer",
                "ivivan@mailbox.com", "892312312", 30000, 30);
        person[1] = new Person("Petrov Ivan", "Administrator",
                "ivivan@mailbox.com", "892782312", 35000, 35);
        person[2] = new Person("Ivanov Petr", "Cook",
                "ivivan@mailbox.com", "898317912", 40000, 40);
        person[3] = new Person("Smirnov Ivan", "Farmer",
                "ivivan@mailbox.com", "892462738", 45000, 45);
        person[4] = new Person("Smirnov Petr", "Maker-up",
                "ivivan@mailbox.com", "892312372", 50000, 50);

        for (int i = 0; i < person.length; i++) {
            if (person[i].getAge() > 40){
                System.out.println(person[i].toString());
            }
        }
    }

}
