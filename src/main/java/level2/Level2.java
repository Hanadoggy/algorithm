package level2;

import java.util.*;

import static java.lang.Math.*;

public class Level2 {

    public static void main(String[] args) {

//        System.out.println(minMaxSol("1 2 3 4"));
    }

    private static String minMaxSol(String s) {
        String[] split = s.split(" ");
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (String word : split) {
            int num = Integer.parseInt(word);
            max = max(num, max);
            min = min(num, min);
        }

        return String.format("%d %d", min, max);
    }


    private static int intercept(int[][] targets) { // 풀이 참고

        Arrays.sort(targets, Comparator.comparing(a -> a[1]));
        int pivot = 0;
        int answer = 0;
        for (int[] target : targets) {
            if (target[0] >= pivot) {
                answer++;
                pivot = target[0];
            }
        }

        return answer;
    }

    private static long twoCircle(int r1, int r2) {

        long count = 0;
        int y;
        int height;
        for (int x = 1; x <= r2; x++) {
            y = (int) Math.ceil(Math.sqrt(Math.pow(r1, 2) - Math.pow(x, 2)));
            height = (int) Math.floor(Math.sqrt(Math.pow(r2, 2) - Math.pow(x, 2)));
            count += (height - y + 1);
        }

        return count * 4; // 오타 수정
    }

    private static int[] partition(int[] sequence, int k) {

        int resultX = 0;
        int resultY = sequence.length;
        int index;
        int[] counts = new int[sequence.length];
        counts[0] = sequence[0];
        for (int i = 1; i < sequence.length; i++) {
            counts[i] = counts[i - 1] + sequence[i];
        }
        for (int i = 0; i < counts.length; i++) {
            index = Arrays.binarySearch(counts, k + counts[i] - sequence[i]);
            if (index >= 0 && index - i < resultY - resultX) {
                resultY = index;
                resultX = i;
            }
        }

        return new int[]{resultX, resultY};
    }

    private static String[] solveAssignment(String[][] plans) {

        List<String> result = new ArrayList<>();
        Stack<Assignment> stack = new Stack<>();
        List<Assignment> list = new ArrayList<>();
        for (String[] plan : plans) {
            String[] time = plan[1].split(":");
            list.add(new Assignment(plan[0], Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]),
                    Integer.parseInt(plan[2])));
        }
        list.sort(Comparator.comparing(assignment -> assignment.start));

        int beforeTime = 0;
        for (Assignment assignment : list) {
            int leftTime = assignment.start - beforeTime;
            while (!stack.isEmpty() && leftTime > 0) {
                Assignment pop = stack.pop();
                if (leftTime >= pop.playtime) {
                    leftTime -= pop.playtime;
                    result.add(pop.name);
                } else {
                    stack.push(new Assignment(pop.name, pop.start, pop.playtime - leftTime));
                    break;
                }
            }
            beforeTime = assignment.start;
            stack.push(assignment);
        }

        while (!stack.isEmpty()) {
            result.add(stack.pop().name);
        }

        return result.toArray(new String[0]);
    }

    static class Assignment {
        public String name;
        public int start;
        public int playtime;

        public Assignment(String name, int start, int playtime) {
            this.name = name;
            this.start = start;
            this.playtime = playtime;
        }
    }
}

