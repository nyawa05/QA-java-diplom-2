package pojo;

public class Ingredients {
    private boolean success;
    private DataIngredients[] data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataIngredients[] getData() {
        return data;
    }

    public void setData(DataIngredients[] data) {
        this.data = data;
    }
}
