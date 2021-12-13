package Controllers.DataBaseFunctions.ProductFunctionality;

import java.util.Map;

import Models.ProductModel;

public class ClearProducts {
    public static void clearProductsUsingMainCategory(String mainCategoryName){
        for (Map.Entry<Integer, ProductModel> currentProdcut : AllProducts.products.entrySet()) {
            if (currentProdcut.getValue().GetMainCategory().equals(mainCategoryName)) {
                DeleteProductFromDB.deleteProductFromDB(currentProdcut.getValue().GetID());
            }
        }
    }
    public static void clearProductsUsingChildCategory(String childCategoryName){
        for (Map.Entry<Integer, ProductModel> currentProdcut : AllProducts.products.entrySet()) {
            if (currentProdcut.getValue().GetCategory().equals(childCategoryName)) {
                DeleteProductFromDB.deleteProductFromDB(currentProdcut.getValue().GetID());
            }
        }
    }
}
