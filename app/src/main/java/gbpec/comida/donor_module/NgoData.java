package gbpec.comida.donor_module;

/**
 * Created by Parva Singhal on 13-04-2018.
 */

public class NgoData {
    private String ngoName;
    private String ngoContact;
    private String type;
    private String head;
    private String email;
    private String addres;
    private String pic;

    public NgoData(String ngoName, String ngoContact, String type, String head, String email, String addres, String pic) {
        this.ngoName = ngoName;
        this.ngoContact = ngoContact;
        this.type = type;
        this.head = head;
        this.email = email;
        this.addres = addres;
        this.pic = pic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public  NgoData(String ngoName, String ngoContact){
        this.ngoName=ngoName;
        this.ngoContact=ngoContact;
    }

    public String getNgoName() {
        return ngoName;
    }

    public void setNgoName(String ngoName) {
        this.ngoName = ngoName;
    }

    public String getNgoContact() {
        return ngoContact;
    }

    public void setNgoContact(String ngoContact) {
        this.ngoContact = ngoContact;
    }
}
