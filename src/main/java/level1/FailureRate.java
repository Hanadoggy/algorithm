package level1;

import java.util.*;

public class FailureRate {

    // 반례???????
    public int[] solution(int N, int[] stages) {

        Integer[] stage = Arrays.stream(stages).boxed().toArray(Integer[]::new);
        Arrays.sort(stage);
        Map<Integer, Double> counts = new HashMap<>();
        for (int i = 1; i <= N; i++) {
            counts.put(i, 0d);
        }

        for (int i = 0, last = stage[0], count = 0, size = stage.length; i <= stage.length; i++) {

            if (i == stage.length) {
                counts.put(last, (double) count / size);
            } else if (last == stage[i]) {
                count++;
            } else if (stage[i] <= N){
                counts.put(last, (double) count / size);
                size -= count;
                last = stage[i];
                count = 1;
            }
        }
        List<Map.Entry<Integer, Double>> entryList = new ArrayList<>(counts.entrySet());
        entryList.sort(Map.Entry.<Integer, Double>comparingByValue().reversed()
                .thenComparing(Map.Entry.comparingByKey()));
        int[] answer = new int[entryList.size()];
        for (int i = 0; i < entryList.size(); i++) {
            answer[i] = entryList.get(i).getKey();
        }

        return answer;
    }
}
