package level3;

import java.util.*;

public class Kakao2023 {

    public String[] mergeTable(String[] commands) {
        // ??????????
        int[][] table = new int[51][51];
        Map<Integer, String> maps = new HashMap<>();
        List<String> answer = new ArrayList<>();
        final int COL = 51;
        int count = 1;

        maps.put(0, "EMPTY");
        for (String command : commands) {
            String[] split = command.split(" ");
            if (split[0].equals("UPDATE")) {
                if (split.length == 4) {
                    int r = Integer.parseInt(split[1]);
                    int c = Integer.parseInt(split[2]);
                    if (table[r][c] == 0) {
                        table[r][c] = count++;
                    }
                    maps.put(table[r][c], split[3]);
                } else {
                    for (int key : maps.keySet()) {
                        if (maps.get(key).equals(split[1])) {
                            maps.put(key, split[2]);
                            break;
                        }
                    }
                }
            } else if (split[0].equals("MERGE")) {
                int r = Integer.parseInt(split[1]);
                int c = Integer.parseInt(split[2]);
                int r2 = Integer.parseInt(split[3]);
                int c2 = Integer.parseInt(split[4]);
                if (r == r2 && c == c2) {
                    continue;
                }
                if (table[r][c] != 0 && !maps.get(table[r][c]).equals("EMPTY")) {
                    int target = table[r][c];
                    int temp = table[r2][c2];
                    if (temp != 0) {
                        for (int i = 0; i < COL*COL; i++) {
                            if (table[i/COL][i%COL] == temp) {
                                table[i/COL][i%COL] = target;
                            }
                        }
                        maps.remove(temp);
                    }
                    table[r2][c2] = target;
                } else if (table[r2][c2] != 0) {
                    int target = table[r2][c2];
                    int temp = table[r][c];
                    if (temp != 0) {
                        for (int i = 0; i < COL*COL; i++) {
                            if (table[i/COL][i%COL] == temp) {
                                table[i/COL][i%COL] = target;
                            }
                        }
                        maps.remove(temp);
                    }
                    table[r][c] = target;
                } else if (table[r][c] != 0) {
                    table[r2][c2] = table[r][c];
                } else {
                    table[r][c] = count++;
                    table[r2][c2] = table[r][c];
                    maps.put(table[r][c], "EMPTY");
                }
            } else if (split[0].equals("UNMERGE")) {
                int r = Integer.parseInt(split[1]);
                int c = Integer.parseInt(split[2]);
                int idx = table[r][c];
                if (idx == 0) {
                    continue;
                }
                for (int i = 0; i < COL*COL; i++) {
                    if (table[i/COL][i%COL] == idx) {
                        table[i/COL][i%COL] = 0;
                    }
                }
                if (!maps.get(idx).equals("EMPTY")) {
                    table[r][c] = idx;
                } else {
                    maps.remove(idx);
                }
            } else if (split[0].equals("PRINT")){
                answer.add(maps.get(table[Integer.parseInt(split[1])][Integer.parseInt(split[2])]));
            }
        }
        String[] result = new String[answer.size()];
        for (int i = 0; i < answer.size(); i++) {
            result[i] = answer.get(i);
        }
        return result;
    }

    public String escapeMazeCommand(int n, int m, int x, int y, int r, int c, int k) {
        StringBuilder answer = new StringBuilder();
        if (k < getDistance(x,y,r,c) || (k-getDistance(x,y,r,c)) % 2 != 0) {
            return "impossible";
        }
        while (k > getDistance(x,y,r,c)) {
            if (x < n && k > getDistance(x+1,y,r,c)) {
                x++;
                answer.append("d");
            } else if (y > 1 && k > getDistance(x,y-1,r,c)) {
                y--;
                answer.append("l");
            } else if (y < m && k > getDistance(x,y+1,r,c)) {
                y++;
                answer.append("r");
            } else {
                x--;
                answer.append("u");
            }
            k--;
        }
        while (x != r || y != c) {
            if (x < r) {
                x++;
                answer.append("d");
            } else if (y > c) {
                y--;
                answer.append("l");
            } else if (y < c) {
                y++;
                answer.append("r");
            } else {
                x--;
                answer.append("u");
            }
        }
        return answer.toString();
    }

    public int getDistance(int x, int y, int r, int c) {
        return Math.abs(x-r) + Math.abs(y-c);
    }
}
