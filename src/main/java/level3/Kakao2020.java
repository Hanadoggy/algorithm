package level3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Kakao2020 {

    public int[] solution(String[] gems) {
        Set<String> totalJewelry = new HashSet<>();
        Map<String, Integer> jewelry = new HashMap<>();
        int[] answer = new int[]{0, gems.length - 1};
        int start = 0;
        int end = 0;

        for (String gem : gems) {
            totalJewelry.add(gem);
        }

        while (jewelry.keySet().size() < totalJewelry.size()) {
            jewelry.put(gems[end], jewelry.getOrDefault(gems[end], 0) + 1);
            end++;
        }
        end--;

        while (end < gems.length) {
            while (start <= end) {
                if (jewelry.get(gems[start]) == 1) {
                    if (answer[1] - answer[0] > end - start ||
                            (answer[1] - answer[0] == end - start && answer[0] > start)) {
                        answer[0] = start;
                        answer[1] = end;
                    }
                    break;
                }
                jewelry.put(gems[start], jewelry.get(gems[start]) - 1);
                start++;
            }
            if (end == gems.length - 1) {
                break;
            }
            while (end < gems.length - 1) {
                end++;
                jewelry.put(gems[end], jewelry.get(gems[end]) + 1);
                if (jewelry.get(gems[start]) > 1) {
                    break;
                }
            }

        }

        answer[0]++;
        answer[1]++;
        return answer;
    }

}
