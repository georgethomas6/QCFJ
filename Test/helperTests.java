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
    public void testProppedPieces(){
        String[][] firstAssertion = {
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "YYY"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "YYY", "PPP"},
                {"XXX", "XXX", "XXX", "XXX", "YYY", "PPP", "YYY"},
                {"XXX", "XXX", "XXX", "YYY", "PPP", "PPP", "PPP"},
        };
        logic.setBoard(firstAssertion);
        assertTrue(logic.noProppedPiece(3, 7));
        assertTrue(logic.noProppedPiece(4, 6));
        assertTrue(logic.noProppedPiece(5, 5));
        assertTrue(logic.noProppedPiece(6, 4));

    }

    @Test
    public void testCheckRows(){
        String[][] firstAssertion = {
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "PPP", "PPP", "PPP", "PPP"},
        };
        logic.setBoard(firstAssertion);
        assertEquals("PPP", logic.checkRows());

        String[][] secondAssertion = {
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "PPP", "XXX", "PPP", "PPP"},
        };
        logic.setBoard(secondAssertion);
        assertEquals("XXX", logic.checkRows());

        String[][] thirdAssertion = {
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"YYY", "YYY", "YYY", "YYY", "XXX", "PPP", "PPP"},
                {"YYY", "PPP", "YYY", "PPP", "YYY", "PPP", "PPP"},
                {"YYY", "PPP", "YYY", "PPP", "YYY", "PPP", "PPP"},
                {"YYY", "PPP", "YYY", "PPP", "YYY", "PPP", "PPP"},
                {"YYY", "PPP", "YYY", "PPP", "YYY", "PPP", "PPP"},
                {"YYY", "PPP", "YYY", "PPP", "YYY", "PPP", "PPP"},
        };

        logic.setBoard(thirdAssertion);
        assertEquals("YYY", logic.checkRows());
    }

    @Test
    public void testCheckDescending(){
        String[][] firstAssertion = {
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"YYY", "YYY", "YYY", "YYY", "XXX", "PPP", "PPP"},
                {"YYY", "PPP", "YYY", "PPP", "YYY", "PPP", "PPP"},
                {"YYY", "PPP", "YYY", "PPP", "YYY", "PPP", "PPP"},
                {"YYY", "YYY", "YYY", "PPP", "YYY", "PPP", "PPP"},
                {"YYY", "PPP", "YYY", "PPP", "YYY", "PPP", "PPP"},
                {"YYY", "PPP", "YYY", "YYY", "YYY", "PPP", "PPP"},
        };
        logic.setBoard(firstAssertion);
        assertEquals("YYY", logic.checkDescendingDiagonals());


        String[][] secondAssertion = {
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"YYY", "YYY", "YYY", "PPP", "XXX", "PPP", "PPP"},
                {"YYY", "PPP", "YYY", "PPP", "PPP", "PPP", "PPP"},
                {"YYY", "PPP", "YYY", "PPP", "YYY", "PPP", "PPP"},
                {"YYY", "YYY", "YYY", "PPP", "YYY", "PPP", "PPP"},
                {"YYY", "PPP", "PPP", "PPP", "YYY", "PPP", "PPP"},
                {"YYY", "PPP", "YYY", "YYY", "YYY", "PPP", "PPP"},
        };


        logic.setBoard(secondAssertion);
        assertEquals("PPP", logic.checkDescendingDiagonals());
    }
}
