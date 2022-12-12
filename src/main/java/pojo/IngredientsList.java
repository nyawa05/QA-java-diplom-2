package pojo;

import java.util.ArrayList;

public class IngredientsList {
    private String[] ingredients;

    public IngredientsList(ArrayList<String> ingredients) {
        this.ingredients = ingredients.toArray(new String[ingredients.size()]);
    }


    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

}
