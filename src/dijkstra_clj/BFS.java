package dijkstra_clj;

import java.util.*;

public class BFS {

    static int[][] testGraph = new int[][] {
            {0, 7, 5, 9},
            {0, 0, 1, 0},
            {0, 0, 0, 1},
            {0, 0, 0, 0}
    };


    public static void main(String[] args) {
        new BFS().run();
    }

    private void run() {
        int from = 0;
        int to = 3;

        int[][] bestPaths = new int[testGraph.length][2];
        List<Integer[]> quie = new ArrayList<>();
        quie.add(from, new Integer[]{from,0});

        runAlgo(quie, bestPaths);

        System.out.println(getBestPath(bestPaths, from, to));
        System.out.println("weight: " + bestPaths[to][1]);
    }

    private void runAlgo(List<Integer[]> quie, int[][] bestPaths) {
        boolean finished = false;
        while(!finished) {
            Integer[] curr = quie.remove(0);
            int currNode = curr[0];
            int currWeight = curr[1];
            forLoop(currNode, bestPaths, currWeight, quie);
            finished = quie.isEmpty();
        }
    }

    private void forLoop(int currNode, int[][] bestPaths, int currWeight, List<Integer[]> quie){
        for(int i=0; i < testGraph[currNode].length; i++ ){
            int weight = testGraph[currNode][i];
            int bestPath = bestPaths[i][1];
            if(isNeedRelax(weight, bestPath, currWeight)) {
                relax(bestPaths, i, currNode, currWeight+ weight, quie);
            }
        }
    }

    private boolean isNeedRelax(int weight, int bestPath, int currWeight) {
        return weight != 0 && (bestPath == 0 || currWeight + weight < bestPath);
    }

    private void relax(int[][]bestPaths, int i, int currNode, int newWeight, List<Integer[]> quie){
        bestPaths[i][0] = currNode;
        bestPaths[i][1] = newWeight;
        quie.add(new Integer[]{i, newWeight });
    }

    private List<Integer> getBestPath(int[][] bestPaths, int from, int to) {
        List<Integer> best = new ArrayList<>();
        int cur = to;
        best.add(to);
        while(cur != from){
            best.add(bestPaths[cur][0]);
            cur = bestPaths[cur][0];
        }
        Collections.reverse(best);
        return best;
    }
}
