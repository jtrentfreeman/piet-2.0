package com.frejt.piet.utils.reader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.frejt.piet.entity.Board;
import com.frejt.piet.entity.Codel;
import com.frejt.piet.entity.PPMMetadata;
import com.frejt.piet.exception.ContentTypeNotFoundException;
import com.frejt.piet.exception.PietColorNotFoundException;
import com.frejt.piet.exception.PietFileNotReadException;
import com.frejt.piet.utils.color.PietColor;

/**
 * Class dedicated to reading a Piet file.
 */
public class PietFileReader {

    private static final Logger log = LogManager.getLogger(PietFileReader.class);

    private Path path;

    public PietFileReader(Path path) {
        this.path = path;
    }

	/**
	 * Converts a file of a known {@link ContentType} into a {@link Board}.
	 * 
	 * @return a Board representing the {@link #path}s file.
	 * @throws FileNotReadException if the file was, for any reason, not able to be read
	 */
    public Board convertFileToBoard() throws PietColorNotFoundException, PietFileNotReadException {

        ContentType contentType = getFileType();

        switch(contentType) {
            case PPM:
                return readPPM();
            case PNG:
                return readPNG();
            default:
                throw new PietFileNotReadException(contentType + " is not a supported content type");
        }

    }

    /**
     * Gets the {@link ContentType} of the file being by the program.
     * 
     * @return a {@link ContentType} dictating how the file is to be read.
     * @throws PietFileNotReadException if the file was, for any reason, not able to be read
     */
    public ContentType getFileType() throws PietFileNotReadException {

        ContentType contentType;

        try {
            contentType = ContentType.getContentType(Files.probeContentType(path));
        } catch(ContentTypeNotFoundException e) {
            throw new PietFileNotReadException("Could not read file: " + e.getMessage());
        } catch(IOException e) {
            throw new PietFileNotReadException("Could not read file: " + e.getMessage());
        }

        return contentType;
    }

    /**
     * Using the {@link #path} to a PPM file provided to this PietFileReader, converts
     * the file into a {@link Board}
     * 
     * @return a Board representation of the PPM file
     * @throws PietFileNotReadException
     */
    private Board readPPM() throws PietColorNotFoundException, PietFileNotReadException {

        Board board;

        try(Scanner sc = new Scanner(path.toFile())) {

            String magicNumber = sc.next();
            Integer columns = sc.nextInt();
            Integer rows = sc.nextInt();
            Integer maxVal = sc.nextInt();

            PPMMetadata metadata = new PPMMetadata(magicNumber, columns, rows, maxVal);
            board = new Board(metadata);

            for(int i = 0; i < board.getSizeRow(); i++) {
                for(int j = 0; j < board.getSizeCol(); j++) {
                    Integer red, blue, green;

                    red = sc.nextInt();
                    green = sc.nextInt();
                    blue = sc.nextInt();

                    Codel coordinate = new Codel(i, j);
                    PietColor color = PietColor.getColorFromValues(red, green, blue);
                    board.setColor(coordinate, color);
                }
            }

            return board;

        } catch(FileNotFoundException e) {
            throw new PietFileNotReadException(e.getMessage());
        }
    }

    /**
     * Using the {@link #path} to a PNG file proivded to this PietFileReader, converts 
     * the file into a {@link Board}
     * 
     * @return a Board representation of the PNG file
     * @throws PietFileNotReadException
     */
    private Board readPNG() throws PietFileNotReadException, PietColorNotFoundException {

        try {
            BufferedImage image = ImageIO.read(new FileInputStream(path.toFile()));
            Board board = new Board(image.getHeight(), image.getWidth());

            for(int i = 0; i < image.getHeight(); i++) {
                for(int j = 0; j < image.getWidth(); j++) {
                    Integer color = image.getRGB(j, i);
                    Integer red = (color & 0x00ff0000) >> 16;
                    Integer green = (color & 0x0000ff00) >> 8;
                    Integer blue = color & 0x000000ff;

                    PietColor pietColor = PietColor.getColorFromValues(red, green, blue);
                    Codel coordinate = new Codel(i, j);
                    board.setColor(coordinate, pietColor);
                }
            }
            return board;
        } catch(IOException e) {
            throw new PietFileNotReadException(e.getMessage());
        }

    }

}
