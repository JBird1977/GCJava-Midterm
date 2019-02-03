import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MidtermApp {


    public static void main(String[] args) 
    {
        Scanner scan = new Scanner(System.in);
        ArrayList<ArrayList<Minefield>> gameBoard = new ArrayList<ArrayList<Minefield>>();
        int x = 0; // columns
        int y = 0; // rows
        
        printTitle();   //prints title
        do {y = getYCoordinate(scan);} while ((y == -1));
        do {x = getXCoordinate(scan);} while ((x == -1));
        createBoard(x, y, gameBoard);         
    } // end Main

    private static void printTitle()
    {
        System.out.println("Welcome to Minesweeper!");
    } // end printTitle

    private static int getYCoordinate(Scanner scan) 
    {
        int in = -1;
        System.out.println("Please enter how many rows you want to generate: ");
        try 
        {
            in = scan.nextInt();
        } catch (InputMismatchException e) 
        {
            System.out.println("Please enter a number.");
            return -1;
        }
        return in;
    } // end getYCoordinate

    private static int getXCoordinate(Scanner scan) 
    {
        int in = -1;
        System.out.println("Please enter how many columns you want to generate: ");
        try 
        {
            in = scan.nextInt(); 
        } catch (InputMismatchException e) 
        {
            System.out.println("Please enter a number.");
            return -1;
        }
        return in;
    } // end getYCoordinate

    
    private static void createBoard(int x, int y, ArrayList<ArrayList<Minefield>> gameBoard) 
    {
        for (int i = 0; i < y; i++) 
        { 
            gameBoard.add(new ArrayList<Minefield>());
            for (int j = 0; j < x; j++) 
            {
                gameBoard.get(i).add(new Minefield(false, false));
            } // end inner loop
            System.out.println();
        } // end outer loop

        for (int i = 0; i < gameBoard.size(); i++) 
        {
            for (int j = 0; j < gameBoard.get(i).size(); j++) 
            {
                // System.out.println("gameBoard.get(i).size() = " + gameBoard.get(i).size());
                if (!gameBoard.get(i).get(j).getRevealed()) 
                { 
                    System.out.print("[ ]");
                } // end if statement

            } // end inner loop
            System.out.println(); //after printing a row of the board go down to next line
        } // end outer loop
        
    }
    
}
