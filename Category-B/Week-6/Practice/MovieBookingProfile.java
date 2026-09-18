package cinehub;

public class MovieBookingProfile {
    private String name;
    private boolean confirmed;
    private String otpHash;

    public MovieBookingProfile() {
    }

    public MovieBookingProfile(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    // Write-only: stores a one-way transformed value; no getOtp() exists anywhere.
    public void setOtp(String otp) {
        this.otpHash = String.valueOf(otp.hashCode());
    }
}
