import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

public class Logic {
    final private int HEIGHT = 8;
    final private int WIDTH = 7;

    private String[][] board;
    private TurnInProgress turnInProgress;
    private ArrayList<String> gameState;
    private String winner;
    private String moveStates;

    Logic() {
        this.board = new String[HEIGHT][WIDTH];
        this.gameState = new ArrayList<>();
        this.moveStates = "";
        this.turnInProgress = new TurnInProgress("purple");
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

    public TurnInProgress getTurnInProgress() {
        return turnInProgress;
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
            boolean foundTarget = board[y][column].equals(target);
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
        String moveStates = getMoveStates(); // placeholder to simplify syntax
        String[][] newBoard = initBlankBoard(); // this is the board that will reflect our new gameState

        for (int i = 0; i < moveStates.length(); i++) {
            switch (moveStates.charAt(i)) { // switch based on the type of move
                case 'C':
                    int column = Integer.parseInt(String.valueOf(gameState.getFirst().charAt(i))); // every gameState will have the same character at the ith position since it's certain
                    int row = firstOpenRow(newBoard, column); // find the depth that the piece should fall to
                    if (i % 2 == 0) {
                        newBoard[row][column] = "PPP";
                        break;
                    } else {
                        newBoard[row][column] = "YYY";
                        break;
                    }
                case 'H':
                    int[] columnsPlayedIn = getIthCharacter(i); // the columns played in are the unique ith
                    // characters in the gameStates
                    int[] rows1 = findDepths(newBoard, columnsPlayedIn); // find the depths of the columns played in place the pieces in the horizontal superposition on the board
                    for (int col = 0; col < columnsPlayedIn.length; col++) {
                        int row1 = rows1[col];
                        int column1 = columnsPlayedIn[col];
                        if (i % 2 == 0) {
                            newBoard[row1][column1] = "PXX";
                        } else {
                            newBoard[row1][column1] = "YXX";
                        }
                    }
                    break;
                case 'V':
                    int[] colsPlayedIn2 = getIthCharacter(i);
                    int[] rows2 = findDepths(newBoard, colsPlayedIn2);
                    for (int col = 0; col < colsPlayedIn2.length; col++) {
                        int row2 = rows2[col];
                        int column2 = colsPlayedIn2[col];
                        if (i % 2 == 0) {
                            newBoard[row2][column2] = "XXP";
                        } else {
                            newBoard[row2][column2] = "XXY";
                        }
                    }
                    break;
            }
        }
        setBoard(newBoard); // Update Board to reflect gameState
    }

    /*
     * @return an 8 x 7 array of all "XXX"
     */
    public String[][] initBlankBoard() {
        String[][] board = new String[HEIGHT][WIDTH];
        for (int y = 0; y < HEIGHT; y++) {
            board[y] = new String[]{"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"};
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
            int row = firstOpenRow(board, column);
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
                        (board[y][x].equals("XXP") && board[y - 1][x].equals("YXX")) ||
                        (board[y][x].equals("YXX") && board[y - 1][x].equals("XXP")) ||
                        (board[y][x].equals("XXY") && board[y - 1][x].equals("PXX"));
                if (isEntangled) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This function tries to find pieces who need to be measured. It returns an array of -1's if no pieces need to be measured.
     * @returns [x1, y1, x2, y2] or [-1, -1, -1, -1]
     */
    public int[] findPiecesToMeasure() {
        int indexToMeasure = moveStates.length() - 3;  // always measuring 3 spots back from the last character
        int[] nothingToMeasure = {-1, -1, -1, -1};


        if (indexToMeasure < 0) {
            return nothingToMeasure;
        }

        if (moveStates.charAt(indexToMeasure) == 'C') {
            return nothingToMeasure;
        }

        ArrayList<Integer> piecesToMeasure = new ArrayList<>();
        char state = moveStates.charAt(indexToMeasure);
        int[] options = getIthCharacter(indexToMeasure);
        int colorToLookFor = moveStates.length() % 2;

        if (colorToLookFor == 1 && state == 'V') {
            String target = "XXP";
            piecesToMeasure.add(options[0]);
            piecesToMeasure.add(findInColumn(target, options[0]));
            piecesToMeasure.add(options[1]);
            piecesToMeasure.add(findInColumn(target, options[1]));
        } else if (colorToLookFor == 1 && state == 'H') {
            String target = "PXX";
            piecesToMeasure.add(options[0]);
            piecesToMeasure.add(findInColumn(target, options[0]));
            piecesToMeasure.add(options[1]);
            piecesToMeasure.add(findInColumn(target, options[1]));
        } else if (colorToLookFor == 0 && state == 'V') {
            String target = "XXY";
            piecesToMeasure.add(options[0]);
            piecesToMeasure.add(findInColumn(target, options[0]));
            piecesToMeasure.add(options[1]);
            piecesToMeasure.add(findInColumn(target, options[1]));
        } else if (colorToLookFor == 0 && state == 'H') {
            String target = "YXX";
            piecesToMeasure.add(options[0]);
            piecesToMeasure.add(findInColumn(target, options[0]));
            piecesToMeasure.add(options[1]);
            piecesToMeasure.add(findInColumn(target, options[1]));
        }

        int[] returnValue = new int[piecesToMeasure.size()];
        for (int i = 0; i < piecesToMeasure.size(); i++) {
            returnValue[i] = piecesToMeasure.get(i);
        }

        return returnValue;
    }

    /**
     * This function finds the pieces on the board that need to be measured, and filters
     * the gameStates depending on what kind of measurement needs to occur.
     */
    public void measure() {
        //Return if there is nothing to measure
        int[] piecesToMeasure = findPiecesToMeasure();
        if (piecesToMeasure[0] == -1) {
            return;
        }
        int indexToMeasure = moveStates.length() - 3;
        Random random = new Random();
        int choice = random.nextInt() % 2;
        int[] ithCharacters = getIthCharacter(indexToMeasure);
        int chosenColumn = choice == 0 ? ithCharacters[0] : ithCharacters[1];
        boolean isEntangled = isEntanglementOccurring();
        ArrayList<String> newGameState = new ArrayList<>();
        String newMoveStates = "";
        if (isEntangled) {
            char lastMoveState = moveStates.charAt(indexToMeasure);
            if (lastMoveState == 'C') {
                int newChoice = random.nextInt() % gameState.size();
                newGameState.add(gameState.get(newChoice));
                setGameState(newGameState);
                for (int i = 0; i < moveStates.length(); i++) {
                    newMoveStates = newMoveStates.concat("C");
                }
                setMoveStates(newMoveStates);
                return;
            }

            for (String game : gameState) {
                if (game.charAt(indexToMeasure) == chosenColumn) {
                    newGameState.add(game);
                }
            }
            setGameState(newGameState);
            for (int t = 0; t < moveStates.length() - 1; t++) {
                newMoveStates = newMoveStates.concat("C");
            }
            newMoveStates = newMoveStates.concat(String.valueOf(moveStates.charAt(moveStates.length() - 1)));
            setMoveStates(newMoveStates);
            return;
        }

        for (String game : gameState) {
            if (game.charAt(indexToMeasure) == chosenColumn) {
                newGameState.add(game);
            }
        }

        for (int i = 0; i < moveStates.length() - 2; i++) {
            newMoveStates = newMoveStates.concat("C");
        }

        newMoveStates = newMoveStates.concat(String.valueOf(moveStates.charAt(moveStates.length() - 2)));
        newMoveStates = newMoveStates.concat(String.valueOf(moveStates.charAt(moveStates.length() - 1)));
        setMoveStates(newMoveStates);
    }


    /**
     * This function returns a list containing the x and y position if we need to entangle.
     * It returns an empty list otherwise.
     *
     * @return List containing [x, y] or an empty list [-1]
     */
    public ArrayList<Integer> doWeNeedToEntangle(int firstPlacement, int secondPlacement) {
        ArrayList<Integer> returnValue = new ArrayList<>();

        int firstX = firstPlacement;
        int secondX = secondPlacement;
        int firstY = firstOpenRow(board, firstPlacement) + 1;
        int secondY = firstOpenRow(board, secondPlacement) + 1;

        // CHECK FOR DOUBLE ENTANGLEMENT. THIS CHECK IS OK BECAUSE WE ONLY EVER HAVE THREE QUANTUM PIECES ON THE BOARD AT A TIME
        if (firstY < 6) {
            String pieceBelowFirst = board[firstY + 1][firstX];
            String secondPieceBelowFirst = board[firstY + 2][firstX];
            boolean piecesBelowAlreadyEntangled =
                    (secondPieceBelowFirst.equals("YXX") && pieceBelowFirst.equals("XXP")) ||
                            (secondPieceBelowFirst.equals("XXY") && pieceBelowFirst.equals("PXX")) ||
                            (secondPieceBelowFirst.equals("PXX") && pieceBelowFirst.equals("XXY")) ||
                            (secondPieceBelowFirst.equals("XXP") && pieceBelowFirst.equals("YXX"));
            if (piecesBelowAlreadyEntangled) {
                return new ArrayList<>(); // Return an empty list
            }
        }

        if (secondY < 6) {
            String pieceBelowFirst = board[secondY + 1][secondX];
            String secondPieceBelowFirst = board[secondY + 2][secondX];
            boolean piecesBelowAlreadyEntangled =
                    (secondPieceBelowFirst.equals("YXX") && pieceBelowFirst.equals("XXP")) ||
                            (secondPieceBelowFirst.equals("XXY") && pieceBelowFirst.equals("PXX")) ||
                            (secondPieceBelowFirst.equals("PXX") && pieceBelowFirst.equals("XXY")) ||
                            (secondPieceBelowFirst.equals("XXP") && pieceBelowFirst.equals("YXX"));
            if (piecesBelowAlreadyEntangled) {
                return new ArrayList<>(); // Return an empty list
            }
        }

        if (firstY < 7) {
            String firstPiece = board[firstY][firstX];
            String pieceBelowFirst = board[firstY + 1][firstX];
            boolean isFirstPieceEntangled =
                    (firstPiece.equals("YXX") && pieceBelowFirst.equals("XXP")) ||
                            (firstPiece.equals("XXY") && pieceBelowFirst.equals("PXX")) ||
                            (firstPiece.equals("PXX") && pieceBelowFirst.equals("XXY")) ||
                            (firstPiece.equals("XXP") && pieceBelowFirst.equals("YXX"));
            if (isFirstPieceEntangled) {
                returnValue.add(firstX);
                returnValue.add(firstY);
                return returnValue;
            }
        }

        if (secondY < 7) {
            String secondPiece = board[secondY][secondX];
            String pieceBelowSecond = board[secondY + 1][secondX];
            boolean isSecondPieceEntangled =
                    (secondPiece.equals("YXX") && pieceBelowSecond.equals("XXP")) ||
                            (secondPiece.equals("XXY") && pieceBelowSecond.equals("PXX")) ||
                            (secondPiece.equals("PXX") && pieceBelowSecond.equals("XXY")) ||
                            (secondPiece.equals("XXP") && pieceBelowSecond.equals("YXX"));
            if (isSecondPieceEntangled) {
                returnValue.add(secondX);
                returnValue.add(secondY);
                return returnValue;
            }
        }

        return returnValue;
    }

    /**
     * This function filters the game states if entanglement should occur. Should be called at the end of placing a vertical or horizontal piece.
     *
     * @param firstPlacement  -> where the first half of the superposition was placed
     * @param secondPlacement -> where the second half of the superposition was placed
     */
    public void handleEntanglement(int firstPlacement, int secondPlacement) {
        ArrayList<Integer> doWeNeedToEntangle = doWeNeedToEntangle(firstPlacement, secondPlacement);
        boolean isEntanglementOccurring = doWeNeedToEntangle.isEmpty();
        if (!isEntanglementOccurring) {
            int[] placeOfEntanglement = new int[doWeNeedToEntangle.size()];
            int i = 0;
            for (int num : doWeNeedToEntangle) {
                placeOfEntanglement[i] = num;
                i++;
            }
            char entanglementType = findEntanglementType(placeOfEntanglement[0], placeOfEntanglement[1]);
            char repeatedChar = findRepeatedChar();
            int superPositionIndex = moveStates.length() - 1;
            ArrayList<String> newGameState = new ArrayList<>();
            if (entanglementType == 'A') {
                for (String game : gameState) {
                    if ((game.charAt(superPositionIndex) == repeatedChar && game.charAt(superPositionIndex - 1) == repeatedChar) ||
                            (game.charAt(superPositionIndex) != repeatedChar && game.charAt(superPositionIndex - 1) != repeatedChar)) {
                        newGameState.add(game);
                    }
                }
            } else if (entanglementType == 'B') {
                for (String game : gameState) {
                    if (!((game.charAt(superPositionIndex) == repeatedChar && game.charAt(superPositionIndex - 1) == repeatedChar) ||
                            (game.charAt(superPositionIndex) != repeatedChar && game.charAt(superPositionIndex - 1) != repeatedChar))) {
                        newGameState.add(game);
                    }
                }
            }
            setGameState(newGameState);
        }
    }


    /**
     * Checks for an uncertain piece under the top piece played in a given column.
     *
     * @return true if there are no uncertain pieces in the given column, false otherwise
     */

    public boolean noProppedPiece(int col, int row) {
        while (row < 8) {
            boolean isCertain =
                    board[row][col].equals("PPP") || board[row][col].equals("YYY");
            if (!isCertain) {
                return false;
            }
            row++;
        }
        return false;
    }

    /**
     * This function checks every single column for a group. No need to check for propped pieces here
     *
     * @return "PPP" if there is a purple group, "YYY" if there is a yellow group, and "XXX" if there is not a group
     */
    public String checkColumns() {
        for (int y = 7; y > 4; y--) {
            for (int x = 0; x < 7; x++) {
                String state = board[y][x];
                boolean itsCertain = state.equals("PPP") || state.equals("YYY");
                boolean thereIsAGroup =
                        board[y][x].equals(board[y - 1][x]) &&
                                board[y][x].equals(board[y - 2][x]) &&
                                board[y][x].equals(board[y - 3][x]);
                if (thereIsAGroup && itsCertain) {
                    return state;
                }
            }
        }
        return "XXX";
    }

    /**
     * This function checks every single row for a group. In the process it makes sure no member of the group is being propped up by an uncertain piece
     *
     * @return "PPP" if there is a purple group, "YYY" if there is a yellow group, and "XXX" if there is not a group
     */
    public String checkRows() {
        for (int y = 7; y > 1; y--) {
            for (int x = 0; x < 4; x++) {
                String state = board[y][x];
                boolean itsCertain = state.equals("PPP") || state.equals("YYY");
                boolean thereIsAGroup =
                        board[y][x].equals(board[y][x + 1]) &&
                                board[y][x].equals(board[y][x + 2]) &&
                                board[y][x].equals(board[y][x + 3]);
                boolean noProppedPieces =
                        noProppedPiece(x, y + 1) &&
                                noProppedPiece(x + 1, y + 1) &&
                                noProppedPiece(x + 2, y + 1) &&
                                noProppedPiece(x + 3, y + 1);
                if (thereIsAGroup && itsCertain && noProppedPieces) {
                    return state;
                }
            }
        }
        return "XXX";
    }

    /**
     * Checks for ascending diagonal groups on the board. In the process it checks for propped pieces
     *
     * @returns "PPP" if there is a purple group, "YYY" if there is a yellow group, and "XXX" if there is not a group
     */
    public String checkAscendingDiagonals() {
        for (int y = 7; y > 4; y--) {
            for (int x = 0; x < 4; x++) {
                String state = board[y][x];
                boolean itsCertain = state.equals("PPP") || state.equals("YYY");
                boolean thereIsAGroup =
                        board[y][x].equals(board[y - 1][x + 1]) &&
                                board[y][x].equals(board[y - 2][x + 2]) &&
                                board[y][x].equals(board[y - 3][x + 3]);
                boolean noProppedPieces =
                        noProppedPiece(x, y + 1) &&
                                noProppedPiece(x + 1, y) &&
                                noProppedPiece(x + 2, y - 1) &&
                                noProppedPiece(x + 3, y - 2);
                if (thereIsAGroup && itsCertain && noProppedPieces) {
                    return state;
                }
            }
        }
        return "XXX";
    }

    /**
     * Checks for descending diagonal groups on the board. In the process it checks for propped pieces
     *
     * @returns "PPP" if there is a purple group, "YYY" if there is a yellow group, and "XXX" if there is not a group
     */
    public String checkDescendingDiagonals() {
        for (int y = 2; y < 5; y++) {
            for (int x = 0; x < 4; x++) {
                String state = board[y][x];
                boolean itsCertain = state.equals("PPP") || state.equals("YYY");
                boolean thereIsAGroup =
                        board[y][x].equals(board[y + 1][x + 1]) &&
                                board[y][x].equals(board[y + 2][x + 2]) &&
                                board[y][x].equals(board[y + 3][x + 3]);
                boolean noProppedPieces =
                        noProppedPiece(x, y + 1) &&
                                noProppedPiece(x + 1, y + 2) &&
                                noProppedPiece(x + 2, y + 3) &&
                                noProppedPiece(x + 3, y + 4);
                if (thereIsAGroup && itsCertain && noProppedPieces) {
                    return state;
                }
            }
        }
        return "XXX";
    }


    /**
     * Checks to see if the game has been won
     *
     * @return "PPP" if purple has won the game, "YYY" if yellow has won the game, "XXX" if there game has not been won
     */
    public String checkWinner() {
        if (checkColumns().equals("XXX")) {
            return checkColumns();
        } else if (checkRows().equals("XXX")) {
            return checkRows();
        } else if (checkAscendingDiagonals().equals("XXX")) {
            return checkAscendingDiagonals();
        } else if (checkDescendingDiagonals().equals("XXX")) {
            return checkDescendingDiagonals();
        }
        return "XXX";
    }

    /**
     * Checks to see if the game has been drawn
     *
     * @return true if the game has been drawn, false otherwise
     */
    public boolean isGameDrawn() {
        int count = 0;
        for (int y = 7; y > 1; y--) {
            for (int x = 0; x < 7; x++) {
                if (board[y][x].equals("XXX")) {
                    count++;
                }
            }
        }
        return count == 42;
    }

    /**
     * Sets the following variables to these values
     * canModifyState = true
     * column = 3
     * firstPlacement = -1
     * state = "certain"
     * color = opposite of previous
     */
    public void changeTurn() {
        turnInProgress.setCanModifyState(true);
        turnInProgress.setColumn(3);
        turnInProgress.setFirstPlacement(-1);
        turnInProgress.setSecondPlacement(-1);
        turnInProgress.setState("certain");
        if (turnInProgress.getColor().equals("purple")) {
            turnInProgress.setColor("yellow");
        } else {
            turnInProgress.setColor("purple");
        }
    }

    /**
     * If a vertical piece has not been placed during the turn, this function temporarily places a vertical piece
     * at the appropriate location above the board. Otherwise, it gets rid of the temporary piece and drops the pieces
     * to the correct depths. Should only be called on valid moves.
     *
     * @return "done" if the turn is over, "notDone" otherwise
     */
    public String placeVerticalPiece() {
        int column = turnInProgress.getColumn();
        int firstPlacement = turnInProgress.getFirstPlacement();
        if (firstPlacement == -1) {
            if (turnInProgress.getColor().equals("purple")) {
                board[turnInProgressDepth(column)][column] = "XXP";
            } else {
                board[turnInProgressDepth(column)][column] = "XXY";
            }
            turnInProgress.setFirstPlacement(column);
            turnInProgress.setCanModifyState(false);
            turnInProgress.setColumn((column + 1) % 7);
            return "notDone";
        }

        board[firstOpenRow(board, firstPlacement) + 1][firstPlacement] = "XXX";
        setMoveStates(moveStates.concat("V"));
        updateGameState(firstPlacement, column);
        gameStateToBoard();
        handleEntanglement(firstPlacement, column);
        gameStateToBoard();
        return "done";
    }

    /*
     * First this function checks to see if the placement is valid. If the placement was valid it places a piece in the correct state on the board.
     * @returns "done" if the turn is over, if not it returns "notDone"
     */
    public String place() {
        int column = turnInProgress.getColumn();
        int row = firstOpenRow(board, column);
        String state = turnInProgress.getState();
        int firstPlacement = turnInProgress.getFirstPlacement();
        boolean validClassicMove = state.equals("certain") && row >= 2;
        boolean validQuantumMove =
                !state.equals("certain") &&
                        row >= 2 &&
                        firstPlacement != column &&
                        !board[2][column].equals("PPP") &&
                        !board[2][column].equals("YYY");
        if (state.equals("certain") && validClassicMove) {
            return placeCertainPiece();
        } else if (state.equals("horizontal") && validQuantumMove) {
            return placeHorizontalPiece();
        } else if (state.equals("vertical") && validQuantumMove) {
            return placeVerticalPiece();
        }
        return "notDone";
    }

    /**
     * Places a certain piece on the board. Should only be called on valid moves.
     *
     * @returns "done"
     */
    public String placeCertainPiece() {
        int column = turnInProgress.getColumn();
        updateGameState(column, column);
        moveStates = moveStates.concat("C");
        gameStateToBoard();
        return "done";
    }

    /**
     * This function calculates the height at which a turnInProgress should be drawn
     *
     * @return (int) depth
     */
    public int turnInProgressDepth(int column) {
        int firstOpenRow = firstOpenRow(board, column);
        return firstOpenRow >= 2 ? 1 : firstOpenRow;
    }


    /**
     * If a horizontal piece has not been placed during the turn, this function temporarily places a horizontal piece
     * at the appropriate location above the board. Otherwise, it gets rid of the temporary piece and drops the pieces
     * to the correct depths. Should only be called on valid moves.
     *
     * @return "done" if the turn is over, "notDone" otherwise
     */
    public String placeHorizontalPiece() {
        int column = turnInProgress.getColumn();
        int firstPlacement = turnInProgress.getFirstPlacement();
        if (firstPlacement == -1) {
            if (turnInProgress.getColor().equals("purple")) {
                board[turnInProgressDepth(column)][column] = "PXX";
            } else {
                board[turnInProgressDepth(column)][column] = "YXX";
            }
            turnInProgress.setFirstPlacement(column);
            turnInProgress.setCanModifyState(false);
            turnInProgress.setColumn((column + 1) % 7);
            return "notDone";
        }

        board[firstOpenRow(board, firstPlacement) + 1][firstPlacement] = "XXX";
        setMoveStates(moveStates.concat("H"));
        updateGameState(firstPlacement, column);
        gameStateToBoard();
        handleEntanglement(firstPlacement, column);
        gameStateToBoard();
        return "done";
    }

    public void printBoard() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                System.out.print(board[y][x]);
                System.out.print(" ");
            }
            System.out.println();
        }




    }

}

