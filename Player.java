public class Player {


    public static final String SPACE = " ";
    public static final String RED_PIECE = "\uD83D\uDD34";
    public static final String EMPTY_SQUARE = "◾";
    public static final String BLUE_PIECE = "\uD83D\uDD35";
    public static final String BLUE_KING = "♔";
    public static final String RED_KING = "♚";
    private String type;

    private boolean isKing;

    private final boolean isRed;

    private final boolean isNull;

    private boolean isSpace;

    public Player(boolean isKing, boolean isRed, boolean isNull, boolean isSpace) {
        this.isKing = isKing;
        this.isRed = isRed;
        this.isNull = isNull;
        this.isSpace = isSpace;
        if (isRed)
            setType(RED_PIECE);
        else if (isNull)
            setType(EMPTY_SQUARE);
        else if (isSpace) {
            setType(SPACE);
        } else {
            setType(BLUE_PIECE);
        }
        setIsKing(isKing);
    }

    public boolean isRed() {
        return isRed;
    }

    public String getType() {
        return type;
    }

    public boolean isKing() {
        return isKing;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIsKing(boolean isKing) {
        this.isKing = isKing;
        if (isKing){
            if (isRed)
                setType(RED_KING);
            else
                setType(BLUE_KING);
    }
    }

    public boolean isNotNull() {
        return !isNull;
    }

    public boolean isSpace() {
        return isSpace;
    }

    public void setIsSpace(boolean isSpace) {
        this.isSpace = isSpace;
        setType(SPACE);
    }

    public Player copy() {

        return new Player(isKing, isRed, isNull, isSpace);
    }

}
