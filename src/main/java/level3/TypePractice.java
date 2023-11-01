package level3;

import java.util.*;

public class TypePractice {

    public int network(int n, int[][] computers) {
        boolean[] visited = new boolean[n];
        Queue<Integer> next = new LinkedList<>();
        int answer = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!visited[j] && computers[i][j] == 1) {
                    next.add(j);
                    visited[j] = true;
                    break;
                }
            }
            while (!next.isEmpty()) {
                int cur = next.poll();

                for (int k = 0; k < n; k++) {
                    if (!visited[k] && computers[cur][k] == 1) {
                        next.add(k);
                        visited[k] = true;
                    }
                }
                if (next.isEmpty()) {
                    answer++;
                    break;
                }
            }
        }
        return answer;
    }

    public int[] DoublePriorityQueue(String[] operations) {
        PriorityQueue<Integer> asc = new PriorityQueue<>();
        PriorityQueue<Integer> desc = new PriorityQueue<>(Comparator.reverseOrder());

        for (String operation : operations) {
            int num = Integer.parseInt(operation.substring(2));

            if (operation.charAt(0) == 'I') {
                asc.add(num);
                desc.add(num);
            } else if (!asc.isEmpty()) {
                if (num > 0) {
                    asc.remove(desc.poll());
                } else {
                    desc.remove(asc.poll());
                }
            }
        }
        if (asc.isEmpty()) {
            return new int[]{0,0};
        }
        return new int[]{desc.poll(), asc.poll()};
    }

    public int IntegerTriangle(int[][] triangle) {
        int answer = 0;
        int len = triangle.length;

        for (int i = 1; i < len; i++) {
            for (int j = 0; j < triangle[i].length; j++) {
                int temp = triangle[i][j];

                if (j > 0) {
                    temp += triangle[i-1][j-1];
                }
                if (j < triangle[i-1].length) {
                    temp = Math.max(temp, triangle[i][j] + triangle[i-1][j]);
                }
                triangle[i][j] = temp;
            }
        }
        for (int i = 0; i < triangle[len-1].length; i++) {
            answer = Math.max(answer, triangle[len-1][i]);
        }
        return answer;
    }

}
