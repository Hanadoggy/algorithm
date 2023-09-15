package level2;

import java.util.*;

import static java.lang.Math.*;

public class Level2 {

    public static void main(String[] args) {


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

    static class Point {
        int r;
        int c;

        public Point() {}

        public Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
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
}
