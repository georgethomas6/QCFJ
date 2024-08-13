import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Logic {
    final private int HEIGHT = 8;
    final private int WIDTH = 7;

    private String[][] board;

    private ArrayList<String> gameState;
    private String winner;
    private String moveStates;

    Logic() {
        this.board = new String[HEIGHT][WIDTH];
        this.gameState = new ArrayList<>();
        this.moveStates = "";
        this.winner = "XXX";
        for (int y = 0; y < HEIGHT; y++) {
            String[] row = {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"};
            board[y] = row;
        }

    }

    public ArrayList<String> getGameState() {
        return gameState;
    }

    public String[][] getBoard() {
        return board;
    }

    public String getMoveStates() {
        return moveStates;
    }

    public String getWinner() {
        return winner;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public void setGameState(ArrayList<String> gameState) {
        this.gameState = gameState;
    }

    public void setMoveStates(String moveStates) {
        this.moveStates = moveStates;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    /**
     * Returns the first instance of a value occurring on the board in a given
     * column
     *
     * @param target -> String to look for
     * @param column -> Column to look in
     *               Returns row of first occurrence of target. Returns -1 if not
     *               found
     */
    public int findInColumn(String target, int column) {
        for (int y = 7; y > 0; y--) {
            boolean foundTarget = this.board[y][column].equals(target);
            if (foundTarget) {
                return y;
            }
        }
        return -1;
    }

    /**
     * @return -> an array containing the ith character of each string;
     */
    public int[] getIthCharacter(int index) {
        Set<Integer> ithChars = new HashSet<>();
        for (String game : gameState) {
            if (game.length() > index) {
                ithChars.add(Integer.parseInt(String.valueOf(game.charAt(index))));
            }
        }
        int[] ret = new int[ithChars.size()];
        int i = 0;
        for (int num : ithChars) {
            ret[i] = num;
            i++;
        }
        return ret;
    }

    /**
     * This function updates the board to reflect the gameState.
     */
    public void gameStateToBoard() {
        String moveStates = this.moveStates; // placeholder to simplify syntax
        String[][] newBoard = this.initBlankBoard(); // this is the board that will reflect our new gameState

        for (int i = 0; i < moveStates.length(); i++) {
            switch (moveStates.charAt(i)) { // switch based on the type of move
                case 'C': {
                    int column = Integer.parseInt(String.valueOf(gameState.getFirst().charAt(i))); // every gameState will have the same character at the ith position since it's certain
                    int row = this.firstOpenRow(newBoard, column); // find the depth that the piece should fall to
                    if (i % 2 == 0) {
                        newBoard[row][column] = "PPP";
                    } else {
                        newBoard[row][column] = "YYY";
                    }
                    break;
                }
                case 'H': {
                    int[] columnsPlayedIn = this.getIthCharacter(i); // the columns played in are the unique ith
                    // characters in the gameStates
                    int[] rows1 = this.findDepths(newBoard, columnsPlayedIn); // find the depths of the columns played in place the pieces in the horizontal superposition on the board
                    for (int col = 0; col < columnsPlayedIn.length; col++) {
                        int row = rows1[col];
                        int column = columnsPlayedIn[col];
                        if (i % 2 == 0) {
                            newBoard[row][column] = "PXX";
                        } else {
                            newBoard[row][column] = "YXX";
                        }
                    }
                    break;
                }
                case 'V': {
                    int[] colsPlayedIn2 = this.getIthCharacter(i);
                    int[] rows2 = this.findDepths(newBoard, colsPlayedIn2);
                    for (int col = 0; col < colsPlayedIn2.length; col++) {
                        int row = rows2[col];
                        int column = colsPlayedIn2[col];
                        if (i % 2 == 0) {
                            newBoard[row][column] = "XXP";
                        } else {
                            newBoard[row][column] = "XXY";
                        }
                    }
                    break;
                }
            }
        }
        this.board = newBoard; // Update Board to reflect gameState
    }

    /*
     * @return an 8 x 7 array of all "XXX"
     */
    public String[][] initBlankBoard() {
        String[][] board = new String[HEIGHT][WIDTH];
        String[] row = {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"};
        for (int y = 0; y < HEIGHT; y++) {
            board[y] = row;
        }
        return board;
    }

    /**
     * @param board  -> the board the find the first open row on
     * @param column -> must be an integer
     * @return the FIRST open row on the board in the given column.
     */
    public int firstOpenRow(String[][] board, int column) {
        int depth = -1;
        for (int y = 0; y < HEIGHT; y++) {
            boolean spotIsEmpty = board[y][column].equals("XXX");
            if (spotIsEmpty) {
                depth++;
            }
        }
        return depth;
    }


    /**
     * @param board           -> board to use
     * @param columnsPlayedIn -> array of columns played in, should not have
     *                        repeated entries
     * @return an array of the depths at which the given pieces will fall.
     */
    public int[] findDepths(String[][] board, int[] columnsPlayedIn) {
        int[] rows = new int[2];
        int i = 0;
        for (int column : columnsPlayedIn) {
            int row = this.firstOpenRow(board, column);
            rows[i] = row;
            i++;
        }
        return rows;
    }

    /**
     * This function updates the gameState based on the given placements. If it is
     * given two different moves
     * it behaves like it is responding to a quantum move. If it is given the same
     * move it behaves like it is
     * responding to a certain move.
     *
     * @param placementOne -> the first half of the superposition, or the column
     *                     placed in if certain
     * @param placementTwo -> the second half of the superposition, or the column
     *                     placed in if certain
     */
    public void updateGameState(int placementOne, int placementTwo) {
        String firstPlacement = Integer.toString(placementOne);
        String secondPlacement = Integer.toString(placementTwo);
        boolean wasCertainMove = placementOne == placementTwo;
        boolean gameStateEmpty = gameState.isEmpty();
        if (gameStateEmpty) {
            gameState.add(firstPlacement);
            if (!wasCertainMove) {
                gameState.add(secondPlacement);
            }
            return;
        }

        ArrayList<String> newGameState = new ArrayList<>();
        if (wasCertainMove) {
            for (String game : gameState) {
                newGameState.add(game.concat(firstPlacement));
            }
            setGameState(newGameState);
        } else {

            for (String game : gameState) {
                String vectorOne = game.concat(firstPlacement);
                String vectorTwo = game.concat(secondPlacement);
                newGameState.add(vectorOne);
                newGameState.add(vectorTwo);
            }
            setGameState(newGameState);
        }
    }


    /**
     * Finds the repeating character in an entanglement. This function should only
     * be called in handleEntanglement().
     *
     * @return -> the character that appears in the all case, if did not find a
     * repeated char it returns 'X'
     */
    public char findRepeatedChar() {
        int index = moveStates.length() - 2;
        int[] ithChars = getIthCharacter(index);
        int count = 0;
        for (String game : gameState) {
            for (int character : ithChars) {
                for (int i = index; i < game.length(); i++) {
                    if (game.charAt(i) == character + 48) {
                        count++;
                    }
                    if (count == 2) {
                        return (char) (character + 48);
                    }
                }
            }
        }
        return 'X';
    }

    /**
     * Returns 'A' if it is an all or nothing case, 'B' otherwise
     *
     * @param column -> entanglement is occurring in
     * @param row    -> row first height in the entanglement
     * @return 'A', 'B' or 'X' if index out of bound error
     */
    public char findEntanglementType(int column, int row) {
        if (row == 7) {
            System.out.println("OOPS INDEX OUT OF BOUNDS IN findEntanglementType");
            return 'X';
        }
        String topPiece = board[row][column];
        String bottomPiece = board[row + 1][column];
        if ((bottomPiece.equals("PXX") && topPiece.equals("XXY"))
                || (bottomPiece.equals("YXX") && topPiece.equals("XXP"))) {
            {
                return 'A';
            }
        } else {
            return 'B';
        }
    }

    /**
     * Is entanglement occurring
     */
    public boolean isEntanglementOccurring() {
        for (int y = 7; y > 1; y--) {
            for (int x = 0; x < 7; x++) {
                boolean isEntangled = (board[y][x].equals("PXX") && board[y - 1][x].equals("XXY")) ||
                        (board[y][x].equals("PXX") && board[y - 1][x].equals("YXX")) ||
                        (board[y][x].equals("YXX") && board[y - 1][x].equals("XXP")) ||
                        (board[y][x].equals("XXY") && board[y - 1][x].equals("PXX"));
                if (isEntangled) {
                    return true;
                }
            }
        }
        return false;
    }

    public int[] findPiecesToMeasure() {
        int indexToMeasure = this.moveStates.length() - 3;  // always measuring 3 spots back from the last character
        int[] nothingToMeasure = {-1, -1, -1, -1};


        if (indexToMeasure < 0) {
            return nothingToMeasure;
        }

        if (this.moveStates.charAt(indexToMeasure) == 'C') {
            return nothingToMeasure;
        }

        ArrayList<Integer> piecesToMeasure = new ArrayList<Integer>();
        char state = this.moveStates.charAt(indexToMeasure);
        int[] options = this.getIthCharacter(indexToMeasure);
        int colorToLookFor = this.moveStates.length() % 2;

        if (colorToLookFor == 1 && state == 'V') {
            String target = "XXP";
            piecesToMeasure.add(options[0]);
            piecesToMeasure.add(this.findInColumn(target, options[0]));
            piecesToMeasure.add(options[1]);
            piecesToMeasure.add(this.findInColumn(target, options[1]));
        } else if (colorToLookFor == 1 && state == 'H') {
            String target = "PXX";
            piecesToMeasure.add(options[0]);
            piecesToMeasure.add(this.findInColumn(target, options[0]));
            piecesToMeasure.add(options[1]);
            piecesToMeasure.add(this.findInColumn(target, options[1]));
        } else if (colorToLookFor == 0 && state == 'V') {
            String target = "XXY";
            piecesToMeasure.add(options[0]);
            piecesToMeasure.add(this.findInColumn(target, options[0]));
            piecesToMeasure.add(options[1]);
            piecesToMeasure.add(this.findInColumn(target, options[1]));
        } else if (colorToLookFor == 0 && state == 'H') {
            String target = "YXX";
            piecesToMeasure.add(options[0]);
            piecesToMeasure.add(this.findInColumn(target, options[0]));
            piecesToMeasure.add(options[1]);
            piecesToMeasure.add(this.findInColumn(target, options[1]));
        }

        int[] returnValue = new int[piecesToMeasure.size()];
        for (int i = 0; i < piecesToMeasure.size(); i++) {
            returnValue[i] = piecesToMeasure.get(i);
        }

        return returnValue;


    }

}
