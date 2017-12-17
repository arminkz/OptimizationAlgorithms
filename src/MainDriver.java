import problems.*;
import algorithm.*;

public class MainDriver {

    public static void main(String[] args) {
        NQueenProblem Q = new NQueenProblem(8);
        HillClimbing HC = new HillClimbing();
        HC.solve(Q, HillClimbingStrategy.FIRST_CHOICE,false,5);
    }

}
