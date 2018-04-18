package gbpec.comida.donor_module;

/**
 * Created by Parva Singhal on 20-10-2017.
 */

public class Food {
   private String food_details;
    private String status,time,Date,id;


    public Food(String food_details, String status,String time, String Date, String id) {
        this.food_details = food_details;
        this.status = status;
        this.Date=Date;
        this.time=time;
        this.id=id;
    }
//
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
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
