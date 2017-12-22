package algorithm;

import java.util.ArrayList;

public interface GeneticProblem {

    ArrayList<State> initialPopulation(int size);

    double fitness(State s);

    State crossover(State s1,State s2);

    State mutate(State s);

}
