package mainPackage;

public class Apple extends  Food{
    private String size;

    public String getSize(){
        return size;
    }

    public Apple(String size){
        super ("Яблоко");
        this.size = size;
    }

    public void consume(){
        System.out.println(this + " съедено");
    }

    @Override
    public String toString() {
        return super.toString() + " размера '" + size.toUpperCase() + "'";
    }
}
