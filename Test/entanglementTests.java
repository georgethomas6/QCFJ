import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;

public class entanglementTests {
    private Logic logic;

    @BeforeEach
    public void setUp(){
        logic = new Logic();
    }

    @Test
    public void testFindRepeatedChar() {
        ArrayList<String> newGameStates = new ArrayList<>();
        newGameStates.add("44");
        newGameStates.add("36");
        newGameStates.add("46");
        newGameStates.add("34");
        logic.setMoveStates("HV");
        logic.setGameState(newGameStates);
        assertEquals('4', logic.findRepeatedChar());

        newGameStates.clear();
        newGameStates.add("22");
        newGameStates.add("23");
        logic.setMoveStates("CH");
        logic.setGameState(newGameStates);
        assertEquals('2', logic.findRepeatedChar());

    }

    @Test
    public void testisEntanglementOccurring(){
        ArrayList<String> newGameStates = new ArrayList<>();
        newGameStates.add("44");
        newGameStates.add("36");
        newGameStates.add("46");
        newGameStates.add("34");
        logic.setMoveStates("HV");
        logic.setGameState(newGameStates);
        logic.gameStateToBoard();
        assertTrue(logic.isEntanglementOccurring());

        logic.setMoveStates("HH");
        logic.gameStateToBoard();
        assertFalse(logic.isEntanglementOccurring());

        newGameStates.clear();
        newGameStates.add("3654");
        newGameStates.add("3641");
        newGameStates.add("3651");
        newGameStates.add("3644");
        logic.setMoveStates("CCVH");
        logic.setGameState(newGameStates);
        logic.gameStateToBoard();
        assertTrue(logic.isEntanglementOccurring());
    }


    @Test
    public void testHandleEntanglement(){
        ArrayList<String> expected = new ArrayList<>();
        expected.add("22");
        expected.add("33");
        logic.updateGameState(2, 3);
        logic.updateGameState(2, 3);
        logic.setMoveStates("HV");
        logic.gameStateToBoard();
        logic.handleEntanglement(2, 3);
        assertEquals(logic.getGameState(), expected);
        expected.clear();

        logic.setGameState(new ArrayList<String>());
        logic.updateGameState(2, 3);
        logic.updateGameState(2, 3);
        expected.add("23");
        expected.add("32");
        logic.setMoveStates("VH");
        logic.gameStateToBoard();
        logic.handleEntanglement(2, 3);
        assertEquals(logic.getGameState(), expected);
        expected.clear();

        logic.setGameState(new ArrayList<String>());
        logic.updateGameState(1, 1);
        logic.updateGameState(2, 1);
        logic.updateGameState(6, 2);
        logic.setMoveStates("CVH");
        logic.gameStateToBoard();
        logic.handleEntanglement(6, 2);
        expected.add("126");
        expected.add("112");
        assertEquals(logic.getGameState(), expected);
    }



    

    @Test
    public void testFindEntanglementType() {
        ArrayList<String> newGameStates = new ArrayList<>();
        newGameStates.add("23");
        newGameStates.add("34");
        newGameStates.add("24");
        newGameStates.add("33");
        logic.setMoveStates("HV");
        logic.setGameState(newGameStates);
        logic.gameStateToBoard();
        assertEquals('A', logic.findEntanglementType(3, 6));

        newGameStates.clear();
        newGameStates.add("23");
        newGameStates.add("34");
        newGameStates.add("24");
        newGameStates.add("33");
        logic.setMoveStates("VH");
        logic.setGameState(newGameStates);
        logic.gameStateToBoard();
        assertEquals('B', logic.findEntanglementType(3, 6));

    }

    @Test
    public void testDoWeNeedToEntangle() {
        ArrayList<String> newGameStates = new ArrayList<>();
        newGameStates.add("23");
        newGameStates.add("34");
        newGameStates.add("24");
        newGameStates.add("33");
        logic.setMoveStates("HV");
        logic.setGameState(newGameStates);
        logic.gameStateToBoard();
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(3, 6));
        assertEquals(expected, logic.doWeNeedToEntangle(3, 4));

        newGameStates.clear();
        newGameStates.add("23");
        newGameStates.add("24");

        logic.setMoveStates("CV");
        logic.setGameState(newGameStates);
        logic.gameStateToBoard();
        ArrayList<Integer> expects = new ArrayList<>();
        assertEquals(expects, logic.doWeNeedToEntangle(3, 4));

        newGameStates.clear();
        newGameStates.add("23");
        newGameStates.add("32");
        newGameStates.add("22");
        newGameStates.add("33");
        logic.setMoveStates("HV");
        logic.setGameState(newGameStates);
        logic.gameStateToBoard();
        ArrayList<Integer> expect = new ArrayList<>(Arrays.asList(2, 6));
        assertEquals(expect, logic.doWeNeedToEntangle(2, 3));
    }




}
