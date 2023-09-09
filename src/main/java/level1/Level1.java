package level1;

import java.util.*;

public class Level1 {

    public static void main(String[] args) {

//        System.out.println(password("aukks", "wbqd", 5));
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
}
