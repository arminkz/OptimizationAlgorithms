package algorithm;
//Hill Climbing Algorithm
//Created by Armin

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Random;

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


    public void solve(OptimizationProblem op, HillClimbingSterategy strategy ,boolean maximize){

        State currentState = op.initialState();

        while(true){

            //Get Neighbours
            ArrayList<Pair<State,Action>> neighbours = new ArrayList<>();
            for(Action act : op.actions(currentState)){
                for(State target : op.result(currentState,act)) {
                    neighbours.add(new Pair(target,act));
                }
            }

            if(strategy == HillClimbingSterategy.SIMPLE){

                State bestNode = null ;
                int bestVal = Integer.MIN_VALUE;
                Action bestAction = null;

                for(Pair<State,Action> psa : neighbours){
                    int tval = op.eval(psa.getKey());
                    if ((maximize && tval > bestVal) || (!maximize && tval < bestVal) || bestNode == null){
                        bestNode = psa.getKey();
                        bestVal = tval;
                        bestAction = psa.getValue();
                    }
                }

                if(bestNode == null){
                    //no better node exists final node is current node
                    finalState = currentState;
                    return;
                }

                currentState = bestNode;
                solution.add(bestAction);

            }else if(strategy == HillClimbingSterategy.FIRST_CHOICE){

                int curval = op.eval(currentState);
                boolean isFound = false;

                Random rnd = new Random();
                while(neighbours.size() > 0){
                    int index = rnd.nextInt(neighbours.size());
                    Pair<State,Action> psa = neighbours.get(index);

                    int tval = op.eval(psa.getKey());
                    if (tval > curval){
                        //Choose First Better State
                        currentState = psa.getKey();
                        solution.add(psa.getValue());
                        isFound = true;
                        break;
                    }

                    //remove visited
                    neighbours.remove(index);
                }

                if(!isFound){
                    //No Better State Found Declare as final
                    finalState = currentState;
                    return;
                }

            }else if(strategy == HillClimbingSterategy.STOCHASTIC){

                ArrayList<Pair<State,Action>> betterStates = new ArrayList<>();

                int curval = op.eval(currentState);
                boolean isFound = false;

                for(Pair<State,Action> psa : neighbours){
                    int tval = op.eval(psa.getKey());
                    if (tval > curval){
                        //Add Better State to Array
                        betterStates.add(psa);
                        isFound = true;
                        break;
                    }
                }

                if(!isFound){
                    //No Better State Found Declare as final
                    finalState = currentState;
                    return;
                }else{
                    Random rnd = new Random();
                    int index = rnd.nextInt(betterStates.size());
                    Pair<State,Action> s = betterStates.get(index);
                    currentState = s.getKey();
                    solution.add(s.getValue());
                }

            }else{

                System.err.println("Invalid Strategy !");

            }


        }

    }

}
