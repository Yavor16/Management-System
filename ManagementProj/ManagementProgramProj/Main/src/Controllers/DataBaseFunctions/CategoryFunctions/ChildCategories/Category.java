package Controllers.DataBaseFunctions.CategoryFunctions.ChildCategories;

public class Category {
    private final int categoriId;
    private final int mainCategoryId;
    private final int parentCategoryId;
    private final String childCategory;

    public Category(int id, int maincategoryId ,int parentCategoryId, String childCategory) {
        this.categoriId = id;
        this.mainCategoryId = maincategoryId;
        this.childCategory = childCategory;
        this.parentCategoryId = parentCategoryId;
    }
    public int getCategoriId() {
        return categoriId;
    }
    public int getMainCategoryId() {
        return mainCategoryId;
    }
    public int getParentCategoryId() {
        return parentCategoryId;
    }
    public String getChildCategory() {
        return childCategory;
    }
}
