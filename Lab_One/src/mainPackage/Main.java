// As program arguments: "Apple/малое" "Cheese" "Apple/большое" "Cake/шоколадная" "Cake/карамель"

package mainPackage;

public class Main {
    public static void main(String[] args) {
        Food[] breakfast = new Food[args.length];

        for(int i = 0; i < args.length; i++) {
            String[] parts = args[i].split("/");

            switch (parts[0]) {
                case "Cheese":
                    breakfast[i] = new Cheese();
                    break;
                case "Apple":
                    breakfast[i] = new Apple(parts[1]);
                    break;
                case "Cake":
                    breakfast[i] = new Cake(parts[1]);
                    break;
            }
        }

        for (Food i: breakfast) {
            if (i == null) {
                break;
            } else {
               i.consume();
            }
        }
        counter(breakfast);
    }

    public static void counter(Food[] breakfast) {
        Cheese cheese = new Cheese();
        Apple apple = new Apple("");
        Cake cake = new Cake("");

        int countCheese = 0;
        int countApple = 0;
        int countTea = 0;

        for(int i = 0; i < breakfast.length; i++) {
            if (breakfast[i] == null) {
                break;
            } else if(breakfast[i].equals(cheese)) {
                countCheese++;
            } else if(breakfast[i].equals(apple)) {
                countApple++;
            } else if(breakfast[i].equals(cake)) {
                countTea++;
            }
        }
        System.out.println("\nКоличество cыр : " + countCheese);
        System.out.println("\nКоличество яблок : " + countApple);
        System.out.println("\nКоличество пирожных : " + countTea);
    }
}
