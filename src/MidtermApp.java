import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class MidtermApp {

    // CREATE 2D ARRAY FOR MINEFIELD
    // user enters 2 integers -- i for the # of columns, n for the # of mines
    // iterate n times through a for loop using random.int to generate both
    // coordinates (boardRows,boardColumns) for the locations of mines
    // use an if-then to check for duplicate locations -- when found, i-- will force
    // an extra iteration

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        ArrayList<ArrayList<Minefield>> gameBoard = new ArrayList<ArrayList<Minefield>>();
        int boardColumns = 0; // columns
        int boardRows = 0; // rows
    //    int userX = 0; // user input for boardColumns coordinate while playing game
     //   int userY = 0; // user input for boardRows coordinate while playing game
      //  int row = 0;
      //  int column = 0;

        // creates the board
        printTitle(); // prints title
        System.out.println("Please enter how many rows you want to generate: ");
        do {boardRows = getYCoordinate(scan);} while ((boardRows == -1));
        System.out.println("Please enter how many columns you want to generate: ");
        do {boardColumns = getXCoordinate(scan);} while ((boardColumns == -1));
        createBoard(boardColumns, boardRows, gameBoard);
        
        
        // generate and place mines on the board
        int numMines = getNumMines(scan);
        placeMines(boardColumns, boardRows, numMines, gameBoard);
        //method to check for adjacency goes here :)
     
   //     checkCells(row, column, boardColumns, gameBoard);
    //    System.out.println("There are " + checkCells(row, column, boardColumns, gameBoard) + "mines next to the cell you picked.");
        // get the user's choice of coordinates to reveal a square
    /*    System.out.println("Please select a row: ");
        userY = getYCoordinate(scan);
        userY--;                                        //decrement user choice to align with our gameBoard arraylist
        System.out.println("Please select a column: ");
        userX = getXCoordinate(scan);
        userX--;                                        //decrement user choice to align with our gameBoard arraylist
        */
        //reveal the space the user selected and redisplay the board
   //     revealInput(userX, userY, gameBoard);
        displayBoard(boardColumns, boardRows, gameBoard);

     
        //System.out.println("There are: " + checkCells(userY, userX, boardRows, boardColumns, gameBoard) + " mines around you."); ;
      

    } // end Main

    private static void printTitle() {
        System.out.println("Welcome to Minesweeper!");
    } // end printTitle

    private static int getYCoordinate(Scanner scan) {
        int in = -1;
        try {
            in = scan.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Please enter a number.");
            return -1;
        }
        return in;
    } // end getYCoordinate

    private static int getXCoordinate(Scanner scan) {
        int in = -1;
        try {
            in = scan.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Please enter a number.");
            return -1;
        }
        return in;
    } // end getYCoordinate

    private static void revealInput(int userX, int userY, ArrayList<ArrayList<Minefield>> gameBoard) {
        
        gameBoard.get(userY).get(userX).setRevealed(true);
    }

    private static void createBoard(int boardColumns, int boardRows, ArrayList<ArrayList<Minefield>> gameBoard) {

        for (int k = 0; k < boardRows; k++) {
            if (k == 0) {
                System.out.printf("   %-1d", (k + 1));
            } else {
                System.out.printf("  %-1d", (k + 1));
            }

        }
        for (int i = 0; i < boardRows; i++) // create the game board
        {
            gameBoard.add(new ArrayList<Minefield>());
            for (int j = 0; j < boardColumns; j++) {
                gameBoard.get(i).add(new Minefield(true, false));
            }

            System.out.println();
        } // end outer loop
        System.out.print("1 ");
        for (int i = 0; i < gameBoard.size(); i++) {
            for (int j = 0; j < gameBoard.get(i).size(); j++) {
                // System.out.println("gameBoard.get(i).size() = " + gameBoard.get(i).size());
                if (!gameBoard.get(i).get(j).getRevealed()) {
                    System.out.print("[ ]");
                } else 
                {
                    System.out.print("[ ]"); 
                }

            } // end inner loop
            System.out.println(); // after printing a row of the board go down to next line
            if (i < gameBoard.size() - 1) {
                System.out.print((i + 2) + " ");
            }
        } // end outer loop

        System.out.println(); // insert a blank line for cleaner appearance
    }

    private static void displayBoard(int boardColumns, int boardRows, ArrayList<ArrayList<Minefield>> gameBoard) {
        for (int k = 0; k < boardRows; k++) // display number identifying each column
        {
            if (k == 0) {
                System.out.printf("   %-1d", (k + 1));
            } else {
                System.out.printf("  %-1d", (k + 1));
            }
        }
        System.out.println();
        System.out.print("1 ");
        for (int i = 0; i < gameBoard.size(); i++) {
            for (int j = 0; j < gameBoard.get(i).size(); j++) {

                if (!gameBoard.get(i).get(j).getRevealed()) // if the space hasn't been revealed....
                {
                    System.out.print("[ ]");
                } else if (gameBoard.get(i).get(j).getRevealed() && gameBoard.get(i).get(j).getMine()) // if the space
                                                                                                       // has been
                                                                                                       // revealed AND a
                                                                                                       // mine is in
                                                                                                       // that space...
                {
                    System.out.print("[*]");
                } else if (gameBoard.get(i).get(j).getRevealed() && !gameBoard.get(i).get(j).getMine()) {
                    System.out.print("[0]");
                }

            }
            System.out.println(); // after printing a row of the board go down to next line
            if (i < gameBoard.size() - 1) // print out a number next to the row for purposes of user input
            {
                System.out.print((i + 2) + " ");
            }
        }
        System.out.println(); // insert a blank line for cleaner appearance
    }

    private static int getNumMines(Scanner scan) {
        int in = -1;
        System.out.println("Please enter how many mines you would like in your minefield: ");
        try {
            in = scan.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Please enter a number.");
            return -1;
        }
        return in;
    } // end getnumMines

    private static void placeMines(int boardColumns, int boardRows, int numMines, ArrayList<ArrayList<Minefield>> gameBoard) {
        int numMinesSet = 0;
        Random rand = new Random();
        int randomYCoordinate = 0;
        int randomXCoordinate = 0;

        while (numMinesSet < numMines) {

            // get random coordinates to place a mine
            randomYCoordinate = rand.nextInt(boardRows);
            randomXCoordinate = rand.nextInt(boardColumns);

            if (!(gameBoard.get(randomYCoordinate).get(randomXCoordinate).getMine())) {

                gameBoard.get(randomYCoordinate).get(randomXCoordinate).setMine(true);
                numMinesSet++;
            }
        }

    }

   private static int checkCells(int currentRow, int currentColumn, int boardColumns, ArrayList<ArrayList<Minefield>> gameBoard)
  //  private static int checkCells(int userY, int userX, int boardColumns, int boardRows, ArrayList<ArrayList<Minefield>> gameBoard)
    {
        int mineCounter = 0;
        int smallRow = currentRow-1;
        int bigRow = currentRow+1;
        int smallColumn = currentColumn-1;
        int bigColumn = currentColumn+1;
        
        
        if (smallRow < 0)
        {
            smallRow = currentRow;
        }
        if (bigRow >= gameBoard.size()) 
        {
            bigRow = currentRow;
        }
        if (smallColumn < 0)
        {
            smallColumn = currentColumn;
        }
        if (bigColumn >= boardColumns-1) 
        {
            System.out.println("bigColumn = " + bigColumn);
            bigColumn = currentColumn;
        }
        
        for (int Row = smallRow; Row <= bigRow; Row++) {
            
            for (int Column = smallColumn; Column <= bigColumn; Column++) {
                if (gameBoard.get(Row).get(Column).getMine()) {
                    mineCounter++;
                }
                
            }
            
        }
        return mineCounter;  
    }
    
}
