import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class MidtermApp {

    // CREATE 2D ARRAY FOR MINEFIELD
    // user enters 2 integers -- i for the # of columns, n for the # of mines
    // iterate n times through a for loop using random.int to generate both
    // coordinates (boardRows,boardColumns) for the locations of mines
    // use an if-then to check for duplicate locations -- when found, i-- will force
    // an extra iteration
	
		
	// TO-DO LIST (Priority 1 = most important):
	// 1. Re-loop to reveal non-mine cells adjacent to userCell...reloop, reloop... 
	// 1. Add field to minefield class: flagged (as mine)
	// 3. Try-catch or if: numMines <= numSquares (boardRows * boardColumns)

    
    private static ArrayList<ArrayList<Minefield>> gameBoard = new ArrayList<ArrayList<Minefield>>();
    static int boardColumns = 0;
    static int boardRows = 0;
	private static Stack<StoredCoordinates> coordinatesToCheck = new Stack<StoredCoordinates>();


    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        int userRow = 0;
        int userColumn = 0;
        boolean gameCheck = false; // checks for game over status

        printTitle(); // prints Welcome
        
        System.out.println("Please enter how many rows you want to generate: ");
        do {
            boardRows = getYCoordinate(scan);
        } while ((boardRows == -1));
        
        System.out.println("Please enter how many columns you want to generate: ");
        do {
            boardColumns = getXCoordinate(scan);
        } while ((boardColumns == -1));
        
        createBoard();  //creates and populates game board; all cells set to mines and reveal = false; also prints entire board first time only

        
        int numMines = getNumMines(scan);
        placeMines(numMines);  // randomly places user's # mines on the board
        
        findAdjacency();
        // sets adjacency values for each element in the gameboard arraylist

        // checkCells(userRow, userColumn, boardColumns, gameBoard);  DEBUGGER

        do {
            // get the user's choice of coordinates to reveal a square
            System.out.println("Please select a row: ");
            userRow = getYCoordinate(scan);
            userRow--; // decrement user choice to align with our gameBoard arraylist
            System.out.println("Please select a column: ");
            userColumn = getXCoordinate(scan);
            userColumn--; // decrement user choice to align with our gameBoard arraylist

            // System.out.println("There are " + checkCells(userRow, userColumn,
            // boardColumns, gameBoard) + "mines next to the cell you picked.");

            // reveal the space the user selected and redisplay the board
            revealInput(userRow, userColumn);
           
            displayBoard();
            
            gameCheck = gameOverCheck(userRow, userColumn);
            
        //    checkAdjacentCells();
            
        } while (gameCheck == false);

        // System.out.println("There are: " + checkCells(userY, userX, boardRows,
        // boardColumns, gameBoard) + " mines around you."); ;

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

    private static void revealInput(int userX, int userY) {

    	
    	
    	
        gameBoard.get(userX).get(userY).setRevealed(true);
    }

    private static void createBoard() {
        
        // print column header for game board
        	for (int k = 0; k < boardColumns; k++) {
                if (k == 0) {
                    System.out.printf("   %-1d", (k + 1));
                } else {
                    System.out.printf("  %-1d", (k + 1));
                }
            }
        

        for (int Row = 0; Row < boardRows; Row++) {   // populate the game board -- set all cells to mine=false (default)
            gameBoard.add(new ArrayList<Minefield>());
            
            for (int Column = 0; Column < boardColumns; Column++) {
                gameBoard.get(Row).add(new Minefield(false, false));
            }
        } // end outer loop
        
        System.out.println();
        System.out.print("1 ");
        
// print the entire board
        for (int i = 0; i < gameBoard.size(); i++) {  
            for (int j = 0; j < gameBoard.get(i).size(); j++) {
                // System.out.println("gameBoard.get(i).size() = " + gameBoard.get(i).size());
                if (!gameBoard.get(i).get(j).getRevealed()) {
                    System.out.print("[ ]");
                } else {
                    System.out.print("[ ]");
                }

            } // end inner loop
            
            System.out.println(); // after printing a row of the board print the row header for next row
            if (i < gameBoard.size() - 1) {
                System.out.print((i + 2) + " ");
            }
        } // end outer loop

        System.out.println(); // insert a blank line for cleaner appearance
    }

    private static void displayBoard() {
        for (int k = 0; k < boardColumns; k++) //  print column header for game board
        {
            if (k == 0) {
                if (boardRows > 9) {
                    System.out.printf("    %-1d", (k + 1));
                } else {
                    System.out.printf("   %-1d", (k + 1));
                }

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
                } else if (gameBoard.get(i).get(j).getRevealed() && gameBoard.get(i).get(j).getMine()) {
                    // mine is in
                    System.out.print("[*]");

                } else if (gameBoard.get(i).get(j).getRevealed() && !gameBoard.get(i).get(j).getMine()) {
                    System.out.print("[" + gameBoard.get(i).get(j).getAdjacency() + "]");
                }

            }
            System.out.println(); // after printing a row of the board go down to next line
            if (i < gameBoard.size() - 1) // print out a number next to the row for purposes of user input
            {
                if (boardRows > 9) {
                    System.out.print((i + 2) + "  ");
                } else {
                    System.out.print((i + 2) + " ");
                }
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

    private static void placeMines(int numMines) {
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

    private static void findAdjacency() {

        for (int i = 0; i < gameBoard.size(); i++) { // gameboard.size() = # rows, i = row # being iterated

            for (int j = 0; j < gameBoard.get(i).size(); j++) { // gameboard.get(i).size() = # columns, j = column #
                                                                // being iterated

                gameBoard.get(i).get(j).setAdjacency(checkCells(i, j));
            }
        }

    }

    private static boolean gameOverCheck(int userRow, int userColumn) {
        if (gameBoard.get(userRow).get(userColumn).getRevealed() && gameBoard.get(userRow).get(userColumn).getMine()) {
            System.out.println("Game over!!!");
            return true;
        }
        return false;
    }

    private static int checkCells(int currentRow, int currentColumn)
    // private static int checkCells(int userY, int userX, int boardColumns, int
    // boardRows, ArrayList<ArrayList<Minefield>> gameBoard)
    {
        int mineCounter = 0;
        int smallRow = currentRow - 1;
        int bigRow = currentRow + 1;
        int smallColumn = currentColumn - 1;
        int bigColumn = currentColumn + 1;

        if (smallRow < 0) {
            smallRow = currentRow;
        }
        if (bigRow >= gameBoard.size()) {
            bigRow = currentRow;
        }
        if (smallColumn < 0) {
            smallColumn = currentColumn;
        }
        if (bigColumn >= boardColumns - 1) {
            // System.out.println("bigColumn = " + bigColumn);
            bigColumn = currentColumn;
        }

        for (int Row = smallRow; Row <= bigRow; Row++) {

            for (int Column = smallColumn; Column <= bigColumn; Column++) {
            	if (gameBoard.get(Row).get(Column).getMine()) {
            		mineCounter++;
            	}
 
                if (( gameBoard.get(Row).get(Column).getAdjacency() == 0) ) {
                  // If this cell is NOT adjacent to a mine, add it to the list of cells to re-check.
                	coordinatesToCheck.push( new StoredCoordinates(Row,Column) );
                	gameBoard.get(Row).get(Column).setRevealed(true);
                }
            }

        }
        return mineCounter;
    }


//    private static void checkAdjacentCells() {   
//        
//    	  int mineCounter = 0;
//          int smallRow = currentRow - 1;
//          int bigRow = currentRow + 1;
//          int smallColumn = currentColumn - 1;
//          int bigColumn = currentColumn + 1;
//
//          if (smallRow < 0) {
//              smallRow = currentRow;
//          }
//          if (bigRow >= gameBoard.size()) {
//              bigRow = currentRow;
//          }
//          if (smallColumn < 0) {
//              smallColumn = currentColumn;
//          }
//          if (bigColumn >= boardColumns - 1) {
//              // System.out.println("bigColumn = " + bigColumn);
//              bigColumn = currentColumn;
//          }
//
//          for (int Row = smallRow; Row <= bigRow; Row++) {
//
//              for (int Column = smallColumn; Column <= bigColumn; Column++) {
//              	if (gameBoard.get(Row).get(Column).getMine()) {
//              		mineCounter++;
//              	}
//   
//                  if (( gameBoard.get(Row).get(Column).getAdjacency() == 0) ) {
//                    // If this cell is NOT adjacent to a mine, add it to the list of cells to re-check.
//                  	coordinatesToCheck.push( new StoredCoordinates(Row,Column) );
//                  	gameBoard.get(Row).get(Column).setRevealed(true);
//                  }
//              }


   
   
    	
  //  }
    	
    	
}
