package problems;

import algorithm.*;

import java.util.ArrayList;
import java.util.Random;

public class GraphClusteringProblem implements OptimizationProblem {

    //Adjacency Matrix used to store Graph
    int[][] G;
    int size;

    public GraphClusteringProblem(int[][] graph,int size){
        this.G = graph;
        this.size = size;
    }

    @Override
    public State initialState() {
        GraphClusterState init = new GraphClusterState();
        //Randomly assign clusters
        Random rnd = new Random();
        for (int i = 0; i < size; i++) {
            if(rnd.nextInt(2)%2==0){
                init.cluster1.add(i);
            }else{
                init.cluster2.add(i);
            }
        }
        return init;
    }

    @Override
    public ArrayList<Action> actions(State s) {

        ArrayList<Action> actions = new ArrayList<>();
        GraphClusterState gcs = (GraphClusterState)s;

        //for each node in cluster 1 we can move that node to cluster 2
        for(Integer c1n : gcs.cluster1){
            actions.add(new GraphClusterAction(c1n,1,2));
        }

        //for each node in cluster 2 we can move that node to cluster 1
        for(Integer c2n : gcs.cluster2){
            actions.add(new GraphClusterAction(c2n,2,1));
        }

        return actions;
    }

    @Override
    public ArrayList<State> result(State s, Action a) {

        GraphClusterState gcs = (GraphClusterState)s;
        GraphClusterAction gca = (GraphClusterAction)a;

        //clone current state
        GraphClusterState newState = new GraphClusterState();
        for(Integer c1n : gcs.cluster1) newState.cluster1.add(c1n);
        for(Integer c2n : gcs.cluster2) newState.cluster2.add(c2n);

        if(gca.from == 1){
            for (int i = 0; i < newState.cluster1.size(); i++) {
                if(newState.cluster1.get(i) == gca.vertex){
                    newState.cluster1.remove(i);
                    newState.cluster2.add(gca.vertex);
                    break;
                }
            }
        }else{
            for (int i = 0; i < newState.cluster2.size(); i++) {
                if(newState.cluster2.get(i) == gca.vertex){
                    newState.cluster2.remove(i);
                    newState.cluster1.add(gca.vertex);
                    break;
                }
            }
        }

        ArrayList<State> singleState = new ArrayList<>();
        singleState.add(newState);
        return singleState;
    }

    @Override
    public int eval(State s) {

        GraphClusterState gcs = (GraphClusterState)s;

        int connectionCost = 0;
        int sizeDiffCost = 0;

        //calc connection cost
        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {
                if(G[i][j] == 1){

                    //determine cluster of node i
                    int clusterI = 0;
                    for(Integer cic : gcs.cluster1){
                        if(cic == i) clusterI = 1;
                    }
                    for(Integer cic : gcs.cluster2){
                        if(cic == i) clusterI = 2;
                    }

                    //determine cluster of node j
                    int clusterJ = 0;
                    for(Integer cic : gcs.cluster1){
                        if(cic == j) clusterJ = 1;
                    }
                    for(Integer cic : gcs.cluster2){
                        if(cic == j) clusterJ = 2;
                    }

                    if(clusterI != clusterJ) connectionCost++;
                }
            }
        }

        //size diff cost
        sizeDiffCost = Math.abs(gcs.cluster1.size() - gcs.cluster2.size());

        return 2 * connectionCost + sizeDiffCost;
    }

}

class GraphClusterState implements State{

    public ArrayList<Integer> cluster1 = new ArrayList<>();
    public ArrayList<Integer> cluster2 = new ArrayList<>();

    @Override
    public boolean isEquals(State s) {
        return false;
    }

    @Override
    public String toString() {
        String str = "C1 :\n";
        for(Integer i : cluster1){
            str += " " + i;
        }
        str += "C2 :\n";
        for(Integer i : cluster2){
            str += " " + i;
        }
        return str;
    }
}

class GraphClusterAction implements Action{

    int vertex;
    int from;
    int to;

    public GraphClusterAction(int vertex,int from,int to){
        this.vertex = vertex;
        this.from = from;
        this.to = to;
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public int actionCode() {
        return 0;
    }

}
