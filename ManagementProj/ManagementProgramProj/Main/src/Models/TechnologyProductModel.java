package Models;

public class TechnologyProductModel extends ProductModel{
    String resolution;
    Boolean used;
    
    public TechnologyProductModel(int id, String name, String category, int quantity, float price, String resolution, Boolean used) {
        super(id,name,category,quantity,price);
        super.mainCategory = "Technology";
        this.resolution = resolution;
        this.used = used;
    }
    public Boolean GetUsed() {
        return used;
    }
    public String GetResolution() {
        return resolution;
    }
}
