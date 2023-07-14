package level1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MemoryScore {
    public static int[] solution(String[] name, int[] yearning, String[][] photo) {

        Map<String, Integer> score = new HashMap<>();
        for (int i = 0; i < name.length; i++) {
            score.put(name[i], yearning[i]);
        }
        int[] answer = new int[photo.length];
        for (int i = 0, temp = 0; i < photo.length; i++) {
            for (int j = 0; j < photo[i].length; j++) {
                System.out.println(photo[i][j]);
                try {
                    temp += score.get(photo[i][j]);
                } catch (NullPointerException e) {}
            }
            answer[i] = temp;
            temp = 0;
        }

        return answer;
    }

    public static void main(String[] args) {
        String[] name = new String[]{"may", "kein", "kain", "radi"};
        int[] yearning = new int[]{5, 10, 1, 3};
        String[][] photo = new String[][]{{"may", "kein", "kain", "radi"}, {"may", "kein", "brin", "deny"}, {"kon", "kain", "may", "coni"}};
        System.out.println(Arrays.toString(solution(name, yearning, photo)));
    }
}
