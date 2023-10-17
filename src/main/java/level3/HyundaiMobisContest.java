package level3;

import java.util.Arrays;

public class HyundaiMobisContest {

    public int counselor(int k, int n, int[][] reqs) {
        int[] waiting = new int[k+1];
        int[] counseling = new int[k+1];
        int remain = n - k;

        for (int[] req : reqs) {
            int idx = req[2];
            if (counseling[idx] > req[0]) {
                waiting[idx] += counseling[idx] - req[0];
                counseling[idx] += req[1];
            } else {
                counseling[idx] = req[0] + req[1];
            }
        }
        Arrays.fill(counseling, 1);
        while (remain > 0) {
            int shortestCut = 0;
            int idx = 0;

            for (int i = 1; i < k+1; i++) {
                int wait = getWaiting(reqs, counseling[i]+1, i);
                if (waiting[i] - wait > shortestCut) {
                    idx = i;
                    shortestCut = waiting[i] - wait;
                }
            }
            counseling[idx]++;
            waiting[idx] -= shortestCut;
            remain--;
        }
        return Arrays.stream(waiting).sum();
    }

    private int getWaiting(int[][] reqs, int counselors, int type) {
        int[] counseling = new int[counselors];
        int waiting = 0;

        for (int[] req : reqs) {
            if (type == req[2]) {
                int idx = -1;
                int wait = Integer.MAX_VALUE;
                for (int i = 0; i < counselors; i++) {
                    if (counseling[i] <= req[0]) {
                        counseling[i] = req[0] + req[1];
                        idx = -1;
                        break;
                    } else if (counseling[i] - req[0] < wait) {
                        wait = counseling[i] - req[0];
                        idx = i;
                    }
                }
                if (idx != -1) {
                    waiting += wait;
                    counseling[idx] += req[1];
                }
            }
        }
        return waiting;
    }

    public int aircon(int temperature, int t1, int t2, int a, int b, int[] onboard) {
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
