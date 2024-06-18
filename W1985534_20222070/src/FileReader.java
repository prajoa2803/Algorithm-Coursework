import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Student ID: 20222070/W1985534
 * Name: Thilaganathan Prajoathayan
 *
 * The class to read the file and update the array
 */
public class FileReader {

    private double takeStartTime;
    public Cell[][] readFileToArray() {
        //Open windows to choose the file
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        if (result != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        File file = fileChooser.getSelectedFile();

        try {
            //scan the file and update it to 2D array
            Scanner reader = new Scanner(file);
            takeStartTime = System.nanoTime(); //Record the starting time
            String line = reader.nextLine();
            int width = line.length();
            int height = 1;
            while (reader.hasNext()) {
                height++;
                reader.nextLine(); // Move scanner to the next line
            }
            reader.close();

            Cell[][] map = new Cell[height][width];

            reader = new Scanner(file);
            int i = 0;
            while (reader.hasNext()) {
                line = reader.nextLine();
                System.out.println(line);
                for (int j = 0; j < width; j++) {
                    map[i][j] = new Cell(i,j,String.valueOf(line.charAt(j)));
                }
                i++;
            }
            return map;
        } catch (NoSuchElementException e){
            System.out.println("Wrong File Structure");
        } catch (Exception e) {
            System.out.println("Error reading file");
        }
        return null;
    }
    //Method to get the starting time
    public double getStartTime() {
        return takeStartTime;
    }
}