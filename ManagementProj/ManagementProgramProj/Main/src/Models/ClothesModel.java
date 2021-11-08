package Models;

public class ClothesModel extends ProductModel{

    String size;
    public ClothesModel(int id, String name, String category, int quantity, float price, String size) {
        super(id, name, category, quantity, price);
        super.mainCategory = "Clothes";
        this.size = size;
    }
    public String GetSize() {
        return size;
    }
    
}
