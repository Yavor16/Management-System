package Controllers.DataBaseFunctions.CategoryFunctions.ChildCategories;

public class Category {
    private int mainCategoryId;
    private String parentCategoryId;

    private String childCategory;

    public Category(int maincategoryId, String parentCategoryId, String childCategory) {
        this.mainCategoryId = maincategoryId;
        this.childCategory = childCategory;
        this.parentCategoryId = parentCategoryId;
    }
    public int getMainCategoryId() {
        return mainCategoryId;
    }
    public String getParentCategoryId() {
        return parentCategoryId;
    }
    public String getChildCategory() {
        return childCategory;
    }
    public void setMainCategoryId(int maincategoryId) {
        this.mainCategoryId = maincategoryId;
    }
    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
    public void setChildCategory(String childCategory) {
        this.childCategory = childCategory;
    }
}
