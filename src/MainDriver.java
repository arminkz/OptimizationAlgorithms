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
        SimulatedAnnealing SA = new SimulatedAnnealing(0.03);
        SA.solve(GC,SimulatedAnnealingStrategy.EXPOTENTIAL,false);
        System.out.println(SA.finalState);

        /*
        MathematicalEqualityProblem ME = new MathematicalEqualityProblem(1,2,3,4,30,30);
        GeneticAlghorithm GA = new GeneticAlghorithm(20,0.2,0.1);
        GA.solve(ME,10000);
        System.out.println(GA.finalState.toString());
        System.out.println("Fitness : " + ME.fitness(GA.finalState));
        */
    }

}
