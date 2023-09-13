package level1;

import java.util.*;

public class Level1 {

    public static void main(String[] args) {

    }

    private static String[] RunningRace(String[] players, String[] callings) {

        Map<String, Integer> ranking = new HashMap<>();

        for (int i = 0; i < players.length; i++) {
            String player = players[i];
            ranking.put(player, i + 1);
        }

        for (String calling : callings) {
            int rank = ranking.get(calling);
            String forward = players[rank - 1];
            players[rank - 1] = calling;
            ranking.put(calling, rank - 1);
            players[rank] = forward;
            ranking.put(forward, rank);
        }

        return players;
    }

    private static int[] walkingPark(String[] park, String[] routes) {

        int row = 0;
        int col = 0;

        for (int j = 0; j < park.length; j++) {
            for (int i = 0; i < park[j].length(); i++) {
                if (park[j].charAt(i) == 'S') {
                    row = j;
                    col = i;
                }
            }
        }

        for (String route : routes) {
            String[] word = route.split(" ");
            switch (word[0]) {
                case "E" -> {
                    if (walkingParkCheck(park, row, row, col, col + Integer.parseInt(word[1]))) {
                        col += Integer.parseInt(word[1]);
                    }
                }
                case "W" -> {
                    if (walkingParkCheck(park, row, row, col - Integer.parseInt(word[1]), col)) {
                        col -= Integer.parseInt(word[1]);
                    }
                }
                case "S" -> {
                    if (walkingParkCheck(park, row, row + Integer.parseInt(word[1]), col, col)) {
                        row += Integer.parseInt(word[1]);
                    }
                }
                case "N" -> {
                    if (walkingParkCheck(park, row - Integer.parseInt(word[1]), row, col, col)) {
                        row -= Integer.parseInt(word[1]);
                    }
                }
            }
        }
        return new int[]{row, col};
    }

    private static boolean walkingParkCheck(String[] park, int rs, int re, int cs, int ce) {
        if (cs >= 0 && rs >= 0 && re < park.length && ce < park[0].length()) {
            for (int j = rs; j <= re; j++) {
                for (int i = cs; i <= ce; i++) {
                    if (park[j].charAt(i) == 'X') {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private static int[] wallpaper(String[] wallpaper) {

        int startR = wallpaper.length;
        int startC = wallpaper[0].length();
        int endR = 0;
        int endC = 0;

        for (int j = 0; j < wallpaper.length; j++) {
            for (int i = 0; i < wallpaper[j].length(); i++) {
                if (wallpaper[j].charAt(i) == '#') {
                    startR = Math.min(startR, j);
                    startC = Math.min(startC, i);
                    endR = Math.max(endR, j);
                    endC = Math.max(endC, i);
                }
            }
        }

        return new int[]{startR, startC, endR + 1, endC + 1};
    }

    private static int paintOver(int n, int m, int[] section) {

        int check = 0;
        int count = 0;
        for (int i = 0; i < section.length; i++) {
            if (check == 0 || section[i] >= check + m) {
                check = section[i];
                count++;
            }
        }

        return count;
    }

    private static Integer[] roughKeyboard(String[] keymap, String[] targets) {

        List<Integer> counts = new ArrayList<>();
        Map<Character, Integer> countMap = new HashMap<>();
        for (String key : keymap) {
            for (int i = 0; i < key.length(); i++) {
                if (countMap.containsKey(key.charAt(i))) {
                    if (countMap.get(key.charAt(i)) > i + 1) {
                        countMap.put(key.charAt(i), i + 1);
                    }
                } else {
                    countMap.put(key.charAt(i), i + 1);
                }
            }
        }

        for (String target : targets) {
            int count = 0;
            for (int i = 0; i < target.length(); i++) {
                if (countMap.containsKey(target.charAt(i))) {
                    count += countMap.get(target.charAt(i));
                } else {
                    count = -1;
                    break;
                }
            }
            counts.add(count);
        }

        return counts.toArray(new Integer[0]);
    }

    private static String cardDeck(String[] cards1, String[] cards2, String[] goal) {

        LinkedList<String> deck1 = new LinkedList<>();
        LinkedList<String> deck2 = new LinkedList<>();

        for (String card : cards1) {
            deck1.addLast(card);
        }
        for (String card : cards2) {
            deck2.addLast(card);
        }

        for (String word : goal) {
            if (!deck1.isEmpty() && deck1.getFirst().equals(word)) {
                deck1.removeFirst();
            } else if (!deck2.isEmpty() && deck2.getFirst().equals(word)) {
                deck2.removeFirst();
            } else {
                return "No";
            }
        }

        return "Yes";
    }

    private static String password(String s, String skip, int index) {

        List<Character> checkList = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            Character word = (char) ('a' + i);
            if (!skip.contains(word.toString())) {
                checkList.add(word);
            }
        }

        StringBuilder result = new StringBuilder();
        s.chars().mapToObj(c -> (char) c)
                .forEach(ch -> result.append(
                        checkList.get((checkList.indexOf(ch) + index) % checkList.size()))
                );
        return result.toString();
    }

    private static Integer[] personalInformation(String today, String[] terms, String[] privacies) {
        // every month has 28 days
        final int TOTALDAYS = 28 * 12;
        Map<String, Integer> termMap = new HashMap<>();
        List<Integer> answer = new ArrayList<>();
        int thisDays = ((Integer.parseInt(today.substring(0, 4)) - 2000) * TOTALDAYS) +
                (Integer.parseInt(today.substring(5, 7)) * 28) + Integer.parseInt(today.substring(8));

        for (String term : terms) {
            String[] split = term.split(" ");
            termMap.put(split[0], Integer.parseInt(split[1]));
        }

        for (int i = 0; i < privacies.length; i++) {
            String privacy = privacies[i];
            String[] split = privacy.split(" ");
            int compareDays = ((Integer.parseInt(split[0].substring(0, 4)) - 2000) * TOTALDAYS) +
                    (Integer.parseInt(split[0].substring(5, 7)) * 28) + Integer.parseInt(split[0].substring(8));
            if (thisDays - compareDays >= termMap.get(split[1]) * 28) {
                answer.add(i + 1);
            }
        }

        return answer.toArray(new Integer[0]);
    }

    private static int littleSizeString(String t, String p) {

        int answer = 0;
        long compare = Long.parseLong(p);
        int size = p.length();
        for (int i = 0; i <= t.length() - size; i++) {
            if (compare >= Long.parseLong(t.substring(i, i + size))) {
                answer++;
            }
        }

        return answer;
    }

    private static int[] closeSameChar(String s) {

        int[] count = new int[26];
        Arrays.fill(count, -1);
        int[] answer = new int[s.length()];
        Arrays.fill(answer, -1);

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (count[c - 'a'] > -1) {
                answer[i] = i - count[c - 'a'];
            }
            count[c - 'a'] = i;
        }

        return answer;
    }

    private static int splitString(String s) {

        int same = 0;
        int diff = 0;
        int answer = 0;
        char word = s.charAt(0);

        for (int i = 0; i < s.length(); i++) {
            if (same == 0 && diff == 0) {
                word = s.charAt(i);
            }
            if (word == s.charAt(i)) {
                same++;
            } else {
                diff++;
            }
            if (same == diff) {
                answer++;
                same = 0;
                diff = 0;
            }
        }

        return (same != diff) ? answer + 1 : answer;
    }

    private static int[] hallOfFame1(int k, int[] score) {

        PriorityQueue<Integer> hall = new PriorityQueue<>(k);
        int[] answer = new int[score.length];

        for (int i = 0; i < k && i < score.length; i++) {
            hall.add(score[i]);
            answer[i] = hall.peek();
        }

        for (int i = k; i < score.length; i++) {
            if (hall.peek() < score[i]) {
                hall.poll();
                hall.add(score[i]);
            }
            answer[i] = hall.peek();
        }

        return answer;
    }

    private static int knightsTemplar(int number, int limit, int power) {

        int[] count = new int[number + 1];
        Arrays.fill(count, 1);
        int answer = 1;
        for (int i = 2; i <= number; i++) {
            if (count[i] <= limit) {
                for (int j = i; j <= number; j += i) {
                    if (count[i] <= limit) {
                        count[j]++;
                    }
                }
            }
        }

        for (int i = 2; i <= number; i++) {
            if (count[i] > limit) {
                answer += power;
            } else {
                answer += count[i];
            }
        }

        return answer;
    }
}
