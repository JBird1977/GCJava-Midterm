import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class MidtermApp {


   	// CREATE 2D ARRAY FOR MINEFIELD
	// user enters 2 integers -- i for the # of columns, n for the # of mines
	// iterate n times through a for loop using random.int to generate both coordinates (y,x) for the locations of mines
	// use an if-then to check for duplicate locations -- when found, i-- will force an extra iteration
	

	
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
        
        // take user input for # of mines
        int numMines = getNumMines(scan);
        
        // place mines randomly
        placeMines(x, y, numMines, gameBoard);
        
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
    
    
    private static int getNumMines(Scanner scan) 
    {
        int in = -1;
        System.out.println("Please enter how many mines you would like in your minefield: ");
        try 
        {
            in = scan.nextInt();
        } catch (InputMismatchException e) 
        {
            System.out.println("Please enter a number.");
            return -1;
        }
        return in;
    } // end getnumMines
    
    
    private static void placeMines(int x, int y, int numMines, ArrayList<ArrayList<Minefield>> gameBoard) 
    {
    	int numMinesSet = 0;
    	Random rand = new Random();
    	int randomYCoordinate = 0;
    	int randomXCoordinate = 0;
    	
    	while (numMinesSet <= numMines) {
         
        // get random coordinates to place a mine 
    	randomYCoordinate = rand.nextInt(y + 1);
    	randomXCoordinate = rand.nextInt(x + 1);
    	
    		if (! ( gameBoard.get(y).get(x).getMine() ) ) {
    
    			gameBoard.get(y).get(x).setMine(true);
    			numMinesSet ++;		
    		}
    	}
    	
    	

    }
}
