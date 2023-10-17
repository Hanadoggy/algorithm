package hackerrank.algorithm;

import java.util.*;

public class Basic {

    public static int pickingNumbers(List<Integer> list) {
        int[] count = new int[100];
        int answer = 0;

        for (int num : list) {
            count[num]++;
        }
        for (int i = 1; i < 100; i++) {
            answer = Math.max(answer, count[i] + count[i-1]);
        }
        return answer;
    }

}
