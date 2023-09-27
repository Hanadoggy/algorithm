package level2;

import java.util.*;

public class MenuRenewal {
    public static Map<String, Integer> combinationMaps = new HashMap<>();
    public static int[] maxOrdered = new int[11];

    public String[] solution(String[] orders, int[] course) {
        List<String[]> courses = new ArrayList<>();

        for (int depth : course) {
            for (String order : orders) {
                char[] item = order.toCharArray();
                Arrays.sort(item);
                renewal(new boolean[item.length], depth, 0, item, 0);
            }
        }
        for (String item : combinationMaps.keySet()) {
            if (combinationMaps.get(item) == maxOrdered[item.length()] &&
                    maxOrdered[item.length()] > 1) {
                courses.add(new String[]{item, combinationMaps.get(item).toString()});
            }
        }
        courses.sort(Comparator.comparing((String[] i) -> i[0]));
        String[] answer = new String[courses.size()];
        for (int i = 0; i < courses.size(); i++) {
            answer[i] = courses.get(i)[0];
        }
        return answer;
    }

    public static void renewal(boolean[] check, int depth, int curDepth, char[] order, int beforeIndex) {
        if (depth == curDepth) {
            StringBuilder combination = new StringBuilder();
            for (int i = 0; i < check.length; i++) {
                if (check[i]) {
                    combination.append(order[i]);
                }
            }
            combinationMaps.put(combination.toString(),
                    combinationMaps.getOrDefault(combination.toString(), 0) + 1);
            maxOrdered[depth] = Math.max(maxOrdered[depth], combinationMaps.get(combination.toString()));
        } else {
            for (int i = beforeIndex; i < check.length; i++) {
                if (!check[i]) {
                    check[i] = true;
                    renewal(check, depth, curDepth + 1, order, i + 1);
                    check[i] = false;
                }
            }
        }
    }
}
