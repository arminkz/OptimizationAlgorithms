package problems;

import algorithm.*;

import java.util.ArrayList;

public class NQueenProblem implements OptimizationProblem {

    int N;

    public NQueenProblem(int n){
        this.N = n;
    }

    @Override
    public State initialState() {
        return (new ChessBoardState(N));
    }

    @Override
    public ArrayList<Action> actions(State s) {
        ChessBoardState cbs = (ChessBoardState)s;
        ArrayList<Action> actions = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            int qoi = cbs.get(i);
            for (int j = 0; j < N; j++) {
                if(j != qoi){
                    //add all possible actions
                    actions.add(new QueenMovementAction(i,qoi,j));
                }
            }
        }
        return actions;
    }

    @Override
    public ArrayList<State> result(State s, Action a) {
        ChessBoardState cbs = (ChessBoardState)s;
        QueenMovementAction qma = (QueenMovementAction)a;
        //clone current state
        ChessBoardState newboard = new ChessBoardState(N);
        for (int i = 0; i < N; i++) {
            newboard.set(i,cbs.get(i));
        }
        //apply new changes
        newboard.set(qma.col,qma.to);
        ArrayList<State> singleState = new ArrayList<>();
        singleState.add(newboard);
        return singleState;
    }

    @Override
    public int eval(State s) {
        ChessBoardState cbs = (ChessBoardState)s;
        int hitcount = 0;
        for (int col = 0; col < N; col++) {
            int row = cbs.get(col);
            for (int o= 0; o < N; o++) {
                if(o != col){
                    //check horizontal hits
                    if(cbs.get(o) == row) hitcount++;
                    //no need to check vertical hits because of proper definition
                    //check diagonal hits
                    if(Math.abs(row - cbs.get(o)) == Math.abs(col - o)) hitcount++;
                }
            }
        }
        return hitcount / 2;
    }
}

class ChessBoardState implements State{

    private int[] board;

    public ChessBoardState(int n){
        board = new int[n];
        for (int i = 0; i < n; i++) {
            board[i] = 0;
        }
    }

    public int get(int i){
        return board[i];
    }

    public void set(int i,int val){
        board[i] = val;
    }

    @Override
    public boolean isEquals(State s) {
        return false;
    }
}

class QueenMovementAction implements Action{

    int col;
    int from;
    int to;

    public QueenMovementAction(int col,int from,int to){
        this.col = col;
        this.from = from;
        this.to = to;
    }


    @Override
    public String description() {
        return "Move Queen of Column " + col + " : from " + from + " to " + to;
    }

    @Override
    public int actionCode() {
        return 0;
    }
}
