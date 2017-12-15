package algorithm;
//Hill Climbing Algorithm
//Created by Armin

import java.util.ArrayList;

public class HillClimbing {

    public HillClimbing(){
        solution = new ArrayList<>();
        finalState = null;
    }

    private ArrayList<Action> solution;
    public ArrayList<Action> getSolution(){
        return solution;
    }

    private State finalState;
    public State getFinalState(){
        return finalState;
    }


    public void solve(OptimizationProblem op,boolean maximize){

        State currentState = op.initialState();

        while(true){

            State bestNode = null ;
            int bestVal = Integer.MIN_VALUE;
            Action bestAction = null;

            for(Action act : op.actions(currentState)){
                for(State target : op.result(currentState,act)) {
                    int tval = op.eval(target);
                    if ((maximize && tval > bestVal) || (!maximize && tval < bestVal) || bestNode == null){
                        bestNode = target;
                        bestVal = tval;
                        bestAction = act;
                    }
                }
            }

            if(bestNode == null){
                //no better node exists final node is current node
                finalState = currentState;
                return;
            }

            currentState = bestNode;
            solution.add(bestAction);

        }

    }

}
