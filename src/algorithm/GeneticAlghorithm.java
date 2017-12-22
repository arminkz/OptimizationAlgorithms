package algorithm;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Random;

public class GeneticAlghorithm {

    private int population_size;
    private int mutation_rate;
    private int crossover_rate;

    public GeneticAlghorithm(int population_size, int crossover_rate , int mutation_rate){
        this.population_size = population_size;
        this.mutation_rate = mutation_rate;
        this.crossover_rate = crossover_rate;
    }

    private State finalState;

    public void solve(GeneticProblem gp,int generations){

        //initialization
        ArrayList<State> population = gp.initialPopulation(population_size);

        int k = 0;
        while(k < generations) {

            //evaluate
            double fitness_sum = 0;
            ArrayList<Pair<State, Double>> populationFitness = new ArrayList<>();
            for (State p : population) {
                populationFitness.add(new Pair<>(p, gp.fitness(p)));
                fitness_sum += gp.fitness(p);
            }


            //calculate cumulative probability for RWS
            double cumulativeP[] = new double[population.size() + 1];
            cumulativeP[0] = 0;
            double cumulativeS = 0;
            for (int i = 1; i <= populationFitness.size(); i++) {
                double p = populationFitness.get(i - 1).getValue() / fitness_sum;
                cumulativeS += p;
                cumulativeP[i] = cumulativeS;
            }


            //RWS
            ArrayList<State> selectedPopulation = new ArrayList<>();
            Random rnd = new Random();
            while (selectedPopulation.size() < population_size) {
                double r = rnd.nextDouble();
                for (int i = 0; i <= population_size; i++) {
                    if (r < cumulativeP[i]) {
                        //select i-1 nth element
                        selectedPopulation.add(populationFitness.get(i - 1).getKey());
                        break;
                    }
                }
            }

            ArrayList<State> new_population = new ArrayList<>();

            //Add Parents
            for (State s : selectedPopulation) new_population.add(s);

            //Select for Crossover
            ArrayList<State> selectedForCrossover = new ArrayList<>();
            for (int i = 0; i < population_size; i++) {
                if (rnd.nextDouble() < crossover_rate) {
                    selectedForCrossover.add(selectedPopulation.get(i));
                }
            }
            //Crossover
            if (selectedForCrossover.size() >= 2) {
                for (int i = 0; i < selectedForCrossover.size(); i++) {
                    for (int j = i + 1; j < selectedForCrossover.size(); j++) {
                        new_population.add(gp.crossover(selectedForCrossover.get(i), selectedForCrossover.get(j)));
                    }
                }
            }

            //Mutation
            int number_of_mutations = (int) Math.floor(mutation_rate * population_size);
            for (int i = 0; i < number_of_mutations; i++) {
                int mut_index = rnd.nextInt(population_size);
                new_population.add(gp.mutate(selectedPopulation.get(mut_index)));
            }

            //update population
            population = new_population;
        }

        //after k iterations return answer
        double bestFitness = Double.MIN_VALUE;
        State bestState = null;

        for(State s : population){
            if(bestState == null || gp.fitness(s) < bestFitness){
                bestState = s;
                bestFitness = gp.fitness(s);
            }
        }

        finalState = bestState;
    }




}
