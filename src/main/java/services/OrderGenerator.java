package services;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import pojo.DataIngredients;
import pojo.Ingredients;
import pojo.IngredientsList;

import java.util.ArrayList;

public class OrderGenerator {
    public static ArrayList<String> getIngredientsArrayList() {
        Ingredients ingredients = OrderRequests.getIngredients()
                .then()
                .extract().body().as(Ingredients.class);
        DataIngredients[] dataIngredients = ingredients.getData();
        ArrayList<String> ingredientsList = new ArrayList<>();
        for(int i = 0; i < dataIngredients.length; i++){
            ingredientsList.add(dataIngredients[i].get_id());
        }
        return ingredientsList;
    }
    public static IngredientsList getAllIngredientsList(){
        ArrayList<String> ingredientsList = getIngredientsArrayList();
        return new IngredientsList(ingredientsList);
    }
    public static IngredientsList getRandomIngredientsList() {
        ArrayList<String> ingredientsList = getIngredientsArrayList();
        ArrayList<String> newIngredientsList = new ArrayList<>();
        int length = RandomUtils.nextInt(2,6);
        for (int i = 0; i < length; i++){
            int index = RandomUtils.nextInt(0,ingredientsList.size());
            newIngredientsList.add(ingredientsList.get(index));
        }
        return new IngredientsList(newIngredientsList);
    }
    public static IngredientsList getWrongIngredientsList() {
        ArrayList<String> newIngredientsList = new ArrayList<>();
        int length = RandomUtils.nextInt(2,6);
        for (int i = 0; i < length; i++){
            newIngredientsList.add(RandomStringUtils.randomAlphanumeric(10));
        }
        return new IngredientsList(newIngredientsList);
    }
    public static IngredientsList getNullIngredientList() {
        ArrayList<String> newIngredientsList = new ArrayList<>();
        return new IngredientsList(newIngredientsList);
    }

    public int createRandomOrders(String token) throws InterruptedException {
        int count = RandomUtils.nextInt(2,6);
        for (int i = 0; i < count; i++){
            Thread.sleep(500);
            OrderRequests.createOrder(token,OrderGenerator.getRandomIngredientsList());
        }
        return count;
    }
}
