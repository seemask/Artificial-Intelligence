    import org.w3c.dom.Node;

    import java.text.DecimalFormat;
    import java.util.ArrayList;
    import java.util.List;

    public class MazeHMM {

        private double[][] maze;
        private int sizeX;
        private int sizeY;
        MazeHMM(double[][] maze, int sizeX, int sizeY) {
            this.maze = maze;
            this.sizeY = sizeY;
            this.sizeX = sizeX;
        }
        //Checking if the X Co-ordinates lie in  the range of the matrix
        private boolean inBoundsX(int number){
            return number >= 0 && number <sizeX;
        }
        //Checking if the Y Co-ordinates lie in  the range of the matrix
        private boolean inBoundsY(int number){
            return number >= 0 && number <sizeY;
        }

        //Using Print_Array Method to print matrix after every filtering or prediction
        public static void print_array(double[][] maze) {
            DecimalFormat df = new DecimalFormat("0.00");

            for (int i = 0; i <8; i++) {
                for (int j = 0; j <9; j++) {
                    if (maze[i][j] != -1) {

                        System.out.print(" " + df.format(maze[i][j]) + "  ");
                    } else if(maze[i][j]==-1){
                        if (i == 0 || j == 0 || i == 7 || j == 8) {
                            System.out.print("  ");
                        }
                        else{
                            System.out.print(" ####  ");
                        }

                    }
                }
                System.out.println(" ");
            }

        }

        public  void filtering( char[] evidence) {
            //Using this method to calculate both Filtering and Conditional probability
            // Initializing Evidence Matrix to -1 as surrounding walls and walls with in the maze and 1 for the open square
            double[][] evidenceMatrix={{-1,-1,-1,-1,-1,-1,-1,-1,-1},
                    {-1,1, 1,1,1,1,1,1,-1},
                    {-1,1, 1, -1, -1, -1, -1, 1,-1},
                    {-1,1, 1, -1,1, 1, -1, 1,-1},
                    {-1,1, -1, -1,1, 1, -1,1,-1},
                    {-1,1, 1, 1, 1, -1, -1, 1,-1},
                    {-1,1, 1, 1, 1,1, 1, 1,-1},
                    {-1,-1,-1,-1,-1,-1,-1,-1,-1},
            };
            double totalProb=0.0;
            NodeMaze NodeMaze=new NodeMaze(0,0);
            List<Character> correctEvidence= new ArrayList<>();
            double obstacleErrorrate=0.85;// Robot can detect the obstacle with 85%
            double opensquareErrorrate=0.9;//Robot can detect the Opensquare with 90%
            double conditonalProbability=1;
            for(int i=0;i<8;i++)
            {


                for(int j=0;j<9;j++)
                {



                    if(maze[i][j]!=-1)// Checking if if it's wall of a maze
                    {
                        //NodeMaze(x:i,y:j);


                        correctEvidence= getAdjacentEdges(new NodeMaze(i,j));// Correct Evidence stores whether it's 'O' for obstacle and 'S' for Open square

                     if(!correctEvidence.isEmpty()) {
                          conditonalProbability=1;
                          for(int k=0;k<4;k++)
                         {
                             switch (correctEvidence.get(k))
                             {
                                 case'O':
                                     if(correctEvidence.get(k)==evidence[k])
                                     {
                                         conditonalProbability=conditonalProbability*obstacleErrorrate;//Calculating conditional probability

                                     }
                                     else

                                     {
                                         //It's an obstacle but got detected as opensquare
                                         conditonalProbability=conditonalProbability*(1-obstacleErrorrate);

                                     }
                                     break;
                                 case'S':

                                     if(correctEvidence.get(k)==evidence[k])
                                     {

                                         conditonalProbability=conditonalProbability*opensquareErrorrate;

                                     }
                                     else

                                     {
                                         //It's an open square but got detected as obstacle
                                         conditonalProbability=conditonalProbability*(1-opensquareErrorrate);

                                     }
                                     break;
                             }

                         }

                        }


                     conditonalProbability=conditonalProbability*maze[i][j];

                        evidenceMatrix[i][j]=conditonalProbability;
                        totalProb=totalProb+evidenceMatrix[i][j];// Sum of all the probabilities of entire maze

                    }


                }
            }


            for(int i=0;i<8;i++)
            {
                for (int j=0;j<9;j++)
                {

                    if(maze[i][j]!=-1) {
                        maze[i][j] =(evidenceMatrix[i][j] / totalProb)*100;//For filtering process, calculating each filtering values after normalizing
                    }
                }
            }


        }
        public void prediction(char dir){
            NodeMaze NodeMaze=new NodeMaze(0,0);
            List<Character> directions= new ArrayList<>();



                    switch(dir)
                    {
                        case 'N'://if Robot moves in North Direction
                            double[][] transition_MatrixN={{-1,-1,-1,-1,-1,-1,-1,-1,-1}, //initializing transition matrix obstacles by -1 and walls by 1
                                    {-1,1, 1,1,1,1,1,1,-1},
                                    {-1,1, 1, -1, -1, -1, -1, 1,-1},
                                    {-1,1, 1, -1,1, 1, -1, 1,-1},
                                    {-1,1, -1, -1,1, 1, -1,1,-1},
                                    {-1,1, 1, 1, 1, -1, -1, 1,-1},
                                    {-1,1, 1, 1, 1,1, 1, 1,-1},
                                    {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            };
                        for (int i =0;i<8;i++) {
                            for (int j = 0; j < 9; j++) {
                                double transitionProb = 0.0;

                                    if (maze[i][j] != -1) {
                                        directions = getAdjacentEdges(new NodeMaze(i, j));// gets the surrounding of each square

                                        if (directions.get(0) == 'O') {
                                            transitionProb = transitionProb + maze[i][j] * 0.1;
                                        } else {
                                            transitionProb = transitionProb + maze[i][j - 1] * 0.1;
                                        }
                                        if (directions.get(1) == 'O') {
                                            transitionProb = transitionProb + maze[i][j] * 0.8;
                                        }
                                        if (directions.get(2) == 'O') {
                                            transitionProb = transitionProb + maze[i][j] * 0.1;
                                        } else {
                                            transitionProb = transitionProb + maze[i][j + 1] * 0.1;
                                        }
                                        if (directions.get(3) != 'O') {
                                            transitionProb = transitionProb + maze[i + 1][j] * 0.8;
                                        }
                                        transition_MatrixN[i][j] = transitionProb;
                                    }
                            }
                        }
                        for(int i=0;i<8;i++)
                        {
                            for(int j=0;j<9;j++){
                                maze[i][j]=transition_MatrixN[i][j];
                            }
                        }

                            break;
                        case'E':// When Robot moves in East direction

                            double[][] transition_MatrixE={{-1,-1,-1,-1,-1,-1,-1,-1,-1},
                                    {-1,1, 1,1,1,1,1,1,-1},
                                    {-1,1, 1, -1, -1, -1, -1, 1,-1},
                                    {-1,1, 1, -1,1, 1, -1, 1,-1},
                                    {-1,1, -1, -1,1, 1, -1,1,-1},
                                    {-1,1, 1, 1, 1, -1, -1, 1,-1},
                                    {-1,1, 1, 1, 1,1, 1, 1,-1},
                                    {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            };
                            for (int i =0;i<8;i++) {

                                for (int j = 0; j < 9; j++) {
                                    double transitionProb = 0.0;

                            if (maze[i][j] != -1) {
                                directions = getAdjacentEdges(new NodeMaze(i, j));


                                if(directions.get(0)!='O')
                                {
                                    transitionProb=transitionProb+maze[i][j-1]*0.8;

                                }

                                if (directions.get(1) == 'O') {

                                    transitionProb = transitionProb + maze[i][j] * 0.1;

                                } else {
                                    transitionProb = transitionProb + maze[i-1][j] * 0.1;

                                }
                                if (directions.get(2) == 'O') {

                                    transitionProb = transitionProb + maze[i][j] * 0.8;

                                }

                                if(directions.get(3)=='O')
                                {
                                    transitionProb=transitionProb+maze[i][j]*0.1;

                                }
                                else
                                {
                                    transitionProb=transitionProb+maze[i+1][j]*0.1;

                                }

                                transition_MatrixE[i][j]=transitionProb;
                            }

                    }


                }
                            for(int i=0;i<8;i++)
                            {
                                for(int j=0;j<9;j++){
                                    maze[i][j]=transition_MatrixE[i][j];
                                }
                            }
                            break;
            }
        }
        private List<Character> getAdjacentEdges(NodeMaze tmp) {
            List<Character> neighboursEvidence= new ArrayList<>();

            //Finds West neighbour of the particular node
            if(this.inBoundsY(((NodeMaze) tmp).getY()-1)){
                if(this.maze[((NodeMaze) tmp).getX()][((NodeMaze) tmp).getY()-1] != -1){//on West, if it's equal to -1 returns as O other wise S
                    neighboursEvidence.add('S');


                }
                else
                {
                    neighboursEvidence.add('O');


                }
            }
            //Finds North  neighbour of the particular node
            if(this.inBoundsX(((NodeMaze) tmp).getX()-1)){ ////on North, if it's equal to -1 returns as O other wise S
                if(this.maze[((NodeMaze) tmp).getX()-1][((NodeMaze) tmp).getY()] != -1){
                    neighboursEvidence.add('S');


                }
                else
                {
                    neighboursEvidence.add('O');


                }
            }
            //Finds East neighbour of the particular node
            if(this.inBoundsY(((NodeMaze) tmp).getY()+1)){////on East, if it's equal to -1 returns as O other wise S
                if(this.maze[((NodeMaze) tmp).getX()][((NodeMaze) tmp).getY()+1] != -1){
                    neighboursEvidence.add('S');


                }
                else
                {
                    neighboursEvidence.add('O');


                }
            }
            //Finds South neighbour of the particular node
            if(this.inBoundsX(((NodeMaze) tmp).getX()+1)){//on South, if it's equal to -1 returns as O other wise S
                if(this.maze[((NodeMaze) tmp).getX()+1][((NodeMaze) tmp).getY()] != -1){
                    neighboursEvidence.add('S');


                }
                else
                {
                    neighboursEvidence.add('O');


                }
            }





            return neighboursEvidence;
        }


        public static void main(String[] args) {
            double[][] maze = {{-1,-1,-1,-1,-1,-1,-1,-1,-1},
                    {-1,3.23, 3.23,3.23,3.23,3.23,3.23,3.23,-1},
                    {-1,3.23, 3.23, -1, -1, -1, -1, 3.23,-1},
                    {-1,3.23, 3.23, -1,3.23, 3.23, -1, 3.23,-1},
                    {-1,3.23, -1, -1,3.23, 3.23, -1,3.23,-1},
                    {-1,3.23, 3.23, 3.23, 3.23, -1, -1, 3.23,-1},
                    {-1,3.23, 3.23, 3.23, 3.23,3.23, 3.23, 3.23,-1},
                    {-1,-1,-1,-1,-1,-1,-1,-1,-1},
            };
            MazeHMM m=new MazeHMM(maze,8,9);
            char[] evidence1= {'S', 'S', 'S', 'O'};
            char[] evidence2={'S','O','S','S'};
            char[] evidence3={'S','O','S','S'};
            char[] evidence4={'S','S','O','S'};
            System.out.println("Intial Location Probabilities");
           print_array(maze);
           System.out.println("Filtering after Evidence[-,-,-,O]");
            m.filtering(evidence1);
            print_array(maze);
            System.out.println("Prediction after N");
            m.prediction('N');
            print_array(maze);
            System.out.println("Filtering after Evidence[-,O,-,-]");
            m.filtering(evidence2);
            print_array(maze);
            System.out.println("Prediction after E");
           m.prediction('E');
           print_array(maze);
            System.out.println("Filtering after Evidence[-,O,-,-]");
            m.filtering(evidence3);
            print_array(maze);
            System.out.println("Prediction after E");
          m.prediction('E');
           print_array(maze);
            System.out.println("Filtering after Evidence[-,-,O,-]");
           m.filtering(evidence4);
            print_array(maze);

        }
    }


