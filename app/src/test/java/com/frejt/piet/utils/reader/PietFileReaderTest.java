package com.frejt.piet.utils.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.frejt.piet.entity.Board;
import com.frejt.piet.entity.Codel;
import com.frejt.piet.exception.PietColorNotFoundException;
import com.frejt.piet.exception.PietFileNotReadException;
import com.frejt.piet.utils.color.PietColor;

class PietFileReaderTest {

    static Board expected;

    @BeforeAll
    static void setup() {
        expected = new Board(4, 6);
        
        Codel lightRedCodel = new Codel(0, 0);
        expected.setColor(lightRedCodel, PietColor.LIGHT_RED);

        Codel lightYellowCodel = new Codel(0, 1);
        expected.setColor(lightYellowCodel, PietColor.LIGHT_YELLOW);

        Codel lightGreenCodel = new Codel(0, 2);
        expected.setColor(lightGreenCodel, PietColor.LIGHT_GREEN);

        Codel lightCyanCodel = new Codel(0, 3);
        expected.setColor(lightCyanCodel, PietColor.LIGHT_CYAN);

        Codel lightBlueCodel = new Codel(0, 4);
        expected.setColor(lightBlueCodel, PietColor.LIGHT_BLUE);

        Codel lightMagentaCodel = new Codel(0, 5);
        expected.setColor(lightMagentaCodel, PietColor.LIGHT_MAGENTA);

        Codel redCodel = new Codel(1, 0);
        expected.setColor(redCodel, PietColor.RED);

        Codel yellowCodel = new Codel(1, 1);
        expected.setColor(yellowCodel, PietColor.YELLOW);

        Codel greenCodel = new Codel(1, 2);
        expected.setColor(greenCodel, PietColor.GREEN);

        Codel cyanCodel = new Codel(1, 3);
        expected.setColor(cyanCodel, PietColor.CYAN);

        Codel blueCodel = new Codel(1, 4);
        expected.setColor(blueCodel, PietColor.BLUE);

        Codel magentaCodel = new Codel(1, 5);
        expected.setColor(magentaCodel, PietColor.MAGENTA);

        Codel darkRedCodel = new Codel(2, 0);
        expected.setColor(darkRedCodel, PietColor.DARK_RED);

        Codel darkYellowCodel = new Codel(2, 1);
        expected.setColor(darkYellowCodel, PietColor.DARK_YELLOW);

        Codel darkGreenCodel = new Codel(2, 2);
        expected.setColor(darkGreenCodel, PietColor.DARK_GREEN);

        Codel darkCyanCodel = new Codel(2, 3);
        expected.setColor(darkCyanCodel, PietColor.DARK_CYAN);

        Codel darkBlueCodel = new Codel(2, 4);
        expected.setColor(darkBlueCodel, PietColor.DARK_BLUE);

        Codel darkMagentaCodel = new Codel(2, 5);
        expected.setColor(darkMagentaCodel, PietColor.DARK_MAGENTA);

        Codel whiteCodel_0 = new Codel(3, 0);
        expected.setColor(whiteCodel_0, PietColor.WHITE);
        Codel whiteCodel_1 = new Codel(3, 1);
        expected.setColor(whiteCodel_1, PietColor.WHITE);
        Codel whiteCodel_2 = new Codel(3, 2);
        expected.setColor(whiteCodel_2, PietColor.WHITE);

        Codel blackCodel_0 = new Codel(3, 3);
        expected.setColor(blackCodel_0, PietColor.BLACK);
        Codel blackCodel_1 = new Codel(3, 4);
        expected.setColor(blackCodel_1, PietColor.BLACK);
        Codel blackCodel_2 = new Codel(3, 5);
        expected.setColor(blackCodel_2, PietColor.BLACK);
    }

    Path JPG_TEST_FILE = Paths.get("C:\\Users\\frejt\\code\\n" + //
                "ewestPiet\\app\\src\\test\\resources\\com\\frejt\\piet\\Test.jpg");

    Path PNG_TEST_FILE = Paths.get("C:\\Users\\frejt\\code\\n" + //
                "ewestPiet\\app\\src\\test\\resources\\com\\frejt\\piet\\Test.png");

    Path PPM_TEST_FILE = Paths.get("C:\\Users\\frejt\\code\\n" + //
                "ewestPiet\\app\\src\\test\\resources\\com\\frejt\\piet\\Test.ppm");
    
    /**
     * Asserts taht, when an unsupported file type is passed into the 
     * {@link PietFileReader}, a {@link PietFileNotFoundException} is thrown
     */
    @Test
    void getFileType_InvalidContentType_ThrowsPietFileNotReadException() {

        Path pathNotExists = Paths.get("path/not/exists.txt");

        PietFileReader fileReader = new PietFileReader(pathNotExists);

        assertThrows(PietFileNotReadException.class, () -> fileReader.getFileType());

    }

    /**
     * Asserts that, when a supported file type is requested, the respective 
     * {@link ContentType} is returned
     */
    @Test
    void getFileType_KnownFileType_ReturnsCorrectFileType() {
        
        Path testPNGPath = PNG_TEST_FILE;

        PietFileReader fileReader = new PietFileReader(testPNGPath);
        try {
            assertEquals(ContentType.PNG, fileReader.getFileType());
        } catch (PietFileNotReadException e) {
            Assertions.fail("Unexpected PietFileNotReadException: " + e.getMessage());
        }
    }

    /**
     * Asserts that, when a PPM file is converted into a {@link Board}, the shape and
     * colors of the PPM file are retained
     * 
     * @throws PietFileNotReadException
     */
    @Test
    void convertFileToBoard_PPMFile_ReturnsValidBoard() throws PietColorNotFoundException, PietFileNotReadException {

        Path testPPMPath = PPM_TEST_FILE;
        PietFileReader fileReader = new PietFileReader(testPPMPath);
        Board actual = fileReader.convertFileToBoard();

        assertEquals(expected.getSizeCol(), actual.getSizeCol());
        assertEquals(expected.getSizeRow(), actual.getSizeRow());
        for(int i = 0; i < expected.getSizeRow(); i++) {
            for(int j = 0; j < expected.getSizeCol(); j++) {
                Codel currCodel = new Codel(i, j);
                assertEquals(expected.getColor(currCodel), actual.getColor(currCodel));
            }
        }

    }

    /**
     * Asserts that, when a PNG file is converted into a {@link Board}, the shape and
     * colors of the PNG file are retained
     * 
     * @throws PietFileNotReadException
     */
    @Test
    void convertFileToBoard_PNGFile_ReturnsValidBoard() throws PietColorNotFoundException, PietFileNotReadException {

        Path testPNGPath = PNG_TEST_FILE;
        PietFileReader fileReader = new PietFileReader(testPNGPath);
        Board actual = fileReader.convertFileToBoard();

        assertEquals(expected.getSizeCol(), actual.getSizeCol());
        assertEquals(expected.getSizeCol(), actual.getSizeCol());

        for(int i = 0; i < expected.getSizeRow(); i++) {
            for(int j = 0; j < expected.getSizeCol(); j++) {
                Codel currCodel = new Codel(i, j);
                assertEquals(expected.getColor(currCodel), actual.getColor(currCodel));
            }
        }

    }

}
