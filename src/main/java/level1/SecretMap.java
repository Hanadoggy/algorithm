package level1;

public class SecretMap {

    /**
     * 비트연산 사용해서 풀이하면 훨씬 간단
     */
    public String[] solution(int n, int[] arr1, int[] arr2) {

        int[][] map1 = solve(n, arr1);
        int[][] map2 = solve(n, arr2);
        String[] answer = new String[n];
        for (int i = 0; i < n; i++) {
            answer[i] = (map1[i][0] == 1 || map2[i][0] == 1) ? "#" : " ";
            for (int j = 1; j < n; j++) {
                answer[i] += (map1[i][j] == 1 || map2[i][j] == 1) ? "#" : " ";
            }
        }

        return answer;
    }

    public static int[][] solve(int size, int[] arr) {

        int[][] result = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (arr[i] >= Math.pow(2, size - 1 - j)) {
                    arr[i] -= Math.pow(2, size - 1 - j);
                    result[i][j] = 1;
                } else {
                    result[i][j] = 0;
                }
            }
        }

        return result;
    }

}
