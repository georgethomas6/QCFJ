import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

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

    
}
