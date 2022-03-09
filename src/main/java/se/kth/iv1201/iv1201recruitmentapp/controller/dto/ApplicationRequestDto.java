package se.kth.iv1201.iv1201recruitmentapp.controller.dto;

/**
 * The application request dto object representing
 * the status of an application.
 */
public class ApplicationRequestDto {
    private String status;
    private long oldVersion;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getOldVersion() {
        return oldVersion;
    }

    public void setOldVersion(long oldVersion) {
        this.oldVersion = oldVersion;
    }

}
