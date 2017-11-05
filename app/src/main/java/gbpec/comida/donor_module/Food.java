package gbpec.comida.donor_module;

/**
 * Created by Parva Singhal on 20-10-2017.
 */

public class Food {
   private String food_details;
    private String status;

    public Food(String food_details, String status) {
        this.food_details = food_details;
        this.status = status;
    }

    public String getFood_details() {
        return food_details;
    }

    public void setFood_details(String food_details) {
        this.food_details = food_details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
