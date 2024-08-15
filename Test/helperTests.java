import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class helperTests {

    private Logic logic;

    @BeforeEach
    public void setUp(){
        logic = new Logic();
    }

    @Test
    public void testFindInColumn(){
        assertEquals(logic.findInColumn("PXX", 0), -1);
        logic.place();

        assertEquals(logic.findInColumn("PPP", 3), 7);
        logic.getTurnInProgress().incrementPosition();
        logic.getTurnInProgress().incrementPosition();
        logic.place();
        logic.printBoard();
        assertEquals(logic.findInColumn("YYY", 5), 7);
    }

    @Test
    public void testGetIthCharacter(){
        ArrayList<String> newGameState = new ArrayList<>();
        newGameState.add("342");
        newGameState.add("333");
        newGameState.add("333");
        logic.setGameState(newGameState);
        int[] ithChars = {2, 3};
        assertArrayEquals(logic.getIthCharacter(2), ithChars);

        newGameState.add("337");
        int[] IthChars = {2, 3, 7};
        assertArrayEquals(logic.getIthCharacter(2), IthChars);

    }

    @Test
    public void testCheckColumns() {
        ArrayList<String> newGameState = new ArrayList<>();
        String[][] board = {
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "PPP"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "PPP"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "PPP"},
                {"XXX", "XXX", "XXX", "PPP", "YYY", "XXX", "PPP"},
                {"XXX", "XXX", "XXX", "PPP", "YYY", "XXX", "YYY"},
                {"XXX", "XXX", "XXX", "PPP", "YYY", "XXX", "PPP"},
        };
        logic.setBoard(board);
        assertEquals("PPP", logic.checkColumns());
    }

    @Test
    public void testCheckAscendingDiagonals() {
        String[][] board = {
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "YYY"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "YYY", "PPP"},
                {"XXX", "XXX", "XXX", "XXX", "YYY", "PPP", "YYY"},
                {"XXX", "XXX", "XXX", "YYY", "PPP", "YYY", "PPP"},
        };
        logic.setBoard(board);
        assertEquals("YYY", logic.checkAscendingDiagonals());
    }
}
