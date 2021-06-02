package mainPackage;

public abstract class Food implements Consumable{
    private String name;

    public String getName(){
        return name;
    }

    public Food(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }

    public boolean equals(Object obj){
        if(!(obj instanceof Food)){
            return false;
        }
        else if (name == null || ((Food)obj).name == null){  // имя не задано
            return false;
        }
        return name.equals(((Food)obj).name);
    }
}
