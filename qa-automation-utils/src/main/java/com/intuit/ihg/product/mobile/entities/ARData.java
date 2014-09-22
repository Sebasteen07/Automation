package com.intuit.ihg.product.mobile.entities;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/26/13
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class ARData {

    private String apptDate;
    private String calDate;
    private String apptTime;
    private String reason;

    public ARData() {
        }

    public String getApptDate() {
        return apptDate;
    }

    public void setApptDate(String apptDate) {
        this.apptDate = apptDate;
    }

    public String getCalDate() {
        return calDate;
    }

    public void setCalDate(String calDate) {
        this.calDate = calDate;
    }

    public String getApptTime() {
        return apptTime;
    }

    public void setApptTime(String apptTime) {
        this.apptTime = apptTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ARData(String apptDate, String calDate, String apptTime, String reason) {
        setApptDate(apptDate);
        setCalDate(calDate);
        setApptTime(apptTime);
        setReason(reason);
    }

    public ARData(String reason) {
        this.apptDate = "First Available Date";
        this.calDate = null;
        this.apptTime = "Any Time";
        this.reason = reason;
    }
    public String toString() {
		return "[apptDate=" + apptDate+ "|calDate=" + calDate + "|apptTime=" + apptTime + "|reason=" + reason + "]";
	}
}
