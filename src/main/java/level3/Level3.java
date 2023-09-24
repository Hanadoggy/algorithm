package level3;

import java.util.*;

public class Level3 {

    public static void main(String[] args) {

        // level 3

    }

    private static int aircon(int temperature, int t1, int t2, int a, int b, int[] onboard) {
        // DP 풀이가 맞는듯
        final int maxValue = 200 * 20000;
        int answer = maxValue;
        int diff = 0;

        if (temperature < t1) {
            diff = t1 - temperature;
        } else if (temperature > t2) {
            diff = temperature - t2;
        }
        int[][] list = new int[onboard.length][diff + 3];

        for (int i = 1; i < diff + 3; i++) {
            list[0][i] = maxValue;
        }

        for (int i = 1; i < onboard.length; i++) {
            for (int j = 0; j < diff + 3; j++) {
                int add = list[i - 1][j] + ((j == 0) ? 0 : b);
                if (j > 0) add = Math.min(list[i - 1][j - 1] + a, add);
                if (j < diff + 2) add = Math.min(list[i - 1][j + 1], add);
                if (onboard[i] == 1 && j < diff) {
                    list[i][j] = maxValue;
                } else {
                    list[i][j] = add;
                }
            }
        }

        for (int i = 0; i < diff + 3; i++) {
            answer = Math.min(answer, list[onboard.length - 1][i]);
        }

        return answer;
    }

}
