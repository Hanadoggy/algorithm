package javapractice;

public class PassByValue {

    public static void main(String[] args) {
        Dog hana = new Dog(10);

        System.out.println("in main() : " + hana.getAge() + " is equal to 10");
        setNewObject(hana);
        System.out.println("in main() : " + hana.getAge() + " is equal to 10");

        Dog doggy = new Dog(30);

        System.out.println("in main() : " + doggy.getAge() + " is equal to 30");
        modifyAge(doggy);
        System.out.println("in main() : " + doggy.getAge() + " is equal to 40");
    }

    public static void setNewObject(Dog dog) {
        System.out.println("in setNewObject() : " + dog.getAge() + " is equal to 10");

        dog = new Dog(20);
        System.out.println("in setNewObject() : " + dog.getAge() + " is equal to 20");
    }

    public static void modifyAge(Dog dog) {
        System.out.println("in modifyAge() : " + dog.getAge() + " is equal to 30");

        dog.setAge(40);
        System.out.println("in modifyAge() : " + dog.getAge() + " is equal to 40");
    }

}
