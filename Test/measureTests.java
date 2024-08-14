import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class measureTests {
    private Logic logic;

    @BeforeEach
    public void setUp(){
        logic = new Logic();
    }

    @Test
    public void testFindPiecesToMeasure() {
        ArrayList<String> newGameStates = new ArrayList<>();
        newGameStates.add("333");
        logic.setMoveStates("CCC");
        logic.setGameState(newGameStates);
        int[] expected = {-1, -1, -1, -1};
        assertArrayEquals(expected, logic.findPiecesToMeasure());

        logic.setMoveStates("C");
        assertArrayEquals(expected, logic.findPiecesToMeasure());

        newGameStates.clear();
        newGameStates.add("322");
        newGameStates.add("422");
        logic.setMoveStates("VCC");
        logic.setGameState(newGameStates);
        int[] expect = {3, -1, 4, -1};
        assertArrayEquals(expect, logic.findPiecesToMeasure());

        newGameStates.clear();
        newGameStates.add("3424");
        newGameStates.add("3524");
        logic.setMoveStates("CHCC");
        logic.setGameState(newGameStates);
        int[] expects = {4, -1, 5, -1};
        assertArrayEquals(expects, logic.findPiecesToMeasure());

    }



}
