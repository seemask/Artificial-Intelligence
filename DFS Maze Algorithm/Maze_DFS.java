

import org.w3c.dom.Node;

import java.util.*;




public class Maze{


    private int[][] maze;
    private Node[][] prev;

    private int sizeX;
    private int sizeY;
    private Node lastNode;
   //Intializing Maze
    Maze(int[][] maze, int sizeX, int sizeY) {
        this.maze = maze;
        this.sizeY = sizeY;
        this.sizeX = sizeX;
    }
    //Checking for each Co-ordinates that's not out of range
    private boolean inBoundsX(int number){
        return number >= 0 && number <sizeX;
    }

    private boolean inBoundsY(int number){
        return number >= 0 && number <sizeY;
    }
//Implementation of DFS Algorithm
    public void solveDFS(Nodeee nodeee){
        Stack<Node> stack = new Stack<>();
        HashSet<Node> visited = new HashSet<>();
        HashSet<Node> traversed = new HashSet<>();
        int pathCount=0;


            stack.push(nodeee);
            traversed.add(nodeee);//Traversed Hast list keeps track of all the Neighbour Node we visited

        if(maze[((Nodeee) nodeee).getX()][((Nodeee) nodeee).getY()]!=-1&&maze[((Nodeee) nodeee).getX()][((Nodeee) nodeee).getY()]!=100) {
            maze[((Nodeee) nodeee).getX()][((Nodeee) nodeee).getY()] = pathCount;

        }

        while(!stack.isEmpty()) {
            Node tmp = stack.pop();
            visited.add(tmp);
            for(Node node : this.getAdjacentEdges(tmp)) {// Fetching all Neighbouring Nodes
                if (!visited.contains(node)) {
                    stack.push(node);



                        if(!traversed.contains(node)) {
                          if (maze[((Nodeee) node).getX()][((Nodeee) node).getY()] == 100) {
                              //Assigned 100 to destination, return if found
                              pathCount = pathCount + 1;
                              maze[((Nodeee) node).getX()][((Nodeee) node).getY()] = pathCount;
                               return;
                          }
                                pathCount = pathCount + 1;
                                maze[((Nodeee) node).getX()][((Nodeee) node).getY()] = pathCount;
                                traversed.add(node);

                        }

                }
            }
        }
    }



    private List<Node> getAdjacentEdges(Node tmp) {
        List<Node> neighbours = new ArrayList<Node>();

        //Finds West neighbour of the particular node
        if(this.inBoundsY(((Nodeee) tmp).getY()-1)){
            if(this.maze[((Nodeee) tmp).getX()][((Nodeee) tmp).getY()-1] != -1){
                neighbours.add(new Nodeee(((Nodeee) tmp).getX(), ((Nodeee) tmp).getY()-1));

            }
        }
        //Finds North  neighbour of the particular node
        if(this.inBoundsX(((Nodeee) tmp).getX()-1)){
            if(this.maze[((Nodeee) tmp).getX()-1][((Nodeee) tmp).getY()] != -1){
                neighbours.add(new Nodeee(((Nodeee) tmp).getX()-1, ((Nodeee) tmp).getY()));

            }
        }
        //Finds East neighbour of the particular node
        if(this.inBoundsY(((Nodeee) tmp).getY()+1)){
                if(this.maze[((Nodeee) tmp).getX()][((Nodeee) tmp).getY()+1] != -1){
                    neighbours.add(new Nodeee(((Nodeee) tmp).getX(), ((Nodeee) tmp).getY()+1));

                }
            }
        //Finds South neighbour of the particular node
        if(this.inBoundsX(((Nodeee) tmp).getX()+1)){
            if(this.maze[((Nodeee) tmp).getX()+1][((Nodeee) tmp).getY()] != -1){
                neighbours.add(new Nodeee(((Nodeee) tmp).getX()+1, ((Nodeee) tmp).getY()));

            }
        }






        return neighbours;
    }


    public static void main(String args[]){
         //Intializing a Maze 0 as "Start", 100 as "Destination" , -3 as Path and -1 as Wall
        int [][] maze =
                {
                        {-3,0,-3,-3,-3,-3,-3},
                        {-3,-3,-1,-1,-1,-1,-3},
                        {-3,-3,-1,-3,-3,-1,-3},
                        {-3,-1,-1,-3,100,-1,-3},
                        {-3,-3,-3,-3,-1,-1,-3},
                        {-3,-3,-3,-3,-3,-3,-3},

                };

        Maze m = new Maze(maze,6,7);


        m.solveDFS (new Nodeee(0, 1));

//Prints Maze
        for(int i = 0; i < maze.length; i++){
            for(int j = 0; j < maze[i].length; j++){
                if(maze[i][j]==-1) {
                    System.out.print(" " + "##" + " ");
                }
                else if (maze[i][j]==-3)
                {
                    System.out.print("    ");
                }
                else {
                    System.out.print(" " + String.format("%02d",maze[i][j])+" ");
                }
            }
            System.out.println();
        }
    }
}
//}
