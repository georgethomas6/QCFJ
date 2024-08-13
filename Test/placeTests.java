import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class placeTests {

    private Logic logic;

    @BeforeEach
    public void setUp(){
        logic = new Logic();
    }

    @Test
    public void testPlace() {
        // Test regular placement
        logic.place();
        logic.gameStateToBoard();
        String[][] firstAssertion = {
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "PPP", "XXX", "XXX", "XXX"},
        };

        // Test horizontal move
        logic.changeTurn();
        logic.getTurnInProgress().changeState();
        logic.getTurnInProgress().incrementPosition();
        logic.place();
        // Make sure you can't change your state after placing half a piece
        assertFalse(logic.getTurnInProgress().getCanModifyState());
        logic.place();
        String[][] secondAssertion = {
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "PPP", "YXX", "YXX", "XXX"},
        };

        // Test horizontal move
        logic.changeTurn();
        logic.getTurnInProgress().changeState();
        logic.getTurnInProgress().changeState();
        logic.getTurnInProgress().decrementPosition();
        logic.getTurnInProgress().decrementPosition();
        logic.place();
        // Make sure you can't change your state after placing half a piece
        assertFalse(logic.getTurnInProgress().getCanModifyState());
        logic.place();
        String[][] thirdAssertion = {
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXP", "XXP", "PPP", "YXX", "YXX", "XXX"},
        };

    }

    @Test
    public void testPlaceCertain() {
        //Place piece and update board
        logic.placeCertainPiece();
        logic.gameStateToBoard();
        String[][] firstAssertion = {
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "PPP", "XXX", "XXX", "XXX"},
        };
        //Test
        assertArrayEquals(logic.getBoard(), firstAssertion);

        // Make another move and test
        logic.changeTurn();
        logic.placeCertainPiece();
        logic.gameStateToBoard();

        String[][] secondAssertion = {
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "YYY", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "PPP", "XXX", "XXX", "XXX"},
        };

        assertArrayEquals(logic.getBoard(), secondAssertion);
    }

    @Test
    public void testPlaceHorizontal() {
        // Place piece and test
        logic.getTurnInProgress().changeState();
        logic.placeHorizontalPiece();
        logic.placeHorizontalPiece();
        String[][] firstAssertion = {
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "PXX", "PXX", "XXX", "XXX"},
        };
        assertArrayEquals(logic.getBoard(), firstAssertion);

        // Place another piece and test
        logic.changeTurn();
        logic.placeHorizontalPiece();
        logic.placeHorizontalPiece();
        String[][] secondAssertion = {
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "YXX", "YXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "PXX", "PXX", "XXX", "XXX"},
        };
        assertArrayEquals(logic.getBoard(), secondAssertion);

        // Place another piece and test
        logic.changeTurn();
        logic.placeHorizontalPiece();
        logic.getTurnInProgress().incrementPosition();
        logic.placeHorizontalPiece();
        String[][] thirdAssertion = {
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "PXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "YXX", "YXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "PXX", "PXX", "PXX", "XXX"},
        };
        assertArrayEquals(logic.getBoard(), thirdAssertion);
    }

    @Test
    public void testPlaceVertical() {
        // Place piece and test
        logic.placeVerticalPiece();
        logic.placeVerticalPiece();
        String[][] firstAssertion = {
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXP", "XXP", "XXX", "XXX"},
        };
        assertArrayEquals(logic.getBoard(), firstAssertion);

        // Place another piece and test
        logic.changeTurn();
        logic.placeVerticalPiece();
        logic.placeVerticalPiece();
        String[][] secondAssertion = {
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXY", "XXY", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXP", "XXP", "XXX", "XXX"},
        };
        assertArrayEquals(logic.getBoard(), secondAssertion);

        // Place another piece and test
        logic.changeTurn();
        logic.placeVerticalPiece();
        logic.getTurnInProgress().incrementPosition();
        logic.placeVerticalPiece();
        String[][] thirdAssertion = {
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXX", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXP", "XXX", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXY", "XXY", "XXX", "XXX"},
                {"XXX", "XXX", "XXX", "XXP", "XXP", "XXP", "XXX"},
        };
        assertArrayEquals(logic.getBoard(), thirdAssertion);
    }
}
