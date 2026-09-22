public abstract class ArtPiece {

    private static int counter = 0;

    protected final String title;
    private final String pieceId;

    public ArtPiece(String title) {
        this.title = title;
        counter++;
        this.pieceId = "ART-" + counter;
    }

    public abstract String describe();

    public String getPieceId() {
        return pieceId;
    }
}
