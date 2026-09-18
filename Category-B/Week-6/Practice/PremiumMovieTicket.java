package cinehub.premium;

import cinehub.MovieTicket;

public class PremiumMovieTicket extends MovieTicket {

    public PremiumMovieTicket(String seatNumber, String screenId, double ticketPrice, String movieTitle) {
        super(seatNumber, screenId, ticketPrice, movieTitle);
    }

    // ALLOWED: this is the SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE case — accessing
    // the inherited protected field through 'this' (own type).
    public double getPriceViaOwnType() {
        return this.ticketPrice;
    }

    // The SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE case is deliberately NOT written
    // here: accessing parentTyped.ticketPrice through a plain MovieTicket-typed
    // reference from this different package would not compile — Java denies
    // protected access based on the reference's declared type, not the object's
    // actual runtime type.
}
