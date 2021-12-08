package Controllers.AdminSceneControllers.Categories;

import java.util.List;

import Controllers.DataBaseFunctions.CategoryFunctions.ChildCategories.*;
import Controllers.DataBaseFunctions.CategoryFunctions.MainCategories.AddMainCategories;

public interface AddCategories {
    public default void saveMainCategories(List<String> mainCategoryNames){
        AddMainCategories.addNewMainCatToDB(mainCategoryNames);
    }
    public default void saveCategories() {
        AddChildrenCategories.addNewMainCatToDB();
    }
}
