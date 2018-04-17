package gbpec.comida.donor_module;

/**
 * Created by Parva Singhal on 17-04-2018.
 */

public class DonorHistoryData {
    private String fDetails, fRequestDate,fRequestTime,fStatus;

    public DonorHistoryData(String fDetails, String fRequestDate, String fRequestTime, String fStatus) {
        this.fDetails = fDetails;
        this.fRequestDate = fRequestDate;
        this.fRequestTime = fRequestTime;
        this.fStatus = fStatus;
    }

    public String getfDetails() {
        return fDetails;
    }

    public void setfDetails(String fDetails) {
        this.fDetails = fDetails;
    }

    public String getfRequestDate() {
        return fRequestDate;
    }

    public void setfRequestDate(String fRequestDate) {
        this.fRequestDate = fRequestDate;
    }

    public String getfRequestTime() {
        return fRequestTime;
    }

    public void setfRequestTime(String fRequestTime) {
        this.fRequestTime = fRequestTime;
    }

    public String getfStatus() {
        return fStatus;
    }

    public void setfStatus(String fStatus) {
        this.fStatus = fStatus;
    }
}
