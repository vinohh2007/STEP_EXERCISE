package cinehub;

public class MovieTicket {
    // seatNumber: only MovieTicket itself needs it internally.
    private String seatNumber;
    // screenId: used by other classes within the booking engine's package.
    String screenId;
    // ticketPrice: must be reachable by PremiumMovieTicket (a subclass in a
    // different package) via protected, cross-package subclass access.
    protected double ticketPrice;
    // movieTitle: needs to be displayed anywhere in the app.
    public String movieTitle;

    public MovieTicket(String seatNumber, String screenId, double ticketPrice, String movieTitle) {
        this.seatNumber = seatNumber;
        this.screenId = screenId;
        this.ticketPrice = ticketPrice;
        this.movieTitle = movieTitle;
    }

    public String getSeatNumber() {
        return seatNumber;
    }
}
