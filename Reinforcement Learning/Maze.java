// Example use:
//   javac Maze.jva
//   java Maze mazegrader1 -debug
//   java Maze mazegrader2

import java.io.File;
import java.util.Scanner;

public class Maze {

    public static boolean debug = false;
    public char[][] map;
    public char[][] map_original;
    public int nrows;
    public int ncols;
    public int rStart;
    public int cStart;

    public void initializeFromFile(String filename) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filename));
        } catch (Exception e) {
            System.out.println("File " + filename + " not found.");
            System.exit(1);
        }
        nrows = scanner.nextInt();
        System.out.println("rows "+nrows);
        ncols = scanner.nextInt();
        System.out.println("Columns "+ncols);
        map = new char[nrows][];
        int rowi = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (line.length() > 0) {
                map[rowi++] = line.toCharArray();
            }
        }
        scanner.close();
        // Find S and G
        for (int r = 0; r < nrows; r++)
            for (int c = 0; c < ncols; c++)
                if (map[r][c] == 'S') {
                    rStart = r;
                    cStart = c;
                }
    }

    public String findPath() {
        String path = findPathFrom(rStart,cStart,"|");
        map[rStart][cStart] = 'S';
        return path;
    }

    public boolean validMove(int r, int c) {
        if (r < 0 || r >= nrows || c < 0 || c >= ncols)
            return false;
        char charHere = map[r][c];
        return charHere == ' ' || charHere == 'G';
    }

    public String findPathFrom(int r, int c, String indent) {

        if (r < 0 || r >= nrows || c < 0 || c >= ncols) {
            if (debug) System.out.println(indent + " Failed. Out of bounds.");
            return "";
        }

        char charHere = map[r][c];
        if (charHere == '#' || charHere == '.') {
            if (debug) System.out.println(indent + " Failed. Found " + charHere);
            return "";
        }

        if (charHere == 'G') {
            if (debug) System.out.println(indent + " YIPPEE. Found G");
            return "G";
        }

        map[r][c] = '.';
        if (debug)
            printMap(indent);

        if (debug) System.out.println(indent + " Trying Up");
        String restOfPath = findPathFrom(r-1,c," " + indent);
        if (restOfPath.length() > 0) {
            if (debug) System.out.println(indent + " Up succeeded. Returning " + "U" + restOfPath);
            return "U" + restOfPath;
        }

        if (debug) System.out.println(indent + " Trying Right");
        restOfPath = findPathFrom(r,c+1, " " + indent);
        if (restOfPath.length() > 0) {
            if (debug) System.out.println(indent + " Right succeeded. Returning " + "R" + restOfPath);
            return "R" + restOfPath;
        }

        if (debug) System.out.println(indent + " Trying Down");
        restOfPath = findPathFrom(r+1,c, " " + indent);
        if (restOfPath.length() > 0) {
            if (debug) System.out.println(indent + " Down succeeded. Returning " + "D" + restOfPath);
            return "D" + restOfPath;
        }

        if (debug) System.out.println(indent + " Trying Left");
        restOfPath = findPathFrom(r,c-1, " " + indent);
        if (restOfPath.length() > 0) {
            if (debug) System.out.println(indent + " Left succeeded. Returning " + "L" + restOfPath);
            return "L" + restOfPath;
        }
        map[r][c] = ' ';
        return "";
    }

    // Only used for debugging
    public void printMap(String indent) {
        for (int r = 0; r < nrows; r++) {
            System.out.print(indent + " ");
            for (int c = 0; c < ncols; c++)
                System.out.print(map[r][c]);
            System.out.println();
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (int r = 0; r < nrows; r++) {
            for (int c = 0; c < ncols; c++)
                result += map[r][c];
            result += "\n";
        }
        return result;
    }

    public static void main(String[] args) {
        String filename = args[0];
        if (args.length > 1 && args[1].equals("-debug"))
            debug = true;

        Maze maze = new Maze();

        maze.initializeFromFile(filename);
        System.out.println(maze);
        String path = maze.findPath();
        System.out.println(maze);
        System.out.println(path);
    }

}