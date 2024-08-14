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


    @Test
    public void testMeasure(){
        logic.updateGameState(1, 2);
        logic.updateGameState(1, 1);
        logic.updateGameState(1, 1);
        logic.gameStateToBoard();
        logic.setMoveStates("HCC");
        logic.measure();
        logic.gameStateToBoard();
        ArrayList<String> expectedOne = new ArrayList<>();
        ArrayList<String> expectedTwo = new ArrayList<>();
        expectedOne.add("111");
        expectedTwo.add("211");
        boolean worked = logic.getGameState().equals(expectedOne) || logic.getGameState().equals(expectedTwo);
        assertTrue(worked);

        ArrayList<String> gameState = new ArrayList<>();
        gameState.add("111");
        logic.setGameState(gameState);
        logic.setMoveStates("CCC");
        logic.gameStateToBoard();
        logic.measure();
        assertEquals(logic.getGameState(), gameState);
        gameState.clear();


        logic.updateGameState(1, 2);
        logic.updateGameState(2, 5);
        logic.setMoveStates("HV");
        logic.gameStateToBoard();
        logic.handleEntanglement(2, 5);
        logic.gameStateToBoard();
        logic.updateGameState(2, 2);
        logic.setMoveStates("HVC");
        logic.measure();
        ArrayList<String> potentialOne = new ArrayList<>();
        ArrayList<String> potentialTwo = new ArrayList<>();
        potentialTwo.add("222");
        potentialOne.add("152");
        boolean itWorked = logic.getGameState().equals(potentialOne) || logic.getGameState().equals(potentialTwo);
        assertTrue(itWorked);

        logic.setGameState(new ArrayList<String>());
        logic.updateGameState(1, 2);
        logic.updateGameState(2, 5);
        logic.setMoveStates("VH");
        logic.gameStateToBoard();
        logic.handleEntanglement(2, 5);
        logic.gameStateToBoard();
        logic.updateGameState(2, 2);
        logic.setMoveStates("VHC");
        logic.measure();
        ArrayList<String> potentialThree = new ArrayList<>();
        ArrayList<String> potentialFour = new ArrayList<>();
        potentialThree.add("122");
        potentialFour.add("252");
        boolean itWorks = logic.getGameState().equals(potentialThree) || logic.getGameState().equals(potentialFour);
        assertTrue(itWorks);

        logic.setGameState(new ArrayList<String>());
        logic.updateGameState(1, 2);
        logic.updateGameState(2, 5);
        logic.setMoveStates("VH");
        logic.gameStateToBoard();
        logic.handleEntanglement(2, 5);
        logic.gameStateToBoard();
        logic.updateGameState(2, 3);
        logic.setMoveStates("VHH");
        logic.measure();
        ArrayList<String> potentialFive = new ArrayList<>();
        ArrayList<String> potentialSix = new ArrayList<>();
        potentialFive.add("123");
        potentialFive.add("122");
        potentialSix.add("252");
        potentialSix.add("253");
        boolean itWork = logic.getGameState().equals(potentialFive) || logic.getGameState().equals(potentialSix);
        assertTrue(itWork);
    }




}
