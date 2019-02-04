import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class MidtermApp {

    // CREATE 2D ARRAY FOR MINEFIELD
    // user enters 2 integers -- i for the # of columns, n for the # of mines
    // iterate n times through a for loop using random.int to generate both
    // coordinates (y,x) for the locations of mines
    // use an if-then to check for duplicate locations -- when found, i-- will force
    // an extra iteration

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        ArrayList<ArrayList<Minefield>> gameBoard = new ArrayList<ArrayList<Minefield>>();
        int x = 0; // columns
        int y = 0; // rows
        int userX = 0; // user input for X coordinate while playing game
        int userY = 0; // user input for Y coordinate while playing game

        // creates the board
        printTitle(); // prints title
        System.out.println("Please enter how many rows you want to generate: ");
        do {y = getYCoordinate(scan);} while ((y == -1));
        System.out.println("Please enter how many columns you want to generate: ");
        do {x = getXCoordinate(scan);} while ((x == -1));
        createBoard(x, y, gameBoard);

        // generate and place mines on the board
        int numMines = getNumMines(scan);
        placeMines(x, y, numMines, gameBoard);
        //method to check for adjacency goes here :)
        
        
        // get the user's choice of coordinates to reveal a square
        System.out.println("Please select a row: ");
        userY = getYCoordinate(scan);
        System.out.println("Please select a column: ");
        userX = getXCoordinate(scan);
        
        //reveal the space the user selected and redisplay the board
        revealInput(userX, userY, gameBoard);
        displayBoard(x, y, gameBoard);

      

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
        userX = userX - 1; // align user inputs to indexes in arraylists
        userY = userY - 1;
        gameBoard.get(userY).get(userX).setRevealed(true);
    }

    private static void createBoard(int x, int y, ArrayList<ArrayList<Minefield>> gameBoard) {

        for (int k = 0; k < y; k++) {
            if (k == 0) {
                System.out.printf("   %-1d", (k + 1));
            } else {
                System.out.printf("  %-1d", (k + 1));
            }

        }
        for (int i = 0; i < y; i++) // create the game board
        {
            gameBoard.add(new ArrayList<Minefield>());
            for (int j = 0; j < x; j++) {
                gameBoard.get(i).add(new Minefield(false, false));
            }

            System.out.println();
        } // end outer loop
        System.out.print("1 ");
        for (int i = 0; i < gameBoard.size(); i++) {
            for (int j = 0; j < gameBoard.get(i).size(); j++) {
                // System.out.println("gameBoard.get(i).size() = " + gameBoard.get(i).size());
                if (!gameBoard.get(i).get(j).getRevealed()) {
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

    private static void displayBoard(int x, int y, ArrayList<ArrayList<Minefield>> gameBoard) {
        for (int k = 0; k < y; k++) // display number identifying each column
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

    private static void placeMines(int x, int y, int numMines, ArrayList<ArrayList<Minefield>> gameBoard) {
        int numMinesSet = 0;
        Random rand = new Random();
        int randomYCoordinate = 0;
        int randomXCoordinate = 0;

        while (numMinesSet < numMines) {

            // get random coordinates to place a mine
            randomYCoordinate = rand.nextInt(y);
            randomXCoordinate = rand.nextInt(x);

            if (!(gameBoard.get(randomYCoordinate).get(randomXCoordinate).getMine())) {

                gameBoard.get(randomYCoordinate).get(randomXCoordinate).setMine(true);
                numMinesSet++;
            }
        }

    }

}
