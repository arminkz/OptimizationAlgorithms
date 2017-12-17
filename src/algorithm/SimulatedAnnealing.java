package algorithm;
//Hill Climbing Algorithm
//Created by Armin

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealing {

    public SimulatedAnnealing(){
        solution = new ArrayList<>();
        finalState = null;
    }

    private ArrayList<Action> solution;
    private State finalState;

    private void solve(OptimizationProblem op, SimulatedAnnealingStrategy strategy , boolean maximize , int kmax){

        State currentState = op.initialState();
        int k=0;

        while(k<kmax){

            //Get Neighbours
            ArrayList<Pair<State,Action>> neighbours = new ArrayList<>();
            for(Action act : op.actions(currentState)){
                for(State target : op.result(currentState,act)) {
                    neighbours.add(new Pair(target,act));
                }
            }

            if(strategy == SimulatedAnnealingStrategy.LINEAR_TEMPERATURE){

                int curval = op.eval(currentState);
                //boolean isFound = false;

                int temp = 100 - ((k / kmax) * 100);

                Random rnd = new Random();
                int index = rnd.nextInt(neighbours.size());
                Pair<State,Action> psa = neighbours.get(index);

                int tval = op.eval(psa.getKey());
                if ((maximize && tval > curval) || (!maximize && tval < curval)){
                    //Choose Better State
                    currentState = psa.getKey();
                    solution.add(psa.getValue());
                    System.out.println("[HC] Eval : " + tval);
                    //isFound = true;
                    break;
                }else{
                    //go to state
                    int p = rnd.nextInt(100);
                    if(temp > p){
                        currentState = psa.getKey();
                        solution.add(psa.getValue());
                        System.out.println("[HC] Eval : " + tval + " (Jump)");
                    }else{
                        //do nothing
                    }
                }

                k++;

            }else{

                System.err.println("Invalid Strategy !");

            }


        }

    }

}
