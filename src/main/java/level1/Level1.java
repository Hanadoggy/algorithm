package level1;

import java.util.*;

public class Level1 {

    public static void main(String[] args) throws RuntimeException {

        // level 1

    }

    private static String numberFriend(String X, String Y) {

        int[] countA = new int[10];
        int[] countB = new int[10];
        StringBuilder answer = new StringBuilder();

        for (int i = 0; i < X.length(); i++) {
            countA[Character.getNumericValue(X.charAt(i))]++;
        }
        for (int i = 0; i < Y.length(); i++) {
            countB[Character.getNumericValue(Y.charAt(i))]++;
        }
        for (int i = 9; i >= 0; i--) {
            while (countA[i] > 0 && countB[i] > 0) {
                answer.append(i);
                countA[i]--;
                countB[i]--;
                if (i == 0 && answer.length() == 1) break;
            }

        }

        return (answer.length() == 0) ? "-1" : answer.toString();
    }

    private static int threeGuys(int[] number) {

        int answer = 0;

        for (int i = 0; i < number.length; i++) {
            for (int j = i + 1; j < number.length; j++) {
                for (int k = j + 1; k < number.length; k++) {
                    if (number[i] + number[j] + number[k] == 0) {
                        answer++;
                    }
                }
            }
        }

        return answer;
    }

    private static int emptyCola(int a, int b, int n) {

        int answer = 0;
        while (n >= a) {
            answer += (n / a) * b;
            n = (n / a) * b + (n % a);
        }

        return answer;
    }

    private static int babbling(String[] babbling) {

        boolean flag;
        char before;
        int answer = 0;

        for (String speak : babbling) {
            flag = true;
            before = 'z';
            for (int i = 0; i < speak.length() && flag; i++) {
                if (i + 2 < speak.length() && speak.charAt(i) == 'a' && speak.charAt(i) != before) {
                    flag = speak.charAt(i + 1) == 'y' && speak.charAt(i + 2) == 'a';
                    i += 2;
                    before = 'a';
                } else if (i + 1 < speak.length() && speak.charAt(i) == 'y' && speak.charAt(i) != before) {
                    flag = speak.charAt(i + 1) == 'e';
                    i++;
                    before = 'y';
                } else if (i + 2 < speak.length() && speak.charAt(i) == 'w' && speak.charAt(i) != before) {
                    flag = speak.charAt(i + 1) == 'o' && speak.charAt(i + 2) == 'o';
                    i += 2;
                    before = 'w';
                } else if (i + 1 < speak.length() && speak.charAt(i) == 'm' && speak.charAt(i) != before) {
                    flag = speak.charAt(i + 1) == 'a';
                    i++;
                    before = 'm';
                } else {
                    flag = false;
                }
            }

            if (flag) {
                answer++;
            }
        }

        /**
         * 포함되는 단어들을 공백으로 치환하여 단어의 길이를 세서 공백만 존재할 때 = 발음이 가능한 단어만 존재할 때
         * 위 경우를 세는 방법으로 최적화 가능
         */

        return answer;
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

    private static int fruitDealer(int k, int m, int[] score) {

        int answer = 0;
        int count = m;
        int[] counts = new int[k + 1];
        Arrays.stream(score).forEach(i -> counts[i]++);


        for (int i = k; i > 0; i--) {
            if (counts[i] >= count) {
                counts[i] -= count;
                answer += (m * i++);
                count = m;
            } else {
                count -= counts[i];
                counts[i] = 0;
            }
        }

        return answer;
    }

    private static String foodFight(int[] food) {

        StringBuilder answer = new StringBuilder();
        for (int i = 1; i < food.length; i++) {
            String add = String.format("%d", i).repeat(food[i] / 2);
            answer.append(add);
        }
        answer.append("0");
        for (int i = food.length - 1; i > 0; i--) {
            String add = String.format("%d", i).repeat(food[i] / 2);
            answer.append(add);
        }

        return answer.toString();
    }

    private static int makeHamburger(int[] ingredient) {

        int answer = 0;
        Stack<Integer> stack = new Stack<>(); // 제대로 쌓인 버거들의 최상위 재료

        for (int i : ingredient) {
            if (i == 1) {
                if (!stack.isEmpty() && stack.peek() == 3) {
                    answer++;
                    stack.pop();
                } else {
                    stack.push(1);
                }
            } else if (i == 2) {
                if (!stack.isEmpty() && stack.peek() == 1) {
                    stack.pop();
                    stack.push(2);
                } else {
                    stack.clear();
                }
            } else {
                if (!stack.isEmpty() && stack.peek() == 2) {
                    stack.pop();
                    stack.push(3);
                } else {
                    stack.clear();
                }
            }
        }

        /**
         * 동일한 크기의 배열에 저장하면서 인덱스만 움직이며 확인하는 방식으로도 가능
         * ex) 버거를 완성하면 포인터를 뒤로 미뤄서 계속 탐색
         */

        return answer;
    }
}
