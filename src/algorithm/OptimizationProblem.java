package algorithm;

import java.util.ArrayList;

public interface OptimizationProblem {

    State initialState();

    ArrayList<Action> actions(State s);

    ArrayList<State> result(State s,Action a);

    int eval(State s);

}
