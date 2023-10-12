package level2;

import java.util.*;

public class HanoiTower {
    List<int[]> result = new ArrayList<>();

    public int[][] solution(int n) {
        calcHanoi(n, 1, 3, 2);
        int[][] answer = new int[result.size()][2];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = result.get(i);
        }
        return answer;
    }

    public void calcHanoi(int size, int from, int to, int temp) {
        if (size > 0) {
            calcHanoi(size-1, from, temp, to);
            result.add(new int[]{from, to});
            calcHanoi(size-1, temp, to, from);
        }
    }
}
