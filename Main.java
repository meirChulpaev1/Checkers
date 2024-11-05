import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Player[][] arr = getPlayers();
        String currentPlayer = Player.BLUE_PIECE;

        while (!isGameOver(arr)) {
            displayBoard(arr);
            currentPlayer = getNextPlayer(currentPlayer);
            playTurn(arr, currentPlayer);
        }
        displayBoard(arr);
        System.out.println(currentPlayer + " Winnnnnnn");
    }
    private static Player[][] getPlayers() {
        Player[][] arr = new Player[8][8];
        int count = -1 ;
        for (int i = 0; i < arr.length; i++) {
            count++;
            for (int j = 0; j < arr[i].length; j++) {
                count++;
                if (count%2==0){
                    arr[i][j]=new Player(false, false, true, false);
                }
                else if (i<3){
                    arr[i][j]=new Player(false, true, false, false);
                }
                else if (i<5){
                    arr[i][j]=new Player(false, false, false, true);
                }
                else {
                    arr[i][j]=new Player(false, false, false, false);
                }
            }
        }
        return arr;
  }



    private static String getNextPlayer(String p) {
        if (p.equals(Player.BLUE_PIECE))
            p = Player.RED_PIECE;
        else
            p = Player.BLUE_PIECE;
        return p;
    }


    public static void displayBoard(Player[][] arr) {

        System.out.println("    1  2  3  4  5  6  7  8 ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(i + 1 + " |");
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j].getType().equals(Player.RED_PIECE) || arr[i][j].getType().equals(Player.BLUE_PIECE))
                    System.out.print(arr[i][j].getType() + "|");
                else
                    System.out.print(arr[i][j].getType() + " |");

            }
            System.out.println();
            System.out.println("  --------------------------");
        }
    }

    public static void playTurn(Player[][] arr, String currentPlayer) {
        Scanner in = new Scanner(System.in);
        int numberOfMoves = -1;
        int secondDestRow = 0, secondDestColumn = 0;
        while (true) {
            System.out.println("player " + currentPlayer + " Enter your row");
            int originalRow = in.nextInt() - 1;
            System.out.println("and your column");
            int originalColumn = in.nextInt() - 1;
            System.out.println("Enter the row you want to move");
            int row2 = in.nextInt() - 1;
            System.out.println("Enter the column you want to move");
            int column2 = in.nextInt() - 1;
            int a;

            boolean IAmAKing = arr[originalRow][originalColumn].isKing();
            boolean TheRedPlayerDoesNotGoBack = (arr[originalRow][originalColumn].isRed() && originalRow < row2 || numberOfMoves == 0); //**
            boolean TheBluePlayerDoesNotGoBack = (!arr[originalRow][originalColumn].isRed() && originalRow > row2 || numberOfMoves == 0);

            if (IAmAKing || TheRedPlayerDoesNotGoBack || TheBluePlayerDoesNotGoBack) {
                a = 0;
                boolean ItsThatPlayersTurn=arr[originalRow][originalColumn].getType().equals(currentPlayer);
                boolean HeIsKing=arr[originalRow][originalColumn].getType().equals(Player.RED_KING) || arr[originalRow][originalColumn].getType().equals(Player.BLUE_KING);
                if (( ItsThatPlayersTurn || HeIsKing ) && CheckMove(originalRow, originalColumn, row2, column2, a, arr) && IfCanMove(originalRow, originalColumn, arr, row2, column2) && numberOfMoves != 0) {
                    DoTheMove(originalRow, originalColumn, arr, row2, column2, a);
                    break;
                }
                a = 1;
                if (( ItsThatPlayersTurn || HeIsKing ) && CheckMove(originalRow, originalColumn, row2, column2, a, arr) && IfCanMove(originalRow, originalColumn, arr, row2, column2)) {
                    boolean IfThePlaceIWantToMoveFromIsTheLastPlaceIWas= numberOfMoves == 0 && secondDestRow != originalRow && secondDestColumn != originalColumn;
                    if (IfThePlaceIWantToMoveFromIsTheLastPlaceIWas) {
                        System.out.println("you cant eat");
                        break;
                    }
                    DoTheMove(originalRow, originalColumn, arr, row2, column2, a);
                    System.out.println("If you want to eat more Enter 1 else Enter 0..");
                    numberOfMoves = in.nextInt();
                    if (numberOfMoves == 0)
                        break;
                    else {
                        displayBoard(arr);
                        secondDestRow = row2;
                        secondDestColumn = column2;
                        numberOfMoves--;
                    }
                }
            }
            if (numberOfMoves < 0)
                System.out.println("please do again");
            else
                System.out.println(" Enter next eat or fix your Mistake");
        }
    }


    public static boolean IfCanMove(int row, int column, Player[][] arr, int row2, int column2) {
        boolean MyPlaceIsNotEmpty = !arr[row][column].isSpace();
        boolean ThePlaceIWantToGoIsEmpty = arr[row2][column2].isSpace();
        boolean MyPlaceIsNotNull = arr[row][column].isNotNull();

        return (MyPlaceIsNotEmpty && MyPlaceIsNotNull && ThePlaceIWantToGoIsEmpty);
    }

    public static boolean CheckMove(int row1, int column1, int row2, int column2, int a, Player[][] arr) {

        boolean ThePlaceIWantToGetToIsACubeOrTwoAwayOptionA = (row1 == row2 + (a + 1) && column1 == column2 + (a + 1));

        boolean ThePlaceIWantToGetToIsACubeOrTwoAwayOptionB = (row1 == row2 - (a + 1) && column1 == column2 - (a + 1));

        boolean ThePlaceIWantToGetToIsACubeOrTwoAwayOptionC = (row1 == row2 - (a + 1) && column1 == column2 + (a + 1));
        boolean ThePlaceIWantToGetToIsACubeOrTwoAwayOptionD = (row1 == row2 + (a + 1) && column1 == column2 - (a + 1));

        if (a == 0)
            return ThePlaceIWantToGetToIsACubeOrTwoAwayOptionA || ThePlaceIWantToGetToIsACubeOrTwoAwayOptionB ||
                    ThePlaceIWantToGetToIsACubeOrTwoAwayOptionC || ThePlaceIWantToGetToIsACubeOrTwoAwayOptionD;
        return (ThePlaceIWantToGetToIsACubeOrTwoAwayOptionA && !arr[row1 - 1][column1 - 1].isSpace()) || (ThePlaceIWantToGetToIsACubeOrTwoAwayOptionB && !arr[row1 + 1][column1 + 1].isSpace()) ||
                    (ThePlaceIWantToGetToIsACubeOrTwoAwayOptionC && !arr[row1 + 1][column1 - 1].isSpace()) || (ThePlaceIWantToGetToIsACubeOrTwoAwayOptionD && !arr[row1 - 1][column1 + 1].isSpace());
    }

    public static void DoTheMove(int row1, int column1, Player[][] arr, int row2, int column2, int a) {
        boolean IAmAKing = arr[row1][column1].isKing();

        arr[row2][column2] = arr[row1][column1].copy();

        boolean PlayerReachedTheLastLine = (row2 == 7 || row2 == 0);

        if (PlayerReachedTheLastLine)
            arr[row2][column2].setIsKing(true);
        if (IAmAKing)
            arr[row2][column2].setIsKing(true);

        arr[row1][column1].setIsSpace(true);
        if (a == 1) {
            boolean OneOption0fDisplacement=row1 == row2 + 2 && column1 == column2 + 2;
            boolean SecondOption0fDisplacement=row1 == row2 - 2 && column1 == column2 - 2;
            boolean AThirdPossibilityOfDisplacement=row1 == row2 - 2 && column1 == column2 + 2;
            boolean AFourthPossibilityOfDisplacement=row1 == row2 + 2 && column1 == column2 - 2;
            if (OneOption0fDisplacement) {
                arr[row1 - 1][column1 - 1].setIsSpace(true);  //Delete the middle one
            }
            if (SecondOption0fDisplacement) {
                arr[row1 + 1][column1 + 1].setIsSpace(true);
            }
            if (AThirdPossibilityOfDisplacement) {
                arr[row1 + 1][column1 - 1].setIsSpace(true);
            }
            if (AFourthPossibilityOfDisplacement) {
                arr[row1 - 1][column1 + 1].setIsSpace(true);
            }
        }
    }

    public static boolean isGameOver(Player[][] arr) {
        int HowManyRedPlayersAreThereOnTheBoard = 0;
        int HowManyBluePlayersAreThereOnTheBoard = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j].isRed())
                    HowManyRedPlayersAreThereOnTheBoard++;
                if (!arr[i][j].isRed() && !arr[i][j].isKing() && !arr[i][j].isSpace() && arr[i][j].isNotNull())
                    HowManyBluePlayersAreThereOnTheBoard++;
            }

        }
        return HowManyBluePlayersAreThereOnTheBoard == 0 || HowManyRedPlayersAreThereOnTheBoard == 0;
    }
}
