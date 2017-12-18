import problems.*;
import algorithm.*;

public class MainDriver {

    public static void main(String[] args) {
        /*
        NQueenProblem Q = new NQueenProblem(8);
        HillClimbing HC = new HillClimbing();
        HC.solve(Q, HillClimbingStrategy.FIRST_CHOICE,false,5);
        */

        /*
        NQueenProblem Q = new NQueenProblem(8);
        SimulatedAnnealing SA = new SimulatedAnnealing();
        SA.solve(Q,SimulatedAnnealingStrategy.LINEAR_TEMPERATURE,false,100000);
        */

        int[][] graph = {{0,1,1,0,0,0},
                         {1,0,1,0,0,0},
                         {1,1,0,0,0,0},
                         {0,0,0,0,1,1},
                         {0,0,0,1,0,1},
                         {0,0,0,1,1,0}};
        GraphClusteringProblem GC = new GraphClusteringProblem(graph,6);
        SimulatedAnnealing SA = new SimulatedAnnealing();
        SA.solve(GC,SimulatedAnnealingStrategy.LINEAR_TEMPERATURE,false);
    }

}
