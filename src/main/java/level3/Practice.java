package level3;

import java.util.*;

public class Practice {

    public int[] bestAlbum(String[] genres, int[] plays) {
        Map<String, Integer> genrePlays = new HashMap<>();
        List<Album> albums = new ArrayList<>();
        int totalSize = genres.length;
        List<Integer> answer = new ArrayList<>();

        for (int i = 0; i < totalSize; i++) {
            albums.add(new Album(i, genres[i], plays[i]));
            genrePlays.put(genres[i], genrePlays.getOrDefault(genres[i], 0) + plays[i]);
        }
        for (Album album : albums) {
            album.totalPlay = genrePlays.get(album.genre);
        }
        albums.sort(Comparator.comparing((Album a) -> -a.totalPlay)
                .thenComparing((Album a) -> -a.play)
                .thenComparing((Album a) -> a.number));
        for (int i = 0, count = 0; i < totalSize; i++) {
            if (i > 0 && !albums.get(i).genre.equals(albums.get(i-1).genre)) {
                count = 0;
            }
            if (count < 2) {
                answer.add(albums.get(i).number);
                count++;
            }
        }
        return answer.stream().mapToInt(i->i).toArray();
    }

    static class Album {
        int number;
        String genre;
        int play;
        int totalPlay;

        Album(int number, String genre, int play) {
            this.number = number;
            this.genre = genre;
            this.play = play;
        }
    }

    public int setUpBaseStations(int n, int[] stations, int w) {
        int answer = 0;
        int cur = 1;
        int div = (2 * w) + 1;

        for (int station : stations) {
            int distance = station - w - cur;
            if (distance > 0) {
                answer += (distance / div) + (distance % div > 0 ? 1 : 0);
            }
            cur = Math.max(cur, station + w + 1);
        }
        int distance = n + 1 - cur;
        if (distance > 0) {
            answer += (distance / div) + (distance % div > 0 ? 1 : 0);
        }
        return answer;
    }

    public int speedCamera(int[][] routes) {
        Arrays.sort(routes, Comparator.comparing(i -> i[1]));
        int answer = 0;
        int pivot = -30001;

        for (int[] route : routes) {
            if (route[0] > pivot) {
                answer++;
                pivot = route[1];
            }
        }
        return answer;
    }

    public int numberGame(int[] A, int[] B) {
        int teamSize = A.length;
        Deque<Integer> scoreA = new ArrayDeque<>(teamSize);
        Deque<Integer> scoreB = new ArrayDeque<>(teamSize);
        int answer = 0;

        Arrays.sort(A);
        Arrays.sort(B);
        for (int i = 0; i < teamSize; i++) {
            scoreA.addLast(A[i]);
            scoreB.addLast(B[i]);
        }
        for (int i = 0; i < teamSize; i++) {
            if (scoreA.pollLast() < scoreB.peekLast()) {
                answer++;
                scoreB.pollLast();
            }
        }
        return answer;
    }

    public int wayToSchool(int m, int n, int[][] puddles) {
        final int DIV = 1_000_000_007;
        int[][] counts = new int[n][m];

        counts[0][0] = 1;
        for (int[] puddle : puddles) {
            counts[puddle[1] - 1][puddle[0] - 1] = -1;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i > 0 && counts[i][j] > -1 && counts[i - 1][j] > -1) {
                    counts[i][j] += counts[i - 1][j] % DIV;
                }
                if (j > 0 && counts[i][j] > -1 && counts[i][j - 1] > -1) {
                    counts[i][j] += counts[i][j - 1] % DIV;
                }
            }
        }
        return counts[n - 1][m - 1] % DIV;
    }

    public int solution(String begin, String target, String[] inputs) {
        Set<String> words = new HashSet<>();
        Queue<String> nextWord = new LinkedList<>();
        Map<String, Integer> result = new HashMap<>();

        for (String input : inputs) {
            words.add(input);
        }
        if (!words.contains(target)) {
            return 0;
        }
        nextWord.add(begin);
        result.put(begin, 0);
        while (!nextWord.isEmpty()) {
            String cur = nextWord.poll();
            for (String notVisit : Set.copyOf(words)) {
                if (checkDiff(cur, notVisit)) {
                    nextWord.add(notVisit);
                    words.remove(notVisit);
                    result.put(notVisit, result.get(cur) + 1);
                    if (notVisit.equals(target)) {
                        nextWord.clear();
                        break;
                    }
                }
            }
        }
        return result.getOrDefault(target, 0);
    }

    private boolean checkDiff(String cur, String next) {
        for (int i = 0, diff = 0; i < cur.length(); i++) {
            if (cur.charAt(i) != next.charAt(i)) {
                diff++;
            }
            if (diff == 2) {
                return false;
            }
        }
        return true;
    }

    public int[] bestSet(int n, int s) {
        int div = s / n;
        int remain = s % n;
        if (div == 0) {
            return new int[]{-1};
        }
        int[] answer = new int[n];

        Arrays.fill(answer, div);
        for (int i = n - 1; i >= 0 && remain > 0; i--) {
            remain--;
            answer[i]++;
        }
        return answer;
    }

    public long leftWork(int n, int[] works) {
        Map<Integer, Integer> leftWorks = new HashMap<>();
        long answer = 0;

        for (int work : works) {
            leftWorks.put(work, leftWorks.getOrDefault(work, 0) + 1);
        }
        List<Integer> times = new ArrayList<>(leftWorks.keySet());
        times.sort(Comparator.reverseOrder());
        for (int i = 0; i < times.size() && n > 0; i++) {
            int currentTime = times.get(i);
            int nextLeftTime = i == times.size() - 1 ? 0 : times.get(i + 1);
            int minusTime = (currentTime - nextLeftTime) * leftWorks.get(currentTime);
            if (n >= minusTime) {
                n -= minusTime;
                if (nextLeftTime != 0) {
                    leftWorks.put(nextLeftTime, leftWorks.get(nextLeftTime) + leftWorks.get(currentTime));
                    leftWorks.remove(currentTime);
                } else {
                    leftWorks.clear();
                }
            } else {
                int currentLeft = leftWorks.get(currentTime);
                leftWorks.remove(currentTime);
                leftWorks.put(currentTime - (n / currentLeft), currentLeft - (n % currentLeft));
                if (n % currentLeft != 0) {
                    leftWorks.put(currentTime - ((n / currentLeft) + 1),
                            leftWorks.getOrDefault(currentTime - ((n / currentLeft) + 1), 0) +
                                    (n % currentLeft));
                }
                n = 0;
            }
        }
        for (Integer key : leftWorks.keySet()) {
            answer += (long) Math.pow(key, 2) * leftWorks.get(key);
        }
        return answer;
    }

    public int[] expressBinaryTree(long[] numbers) {
        int[] answer = new int[numbers.length];

        for (int i = 0; i < numbers.length; i++) {
            answer[i] = check(addZero(Long.toBinaryString(numbers[i])));
        }
        return answer;
    }

    private int check(String number) {
        Queue<int[]> visit = new LinkedList<>();

        visit.add(new int[]{number.length(), number.length()/2});
        while (!visit.isEmpty()) {
            int[] cur = visit.poll();
            int move = Math.abs(cur[0] - cur[1]) / 2;

            if (move > 0) {
                if (number.charAt(cur[1]) == '0' &&
                        (number.charAt(cur[1]-move) == '1' || number.charAt(cur[1]+move) == '1')) {
                    return 0;
                }
                visit.add(new int[]{cur[1], cur[1]-move});
                visit.add(new int[]{cur[1], cur[1]+move});
            }
        }
        return 1;
    }

    private String addZero(String original) {
        for (int i = 0; original.length() >= Math.pow(2, i); i++) {
            if (original.length() < Math.pow(2, i+1)) {
                return "0".repeat((int) Math.pow(2, i+1) - original.length() - 1) + original;
            }
        }
        return original;
    }

    public int personnelReview(int[][] scores) {
        int[] target = new int[]{scores[0][0], scores[0][1]};
        List<int[]> list = new ArrayList<>();
        int answer = 1;

        Arrays.sort(scores,
                Comparator.comparing((int[] i) -> -i[0])
                        .thenComparing((int[] i) -> -i[1]));
        for (int i = 0, top = 0, temp = 0; i < scores.length; i++) {
            if (i == 0) {
                temp = scores[0][1];
            } else if (scores[i][0] < scores[i-1][0]) {
                top = Math.max(top, temp);
                temp = scores[i][1];
            }
            if (scores[i][1] >= top) {
                list.add(scores[i]);
            }
        }
        list.sort(Comparator.comparing((int[] i) -> -i[0] - i[1]));
        for (int i = 0, temp = 0; i < list.size(); i++) {
            if (i > 0 && list.get(i)[0] + list.get(i)[1] < list.get(i-1)[0] + list.get(i-1)[1]) {
                answer += temp;
                temp = 1;
            } else {
                temp++;
            }
            if (list.get(i)[0] == target[0] && list.get(i)[1] == target[1]) {
                return answer;
            }
        }
        return -1;
    }

    public long continuousPurseArraySum(int[] sequence) {
        long answer = Long.MIN_VALUE;

        for (int i = 0; i < 2; i++) {
            answer = Math.max(answer, getMax(sequence, i));
        }
        return answer;
    }

    public long getMax(int[] sequence, int cond) {
        long[] result = new long[sequence.length];

        result[0] = (0 == cond) ? sequence[0] : -sequence[0];
        for (int i = 1; i < sequence.length; i++) {
            int add = (i % 2 == cond) ? sequence[i] : -sequence[i];
            result[i] = Math.max(0, result[i-1]) + add;
        }
        return Arrays.stream(result).max().getAsLong();
    }

    public int vanguard(int n) {
        long[] answer = new long[100_001];
        long[] temp = new long[]{8,0,2};
        final long DIV = 1_000_000_007;

        answer[1] = 1L;
        answer[2] = 3L;
        answer[3] = 10L;
        for (int i = 4; i <= n; i++) {
            int idx = i % 3;

            answer[i] = temp[idx] + ((i%3 == 0) ? 4 : 2);
            answer[i] += (answer[i-1] + 2*answer[i-2] + 5*answer[i-3]);
            answer[i] %= DIV;
            temp[idx] += (2*answer[i-1] + 2*answer[i-2] + 4*answer[i-3]);
            temp[idx] %= DIV;
        }
        return (int) answer[n];
    }
}
