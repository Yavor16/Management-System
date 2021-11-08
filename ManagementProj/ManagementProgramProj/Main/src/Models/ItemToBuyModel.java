package Models;

public class ItemToBuyModel {

    private ProductModel prodModel;
    private int amount;

    public ItemToBuyModel(ProductModel prodModel, int amount) {
        this.amount = amount;
        this.prodModel = prodModel;
    }

    public final int GetAmount(){
        return amount;
    }
    public final void SetAmount(int amount){
        this.amount = amount;
    }
    public final ProductModel GetProduct(){
        return prodModel;
    }
    public final void SetProduct(ProductModel productModel){
        this.prodModel = productModel;
    }
}
