package kr.hs.zion.zionbob.data;

import io.realm.RealmObject;

/**
 * Created by youngbin on 15. 11. 24.
 */
public class MealDataCacheModel extends RealmObject {
    private String Date; // Formatting Example : 2015.11.24_2
    private String Meal;
    private String Origin;
    private String Nutrients;

    // Getters

    public String getDate() {
        return Date;
    }

    public String getMeal() {
        return Meal;
    }

    public String getOrigin() {
        return Origin;
    }

    public String getNutrients() {
        return Nutrients;
    }

    // Setters

    public void setDate(String date) {
        Date = date;
    }

    public void setMeal(String meal) {
        Meal = meal;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public void setNutrients(String nutrients) {
        Nutrients = nutrients;
    }
}
