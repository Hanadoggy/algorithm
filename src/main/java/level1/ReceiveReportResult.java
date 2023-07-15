package level1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReceiveReportResult {

    public int[] solution(String[] id_list, String[] report, int k) {

        int[] answer = new int[id_list.length];
        Map<String, ArrayList<String>> save = new HashMap<>();
        Map<String, Integer> count = new HashMap<>();
        for (String i : id_list) {
            count.put(i, 0);
            save.put(i, new ArrayList<>());
        }

        for (String r : report) {
            String[] split = r.split(" ");
            if (!save.get(split[0]).contains(split[1])) {
                save.get(split[0]).add(split[1]);
                count.compute(split[1], (key, v) -> v + 1);
            }
        }

        for (String id : id_list) {
            if (count.get(id) >= k) {
                for (int j = 0; j < id_list.length; j++) {
                    if (save.get(id_list[j]).contains(id)) {
                        answer[j]++;
                    }
                }
            }
        }

        return answer;
    }
}
