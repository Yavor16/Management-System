package Controllers.AdminSceneControllers.Categories;

import javafx.scene.control.TreeItem;

public interface GetMainCategory {
    static String getMainCategoryOfSelectedProduct(TreeItem<String> item){
        String category = "";
        while(!item.getParent().getValue().equals("Categories")){
            category = item.getParent().getValue();
            item = item.getParent();
        }
        return category;
    }
}
