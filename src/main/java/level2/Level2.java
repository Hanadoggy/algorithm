package level2;

import java.math.BigDecimal;
import java.util.*;

import static java.lang.Math.*;

public class Level2 {

    public static void main(String[] args) {

        // level 2

    }

    private static String[] openChat(String[] record) {
        Map<String, String> nicknames = new HashMap<>();
        List<String> answer = new ArrayList<>();

        for (int i = record.length - 1; i >= 0; i--) {
            String[] split = record[i].split(" ");
            if (!split[0].equals("Leave")) {
                nicknames.putIfAbsent(split[1], split[2]);
            }
        }
        for (String log : record) {
            String[] split = log.split(" ");
            if (split[0].equals("Enter")) {
                answer.add(String.format("%s님이 들어왔습니다.", nicknames.get(split[1])));
            } else if (split[0].equals("Leave")) {
                answer.add(String.format("%s님이 나갔습니다.", nicknames.get(split[1])));
            }
        }
        return answer.stream().toArray(String[]::new);
    }

    private static int candidateKey(String[][] relation) {
        int len = relation[0].length;
        Set<String> keys = new HashSet<>();
        int[] index = new int[len+1];
        int answer = 0;

        for (int count = 0; index[len] == 0; count++) {
            StringBuilder key = new StringBuilder();
            boolean check = true;

            index[0]++;
            for (int i = 0; i < len; i++) {
                if(index[i] == 2) {
                    index[i] = 0;
                    index[i+1]++;
                }
            }
            if (index[len] == 1) break;
            for (int i = 0; i < len; i++) {
                if (index[i] == 1) {
                    key.append(i);
                }
            }
            for (String dup : keys) {
                int keyCount = 0;
                for (int i = 0; i < dup.length() && check; i++) {
                    if (key.toString().contains(dup.substring(i,i+1))) {
                        keyCount++;
                    }
                }
                if (keyCount == dup.length()) {
                    check = false;
                    break;
                }
            }
            if (check) {
                Set<String> duplicated = new HashSet<>();

                for (String[] record : relation) {
                    StringBuilder entity = new StringBuilder();
                    for (int k = 0; k < len; k++) {
                        if (index[k] == 1) {
                            entity.append(record[k]);
                        }
                    }
                    if (duplicated.contains(entity.toString())) {
                        check = false;
                        break;
                    }
                    duplicated.add(entity.toString());
                }
            }
            if (check) {
                answer++;
                keys.add(key.toString());
            }
        }
        return answer;
    }

    private static int targetNumber(int[] numbers, int target) {
        Stack<int[]> stack = new Stack<>();
        int answer = 0;

        stack.push(new int[]{0, -numbers[0]});
        stack.push(new int[]{0, numbers[0]});
        while (!stack.isEmpty()) {
            int[] pop = stack.pop(); // idx, sum
            if (pop[0] == numbers.length - 2) {
                if (pop[1] + numbers[pop[0]+1] == target || pop[1] - numbers[pop[0]+1] == target) {
                    answer++;
                }
            } else {
                stack.push(new int[]{pop[0]+1, pop[1]+numbers[pop[0]+1]});
                stack.push(new int[]{pop[0]+1, pop[1]-numbers[pop[0]+1]});
            }
        }
        return answer;
    }

    private static int skillTrees(String skill, String[] skill_trees) {
        int answer = 0;

        for (String trees : skill_trees) {
            int prioriIdx = 0;
            for (int i = 0; i <= trees.length(); i++) {
                if (i == trees.length()) {
                    answer++;
                } else if (skill.contains(trees.substring(i,i+1))) {
                    if (skill.charAt(prioriIdx) != trees.charAt(i)) {
                        break;
                    }
                    prioriIdx++;
                }
            }
        }
        return answer;
    }

    private static int visitedLength(String dirs) {
        boolean[][][] visited = new boolean[11][11][4];
        int[] dr = new int[]{0,0,1,-1};
        int[] dc = new int[]{1,-1,0,0};
        int[] reverseIdx = new int[]{1,0,3,2};
        int answer = 0;

        dirs = dirs.replaceAll("R","0")
                .replaceAll("L","1")
                .replaceAll("D","2")
                .replaceAll("U","3");
        for (int i = 0, row = 5, col = 5; i < dirs.length(); i++) {
            int idx = Character.getNumericValue(dirs.charAt(i));
            if (row+dr[idx] >= 0 && row+dr[idx] < 11 && col+dc[idx] >= 0 && col+dc[idx] < 11) {
                if (!visited[row][col][idx]) {
                    answer++;
                }
                visited[row][col][idx] = true;
                row += dr[idx];
                col += dc[idx];
                visited[row][col][reverseIdx[idx]] = true;
            }
        }
        return answer;
    }

    private static int compressString(String s) {
        int answer = s.length();

        for (int len = 1; len <= s.length() / 2; len++) {
            StringBuilder compressed = new StringBuilder();
            int idx = len;
            int count = 1;

            while (idx < s.length()) {
                if (idx + len < s.length()) {
                    if (s.substring(idx-len, idx).equals(s.substring(idx,idx+len))) {
                        count++;
                    } else {
                        compressed.append(((count > 1) ? count : "")).append(s, idx-len, idx);
                        count = 1;
                    }
                } else if (idx + len == s.length()) {
                    if (s.substring(idx-len, idx).equals(s.substring(idx,idx+len))) {
                        compressed.append(((++count > 1) ? count : "")).append(s, idx-len, idx);
                    } else {
                        compressed.append(((count > 1) ? count : "")).append(s, idx-len, idx);
                        compressed.append(s, idx, idx+len);
                    }
                } else {
                    compressed.append(((count > 1) ? count : "")).append(s, idx-len, idx);
                    compressed.append(s.substring(idx));
                }
                idx += len;
            }
            answer = Math.min(answer, compressed.length());
        }
        return answer;
    }

    public static String bracketTransform(String word) {
        if (word.isEmpty()) {
            return "";
        } else {
            Stack<Character> stack = new Stack<>();
            StringBuilder left = new StringBuilder();
            StringBuilder right = new StringBuilder();
            int idx = 0;

            for (int count = 0; idx < word.length(); idx++) {
                if (word.charAt(idx) == '(') {
                    count++;
                } else {
                    count--;
                }
                if (count == 0) {
                    left.append(word, 0, ++idx);
                    break;
                }
            }
            if (idx < word.length() - 1) {
                right.append(word.substring(idx));
            }
            for (idx = 0; idx < left.length(); idx++) {
                if (left.charAt(idx) == '(') {
                    stack.push('(');
                } else {
                    if (stack.isEmpty()) {
                        break;
                    } else {
                        stack.pop();
                    }
                }
            }
            if (stack.isEmpty() && idx == left.length()) {
                return left + bracketTransform(right.toString());
            } else {
                return "(" + bracketTransform(right.toString()) +
                        ")" +
                        left.substring(1, left.length() - 1)
                                .replaceAll("\\(", "t")
                                .replaceAll("\\)", "(")
                                .replaceAll("t", ")");
            }
        }
    }

    private static long intactSquare(int w, int h) {
        long answer = 0;

        for (int i = 1; i < w; i++) {
            answer += ((long) h * i) / (long) w;
        }
        return answer * 2;
    }

    private static int[] getTuple(String s) {
        String collect = s.substring(2, s.length() - 2);
        if (!collect.contains(",")) {
            return new int[]{Integer.parseInt(collect)};
        }
        String[] split = collect.split("\\},\\{");
        Set<Integer> numSet = new HashSet<>();
        List<Integer> answer = new ArrayList<>();

        Arrays.sort(split);
        for (String col : split) {
            if (col.length() == 1) {
                numSet.add(Integer.parseInt(col));
                answer.add(Integer.parseInt(col));
            } else {
                String[] nums = col.split(",");
                for (String num : nums) {
                    int item = Integer.parseInt(num);
                    if (!numSet.contains(item)) {
                        numSet.add(item);
                        answer.add(item);
                    }
                }
            }
        }
        return answer.stream().mapToInt(i->i).toArray();
    }

    private static long maximizeOperations(String expression) {
        List<Long> operands = new ArrayList<>();
        List<Character> operators = new ArrayList<>();
        String[] priorities = new String[]{"+-*","+*-","-+*","-*+","*+-","*-+"};
        long answer = 0;
        int beforeIndex = 0;

        for (int i = 0; i < expression.length(); i++) {
            if ("+-*".contains(expression.substring(i,i+1))) {
                operands.add(Long.parseLong(expression.substring(beforeIndex, i)));
                operators.add(expression.charAt(i));
                beforeIndex = i + 1;
            }
        }
        operands.add(Long.parseLong(expression.substring(beforeIndex)));
        for (int i = 0; i < 6; i++) {
            List<Long> numCopy = new ArrayList<>(operands);
            List<Character> opCopy = new ArrayList<>(operators);

            for (int j = 0; j < 3; j++) {
                for (int idx = 0; idx < opCopy.size(); idx++) {
                    if (opCopy.get(idx) == priorities[i].charAt(j)) {
                        if (opCopy.get(idx) == '+') {
                            numCopy.set(idx, numCopy.get(idx) + numCopy.get(idx+1));
                        } else if (opCopy.get(idx) == '-') {
                            numCopy.set(idx, numCopy.get(idx) - numCopy.get(idx+1));
                        } else if (opCopy.get(idx) == '*') {
                            numCopy.set(idx, numCopy.get(idx) * numCopy.get(idx+1));
                        }
                        numCopy.remove(idx+1);
                        opCopy.remove(idx--);
                    }
                }
            }
            answer = Math.max(answer, Math.abs(numCopy.get(0)));
        }
        return answer;


    }

    private static int[] triangleSnail(int n) {
        int[] answer = new int[(n*(n+1))/2];
        int idx = 0;
        int number = 1;
        int row = n;
        int col = 0;
        int depth = 1;

        while (number <= answer.length && answer[idx] == 0) {
            answer[idx] = number++;
            if (depth == row && idx < answer.length - 1 && answer[idx+1] == 0) {
                idx++;
            } else if (idx == (depth*(depth-1))/2 + col) {
                idx += depth++;
            } else {
                idx -= depth--;
                if (answer[idx] > 0) {
                    col++;
                    idx += (depth*2 +2);
                    depth += 2;
                    row--;
                }
            }
        }
        return answer;
    }

    public static int[] repeatBinaryTransform(String s) {
        return binaryTransform(s, new int[2]);
    }

    public static int[] binaryTransform(String num, int[] answer) {
        if (num.length() > 1) {
            int count = 0;
            for (int i = 0; i < num.length(); i++) {
                if (num.charAt(i) == '0') {
                    count++;
                }
            }
            answer[1] += count;
            answer[0]++;
            return binaryTransform(Integer.toBinaryString(num.length() - count), answer);
        } else {
            return answer;
        }
    }

    private static int turnBracket(String s) {
        int answer = 0;
        Stack<Character> stack = new Stack<>();

        if (s.length() % 2 == 0) {
            for (int i = 0; i < s.length(); i++) {
                stack.clear();
                for (int index = i; index < s.length() + i; index++) {
                    char bracket = s.charAt(index % s.length());
                    if ((bracket == ')' || bracket == '}' || bracket == ']') &&
                            !stack.isEmpty()) {
                        Character topBracket = stack.pop();
                        if (!((topBracket == '(' && bracket == ')') ||
                                (topBracket == '{' && bracket == '}') ||
                                (topBracket == '[' && bracket == ']'))) {
                            answer--;
                            break;
                        }
                    } else if ((bracket == ')' || bracket == '}' || bracket == ']')) {
                        answer--;
                        break;
                    } else {
                        stack.add(bracket);
                    }
                }
                answer++;
            }
        }
        return answer;
    }

    private static int[] turnMatrixBorder(int rows, int columns, int[][] queries) {
        List<Integer> answer = new ArrayList<>();
        int[][] board = new int[rows][columns];
        int[] dr = new int[]{0,1,0,-1};
        int[] dc = new int[]{1,0,-1,0};

        for (int i = 0; i < rows * columns; i++) {
            board[i/columns][i%columns] = i + 1;
        }
        for (int[] turn : queries) {
            for (int i = 0; i < 4; i++) {
                turn[i]--;
            }
            int min = rows * columns;
            int row = turn[0];
            int col = turn[1];
            int before = board[turn[0]][turn[1]];
            int temp = before;
            int idx = -1;
            for (int i = 0; i < (turn[2]+turn[3]-turn[0]-turn[1])*2; i++) {
                if ((row==turn[0]&&col==turn[1]) || (row==turn[2]&&col==turn[1]) ||
                        (row==turn[2]&&col==turn[3]) || (row==turn[0]&&col==turn[3])) {
                    idx = (idx+1)%4;
                }
                row += dr[idx];
                col += dc[idx];
                temp = board[row][col];
                min = Math.min(min, temp);
                board[row][col] = before;
                before = temp;
            }
            answer.add(min);
        }
        return answer.stream().mapToInt(i->i).toArray();
    }

    private static long[] diffBit(long[] numbers) {
        List<Long> answer = new ArrayList<>();
        Set<Long> fullBinaryNumbers = new HashSet<>();

        for (long i = 1; Math.pow(2, i)-1 <= Math.pow(10, 16); i++) {
            fullBinaryNumbers.add((long) Math.pow(2, i)-1);
        }
        for (long number : numbers) {
            StringBuilder binaryNumber = new StringBuilder(Long.toBinaryString(number));
            if (fullBinaryNumbers.contains(number)) {
                binaryNumber.setCharAt(0, '0');
                binaryNumber.insert(0, "1");
            } else {
                for (int i = binaryNumber.length()-1; i >= 0; i--) {
                    if (binaryNumber.charAt(i) == '0') {
                        if (i < binaryNumber.length()-1) {
                            binaryNumber.setCharAt(i+1,'0');
                        }
                        binaryNumber.setCharAt(i, '1');
                        break;
                    }
                }
            }
            answer.add(Long.parseLong(binaryNumber.toString(), 2));
        }
        return answer.stream().mapToLong(i -> i).toArray();
    }

    private static int[] distancing(String[][] places) {
        // BFS
        int[] dr = new int[]{0,1,0,-1};
        int[] dc = new int[]{1,0,-1,0};
        Queue<int[]> list = new LinkedList<>();
        List<Integer> answer = new ArrayList<>();
        for (String[] room : places) {
            int[][] stage = new int[5][5];
            boolean check = true;
            for (int i = 0; i < 25; i++) {
                int r = i / 5;
                int c = i % 5;
                if (room[r].charAt(c) == 'P' && stage[r][c] == 0) {
                    list.add(new int[]{r,c});
                    stage[r][c] = 1;
                    while (!list.isEmpty()) {
                        int[] cur = list.poll();
                        for (int j = 0; j < 4; j++) {
                            if (cur[0]+dr[j] >= 0 && cur[0]+dr[j] < 5 && cur[1]+dc[j] >= 0 && cur[1]+dc[j] < 5 &&
                                    stage[cur[0]][cur[1]] < 3 && stage[cur[0]+dr[j]][cur[1]+dc[j]] == 0) {
                                if (room[cur[0]+dr[j]].charAt(cur[1]+dc[j]) == 'P') {
                                    list.clear();
                                    i = 25;
                                    check = false;
                                    break;
                                } else if (room[cur[0]+dr[j]].charAt(cur[1]+dc[j]) != 'X') {
                                    stage[cur[0]+dr[j]][cur[1]+dc[j]] = stage[cur[0]][cur[1]] + 1;
                                    list.add(new int[]{cur[0]+dr[j],cur[1]+dc[j]});
                                }
                            }

                        }
                    }
                }
            } // place
            answer.add((check) ? 1 : 0);
        } // rooms

        return answer.stream().mapToInt(i->i).toArray();
    }

    private static int vowelDictionary(String word) {
        // 완전 탐색
        int[] match = new int[]{1,0,0,0,0};
        Map<String, Integer> maps = new HashMap<>();
        String[] alpha = new String[]{"", "A", "E", "I", "O", "U"};
        int count = 0;

        while (match[0] < 6) {
            maps.put(String.format("%s%s%s%s%s",
                            alpha[match[0]], alpha[match[1]], alpha[match[2]],
                            alpha[match[3]], alpha[match[4]]),
                    ++count);
            for (int i = 0; i < 5; i++) {
                if (match[i] == 0 || i == 4) {
                    match[i]++;
                    break;
                }
            }
            for (int i = 4; i > 0; i--) {
                if (match[i] == 6) {
                    match[i] = 0;
                    match[i-1]++;
                }
            }
        }

        return maps.get(word);
    }

    private static int[] lightCycle(String[] grid) {

        int row = grid.length;
        int col = grid[0].length();
        Map<Character, int[][]> maps = new HashMap<>();
        boolean[][][] board = new boolean[row][col][4];
        List<Integer> answer = new ArrayList<>();

        maps.put('S', new int[][]{{0,-1,0},{0,1,1},{-1,0,2},{1,0,3}});
        maps.put('L', new int[][]{{1,0,3},{-1,0,2},{0,-1,0},{0,1,1}});
        maps.put('R', new int[][]{{-1,0,2},{1,0,3},{0,1,1},{0,-1,0}});

        for (int i = 0; i < row * col; i++) {
            for (int j = 0; j < 4; j++) {
                int r = i / col;
                int c = i % col;
                int sum = 0;
                int[] start = new int[]{r, c, j};

                while (!board[r][c][j]) {
                    board[r][c][j] = true;
                    sum++;
                    int[] next = maps.get(grid[r].charAt(c))[j];
                    r = (row + r + (next[0])) % row;
                    c = (col + c + (next[1])) % col;
                    j = next[2];
                }
                if (r == start[0] && c == start[1] && j == start[2] && sum > 0) {
                    answer.add(sum);
                }
            }
        }

        answer.sort(null);
        return answer.stream().mapToInt(i->i).toArray();
    }

    private static int splitPower(int n, int[][] wires) {

        int answer = Integer.MAX_VALUE;
        Map<Integer, HashSet<Integer>> maps = new HashMap<>();
        Queue<Integer> list = new LinkedList<>();
        boolean[] check = new boolean[n + 1];

        for(int[] wire : wires) {
            if (maps.containsKey(wire[0]))  {
                maps.get(wire[0]).add(wire[1]);
            } else {
                HashSet<Integer> temp = new HashSet<>();
                temp.add(wire[1]);
                maps.put(wire[0], temp);
            }
            if (maps.containsKey(wire[1]))  {
                maps.get(wire[1]).add(wire[0]);
            } else {
                HashSet<Integer> temp = new HashSet<>();
                temp.add(wire[0]);
                maps.put(wire[1], temp);
            }
        }

        for (int[] wire : wires) {
            int sum1 = 1;
            int sum2 = 1;
            list.add(1);
            while (!list.isEmpty()) {
                int cur = list.poll();
                check[cur] = true;
                for (int num : maps.get(cur)) {
                    if (((cur != wire[0] || num != wire[1]) && (cur != wire[1] || num != wire[0]))
                            && !check[num]) {
                        list.add(num);
                        sum1++;
                    }
                }
            }
            list.add((check[wire[0]]) ? wire[1] : wire[0]);
            while (!list.isEmpty()) {
                int cur = list.poll();
                check[cur] = true;
                for (int num : maps.get(cur)) {
                    if (((cur != wire[0] || num != wire[1]) && (cur != wire[1] || num != wire[0]))
                            && !check[num]) {
                        list.add(num);
                        sum2++;
                    }
                }
            }
            Arrays.fill(check, false);
            answer = Math.min(answer, Math.abs(sum1 - sum2));
        }

        return answer;
    }

    private static String[] drawStarts(int[][] line) {

        Map<Integer, HashSet<Integer>> maps = new HashMap<>();
        int[] max = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE};
        int[] min = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};

        for (int i = 0; i < line.length; i++) {
            for (int j = i + 1; j < line.length; j++) {
                long x = ((long) line[i][1] * line[j][2] - (long) line[i][2] * line[j][1]);
                long y = ((long) line[i][2] * line[j][0] - (long) line[i][0] * line[j][2]);
                long under = ((long) line[i][0] * line[j][1] - (long) line[i][1] * line[j][0]);
                if (under != 0) {
                    if (x % under == 0 && y % under == 0) {
                        max[0] = Math.max(max[0], (int) (x / under));
                        max[1] = Math.max(max[1], (int) (y / under));
                        min[0] = Math.min(min[0], (int) (x / under));
                        min[1] = Math.min(min[1], (int) (y / under));
                        if (maps.containsKey((int) (x / under))) {
                            maps.get((int) (x / under)).add((int) (y / under));
                        } else {
                            HashSet<Integer> temp = new HashSet<>();
                            temp.add((int) (y / under));
                            maps.put((int) (x / under), temp);
                        }
                    }
                }
            }
        }

        String[] answer = new String[max[1] - min[1] + 1];
        for (int r = max[1]; r >= min[1]; r--) {
            StringBuilder draw = new StringBuilder();
            for (int c = min[0]; c <= max[0]; c++) {
                if (maps.containsKey(c) && maps.get(c).contains(r)) {
                    draw.append("*");
                } else {
                    draw.append(".");
                }
            }
            answer[max[1] - r] = draw.toString();
        }

        return answer;
    }

    private static int[] cutArray(int n, long left, long right) {

        int[] answer = new int[(int) (right - left + 1)];
        for (long i = left; i < right + 1; i++) {
            int row = (int) (i / n);
            int col = (int) (i % n);
            answer[(int) (i - left)] = (col <= row) ? (row + 1) : (col + 1);
        }

        return answer;
    }

    private static int fatigue(int k, int[][] dungeons) {

        int answer = 0;
        int len = dungeons.length;
        boolean[] checks = new boolean[len];

        for (int i = 0; i < len; i++) {
            checks[i] = true;
            answer = Math.max(answer, calc(dungeons, checks, i, 0, k));
            checks[i] = false;
        }

        return answer;
    }

    private static int calc(int[][] dungeons, boolean[] checks, int idx, int sum, int health) {
        int temp = sum;
        if (health >= dungeons[idx][0]) {
            health -= dungeons[idx][1];
            sum++;
        }
        for (int i = 0; i < dungeons.length; i++) {
            if (!checks[i]) {
                checks[i] = true;
                temp = Math.max(temp, calc(dungeons, checks, i, sum, health));
                checks[i] = false;
            }
        }
        return Math.max(temp, sum);
    }

    private static int getPrimeCounts(int n, int k) {

        int answer = 0;
        StringBuilder transform = new StringBuilder();

        for (int i = 0; Math.pow(k, i) < n; i++) {
            int input = (int) (n / Math.pow(k, i)) % k;
            transform.append((input == 0) ? "0" : input);
        }
        String word = transform.reverse().toString();

        for (int i = 1; i <= word.length(); i++) {
            for (int idx = 0; idx + i <= word.length(); idx++) {
                boolean flag = true;
                String sub = word.substring(idx, i + idx);
                if (!sub.contains("0")) {
                    long num = Long.parseLong(sub);
                    if (idx > 0 && !(word.charAt(idx - 1) == '0')) continue;
                    if (idx + i < word.length() && !(word.charAt(i + idx) == '0')) continue;
                    for (long j = 2; j <= Math.sqrt(num); j++) {
                        if (num % j == 0) flag = false;
                    }
                    if (flag && num > 1) answer++;
                }
            }
        }

        return answer;
    }

    private static int[] calculateParkingFee(int[] fees, String[] records) {
        // 너무 지저분한듯, 더 나은 방법??
        Map<Integer, Integer> in = new HashMap<>();
        Map<Integer, Integer> out = new HashMap<>();
        List<Integer> answer = new ArrayList<>();

        for (String record : records) {
            int time = Integer.parseInt(record.substring(0, 2)) * 60 + Integer.parseInt(record.substring(3, 5));
            int car = Integer.parseInt(record.substring(6, 10));
            if (record.substring(11).equals("IN")) {
                if (in.containsKey(car)) {
                    in.put(car, time - out.get(car) + in.get(car));
                    out.remove(car);
                } else {
                    in.put(car, time);
                }
            } else {
                out.put(car, time);
            }
        }

        int[] cars = in.keySet().stream().sorted().mapToInt(i -> i).toArray();
        for (int car : cars) {
            int start = in.get(car);
            int time = out.getOrDefault(car, (23 * 60) + 59) - start - fees[0];
            int price = fees[1] +
                    ((time <= 0) ?  0 :
                            ((time % fees[2] == 0) ? (time / fees[2]) : ((time / fees[2]) + 1)) * fees[3]);
            answer.add(price);
        }

        return answer.stream().mapToInt(i->i).toArray();
    }

    private static int[] archery(int n, int[] info) {

        int[] answer = new int[11];
        int[] place = new int[11];
        int max = 0;
        int enemyScore = 0;

        for (int i = 0; i < 11; i++) {
            if (info[i] > 0) {
                enemyScore += (10 - i);
            }
        }

        for (int i = 1; i < Math.pow(2, 11); i++) {
            Arrays.fill(place, 0);
            int shoot = n;
            String bi = Integer.toBinaryString(i);
            int diff = -enemyScore;
            boolean flag = false;

            for (int j = bi.length() - 1, idx = 0; j >= 0 && shoot > 0; j--) {
                int check = Character.getNumericValue(bi.charAt(j));
                if (j == 0) {
                    place[idx] = shoot;
                } else if (check == 1 && info[idx] < shoot) {
                    place[idx] = info[idx] + 1;
                    diff += (info[idx] == 0) ? (10 - idx) : 2 * (10 - idx);
                    shoot -= info[idx] + 1;
                }
                idx++;
            } // 모든 경우의 수 탐색

            if (diff == max) {
                for (int j = 10; j >= 0; j--) {
                    if (place[j] > answer[j]) {
                        flag = true;
                        break;
                    } else if (place[j] < answer[j]) {
                        break;
                    }
                }
            }
            if (diff > max || flag) {
                System.arraycopy(place, 0, answer, 0, 11);
                max = diff;
            } // 더 많은 점수를 따거나 동점에서 더 적은 점수를 많이 쏘면 답안 교체
        }

        return (max == 0) ? new int[]{-1} : answer;
    }

    private static int makeTwoQueueSame(int[] queue1, int[] queue2) {

        int len = queue1.length;
        long[] list = new long[len * 2];
        long sum1 = 0;
        long sum2 = 0;
        long target = 0;
        int idxS = 0;
        int idxE = len;
        int answer = 0;

        for (int i = 0; i < len; i++) {
            list[i] = (long) queue1[i];
            list[i + len] = (long) queue2[i];
            sum1 += (long) queue1[i];
            sum2 += (long) queue2[i];
        }

        if ((sum1 + sum2) % 2 == 1) return -1;
        target = (sum1 + sum2) / 2;

        while (sum1 != target) {
            if (sum1 > target) {
                sum1 -= list[idxS++ % (2 * len)];
                answer++;
            } else {
                sum1 += list[idxE++ % (2 * len)];
                answer++;
            }
            if (sum1 != target && (idxS == 2 * len || idxE == 3 * len)) return -1;
        }

        return answer;
    }

    private static int discountEvent(String[] want, int[] number, String[] discount) {

        int answer = 0;
        int days = 0;
        int len = want.length;
        boolean flag = true;
        Map<String, Integer> transform = new HashMap<>();
        int[] counts = new int[len + 1];

        for (int i = 0; i < len; i++) {
            transform.put(want[i], i);
            days += number[i];
        }
        for (int i = 0; i < days; i++) {
            counts[transform.getOrDefault(discount[i], len)]++;
        }

        for (int i = days; i < discount.length; i++) {
            flag = true;
            for (int j = 0; j < len && flag; j++) {
                if (counts[j] != number[j]) flag = false;
            }
            if (flag) answer++;
            counts[transform.getOrDefault(discount[i], len)]++;
            counts[transform.getOrDefault(discount[i - days], len)]--;
        }
        flag = true;
        for (int j = 0; j < len && flag; j++) {
            if (counts[j] != number[j]) flag = false;
        }
        if (flag) answer++; // 마지막 횟수 카운트

        return answer;
    }

    private static int playAlone(int[] cards) {

        int len = cards.length;
        int[] check = new int[len];

        for (int start = 0; start < len; start++) {
            int idx = start;
            int sum = 0;
            while (check[idx] > -1) {
                if (check[idx] == 0) {
                    check[idx] = -1;
                    sum++;
                } else if (idx != start) {
                    sum += check[idx];
                    check[idx] = -1;
                    break;
                } else {
                    break;
                }
                idx = cards[idx] - 1;
            }
            check[idx] = sum;
        }

        Arrays.sort(check);

        return (check[len - 2] < 1) ? 0 : (check[len - 2] * check[len - 1]);
    }

    private static int circleSum(int[] elements) {
        // set 사용하기
        boolean[] check = new boolean[1000001];
        int answer = 0;
        int fullLen = elements.length;

        for (int len = 1, sum = 0, counts = 0; len < fullLen; len++, sum = 0, counts = 0) {
            for (int i = 0; i < len; i++) {
                sum += elements[i];
            }

            for (int idx = 0; idx < fullLen; idx++) {
                sum += (elements[(idx + len) % fullLen] - elements[idx % fullLen]);
                if (!check[sum]) {
                    counts++;
                    check[sum] = true;
                }
            }
            answer += counts;
        }

        return answer + 1;
    }

    private static int deliveryBox(int[] order) {

        boolean[] check = new boolean[order.length + 1];
        int before = 0;
        int answer = 0;

        for (int box : order) {
            if (box + 1 < before) {
                for (int i = before - 1; i > box; i--) {
                    if (!check[i]) return answer;
                }
            }
            check[box] = true;
            before = box;
            answer++;
        }


        return answer;
    }

    private static int cutRollcake(int[] topping) {

        int[][] types = new int[10001][2];
        int answer = 0;
        int big = 0;
        int little = 0;

        for (int i = 0; i < topping.length; i++) {
            if (types[topping[i]][1]++ == 0) {
                little++;
            }
        }

        for (int i = 0; i < topping.length - 1; i++) {
            if (types[topping[i]][0]++ == 0) {
                big++;
            }
            if (--types[topping[i]][1] == 0) {
                little--;
            }
            if (big == little) {
                answer++;
            }
        }

        return answer;
    }

    private static double[] collatzIntegration(int k, int[][] ranges) {

        double[] answer = new double[ranges.length];
        List<Double> stack = new ArrayList<>();
        while (k > 1) {
            if (k % 2 == 0) {
                stack.add((double) 3 * k / 4);
                k /= 2;
            } else {
                stack.add(((double) (4 * k) + 1) / 2);
                k = (3 * k) + 1;
            }
        }

        for (int j = 0; j < ranges.length; j++) {
            if (ranges[j][0] < ranges[j][1] + stack.size()) {
                for (int i = ranges[j][0]; i < (stack.size() + ranges[j][1]); i++) {
                    answer[j] += stack.get(i);
                }
            } else if (ranges[j][0] > ranges[j][1] + stack.size()){
                answer[j] = -1;
            }
        }

        return answer;
    }

    private static int splitNumberCards(int[] arrayA, int[] arrayB) {

        TreeSet<Integer> setB = new TreeSet<Integer>();
        TreeSet<Integer> setA = new TreeSet<Integer>();
        List<Integer> stack = new ArrayList<>();

        Arrays.stream(arrayA).forEach(setA::add);
        Arrays.stream(arrayB).forEach(setB::add);

        int gcdA = setA.first();
        for (Integer i : setA) {
            gcdA = findGCD(i, gcdA);
        }

        int gcdB = setB.first();
        for (Integer i : setB) {
            gcdB = findGCD(i, gcdB);
            if (gcdA != 0 && i % gcdA == 0) gcdA = 0;
        }

        for (Integer i : setA) {
            if (gcdB != 0 && i % gcdB == 0) gcdB = 0;
        }

        return Math.max(gcdA, gcdB);
    }

    private static int findGCD(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return findGCD(b, a % b);
        }
    }

    private static int selectOrange(int k, int[] tangerine) {

        Arrays.sort(tangerine);
        int len = tangerine.length;
        List<Integer> counts = new ArrayList<>();

        if (tangerine.length == 1) return 1;
        for (int i = 1, before = 0; i < len; i++) {
            if (i == len - 1) {
                if (tangerine[len - 1] != tangerine[len - 2]) {
                    counts.add(len - 1 - before);
                    counts.add(1);
                } else {
                    counts.add(len - before);
                }
            } else if (tangerine[i - 1] != tangerine[i]) {
                counts.add(i - before);
                before = i;
            }
        }

        counts.sort(Comparator.reverseOrder());
        for (int i = 0; i < counts.size(); i++) {
            if (k <= counts.get(i)) {
                return i + 1;
            }
            k -= counts.get(i);
        }

        return counts.size();
    }

    private static long pickDots(int k, int d) {

        long answer = 0;
        for (int x = 0; x <= d; x += k) {
            int y = (int) Math.floor(Math.sqrt(Math.pow(d, 2) - Math.pow(x, 2)));
            answer += (y / k) + 1;
        }

        return answer;
    }

    private static int defenseGame(int n, int k, int[] enemy) {

        int index = 0;
        TreeMap<Integer, Integer> stack = new TreeMap<>(); // deleted from k

        for (int damage : enemy) {
            if (k > 0) {
                stack.put(damage, (stack.containsKey(damage)) ? (stack.get(damage) + 1) : 1);
                k--;
            } else if (stack.firstKey() < damage && stack.firstKey() <= n) {
                int min = stack.firstKey();
                if (stack.get(min) == 1) {
                    stack.remove(min);
                    stack.put(damage, 1);
                } else {
                    stack.put(min, (stack.get(min) - 1));
                }
                n -= min;
            } else if (n >= damage) {
                n -= damage;
            } else {
                return index;
            }
            index++;
        }

        return index;
    }

    private static int tableHash(int[][] data, int col, int row_begin, int row_end) {

        int answer = 0;
        int len = data[0].length;
        List<int[]> transform = new ArrayList<>(List.of(data));
        transform.sort(Comparator.comparingInt((int[] o) -> o[col - 1])
                .thenComparingInt(o -> -o[0]));

        for (int i = row_begin - 1; i < row_end; i++) {
            int cur = 0;
            for (int j = 0; j < len; j++) {
                cur += (transform.get(i)[j] % (i + 1));
            }
            answer = (i == row_begin - 1) ? cur : answer ^ cur;
        }

        return answer;
    }

    private static int cantor(int n, int l, int r) {
        return calc(n, r, 0) - calc(n, l - 1, 0);
    }

    private static int calc(int n, long index, int sum) {
        if (n == 1) {
            if (index < 3) sum += (int) index;
            if (index >= 3) sum += (int) (index - 1);
            return sum;
        } else {
            long multi = index / (long) Math.pow(5, n-1);
            if (multi < 2) sum += (int) (Math.pow(4, n-1) * multi);
            if (multi > 2) sum += (int) (Math.pow(4, n-1) * (multi - 1));
            if (multi == 2) return sum + (int) (Math.pow(4, n-1) * 2);
            if ((index % (long) Math.pow(5, n-1)) == 0) return sum;
            return calc(n - 1, (index % (long) Math.pow(5, n-1)), sum);
        }
    }

    private static int magicElevator(int storey) {

        int answer = 0;
        int before = 0;
        String number = Integer.toString(storey);
        for (int i = number.length() - 1; i >= 0; i--) {
            int num = Character.getNumericValue(number.charAt(i));
            if (before == 5) {
                answer += 5;
                before = (num < 5) ? num : num + 1;
            } else if (before < 5) {
                answer += before;
                before = num;
            } else {
                answer += (10 - before);
                before = num + 1;
            }
        }

        return answer + ((before > 5) ? (10 - before) + 1 : before);
    }

    private static int[] sellEmoticons(int[][] users, int[] emoticons) {

        int len = emoticons.length;
        int[][] discountPrices = new int[len][4];
        int[] index = new int[len];
        int maxSubs = 0;
        int maxSell = 0;

        for (int[] user : users) {
            user[0] = (user[0] / 10) - ((user[0] % 10 > 0) ? 0 : 1);
        }

        for (int i = 0; i < len; i++) {
            discountPrices[i][0] = (emoticons[i] / 10) * 9;
            discountPrices[i][1] = (emoticons[i] / 10) * 8;
            discountPrices[i][2] = (emoticons[i] / 10) * 7;
            discountPrices[i][3] = (emoticons[i] / 10) * 6;
        }

        for (int i = 0; i < Math.pow(4, len); i++) { // 0000000 ~ 3333333
            int subs = 0;
            int sell = 0;
            int[] levelSum = new int[4]; // 할인폭 이모티콘 가격 합
            for (int j = 0; j < len; j++) { // j번째 이모티콘의 할인가를 할인 레벨의 가격 합에 추가
                levelSum[index[j]] += discountPrices[j][index[j]];
            }

            for (int[] user : users) { // 사람 별 구매, 구독 결정
                int sum = levelSum[3];
                if (user[0] <= 2) sum += levelSum[2];
                if (user[0] <= 1) sum += levelSum[1];
                if (user[0] == 0) sum += levelSum[0];
                if (sum >= user[1]) {
                    subs++;
                } else {
                    sell += sum;
                }
            }

            if (subs > maxSubs) {
                maxSubs = subs;
                maxSell = sell;
            } else if (subs == maxSubs && sell > maxSell) {
                maxSell = sell;
            }

            index[0]++;
            if (index[0] == 4) {
                index[0] = 0;
                index[1]++;
                for (int j = 1; j < len; j++) {
                    if (index[j] == 4 && j < len - 1) {
                        index[j] = 0;
                        index[j + 1]++;
                    }
                }
            }
        }

        return new int[]{maxSubs, maxSell};
    }

    private static long seesaw(int[] weights) {

        long answer = 0;
        int[] people = new int[2002];

        for (int weight : weights) {
            people[weight]++;
        }

        for (int i = 100; i < 1001; i++) {
            long count = people[i];
            if (count != 0) {
                answer += ((count * (count - 1)) / 2);
                if (i % 2 == 0 && people[(i / 2) * 3] != 0) {
                    answer += (people[(i / 2) * 3] * count);
                }
                if (people[i * 2] != 0) {
                    answer += (people[i * 2] * count);
                }
                if (i % 3 == 0 && people[(i / 3) * 4] != 0) {
                    answer += (people[(i / 3) * 4] * count);
                }
            }
        }

        return answer;
    }

    private static int changeNumber(int x, int y, int n) {

        Queue<Integer> queue = new LinkedList<>();
        int[] answer = new int[1000001];
        queue.add(x);

        if (x == y) return 0;

        while (!queue.isEmpty()) {
            x = queue.poll();
            if (x + n == y || x * 2 == y || x * 3 == y) {
                return answer[x] + 1;
            } else {
                if (x + n < y && answer[x + n] == 0) {
                    queue.add(x + n);
                    answer[x + n] = answer[x] + 1;
                }
                if (x * 2 < y && answer[x * 2] == 0) {
                    queue.add(x * 2);
                    answer[x * 2] = answer[x] + 1;
                }
                if (x * 3 < y && answer[x * 3] == 0) {
                    queue.add(x * 3);
                    answer[x * 3] = answer[x] + 1;
                }
            }
        }

        return -1;
    }

    private static int[] backBigInt(int[] numbers) {

        int lastMax = 0;
        Stack<Integer> stack = new Stack<>();
        int[] answer = new int[numbers.length];
        for (int i = numbers.length - 1; i >= 0; i--) {
            if (numbers[i] >= lastMax) {
                answer[i] = -1;
                stack.clear();
                stack.push(numbers[i]);
                lastMax = numbers[i];
            } else {
                if (numbers[i] < stack.peek()) {
                    answer[i] = stack.peek();
                    stack.push(numbers[i]);
                } else if (numbers[i] == stack.peek()) {
                    stack.pop();
                    answer[i] = stack.peek();
                    stack.push(numbers[i]);
                } else {
                    while (numbers[i] >= stack.peek()) {
                        stack.pop();
                    }
                    answer[i] = stack.peek();
                    stack.push(numbers[i]);
                }
            }
        }

        return answer;
    }

    private static int[] desertIsland(String[] maps) {

        int rows = maps.length;
        int cols = maps[0].length();

        int[][] stage = new int[rows][cols];
        boolean[][] visited = new boolean[rows][cols];
        List<Integer> answer = new ArrayList<>();
        Stack<Point> stack = new Stack<>();
        int[] dr = new int[]{0, 1, 0, -1};
        int[] dc = new int[]{1, 0, -1, 0};
        int sum = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maps[i].charAt(j) != 'X') stage[i][j] = Integer.parseInt(maps[i].substring(j, j + 1));
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum = 0;
                if (stage[i][j] > 0 && !visited[i][j]) {
                    sum += stage[i][j];
                    stack.push(new Point(i, j));
                    visited[i][j] = true;
                    while (!stack.isEmpty()) {
                        Point cur = stack.pop();
                        for (int k = 0; k < 4; k++) {
                            if (stage[cur.r + dr[k]][cur.c + dc[k]] > 0 && !visited[cur.r + dr[k]][cur.c + dc[k]]) {
                                stack.push(new Point(cur.r + dr[k], cur.c + dc[k]));
                                visited[cur.r + dr[k]][cur.c + dc[k]] = true;
                                sum += stage[cur.r + dr[k]][cur.c + dc[k]];
                            }
                        }
                    }
                }
                if (sum != 0) answer.add(sum);
            }
        }

        answer.sort(null);

        return answer.stream().mapToInt(i -> i).toArray();
    }

    private static int hotel(String[][] book_time) {

        PriorityQueue<Integer> roomEndTime = new PriorityQueue<>();
        List<Room> transform = new ArrayList<>();

        for (String[] book : book_time) {
            transform.add(new Room(Integer.parseInt(book[0].substring(0, 2)) * 60 + Integer.parseInt(book[0].substring(3)),
                    Integer.parseInt(book[1].substring(0, 2)) * 60 + Integer.parseInt(book[1].substring(3)) + 10));
        }

        transform.sort(Comparator.comparingInt(o -> o.start));
        transform.forEach(r -> {
            if (!roomEndTime.isEmpty() && roomEndTime.peek() <= r.start) roomEndTime.poll();
            roomEndTime.add(r.end);
        });

        return roomEndTime.size();
    }

    private static int escape(String[] maps) {

        boolean flag = false;
        int len = maps[0].length();
        Queue<Point> stack = new LinkedList<>();
        int[][] stage = new int[maps.length][len];
        int[] dr = new int[]{0, 1, 0, -1};
        int[] dc = new int[]{1, 0, -1, 0};
        Point exit = new Point();

        for (int i = 0; i < maps.length; i++) {
            for (int j = 0; j < len; j++) {
                if (maps[i].charAt(j) == 'S') {
                    stack.add(new Point(i, j));
                } else if (maps[i].charAt(j) == 'X') {
                    stage[i][j] = -1;
                } else if (maps[i].charAt(j) == 'E') {
                    exit.r = i;
                    exit.c = j;
                } else if (maps[i].charAt(j) == 'L') {
                    stage[i][j] = -3;
                }
            }
        }

        while (!stack.isEmpty()) {
            Point cur = stack.poll();
            for (int i = 0; i < 4; i++) {
                if (cur.r + dr[i] >= 0 && cur.r + dr[i] < maps.length && cur.c + dc[i] >= 0 && cur.c + dc[i] < len &&
                        stage[cur.r + dr[i]][cur.c + dc[i]] != -1) {
                    if (stage[cur.r + dr[i]][cur.c + dc[i]] == -3) {
                        int temp = stage[cur.r][cur.c] + 1;
                        for (int x = 0; x < maps.length; x++) {
                            for (int y = 0; y < len; y++) {
                                stage[x][y] = Math.min(stage[x][y], 0);
                            }
                        }
                        flag = true;
                        stack.clear();
                        stage[cur.r + dr[i]][cur.c + dc[i]] = temp;
                        stack.add(new Point(cur.r + dr[i], cur.c + dc[i]));
                        break;
                    } else if (flag && cur.r + dr[i] == exit.r && cur.c + dc[i] == exit.c) {
                        return stage[cur.r][cur.c] + 1;
                    } else if (stage[cur.r + dr[i]][cur.c + dc[i]] == 0) {
                        stack.add(new Point(cur.r + dr[i], cur.c + dc[i]));
                        stage[cur.r + dr[i]][cur.c + dc[i]] = stage[cur.r][cur.c] + 1;
                    }
                }
            }
        }

        return -1;
    }

    private static int tictaekto(String[] board) {

        int countO = 0;
        int countX = 0;
        int winO = 0;
        int winX = 0;
        boolean flagO = false;
        boolean flagX = false;
        int[][] stage = new int[3][3];
        int[][] bingo = new int[][]{{0, 0, 0, 0, 1, 2}, {1, 1, 1, 0, 1, 2}, {2, 2, 2, 0, 1, 2}, {0, 1, 2, 0, 0, 0},
                {0, 1, 2, 1, 1, 1}, {0, 1, 2, 2, 2, 2}, {0, 1, 2, 0, 1, 2}, {2, 1, 0, 0, 1, 2}};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i].charAt(j) == 'O') {
                    countO++;
                    stage[i][j] = 1;
                } else if (board[i].charAt(j) == 'X') {
                    countX++;
                    stage[i][j] = -1;
                }
            }
        }

        if (countX > countO || countO - countX > 1) return 0;

        for (int i = 0; i < 8; i++) {
            winO = 0;
            winX = 0;
            for (int j = 0; j < 3; j++) {
                if (stage[bingo[i][j]][bingo[i][j+3]] == 1) {
                    winO++;
                } else if (stage[bingo[i][j]][bingo[i][j+3]] == -1) {
                    winX++;
                }
            }
            if (winO == 3) flagO = true;
            if (winX == 3) flagX = true; // 동시에 두 개의 빙고를 가지면서 이길 수도 있음!
            if (flagO && flagX) return 0;
            if (flagX && countO != countX) return 0;
            if (flagO && countO == countX) return 0;
        }

        return 1;
    }

    private static int[] billiards(int m, int n, int startX, int startY, int[][] balls) {
        // 완전 수학, 같은 라인 제거할때 방향 고려하기 . 당구연습
        List<Integer> answer = new ArrayList<>();
        int[] dx = new int[]{m, 0, startX, startX};
        int[] dy = new int[]{startY, startY, 0, n};
        int min;

        for (int[] ball : balls) {
            min = Integer.MAX_VALUE;
            for (int i = 0; i < 4; i++) {
                if (i == 0 && startY == ball[1] && startX < ball[0]) continue;
                if (i == 1 && startY == ball[1] && startX > ball[0]) continue;
                if (i == 2 && startX == ball[0] && startY > ball[1]) continue;
                if (i == 3 && startX == ball[0] && startY < ball[1]) continue;
                int subX = startX + (dx[i] - startX) * 2;
                int subY = startY + (dy[i] - startY) * 2;
                min = Math.min(min, (int) (Math.pow(subX - ball[0], 2) + Math.pow(subY - ball[1], 2)));
            }
            answer.add(min);
        }

        return answer.stream().mapToInt(i->i).toArray();
    }

    private static int ricochatRobot(String[] board) {

        int len = board[0].length();
        int[][] stage = new int[board.length][len];
        Queue<Point> list = new LinkedList<>();
        int[] dr = new int[]{0, 1, 0, -1};
        int[] dc = new int[]{1, 0, -1, 0};

        for(int r = 0; r < board.length; r++) {
            for (int c = 0; c < len; c++) {
                stage[r][c] = (board[r].charAt(c) == 'D') ? -1 : 0;
                if (board[r].charAt(c) == 'R') {
                    list.add(new Point(r, c));
                    stage[r][c] = 1;
                }
                if (board[r].charAt(c) == 'G') {
                    stage[r][c] = -2;
                }
            }
        }

        while (!list.isEmpty()) {
            Point cur = list.poll();
            for (int i = 0; i < 4; i++) {
                Point find = new Point(cur.r, cur.c);
                while (true) {
                    if (find.r + dr[i] < 0 || find.r + dr[i] >= board.length || find.c + dc[i] < 0 || find.c + dc[i] >= len ||
                            stage[find.r + dr[i]][find.c + dc[i]] == -1) {
                        if (stage[find.r][find.c] == -2) {
                            return stage[cur.r][cur.c];
                        }
                        if (stage[find.r][find.c] == 0) {
                            stage[find.r][find.c] = stage[cur.r][cur.c] + 1;
                            list.add(find);
                        }
                        break;
                    }
                    find.r += dr[i];
                    find.c += dc[i];
                }
            }
        }

        return -1;
    }

    private static int mining(int[] picks, String[] minerals) {
        // 풀이 참조, 그리디
        // 광물의 티어가 5묶음 마다 상위티어와 동일 -> 5묶음에서 고티어의 돌이 많은 순 대로 좋은 곡괭이 사용해야 함
        Map<String, Integer> price = new HashMap<>();
        List<Integer> stack = new ArrayList<>();
        int count = Arrays.stream(picks).sum();
        int sum = 0;

        price.put("diamond", 100);
        price.put("iron", 10);
        price.put("stone", 1);

        for (int i = 0; i < minerals.length; i++) {
            sum += price.get(minerals[i]);
            if (i % 5 == 4 || i + 1 == minerals.length) {
                stack.add(sum);
                sum = 0;
                if (--count == 0) break;
            }
        }

        sum = 0;
        stack.sort(Comparator.reverseOrder());
        for (int score : stack) {
            if (picks[0] > 0) {
                sum += score / 100;
                score %= 100;
                sum += score / 10;
                score %= 10;
                sum += score;
                picks[0]--;
            } else if (picks[1] > 0) {
                sum += (score / 100) * 5;
                score %= 100;
                sum += score / 10;
                score %= 10;
                sum += score;
                picks[1]--;
            } else if (picks[2] > 0) {
                sum += (score / 100) * 25;
                score %= 100;
                sum += (score / 10) * 5;
                score %= 10;
                sum += score;
                picks[2]--;
            }
        }

        return sum;
    }

    private static String minMaxSol(String s) {
        String[] split = s.split(" ");
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (String word : split) {
            int num = Integer.parseInt(word);
            max = max(num, max);
            min = min(num, min);
        }

        return String.format("%d %d", min, max);
    }

    private static int intercept(int[][] targets) { // 풀이 참고

        Arrays.sort(targets, Comparator.comparing(a -> a[1]));
        int pivot = 0;
        int answer = 0;
        for (int[] target : targets) {
            if (target[0] >= pivot) {
                answer++;
                pivot = target[0];
            }
        }

        return answer;
    }

    private static long twoCircle(int r1, int r2) {

        long count = 0;
        int y;
        int height;
        for (int x = 1; x <= r2; x++) {
            y = (int) Math.ceil(Math.sqrt(Math.pow(r1, 2) - Math.pow(x, 2)));
            height = (int) Math.floor(Math.sqrt(Math.pow(r2, 2) - Math.pow(x, 2)));
            count += (height - y + 1);
        }

        return count * 4; // 오타 수정
    }

    private static int[] partition(int[] sequence, int k) {

        int resultX = 0;
        int resultY = sequence.length;
        int index;
        int[] counts = new int[sequence.length];
        counts[0] = sequence[0];
        for (int i = 1; i < sequence.length; i++) {
            counts[i] = counts[i - 1] + sequence[i];
        }
        for (int i = 0; i < counts.length; i++) {
            index = Arrays.binarySearch(counts, k + counts[i] - sequence[i]);
            if (index >= 0 && index - i < resultY - resultX) {
                resultY = index;
                resultX = i;
            }
        }

        return new int[]{resultX, resultY};
    }

    private static String[] solveAssignment(String[][] plans) {

        List<String> result = new ArrayList<>();
        Stack<Assignment> stack = new Stack<>();
        List<Assignment> list = new ArrayList<>();
        for (String[] plan : plans) {
            String[] time = plan[1].split(":");
            list.add(new Assignment(plan[0], Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]),
                    Integer.parseInt(plan[2])));
        }
        list.sort(Comparator.comparing(assignment -> assignment.start));

        int beforeTime = 0;
        for (Assignment assignment : list) {
            int leftTime = assignment.start - beforeTime;
            while (!stack.isEmpty() && leftTime > 0) {
                Assignment pop = stack.pop();
                if (leftTime >= pop.playtime) {
                    leftTime -= pop.playtime;
                    result.add(pop.name);
                } else {
                    stack.push(new Assignment(pop.name, pop.start, pop.playtime - leftTime));
                    break;
                }
            }
            beforeTime = assignment.start;
            stack.push(assignment);
        }

        while (!stack.isEmpty()) {
            result.add(stack.pop().name);
        }

        return result.toArray(new String[0]);
    }

    static class Assignment {
        public String name;
        public int start;
        public int playtime;

        public Assignment(String name, int start, int playtime) {
            this.name = name;
            this.start = start;
            this.playtime = playtime;
        }
    }

    static class Point {
        int r;
        int c;

        public Point() {}

        public Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static class Room {
        int start;
        int end;
        public Room(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
