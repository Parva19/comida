package gbpec.comida.reciever_module;

/**
 * Created by Parva Singhal on 20-10-2017.
 */

public class Food {
    private String donor,contact,details;

    public Food() {
    }

    public Food(String donor, String contact, String details) {
        this.donor = donor;
        this.contact = contact;
        this.details = details;
    }


    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDonor() {
        return donor;

    }

    public void setDonor(String donor) {
        this.donor = donor;
    }


}
