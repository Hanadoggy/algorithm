package level2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Sorting {

    public static void main(String[] args) {
        System.out.println(maximumNumber(new int[]{555, 565, 566, 55, 56, 5, 54, 544, 549}));
    }

    private static String maximumNumber(int[] numbers) { // 최적화 진행
        StringBuilder answer = new StringBuilder();

        List<String> collect = Arrays.stream(numbers).mapToObj(String::valueOf)
                .sorted(Comparator
                        .comparing((String s) -> s.charAt(0))
                        .thenComparing(s -> (s.length() > 1) ? s.charAt(1) : s.charAt(0))
                        .thenComparing(s -> (s.length() > 2) ? s.charAt(2) : s.charAt(0))
                        .thenComparing(s -> (s.length() > 3) ? s.charAt(3) : s.charAt(0))
                        .thenComparing(s -> s.charAt(s.length()-1))
                        .reversed()).toList();
        for (String num : collect) {
            answer.append(num);
        }
        return (answer.charAt(0) == '0') ? "0" : answer.toString();
    }

    private static int hIndex(int[] citations) {
        int answer = 0;
        int len = citations.length;

        Arrays.sort(citations);
        for (int idx = 0; idx < len; idx++) {
            while (answer < citations[idx] && answer < len-idx) {
                answer++;
            }
        }
        return answer;
    }

}
