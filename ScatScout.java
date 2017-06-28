/**
 * A program to play Scat Scout...
 */

import static java.lang.System.*; // so you can write out.println() instead of System.out.println()

import java.util.*;

class ScatScout {
    static final int boardSize = 10;
    static final Random rand = new Random();
    static final boolean SCAT = true;
    static final boolean CLEAR = false;
    //Added a variable for gamewin
    static boolean gameFail = false;
    static boolean gameWin = false;


    public static void main(String[] args) {
        Scanner input = new Scanner(in);
        boolean[][] board = new boolean[boardSize][boardSize]; // scat or no scat
        boolean[][] exposed = new boolean[boardSize][boardSize]; // showing or hidden
        int[][] counts = new int[boardSize][boardSize]; // number of neighbors with scat

        if (args.length > 0) {
            // expect the only argument if any to be a number used to seed the random number generator for testing
            rand.setSeed(Integer.parseInt(args[0]));

        }


        // initialize the board
        someScat(board);
        showInstruction();

        // First sequence ends
        // The game initialing and start
        while (gameFail == false) {
            showBoard(board, exposed, counts);
            System.out.println();
            System.out.println("Enter two integers (row and column): ");
            int input1 = input.nextInt();
            int input2 = input.nextInt();
            findNeighbor(counts, board);
            expose(input1, input2, board, exposed, counts);
            showGamewin(exposed, board);

        }


        // compute the number of scat filled neighors and store that info in counts
        // play the game until the user either steps in some or no non-scat cells have been exposed
        //    read the move coordinates
        //    see if the stepped in it
        //    if not expose 1 or more cells
        //    print the updated board
        // print an appropriate win/lose message
    }


    /**
     * Expose the specified location. In addition, if the location has a count of zero (no neighbors contain scat),
     * recursively expose all of the neighbors of the specified location.
     *
     * @param r       - the row number of the location to expose
     * @param c       - the column number of the location to expose
     * @param board   - the array indicating where the scat is located
     * @param exposed - the array indicating which locations have been exposed
     * @param counts  - the array of counts of neighbors with scat including the location itself if it contains scat
     */
    // To expose the numbers that does not have anything near
    static void expose(int r, int c, boolean[][] board, boolean[][] exposed, int[][] counts) {
        if (exposed[r][c]) return; // nothing to do
        // expose any neighbors that have zero counts
        exposed[r][c] = true;
        if (counts[r][c] > 0) return;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int x = r + i;
                int y = c + j;
                if (!(i == 0 && j == 0) && x >= 0 && x < board.length && y >= 0 && y < board[x].length) {
                    if (counts[x][y] == 0) {
                        expose(x, y, board, exposed, counts);
                    } else {
                        exposed[x][y] = true; // just expose the boarder region - no recursive call
                    }
                }
            }
        }
    }

    //show the instruction for the start of the game.
    public static void showInstruction() {
        System.out.print("The object is to locate all of the animal droppings (scat)\n" +
                "in the yard without stepping in any of it.\n" +
                "You are able to step anywhere in the yard, and when you do, you\n" +
                "can smell how much scat is around you but you can't be sure where\n" +
                "it is.\n" +
                "\n" +
                "You indicate where you want to step by entering a row and column number.\n" +
                "If you didn't step in any, that cell will be filled with a\n" +
                "number indicating how many neighboring cells have scat in them.\n" +
                "Also, if no neighboring cells have any scat, then all adjoining\n" +
                "cells that are empty, and their immediate neighbors will display\n" +
                "the count of neighboring cells that have scat.\n" +
                "\n Try it!"
        );


    }

    //Can be called to show the board but only expose the things based on exposed 2d array
    public static void showBoard(boolean[][] board, boolean[][] exposed, int[][] counts) {

        System.out.println();
        //Print the first 10 numbers horizontally
        System.out.print(" ");
        for (int i = 0; i < boardSize; i++) {
            System.out.print(i);
        }
        System.out.println();


        //Prints the actual board depending on exposed
        for (int i = 0; i < boardSize; i++) {
            System.out.print(i);

            for (int j = 0; j < boardSize; j++) {


                //if it's empty
                if (exposed[i][j] == false) {
                    System.out.print(".");
                }
                //If it's a scat
                if (exposed[i][j] == true && board[i][j] == true) {
                    gameFail = true;
                    System.out.print("*");

                }

                if (exposed[i][j] == true && board[i][j] == false) {
                    System.out.print(counts[i][j]);
                }


            }
            System.out.print(i);

            System.out.println();
        }


        //Print the bottom 10 numbers horizontally
        System.out.print(" ");
        for (int i = 0; i < boardSize; i++) {
            System.out.print(i);
        }

        if (gameFail == true) showGamefail(board, exposed, counts);
    }

    //Test out some random scat positions
    public static void someScat(boolean[][] board) {

        int maxScat = 8 + rand.nextInt(11);
        for (int i = 0; i < maxScat; i++) {
            int x = (int) (rand.nextInt(10));
            int y = (int) (rand.nextInt(10)); //get two random numbers and use that to create a scat point
            board[x][y] = SCAT; //run loop ten times to place ten poops
        }


    }


    //It counts the nearest Scat, code helped by alex
    public static int findNeighbor(int[][] counts, boolean[][] board) {
        int total = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int x = i - 1;
                int y = j;
                if ((x >= 0) && (y >= 0) && (y < 10) && (x < 10) && board[x][y] == true) {
                    total++;
                }
                x = i - 1;
                y = j + 1;
                if ((x >= 0) && (y >= 0) && (y < 10) && (x < 10) && board[x][y] == true) {
                    total++;
                }
                x = i - 1;
                y = j - 1;
                if ((x >= 0) && (y >= 0) && (y < 10) && (x < 10) && board[x][y] == true) {
                    total++;
                }
                x = i;
                y = j + 1;
                if ((x >= 0) && (y >= 0) && (y < 10) && (x < 10) && board[x][y] == true) {
                    total++;
                }
                x = i;
                y = j - 1;
                if ((x >= 0) && (y >= 0) && (y < 10) && (x < 10) && board[x][y] == true) {
                    total++;
                }
                x = i + 1;
                y = j;
                if ((x >= 0) && (y >= 0) && (y < 10) && (x < 10) && board[x][y] == true) {
                    total++;
                }
                x = i + 1;
                y = j + 1;
                if ((x >= 0) && (y >= 0) && (y < 10) && (x < 10) && board[x][y] == true) {
                    total++;
                }
                x = i + 1;
                y = j - 1;
                if ((x >= 0) && (y >= 0) && (y < 10) && (x < 10) && board[x][y] == true) {
                    total++;
                }
                x = i;
                y = j;
                if ((x >= 0) && (y >= 0) && (y < 10) && (x < 10) && board[x][y] == true) {
                    total++;
                }
                counts[i][j] = total;
                total = 0;
            }
        }
        return total;
    }

    //Just show the message that you have failed
    public static void showGamefail(boolean[][] board, boolean[][] exposed, int[][] counts) {
        System.out.println();
        System.out.println("Eeewww! You stepped in it!");
        showfullBoard(board, exposed, counts);


        System.exit(0);

    }


    //What happens if they win
    public static void showGamewin(boolean[][] exposed, boolean[][] board) {
        int counterboard = 0;
        int counterexposed = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == true) {
                    counterboard++;
                }
            }

        }

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (exposed[i][j] == false) {
                    counterexposed++;
                }
            }

        }

        if (counterboard == counterexposed) gameWin = true;

        if (gameWin) {

            System.out.println("Well done!");
            System.exit(0);
        }


    }

    //Shows the full board regardless of the exposed 2d array
    public static void showfullBoard(boolean[][] board, boolean[][] exposed, int[][] counts) {

        System.out.println();
        //Print the first 10 numbers horizontally
        System.out.print(" ");
        for (int i = 0; i < boardSize; i++) {
            System.out.print(i);
        }
        System.out.println();


        //Prints the actual board depending on exposed
        for (int i = 0; i < boardSize; i++) {
            System.out.print(i);

            for (int j = 0; j < boardSize; j++) {


                //if it's empty

                //If it's a scat
                if (board[i][j] == true) {
                    gameFail = true;
                    System.out.print("*");

                }

                if (board[i][j] == false) {
                    System.out.print(counts[i][j]);
                }


            }
            System.out.print(i);

            System.out.println();
        }


    }

}