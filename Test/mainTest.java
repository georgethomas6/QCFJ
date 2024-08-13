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

    @Test
    public void testPlace(){

    }

    @Test
    public void testPlaceCertain(){
         logic.placeCertainPiece();
         String[][] shouldEqual = {
                 {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                 {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                 {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                 {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                 {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                 {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                 {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                 {"XXX", "XXX", "XXX", "PPP", "XXX", "XXX", "XXX"},
         };
         logic.printBoard();
         assertArrayEquals(logic.getBoard(), shouldEqual);
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
}