import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class mainTest {

    private Logic logic;

    @BeforeEach
    public void setUp(){
        logic = new Logic();
    }

    @Test
    public void templateTest(){

    }






    }

    @Test
    public void testTurnInProgressDepth() {

        assertEquals(1, logic.turnInProgressDepth(3));
        logic.place();
        logic.place();
        assertEquals(1, logic.turnInProgressDepth(0));
        logic.place();
        logic.place();
        logic.place();
        logic.place();
        logic.place();
        logic.place();
        logic.placeHorizontalPiece();
        assertEquals(0, logic.turnInProgressDepth(3));
        logic.placeHorizontalPiece();
        logic.printBoard();
    }



}