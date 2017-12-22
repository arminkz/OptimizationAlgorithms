package algorithm;
//Hill Climbing Algorithm
//Created by Armin

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealing {

    //Cooling rate
    double coolingRate = 0.003;

    public SimulatedAnnealing(double coolingRate){
        solution = new ArrayList<>();
        finalState = null;
        this.coolingRate = coolingRate;
    }

    private ArrayList<Action> solution;
    public State finalState;

    public void solve(OptimizationProblem op, SimulatedAnnealingStrategy strategy , boolean maximize){

        State currentState = op.initialState();

        //Set initial temp
        double temp = 100000;

        while(temp > 1){

            //Get Neighbours
            ArrayList<Pair<State,Action>> neighbours = new ArrayList<>();
            for(Action act : op.actions(currentState)){
                for(State target : op.result(currentState,act)) {
                    neighbours.add(new Pair(target,act));
                }
            }

            int curval = op.eval(currentState);
            //boolean isFound = false;

            Random rnd = new Random();
            int index = rnd.nextInt(neighbours.size());
            Pair<State,Action> psa = neighbours.get(index);

            int tval = op.eval(psa.getKey());
            double p = acceptanceProbability(curval,tval,temp,maximize);
            double d = rnd.nextDouble();
            //System.out.println(p + " ! " + d);
            if(p > d){
                currentState = psa.getKey();
                solution.add(psa.getValue());
                System.out.println("[SA] Eval : " + tval);
            }

            if(strategy == SimulatedAnnealingStrategy.EXPOTENTIAL) {

                //cool system
                temp *= 1 - coolingRate;

            }else if(strategy == SimulatedAnnealingStrategy.LINEAR_TEMPERATURE) {

                temp -= coolingRate;

            }else if(strategy == SimulatedAnnealingStrategy.RANDOM_REDUCE){

                temp -= rnd.nextDouble() * coolingRate;

            }else{

                System.err.println("Invalid Strategy !");
                return;
            }

            finalState = currentState;

        }

    }

    private static double acceptanceProbability(int currentDistance, int newDistance, double temperature , boolean maximize) {
        if(!maximize){
            // If the new solution is better, accept it
            if (newDistance < currentDistance) {
                return 1.0;
            }
            // If the new solution is worse, calculate an acceptance probability
            return Math.exp((currentDistance - newDistance) / temperature);
        }else{
            // If the new solution is better, accept it
            if (newDistance > currentDistance) {
                return 1.0;
            }
            // If the new solution is worse, calculate an acceptance probability
            return Math.exp((newDistance - currentDistance) / temperature);
        }
    }

}
