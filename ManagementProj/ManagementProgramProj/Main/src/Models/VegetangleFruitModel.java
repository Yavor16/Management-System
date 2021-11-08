package Models;

public class VegetangleFruitModel extends ProductModel{

    public VegetangleFruitModel(int id, String name, String category, int quantity, float price) {
        super(id, name, category, quantity, price);
        super.mainCategory = "Food";
    }
    
}
