package level3;

import java.util.*;

public class Kakao2019 {
    Map<String, Set<String>> matching = new HashMap<>();
    Set<String> answer = new HashSet<>();

    public int jumpSteppingStone(int[] stones, int k) {
        PriorityQueue<Integer> steps = new PriorityQueue<>(Comparator.reverseOrder());
        List<Integer> counts = new ArrayList<>();
        int answer = Integer.MAX_VALUE;

        for (int i = 0; i < k; i++) {
            steps.add(stones[i]);
        }
        for (int i = k; i < stones.length; i++) {
            counts.add(steps.peek());
            steps.remove(stones[i - k]);
            steps.add(stones[i]);
        }
        for (int count : counts) {
            answer = Math.min(answer, count);
        }
        return answer;
    }

    public int solution(String[] users, String[] bans) {
        for (String user : users) {
            for (String ban : bans) {
                if (exclude(user, ban)) {
                    Set<String> matches = matching.getOrDefault(ban, new HashSet<>());
                    matches.add(user);
                    matching.put(ban, matches);
                }
            }
        }
        calculate(users, bans, new boolean[users.length], 0);
        return answer.size();
    }

    public void calculate(String[] users, String[] bans, boolean[] checked, int cur) {
        if (cur == bans.length) {
            StringBuilder banned = new StringBuilder();
            for (boolean check : checked) {
                banned.append(check ? "1" : "0");
            }
            answer.add(banned.toString());
            return;
        }
        for (String match : matching.get(bans[cur])) {
            int index = -1;
            for (int i = 0; i < users.length; i++) {
                if (!checked[i] && users[i].equals(match)) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                continue;
            }
            checked[index] = true;
            calculate(users, bans, checked, cur + 1);
            checked[index] = false;
        }
    }

    public boolean exclude(String userName, String banName) {
        char[] user = userName.toCharArray();
        char[] ban = banName.toCharArray();
        if (user.length != ban.length) {
            return false;
        }
        for (int i = 0; i < user.length; i++) {
            if (ban[i] != '*' && user[i] != ban[i]) {
                return false;
            }
        }
        return true;
    }

}
