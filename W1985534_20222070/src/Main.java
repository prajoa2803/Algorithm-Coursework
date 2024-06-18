import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Student ID: 20222070/W1985534
 * Name: Thilaganathan Prajoathayan
 *
 * The main method to run
 */

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        //Iterate until user wants to exit
        while (true) {

            System.out.println("1. Start by Choosing the File\n2. Exit\nEnter Your Option:");
            try {
                int option = scan.nextInt();

                if (option == 1) {
                    System.out.println("Choose the File from FileChooser Window");
                    //Initiate file reader
                    FileReader fileReader = new FileReader();

                    //Assign 2d array from the file
                    Cell[][] map = fileReader.readFileToArray();

                    //Initiate finding the path
                    FindPath shortPath = new FindPath(map);

                    //Initiate process to find the shortest path
                    shortPath.process();

                    //To display the solution
                    shortPath.displaySolution();

                    //Calculate the runtime
                    double endTime = System.nanoTime();
                    double startTime = fileReader.getStartTime();
                    double timeTaken = (endTime - startTime)/1000000;
                    System.out.println("Time Taken to Read and Display the Solution: " + timeTaken + " milliseconds");

                } else if (option == 2) {
                    //End the program
                    break;
                } else {
                    System.out.println("Invalid Option");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please Enter Valid Option!");
                scan.next();
            } catch (NullPointerException e) {
                System.out.println("File not Read");
            }

        }
    }
}