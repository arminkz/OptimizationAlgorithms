package algorithm;
//Hill Climbing Algorithm
//Created by Armin

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Random;

public class HillClimbing {

    public HillClimbing(){
        iterSolution = new ArrayList<>();
        iterFinalState = null;
    }

    private ArrayList<Action> solution;
    private State finalState;

    //Data Structures used to store results of one iteration
    private ArrayList<Action> iterSolution;
    private State iterFinalState;

    public void solve(OptimizationProblem op, HillClimbingSterategy strategy ,boolean maximize){
        solve(op,strategy,maximize,1);
    }

    public void solve(OptimizationProblem op, HillClimbingSterategy strategy ,boolean maximize,int randomRestart){
        for (int i = 0; i < randomRestart ; i++) {
            if(i != 0) System.out.println("[HC] Random Restart "+i);
            solveUtility(op,strategy,maximize);
            if(finalState == null || (maximize && op.eval(iterFinalState)>op.eval(finalState)) || (!maximize && op.eval(iterFinalState) < op.eval(finalState))){
                finalState = iterFinalState;
                solution = iterSolution;
            }
        }
        System.out.println("[HC] Best Eval : " + op.eval(finalState));
    }

    private void solveUtility(OptimizationProblem op, HillClimbingSterategy strategy ,boolean maximize){
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

                State bestNode = currentState ;
                int bestVal = op.eval(currentState);
                Action bestAction = null;
                boolean isFound = false;

                for(Pair<State,Action> psa : neighbours){
                    int tval = op.eval(psa.getKey());
                    if ((maximize && tval > bestVal) || (!maximize && tval < bestVal)){
                        bestNode = psa.getKey();
                        bestVal = tval;
                        bestAction = psa.getValue();
                        isFound = true;
                    }
                }

                if(!isFound){
                    //no better node exists final node is current node
                    iterFinalState = currentState;
                    System.out.println("[HC] Final State Reached !");
                    return;
                }

                currentState = bestNode;
                iterSolution.add(bestAction);
                System.out.println("[HC] Eval : " + bestVal);

            }else if(strategy == HillClimbingSterategy.FIRST_CHOICE){

                int curval = op.eval(currentState);
                boolean isFound = false;

                Random rnd = new Random();
                while(neighbours.size() > 0){
                    int index = rnd.nextInt(neighbours.size());
                    Pair<State,Action> psa = neighbours.get(index);

                    int tval = op.eval(psa.getKey());
                    if ((maximize && tval > curval) || (!maximize && tval < curval)){
                        //Choose First Better State
                        currentState = psa.getKey();
                        iterSolution.add(psa.getValue());
                        System.out.println("[HC] Eval : " + tval);
                        isFound = true;
                        break;
                    }

                    //remove visited
                    neighbours.remove(index);
                }

                if(!isFound){
                    //No Better State Found Declare as final
                    iterFinalState = currentState;
                    System.out.println("[HC] Final State Reached !");
                    return;
                }

            }else if(strategy == HillClimbingSterategy.STOCHASTIC){

                ArrayList<Pair<State,Action>> betterStates = new ArrayList<>();

                int curval = op.eval(currentState);
                boolean isFound = false;

                for(Pair<State,Action> psa : neighbours){
                    int tval = op.eval(psa.getKey());
                    if ((maximize && tval > curval) || (!maximize && tval < curval)){
                        //Add Better State to Array
                        betterStates.add(psa);
                        isFound = true;
                        break;
                    }
                }

                if(!isFound){
                    //No Better State Found Declare as final
                    iterFinalState = currentState;
                    System.out.println("[HC] Final State Reached !");
                    return;
                }else{
                    Random rnd = new Random();
                    int index = rnd.nextInt(betterStates.size());
                    Pair<State,Action> s = betterStates.get(index);
                    currentState = s.getKey();
                    iterSolution.add(s.getValue());
                    System.out.println("[HC] Eval : " + op.eval(s.getKey()));
                }

            }else{

                System.err.println("Invalid Strategy !");

            }


        }

    }

}
