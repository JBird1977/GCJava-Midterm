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
    
    private static ArrayList<ArrayList<Minefield>> gameBoard = new ArrayList<ArrayList<Minefield>>();
    static int boardColumns = 0;
    static int boardRows = 0;
    

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        
         // columns
         // rows
        int numMines = -1;
        int userRow = 0;
        int userColumn = 0;
        int mineCount = -1;         //keeps track of number of mines left on the board
        boolean gameCheck = false; // checks for game over status

        // creates the board
        printTitle(); // prints title
        System.out.println("Please enter how many rows you want to generate. ");
        System.out.println("You may only enter a number between 1 and 9:");
        do {
            boardRows = getYCoordinate(scan);
            if (boardRows == 0) 
            {
                System.out.println("Please enter a number between 1 and 9");
                boardRows = -1;
            }
        } while ((boardRows == -1));
        System.out.println("Please enter how many columns you want to generate. ");
        System.out.println("You may only enter a number between 1 and 9: ");
        do {
            boardColumns = getXCoordinate(scan);
            if (boardColumns == 0)
            {
                System.out.println("Please enter a number between 1 and 9");
                boardColumns = -1;
            }
        } while ((boardColumns == -1));
        createBoard();

        // generate/place mines on the board then populate board with numbers
        do {
            numMines = getNumMines(scan);
        } while (numMines == -1);
        mineCount = numMines;
        placeMines(numMines);
        findAdjacency();
        // sets adjacency values for each element in the arraylist

        // checkCells(userRow, userColumn, boardColumns, gameBoard); DEBUGGER

        do {

            // get the user's choice of coordinates to reveal a square
            System.out.println("Please select a row (or 0 on row/column to (un)flag a cell): ");
            userRow = getYCoordinate(scan);
            userRow--; // decrement user choice to align with our gameBoard arraylist
            System.out.println("Please select a column: ");
            userColumn = getXCoordinate(scan);
            userColumn--; // decrement user choice to align with our gameBoard arraylist

            // System.out.println("There are " + checkCells(userRow, userColumn,
            // boardColumns, gameBoard) + "mines next to the cell you picked.");

            // reveal the space the user selected and redisplay the board
            if (userRow == -1 && userColumn == -1)
            {
                mineCount = flagCell(scan, mineCount);
            } else 
            {
            revealInput(userRow, userColumn);  
            gameCheck = gameOverCheck(userRow, userColumn);
            }
            displayBoard();
            System.out.println("There are: " + mineCount + " mines left.");
            
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
        
        if (in < 0 || in > 9)
        {
            System.out.println("Please enter a number between 0 and 9.");
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
        
        if (in < 0 || in > 9)
        {
            System.out.println("Please enter a number between 1 and 9.");
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

        
        for (int i = 0; i < boardRows; i++) // create the game board
        {
            gameBoard.add(new ArrayList<Minefield>());
            for (int j = 0; j < boardColumns; j++) {
                gameBoard.get(i).add(new Minefield(false, false));
            }

            System.out.println();
        } // end outer loop
        for (int k = 0; k < boardColumns; k++) {
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
                // System.out.println("gameBoard.get(i).size() = " + gameBoard.get(i).size());
                if (!gameBoard.get(i).get(j).getRevealed()) {
                    System.out.print("[ ]");
                } else {
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

    private static void displayBoard() {
        for (int k = 0; k < boardColumns; k++) // display number identifying each column
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

                if (!gameBoard.get(i).get(j).getRevealed() && !gameBoard.get(i).get(j).getFlagged()) // if the space hasn't been revealed....
                {
                    System.out.print("[ ]");
                } else if (!gameBoard.get(i).get(j).getRevealed() && gameBoard.get(i).get(j).getFlagged()) 
                {
                    System.out.print("[F]");
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
        if (in >= (boardRows * boardColumns))
        {
            System.out.println("That's too many mines! Pick a smaller number!");
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

            }

        }
        return mineCounter;
    }

    private static int flagCell(Scanner scan, int mineCount)
    {
        int userRow = 0;
        int userColumn = 0;
        
        System.out.println("Please enter the row of the cell to (un)flagged: ");
        try 
        {
        userRow = scan.nextInt();
        } catch( InputMismatchException e)
        {
            System.out.println("Please enter a number.");
            return -1;
        }
        
        userRow--;
        System.out.println("Please enter the column of the cell to be (un)flagged: ");
        try {
        userColumn = scan.nextInt();
        } catch( InputMismatchException e)
        {
            System.out.println("Please enter a number.");
            return -1;
        }
        
        userColumn--;
        if (!gameBoard.get(userRow).get(userColumn).getFlagged())
        {
        gameBoard.get(userRow).get(userColumn).setFlagged(true);
        mineCount = mineCount-1;
        return mineCount;
        } else 
        {
            gameBoard.get(userRow).get(userColumn).setFlagged(false);
            mineCount = mineCount + 1;
            return mineCount;
        }
        
    }
}
