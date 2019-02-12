import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class MidtermApp {

    // CREATE 2D ARRAY FOR MINEFIELD
    // user enters 2 integers -- for the # of rows and the # of columns
	// user enters integer  for the # of mines
    // iterate n times through a for loop using random.int to generate 
    // both coordinates (boardRow,boardColumn) for the locations of mines
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


        int numMines = -1;
        int userRow = 0;
        int userColumn = 0;
        int mineCount = -1;         //keeps track of number of mines left on the board
        int winCount = 0;           //increments if user flagged a cell that has a mine in it. Used for determining win condition
        int flagUserRow = -1;
        int flagUserColumn = -1;
        boolean gameCheck = false; // checks for game over status
        

        printTitle(); // prints Welcome    
        do {
        System.out.println("Please enter how many rows you want to generate. ");
        System.out.println("You may only enter a number between 1 and 9:");
        
            boardRows = getYCoordinate(scan);
            if (boardRows == 0) 
            {
                System.out.println("Please enter a number between 1 and 9");
                boardRows = -1;
            }
        } while ((boardRows == -1));

        do {
        System.out.println("Please enter how many columns you want to generate. ");
        System.out.println("You may only enter a number between 1 and 9: ");
        
            boardColumns = getXCoordinate(scan);
            if (boardColumns == 0)
            {
                System.out.println("Please enter a number between 1 and 9");
                boardColumns = -1;
            }
        } while ((boardColumns == -1));
        
        createBoard();  //creates and populates game board; all cells set to mines and reveal = false; also prints entire board first time only
        
        // generate/place mines on the board
        do {
            numMines = getNumMines(scan);
        } while (numMines == -1);
        mineCount = numMines;
        
        placeMines(numMines);  // randomly places user's # mines on the board


        findAdjacency();   // sets adjacency values for each element in the gameboard arraylist
       

        // checkCells(userRow, userColumn, boardColumns, gameBoard);  DEBUGGER

        do {  // get the user's choice of coordinates to reveal a square
            
            System.out.println("Please select a row (or 0 on row/column to (un)flag a cell): ");
            userRow = getYCoordinate(scan);
            userRow--; // decrement user choice to align with our gameBoard arraylist
            
            System.out.println("Please select a column: ");
            userColumn = getXCoordinate(scan);
            userColumn--; // decrement user choice to align with our gameBoard arraylist         
           
        //    displayBoard();
            
        //    gameCheck = gameOverCheck(userRow, userColumn);
              

            if (userRow == -1 && userColumn == -1)  // Validation check that the user does NOT want to flag a cell for mine
            {
              do //validate input for flagged row input 
              {  
                System.out.println("Please enter the row of the cell to (un)flagged: ");
                try 
                {
                flagUserRow = scan.nextInt();
                flagUserRow--;
                } catch( InputMismatchException e)
                {
                    System.out.println("Please enter a number.");
                    continue;
                }            
              } while (flagUserRow == -1);
             
              do //validate input for flagged column input 
              {
                System.out.println("Please enter the column of the cell to be (un)flagged: ");
                try 
                {
                flagUserColumn = scan.nextInt();
                flagUserColumn--;
                } catch( InputMismatchException e)
                {
                    System.out.println("Please enter a number.");
                    continue;
                }
                
              } while (flagUserColumn == -1);
              
              if (!gameBoard.get(flagUserRow).get(flagUserColumn).getFlagged()) 
              {
                  winCount = flagCell(flagUserRow,  flagUserColumn, winCount);
                  mineCount--;
                 
              } else if (gameBoard.get(userRow).get(userColumn).getFlagged())
              {
                  unFlagCell(flagUserRow,  flagUserColumn, winCount);
                  mineCount++;
                  winCount = winCountCheck(userRow, userColumn, winCount);
              }
              displayBoard();  // displays the entire board in its current state (including user's chosen cell to check or flagged cell)
              System.out.println("There are: " + mineCount + " mines left.");
              
              if (mineCount == 0)
              {
                  userRow = 0;      //setting these to 0 to prevent ArrayOutofBoundsException
                  userColumn = 0;   //user can't blow up a mine when flagging cells so this is fine
                  gameCheck = gameOverCheck(scan, userRow, userColumn, mineCount, winCount, numMines);
              }
             
            }
            
            if (!(userRow == -1) && !(userColumn == -1)) //condition occurs if user did not choose to flag a cell
            {
            revealInput(userRow, userColumn);  // sets the user's selected cell to reveal = true
            displayBoard();  // displays the entire board in its current state (including user's chosen cell to check or flagged cell)
            gameCheck = gameOverCheck(scan, userRow, userColumn, mineCount, winCount, numMines);
            //only print the amount of mines left if the game continues
                if (gameCheck == false) 
                {
                System.out.println("There are: " + mineCount + " mines left.");
                }
            }
            
            checkAdjacentCells(userRow, userColumn);  // LOOP FOR REVEALING ALL 0-ADJACENCY CELLS 
            
            
            while (! coordinatesToCheck.isEmpty() )
            { System.out.println(coordinatesToCheck.isEmpty());  //DEBUGGER
           
            	StoredCoordinates nextCheck = new StoredCoordinates();
            	nextCheck = coordinatesToCheck.pop();
            	
            	int row = nextCheck.getRow();
            	int column = nextCheck.getColumn();
            	
            	System.out.println("row = " + row);							//DEBUGGER
            	System.out.println("column = " + column);					//DEBUGGER
            	
            	checkAdjacentCells(row, column);
            }
            
            

            
        } while (gameCheck == false);

        // System.out.println("There are: " + checkCells(userY, userX, boardRows,
        // boardColumns, gameBoard) + " mines around you."); ;
        scan.close();
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
            scan.nextLine();
            return -1;
        }
        
        if (in < 0 || in > 9)
        {
            System.out.println("Please enter a number between 1 and 9.");
            scan.nextLine();
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
            scan.nextLine();
            return -1;
        }
        
        if (in < 0 || in > 9)
        {
            System.out.println("Please enter a number between 1 and 9.");
            scan.nextLine();
            return -1;
        }
        return in;
    } // end getYCoordinate

    private static void revealInput(int userX, int userY) {

        if (gameBoard.get(userX).get(userY).getFlagged())
        {
            System.out.println("This cell is flagged. Please select another cell or unflag it to reveal the cell.");
        } else gameBoard.get(userX).get(userY).setRevealed(true);

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
        
        for (int row = 0; row < gameBoard.size(); row++) 
        {
            for (int column = 0; column < gameBoard.get(row).size(); column++) 
            {

                if (!gameBoard.get(row).get(column).getRevealed() && !gameBoard.get(row).get(column).getFlagged()) // if the space hasn't been revealed....
                {
                    System.out.print("[ ]");
                } else if (!gameBoard.get(row).get(column).getRevealed() && gameBoard.get(row).get(column).getFlagged()) 
                {
                    System.out.print("[F]");
                } else if (gameBoard.get(row).get(column).getRevealed() && gameBoard.get(row).get(column).getMine()) 
                {  // mine is in
                    System.out.print("[*]");
                } else if (gameBoard.get(row).get(column).getRevealed() && !gameBoard.get(row).get(column).getMine()) {
                    System.out.print("[" + gameBoard.get(row).get(column).getAdjacency() + "]");
                }        

            }
            
            System.out.println(); // after printing a row of the board go down to next line
            
            if (row < gameBoard.size() - 1) // print out a number next to the row for purposes of user input
            {
            	System.out.print((row + 2) + " ");
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
            scan.nextLine();
            return -1;
        }
        if (in >= (boardRows * boardColumns))
        {
            System.out.println("That's too many mines! Pick a smaller number!");
            scan.nextLine();
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

        for (int row = 0; row < gameBoard.size(); row++) { // gameboard.size() = # rows, row = row # being iterated

            for (int column = 0; column < gameBoard.get(row).size(); column++) { // gameboard.get(row).size() = # columns, column = column # being iterated
 
                gameBoard.get(row).get(column).setAdjacency(checkCells(row, column));
            }
        }
    }

    private static boolean gameOverCheck(Scanner scan, int userRow, int userColumn, int mineCount, int winCount, int numMines) 
    {
        scan.nextLine();
        String in = "";
        //check to see if player revealed a mine - if they did, it's game over
        if (gameBoard.get(userRow).get(userColumn).getRevealed() && gameBoard.get(userRow).get(userColumn).getMine()) 
        {
            System.out.println("BOOM!!! Game over!");
            return true;
        } 
        //if there are no more mines left to find on the board, ask if the user is ready to check and see if they won
        if (mineCount == 0)
        {
            do {  
                System.out.println("All mines are accounted for! Would you like to check and see if you won? (y/n) ");
                in = scan.nextLine();
                if (in.equalsIgnoreCase("y") && in.equalsIgnoreCase("n"))
                {
                    System.out.println("Invalid input. Please enter y or n");
                }
            } while (in.equalsIgnoreCase("y") && in.equalsIgnoreCase("n"));
      
            if (in.equalsIgnoreCase("y"))
            {
  
                if (mineCount == 0  && winCount == numMines) 
                {
                    System.out.println("You won!!!");
                    return true;
                } else if (mineCount == 0 && winCount != numMines) {
                              System.out.println("One or more cells were incorrectly flagged :(. You go boom.");
                              return true;
                }
            }
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
                }
            }

        }
        return mineCounter;
    }



    private static void checkAdjacentCells(int currentRow, int currentColumn) {   
        
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
          if (bigColumn >= boardColumns) {											// MOST RECENT CHANGE: boardColumns -1
              // System.out.println("bigColumn = " + bigColumn);
              bigColumn = currentColumn;
          }

          for (int Row = smallRow; Row <= bigRow; Row++) 
          {
              for (int Column = smallColumn; Column <= bigColumn; Column++) 
              {
            	  if ( gameBoard.get(Row).get(Column).getFlagged() )
            	  { // If the cell is already flagged, do nothing 
            	  } 
            	  
            	  if ( gameBoard.get(Row).get(Column).getMine() )
            	  { // If the cell contains a mine, do nothing 
            	  }	
            	  	
            	  else if ( (gameBoard.get(Row).get(Column).getAdjacency() == 0) && !(gameBoard.get(Row).get(Column).getRevealed()) ) 
    			  {  // If this cell is NOT adjacent to a mine, and NOT yet revealed, 
						coordinatesToCheck.push( new StoredCoordinates(Row,Column) );  //add this cell to the stack of cells to re-check.
						gameBoard.get(Row).get(Column).setRevealed(true);			   //reveal this cell 
    			  }
				  
    			  else gameBoard.get(Row).get(Column).setRevealed(true);
    						
              }
          }
	}
    	

    //this method is called ONLY when the user successfully flags or unflags a space
    private static int winCountCheck(int userRow, int userColumn, int winCount) {
            //if the user flagged a space that DOES contain a mine, increment winCount and return
        if (gameBoard.get(userRow).get(userColumn).getFlagged() && gameBoard.get(userRow).get(userColumn).getMine())
        {
            winCount++;
            return winCount;
                //if the user flags a space that ISN'T a mine, return winCount
        } else if (gameBoard.get(userRow).get(userColumn).getFlagged() && !gameBoard.get(userRow).get(userColumn).getMine())
        { 
            return winCount;
            //if the user unflags a space that contained a mine, decrement the winCount and return 
        } else if (!gameBoard.get(userRow).get(userColumn).getFlagged() && gameBoard.get(userRow).get(userColumn).getMine())
        {
            winCount--;
            return winCount;
        }
        
        return winCount;
        
    }
    
    //called when the user flags or unflags a space
    private static int flagCell(int flagUserRow, int flagUserColumn, int winCount)
    {
        gameBoard.get(flagUserRow).get(flagUserColumn).setFlagged(true);
        return winCount = winCountCheck(flagUserRow, flagUserColumn, winCount);

    }
    
    private static int unFlagCell(int flagUserRow, int flagUserColumn, int winCount)
    {
        gameBoard.get(flagUserRow).get(flagUserColumn).setFlagged(false);
        return winCount = winCountCheck(flagUserRow, flagUserColumn, winCount);
    }
    

}
