package level3;

import java.util.*;

public class DevMatching {

    public int[] sellToothBrush(String[] enroll, String[] referral, String[] seller, int[] amount) {
        Map<String, Node> maps = new HashMap<>();
        int[] answer = new int[enroll.length];

        for (int i = 0; i < enroll.length; i++) {
            maps.put(enroll[i], new Node(referral[i]));
        }
        for (int i = 0; i < seller.length; i++) {
            String name = seller[i];
            int remain = amount[i] * 100;
            while (remain > 0 && !name.equals("-")) {
                int minus = (remain >= 10) ? (remain / 10) : 0;
                Node node = maps.get(name);
                node.earn += (remain - minus);
                maps.put(name, node);
                remain = minus;
                name = node.recommend;
            }
        }
        for (int i = 0; i < enroll.length; i++) {
            answer[i] = maps.get(enroll[i]).earn;
        }
        return answer;
    }

    public static class Node {
        public String recommend;
        public int earn;

        public Node(String recommend) {
            this.recommend = recommend;
            this.earn = 0;
        }
    }
}
