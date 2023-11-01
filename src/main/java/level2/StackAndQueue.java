package level2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class StackAndQueue {

    public static void main(String[] args) {

    }

    public int truckAcrossBridge(int bridge_length, int weight, int[] truck_weights) {
        int answer = 0;
        int front= 0;
        int end = 1;

        for (int[] check = new int[truck_weights.length]; front < truck_weights.length; answer++) {
            int totalWeight = 0;

            for (int i = front; i < end; i++) {
                totalWeight += truck_weights[i];
                if (++check[i] == bridge_length) {
                    front++;
                    totalWeight -= truck_weights[i];
                }
            }
            if (end < truck_weights.length && totalWeight + truck_weights[end] <= weight) {
                end++;
            }
        }
        return answer+1;
    }

    private static int[] stockPrice(int[] prices) {
        Stack<int[]> stack = new Stack<>(); // idx, price
        int[] answer = new int[prices.length];

        for (int i = 0; i < prices.length; i++) {
            if (!stack.isEmpty() && stack.peek()[1] > prices[i]) {
                while (!stack.isEmpty() && stack.peek()[1] > prices[i]) {
                    int[] pop = stack.pop();
                    answer[pop[0]] = i - pop[0];
                }
            }
            stack.push(new int[]{i, prices[i]});
        }
        while (!stack.isEmpty()) {
            int[] pop = stack.pop();
            answer[pop[0]] = prices.length - pop[0] - 1;
        }
        return answer;
    }

    private static int[] funcDevelop(int[] progresses, int[] speeds) {
        int idx = 0;
        List<Integer> answer = new ArrayList<>();

        for (int day = 1; idx < progresses.length; day++) {
            int temp = idx;

            while (idx < progresses.length && progresses[idx] + speeds[idx]*day >= 100) {
                idx++;
            }
            if (temp != idx) {
                answer.add(idx - temp);
            }
        }
        return answer.stream().mapToInt(i->i).toArray();
    }

    private static int process(int[] priorities, int location) {
        int idx = 0;
        int len = priorities.length;
        int[] descOrder = Arrays.copyOf(priorities, len);
        Stack<Integer> stack = new Stack<>();

        Arrays.sort(descOrder);
        for (int num : descOrder) {
            stack.push(num);
        }
        while (!stack.isEmpty()) {
            if (priorities[idx] == stack.peek() && location == idx) {
                return len - stack.size() + 1;
            } else if (priorities[idx] == stack.peek()) {
                stack.pop();
            }
            idx = (idx+1)%len;
        }
        return len;
    }
}
