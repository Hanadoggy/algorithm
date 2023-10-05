package level2;

import java.util.HashMap;
import java.util.Map;

public class Clothes {
    static int[] count = new int[30];

    public int solution(String[][] clothes) {
        Map<String, Integer> maps = new HashMap<>();
        int i = 0;

        for (String[] item : clothes) {
            if (!maps.containsKey(item[1])) {
                maps.put(item[1], i++);
            }
            count[maps.get(item[1])]++;
        }
        int size = maps.size();

        return calcClothes(size, 0, 0, 0);
    }

    public static int calcClothes(int size, int idx, int cur, int sum) {
        if (cur == size) {
            return sum;
        } else {
            int temp = sum;
            sum = (sum == 0) ? 1 : sum;
            for (int i = idx; i < size; i++) {
                temp += calcClothes(size, i+1, cur+1, sum*count[i]);
            }
            return temp;
        }
    }
}
