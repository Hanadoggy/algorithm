package level3;

import java.util.Arrays;

public class Practice {

    public long continuousPurseArraySum(int[] sequence) {
        long answer = Long.MIN_VALUE;

        for (int i = 0; i < 2; i++) {
            answer = Math.max(answer, getMax(sequence, i));
        }
        return answer;
    }

    public long getMax(int[] sequence, int cond) {
        long[] result = new long[sequence.length];

        result[0] = (0 == cond) ? sequence[0] : -sequence[0];
        for (int i = 1; i < sequence.length; i++) {
            int add = (i % 2 == cond) ? sequence[i] : -sequence[i];
            result[i] = Math.max(0, result[i-1]) + add;
        }
        return Arrays.stream(result).max().getAsLong();
    }

    public int vanguard(int n) {
        long[] answer = new long[100_001];
        long[] temp = new long[]{8,0,2};
        final long DIV = 1_000_000_007;

        answer[1] = 1L;
        answer[2] = 3L;
        answer[3] = 10L;
        for (int i = 4; i <= n; i++) {
            int idx = i % 3;

            answer[i] = temp[idx] + ((i%3 == 0) ? 4 : 2);
            answer[i] += (answer[i-1] + 2*answer[i-2] + 5*answer[i-3]);
            answer[i] %= DIV;
            temp[idx] += (2*answer[i-1] + 2*answer[i-2] + 4*answer[i-3]);
            temp[idx] %= DIV;
        }
        return (int) answer[n];
    }
}
