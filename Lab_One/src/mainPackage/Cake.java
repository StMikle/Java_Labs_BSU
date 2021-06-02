package mainPackage;


public class Cake extends Food {

    private String icing;

    public Cake(String fill){
        super ("Пирог");
        this.icing = fill;
    }


    public String getFilling(){
        return icing;
    }

    public void setFilling(String fill){
        this.icing = fill;
    }

    public void consume(){
        System.out.println(this + " съеден");
    }

    @Override
    public String toString() {
        return super.toString() + " c глазурью '" + icing.toUpperCase() + "'";
    }

}
