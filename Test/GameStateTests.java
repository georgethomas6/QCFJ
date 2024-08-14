import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class GameStateTests {

    private Logic logic;

    @BeforeEach
    public void setUp(){
        logic = new Logic();
    }

    @Test
    public void testGameStateToBoard(){
        ArrayList<String> newGameStates = new ArrayList<>();
        newGameStates.add("443");
        newGameStates.add("463");
        logic.setMoveStates("CHC");
        logic.setGameState(newGameStates);
        logic.gameStateToBoard();
        String[][] firstAssertion = {
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "YXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "PPP", "PPP", "XXX", "YXX"},
        };
        assertArrayEquals(logic.getBoard(), firstAssertion);
        newGameStates.clear();
        newGameStates.add("4436");
        newGameStates.add("4431");
        newGameStates.add("4636");
        newGameStates.add("4631");
        logic.setMoveStates("CHCV");
        logic.gameStateToBoard();

        String[][] secondAssertion = {
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "YXX", "XXX", "XXY"},
                {"XXX", "XXY", "XXX", "PPP", "PPP", "XXX", "YXX"},
        };

        assertArrayEquals(logic.getBoard(), secondAssertion);
    }

    @Test
    public void testUpdateGameState(){
        logic.updateGameState(2, 2);
        ArrayList<String> expectedGameState = new ArrayList<>();
        expectedGameState.add("2");
        assertEquals(logic.getGameState(), expectedGameState);
        logic.updateGameState(4, 6);
        expectedGameState.clear();
        expectedGameState.add("24");
        expectedGameState.add("26");
        assertEquals(logic.getGameState(), expectedGameState);
    }

}
