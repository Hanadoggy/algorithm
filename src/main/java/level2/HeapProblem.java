package level2;

import java.util.PriorityQueue;

public class HeapProblem {

    public static void main(String[] args) {

    }

    private static int moreSpicy(int[] scoville, int K) {
        PriorityQueue<Integer> foods = new PriorityQueue<>();
        int answer = 0;

        for (int food : scoville) {
            foods.add(food);
        }
        while (!foods.isEmpty()) {
            int food = foods.poll();
            if (food >= K) {
                break;
            } else if (foods.isEmpty()) {
                return -1;
            } else {
                int nextFood = foods.poll();
                foods.add(food + nextFood*2);
                answer++;
            }
        }
        return answer;
    }
}
