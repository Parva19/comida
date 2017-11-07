package gbpec.comida.donor_module;

/**
 * Created by Parva Singhal on 08-11-2017.
 */

public class HistoryFields {
    private String foodDetails,status,valid_date,reciever,valid_time;

    public HistoryFields(String foodDetails, String status, String valid_date,  String valid_time,String reciever) {
        this.foodDetails = foodDetails;
        this.status = status;
        this.valid_date = valid_date;
        this.reciever = reciever;
        this.valid_time = valid_time;
    }

    public String getFoodDetails() {
        return foodDetails;
    }

    public void setFoodDetails(String foodDetails) {
        this.foodDetails = foodDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValid_date() {
        return valid_date;
    }

    public void setValid_date(String valid_date) {
        this.valid_date = valid_date;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getValid_time() {
        return valid_time;
    }

    public void setValid_time(String valid_time) {
        this.valid_time = valid_time;
    }
}
