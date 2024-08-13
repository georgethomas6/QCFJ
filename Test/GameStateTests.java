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
    }
}
