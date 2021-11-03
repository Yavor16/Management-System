package ManagementProgramProj.Main.src;

public class ProductModel {
    private final int id;
    private final String name;
    private final String category;
    private int quantity;
    private final float price;

    public ProductModel(int id, String name, String category, int quantity, float price) {
        this.id= id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public final int GetID(){
        return id;
    }
    public final String GetName(){
        return name;
    }
    public final String GetCategory(){
        return category;
    }
    public final int GetQuantity(){
        return quantity;
    }
    public final float GetPrice(){
        return price;
    }
    public void SetQuantity(int quantity){
        this.quantity = quantity;
    }
}
