package level3;

import java.util.*;

public class Practice {

    public long leftWork(int n, int[] works) {
        Map<Integer, Integer> leftWorks = new HashMap<>();
        long answer = 0;

        for (int work : works) {
            leftWorks.put(work, leftWorks.getOrDefault(work, 0) + 1);
        }
        List<Integer> times = new ArrayList<>(leftWorks.keySet());
        times.sort(Comparator.reverseOrder());
        for (int i = 0; i < times.size() && n > 0; i++) {
            int currentTime = times.get(i);
            int nextLeftTime = i == times.size() - 1 ? 0 : times.get(i + 1);
            int minusTime = (currentTime - nextLeftTime) * leftWorks.get(currentTime);
            if (n >= minusTime) {
                n -= minusTime;
                if (nextLeftTime != 0) {
                    leftWorks.put(nextLeftTime, leftWorks.get(nextLeftTime) + leftWorks.get(currentTime));
                    leftWorks.remove(currentTime);
                } else {
                    leftWorks.clear();
                }
            } else {
                int currentLeft = leftWorks.get(currentTime);
                leftWorks.remove(currentTime);
                leftWorks.put(currentTime - (n / currentLeft), currentLeft - (n % currentLeft));
                if (n % currentLeft != 0) {
                    leftWorks.put(currentTime - ((n / currentLeft) + 1),
                            leftWorks.getOrDefault(currentTime - ((n / currentLeft) + 1), 0) +
                                    (n % currentLeft));
                }
                n = 0;
            }
        }
        for (Integer key : leftWorks.keySet()) {
            answer += (long) Math.pow(key, 2) * leftWorks.get(key);
        }
        return answer;
    }

    public int[] expressBinaryTree(long[] numbers) {
        int[] answer = new int[numbers.length];

        for (int i = 0; i < numbers.length; i++) {
            answer[i] = check(addZero(Long.toBinaryString(numbers[i])));
        }
        return answer;
    }

    private int check(String number) {
        Queue<int[]> visit = new LinkedList<>();

        visit.add(new int[]{number.length(), number.length()/2});
        while (!visit.isEmpty()) {
            int[] cur = visit.poll();
            int move = Math.abs(cur[0] - cur[1]) / 2;

            if (move > 0) {
                if (number.charAt(cur[1]) == '0' &&
                        (number.charAt(cur[1]-move) == '1' || number.charAt(cur[1]+move) == '1')) {
                    return 0;
                }
                visit.add(new int[]{cur[1], cur[1]-move});
                visit.add(new int[]{cur[1], cur[1]+move});
            }
        }
        return 1;
    }

    private String addZero(String original) {
        for (int i = 0; original.length() >= Math.pow(2, i); i++) {
            if (original.length() < Math.pow(2, i+1)) {
                return "0".repeat((int) Math.pow(2, i+1) - original.length() - 1) + original;
            }
        }
        return original;
    }

    public int personnelReview(int[][] scores) {
        int[] target = new int[]{scores[0][0], scores[0][1]};
        List<int[]> list = new ArrayList<>();
        int answer = 1;

        Arrays.sort(scores,
                Comparator.comparing((int[] i) -> -i[0])
                        .thenComparing((int[] i) -> -i[1]));
        for (int i = 0, top = 0, temp = 0; i < scores.length; i++) {
            if (i == 0) {
                temp = scores[0][1];
            } else if (scores[i][0] < scores[i-1][0]) {
                top = Math.max(top, temp);
                temp = scores[i][1];
            }
            if (scores[i][1] >= top) {
                list.add(scores[i]);
            }
        }
        list.sort(Comparator.comparing((int[] i) -> -i[0] - i[1]));
        for (int i = 0, temp = 0; i < list.size(); i++) {
            if (i > 0 && list.get(i)[0] + list.get(i)[1] < list.get(i-1)[0] + list.get(i-1)[1]) {
                answer += temp;
                temp = 1;
            } else {
                temp++;
            }
            if (list.get(i)[0] == target[0] && list.get(i)[1] == target[1]) {
                return answer;
            }
        }
        return -1;
    }

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
