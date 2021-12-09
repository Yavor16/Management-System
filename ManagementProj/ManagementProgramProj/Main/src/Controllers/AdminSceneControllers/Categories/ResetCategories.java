package Controllers.AdminSceneControllers.Categories;

import java.util.Map;

import Controllers.DataBaseFunctions.CategoryFunctions.ChildCategories.*;
import Controllers.DataBaseFunctions.CategoryFunctions.MainCategories.AllMainCategories;

public class ResetCategories {
    public static void resetChildCategories(){
        
        AllMainCategories.getMainCategories();
        for (int i = 0; i < AllChildCategories.childCategories.size(); i++) {
            Category category = AllChildCategories.childCategories.get(i);

            int newId = getIdMainCategoryOfSelectedProduct(category);
            if (newId == 0 ) {
                AllChildCategories.childCategories.remove(category);
                i--;
            } else{
                AllChildCategories.childCategories.get(i).setMainCategoryId(newId);
            }
        }
    }
    private static int getIdMainCategoryOfSelectedProduct(Category category){
        int newId = 0;
        
        for (Map.Entry<Integer, String> currentCategory: AllMainCategories.mainCategories.entrySet()) {
            if (currentCategory.getValue().equals(category.getMainCategoryName())) {
                return currentCategory.getKey();
            }    
        }

        return newId;
    }
}
