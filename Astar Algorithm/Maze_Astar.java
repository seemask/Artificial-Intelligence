

import org.w3c.dom.Node;

import java.util.*;




public class Maze{


    private int[][] maze;
    private Node[][] prev;

    private int sizeX;
    private int sizeY;
    private Node lastNode;
  //Hash Map used as a data structure to store Node and Pathcost
    HashMap<Nodeee, Integer> openList = new HashMap<Nodeee, Integer>();
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
//Implementation of A Star
    public void solveAstar(Nodeee nodeee){

        int pathCost=0, calPath =0;
        HashSet<Nodeee> closed = new HashSet<>();
        HashSet<Node> traversed = new HashSet<>();////Traversed Hast list keeps track of all the Neighbour Node
        int pathCount=0;
        openList.put(nodeee,pathCost);
        traversed.add(nodeee);
        if(maze[((Nodeee) nodeee).getX()][((Nodeee) nodeee).getY()]!=-1&&maze[((Nodeee) nodeee).getX()][((Nodeee) nodeee).getY()]!=100) {
            maze[((Nodeee) nodeee).getX()][((Nodeee) nodeee).getY()] = pathCount;

        }


        while(!openList.isEmpty()) {

            openList.remove(nodeee);
            closed.add(nodeee);
            for(Node node : this.getAdjacentEdges(nodeee)) {//Fetching all Neighbouring Nodes

                if (!closed.contains(node)) {
                    //calculating f(n);

                    int calcg=0;
                    int calch=0;
                    Nodeee start=new Nodeee(0, 1);
                    Nodeee dest=new  Nodeee(3, 4);
                    calcg=Math.abs(((Nodeee) node).getX()- start.getX())*3+
                            Math.abs(((Nodeee) node).getY()- start.getY())*2;
                    if(((Nodeee) node).getX()>((Nodeee) dest).getX())
                    {
                        calch= Math.abs(((Nodeee) node).getX() - dest.getX()) +// Considering Windy Condition with travel to North
                                Math.abs(((Nodeee) node).getY()- dest.getY())*2;
                    }
                    else
                    {
                        calch=Math.abs(((Nodeee) node).getX()- dest.getX())*3+// Considering Windy Condition with travel to South
                                Math.abs(((Nodeee) node).getY()- dest.getY())*2;
                    }

                    openList.put((Nodeee) node,calcg+calch);

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
            int minVal =  Collections.min(openList.values());//PathCost
            int tmpPath=0;
            //Comparing here if two Nodes have same pathcost, if yes select with first visited node
            for(Map.Entry<Nodeee,Integer> m :openList.entrySet()){
                Nodeee key= m.getKey();

                if(minVal==m.getValue()){

                    if (tmpPath==0)
                    {
                        tmpPath = maze[key.getX()][key.getY()];
                        nodeee = m.getKey();
                    }
                    else if(tmpPath>maze[key.getX()][key.getY()]) {
                        tmpPath = maze[key.getX()][key.getY()];
                        nodeee = m.getKey();

                    }

                }
            }

        }
    }



    private List<Node> getAdjacentEdges(Nodeee tmp) {
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
                {{-3,0,-3,-3,-3,-3,-3},
                        {-3,-3,-1,-1,-1,-1,-3},
                        {-3,-3,-1,-3,-3,-1,-3},
                        {-3,-1,-1,-3,100,-1,-3},
                        {-3,-3,-3,-3,-1,-1,-3},
                        {-3,-3,-3,-3,-3,-3,-3},
                };
        Maze m = new Maze(maze,6,7);
        m.solveAstar (new Nodeee(0, 1));
        //Prints  Maze
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
                    System.out.print(" " +String.format("%02d",maze[i][j]) + " ");
                }
            }
            System.out.println();
        }
    }
}
//}
