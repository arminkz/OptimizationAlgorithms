package problems;

import algorithm.GeneticProblem;
import algorithm.State;

import java.util.ArrayList;
import java.util.Random;

// Equation:
// ax + by + cz + dw = f

public class MathematicalEqualityProblem implements GeneticProblem {

    private int a , b , c , d , f;
    private int ubound;

    public MathematicalEqualityProblem(int a,int b,int c,int d,int f,int ubound){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.f = f;
        this.ubound = ubound;
    }

    @Override
    public ArrayList<State> initialPopulation(int size) {
        ArrayList<State> result = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < size; i++) {
            MathematicalEqualityState mes = new MathematicalEqualityState();
            for (int j = 0; j < 4; j++) {
                mes.C[j] = rnd.nextInt(ubound);
            }
            result.add(mes);
        }
        return result;
    }

    @Override
    public double fitness(State s) {
        MathematicalEqualityState mes = (MathematicalEqualityState)s;
        double val = (a*mes.C[0] + b*mes.C[1] + c*mes.C[2] + d*mes.C[3]) - f;
        return 1 / (1 + val);
    }

    @Override
    public State crossover(State s1, State s2) {
        MathematicalEqualityState mes1 = (MathematicalEqualityState)s1;
        MathematicalEqualityState mes2 = (MathematicalEqualityState)s2;
        MathematicalEqualityState result = new MathematicalEqualityState();

        Random rnd = new Random();
        int cindex = rnd.nextInt(3) + 1;

        for (int i = 0; i < cindex; i++) {
            result.C[i] = mes1.C[i];
        }
        for (int i = cindex; i < 4 ; i++) {
            result.C[i] = mes2.C[i];
        }

        return result;
    }

    @Override
    public State mutate(State s) {
        MathematicalEqualityState mes = (MathematicalEqualityState)s;
        MathematicalEqualityState result = new MathematicalEqualityState();

        Random rnd = new Random();
        int mindex = rnd.nextInt(4);

        for (int i = 0; i < 4; i++ ) {
            if(i != mindex)
                result.C[i] = mes.C[i];
            else
                result.C[i] = rnd.nextInt(ubound);
        }

        return result;
    }

}

class MathematicalEqualityState implements State {

    public int C[];

    public MathematicalEqualityState(){
        C = new int[4];
    }

    @Override
    public boolean isEquals(State s) {
        return false;
    }

    @Override
    public String toString() {
        return C[0] + " | " + C[1] +  " | " + C[2] +  " | "  + C[3];
    }
}