package level2;

import java.util.*;

public class StackAndQueue {

    public static void main(String[] args) {

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
