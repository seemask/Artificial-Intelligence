import java.util.Random;
import java.util.ArrayList;

public class MazeRL extends Maze {

    // Inner class to hold a row,column pair
    public class Location {
        int row;
        int col;
        public Location(int r, int c) {
            row = r;
            col = c;
        }
    }

    // Instance Variables
    double[][][] Q;
    double epsilon = 1.0;
    double gamma = 0.9;
    Random random;
    ArrayList<Integer> validMoves;
    int[] trialLengths;
    boolean drawBug = true;

    // Constructor
    public MazeRL() {
        super();  // call Maze constructor
        random = new Random();
        validMoves = new ArrayList<Integer>();
    }

    // Initialize from file by first calling Maze.initializeFromFile,
    // then initialize Q values and remove 'S' from map.
    public void initializeFromFile(String filename) {
        super.initializeFromFile(filename);
        Q = new double[nrows][ncols][4];
        for (int r = 0; r < nrows; r++)
            for (int c = 0; c < ncols; c++) {
                // remove S
                if (map[r][c] == 'S')
                    map[r][c] = ' ';
                for (int a = 0; a < 4; a++)
                    Q[r][c][a] = 0;
            }
    }

    // Apply move to location r,c.
    public Location nextPosition(int r, int c, int move) {
        switch (move) {
            case 0:
                return new Location(r-1,c);
            case 1:
                return new Location(r,c+1);
            case 2:
                return new Location(r+1,c);
            case 3:
                return new Location(r,c-1);
        }
        return null;
    }

    // Find all valid moves from r,c
    public void findValidMoves(int r, int c) {
        validMoves.clear();
        for (int m = 0; m < 4; m++) {
            Location loc = nextPosition(r,c,m);
            if (validMove(loc.row,loc.col))
                validMoves.add(m);
        }
    }

    // Pick next move from row,col either randomly or by picking greedy action.
    // Called epsilon-greedy strategy.
    public int pickNextMove(int row, int col) {
        findValidMoves(row,col);
        if (random.nextDouble() < epsilon) {
            // Random move
            return validMoves.get(random.nextInt(validMoves.size()));
        } else {
            // Greedy move
            return bestMove(row,col);
        }
    }

    // Return best move from r,c, by picking move with highest Q value.
    // Assumes findValidMoves already called
    public int bestMove(int r, int c) {
        double maxQ = -1;
        int maxQmove = -1;
        for (int move : validMoves) {
            if (Q[r][c][move] > maxQ) {
                maxQ = Q[r][c][move];
                maxQmove = move;
            }
        }
        return maxQmove;
    }

    // Do nTrials trials, and update Q values each step.
    public void train(int nTrials, double epsilonDecay, double learningRate) {
        trialLengths = new int[nTrials];
        for (int trial = 0; trial < nTrials; trial++) {

            epsilon = epsilon * epsilonDecay;

            int row = 0;
            int col = 0;
            int move = 0;
            int oldRow = 0;
            int oldCol = 0;
            int oldMove = 0;
            int reinforcement = 0;

            // Random start position.  Assumes Goal is reachable.
            while (map[row][col] != ' ') {
                row = random.nextInt(nrows);
                col = random.nextInt(ncols);
            }

            int step = 0;
            while (reinforcement < 1) {

                if (step > 5000)
                    break;

                step++;
                //for (step = 0; step < maxSteps && reinforcement != 1; step++) {

                move = pickNextMove(row,col);

                // Update, but only if not very first step
                if (step > 0) {
                    // Update Q
                    double TDerror = 0;
                    if (map[row][col] == 'G') {
                        // Goal found!
                        reinforcement = 1;
                        TDerror = (reinforcement - Q[oldRow][oldCol][oldMove]);
                    } else {
                        // Goal not found.
                        reinforcement = 0;
                        TDerror = reinforcement + gamma * Q[row][col][move]- Q[oldRow][oldCol][oldMove];
                    }
                    Q[oldRow][oldCol][oldMove] += learningRate * TDerror;
                }
                // Advance time by one step
                oldRow = row;
                oldCol = col;
                oldMove = move;

                //Make move
                Location loc = nextPosition(row,col,move);
                row = loc.row;
                col = loc.col;

                if (drawBug) {
                    String mapstr = "";
                    for (int r = 0; r < nrows; r++) {
                        for (int c = 0; c < ncols; c++) {
                            if (r == row && c == col)
                                mapstr += "*";
                            else
                                mapstr += map[r][c];
                        }
                        mapstr += "\n";
                    }
                    final String ANSI_CLS = "\u001b[2J";
                    final String ANSI_HOME = "\u001b[H";
                    System.out.print(ANSI_CLS + ANSI_HOME);
                    System.out.println(mapstr);
                    try {
                        Thread.sleep(20);
                    } catch (Exception e) {
                    }
                }
            } // step loop
            trialLengths[trial] = step;

        } //trial loop
    }

    public String toString() {
        // maze with optimal move at each location.
        String string = "Trial lengths (steps to reach goal):\n";
        for (int i = 0; i < trialLengths.length; i++) {
            string += String.format("%4d",trialLengths[i]);
        }
        string += "\nOptimal moves:\n";
        for (int r = 0; r < nrows; r++) {
            for (int c = 0; c < ncols; c++) {
                char chr = map[r][c];
                if (chr == ' ') {
                    findValidMoves(r,c);
                    switch(bestMove(r,c)) {
                        case 0:
                            chr = '^';
                            break;
                        case 1:
                            chr = '>';
                            break;
                        case 2:
                            chr = 'V';
                            break;
                        case 3:
                            chr = '<';
                            break;
                    }
                }
                string += chr;
            }
            string += "\n";
        }
        return string;
    }

    //----------------------------------------------------------------------
    //  Main method
    //----------------------------------------------------------------------

    public static void main(String[] args) {
         if (args.length < 1) {
          System.out.println("Usage: java MazeRL <mazefile> <number of trials> <epsilon decay, 0.99> <learning rate, 0.1>");
            System.exit(1);
        }
        String filename = args[0];
        int nTrials = 30;//Integer.parseInt(args[1]);
        double epsilonDecay =0.99;// Double.parseDouble(args[2]);
        double learningRate =.1;// Double.parseDouble(args[3]);

        MazeRL maze = new MazeRL();
        maze.initializeFromFile(filename);

        if (nTrials > 20)
            maze.drawBug = false;

        maze.train(nTrials,epsilonDecay,learningRate );
        System.out.println(maze);
    }
}