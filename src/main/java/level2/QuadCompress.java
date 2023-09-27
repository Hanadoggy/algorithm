package level2;

public class QuadCompress {
    public static int[] dr = new int[]{0,0,1,1};
    public static int[] dc = new int[]{0,1,0,1};

    public int[] solution(int[][] arr) {
        return compress(arr, arr.length / 2, 0, 0);
    }

    public static int[] compress(int[][] arr, int depth, int row, int col) {
        if (depth == 0) {
            int[] temp = new int[2];
            temp[arr[row][col]] = 1;
            return temp;
        } else {
            int[] temp = new int[2];
            for (int i = 0; i < 4; i++) {
                int[] result = compress(arr, depth / 2, row+(dr[i]*depth), col+(dc[i]*depth));
                temp[0] += result[0];
                temp[1] += result[1];
            }
            if (temp[0] == 4 && temp[1] == 0) temp[0] = 1;
            if (temp[1] == 4 && temp[0] == 0) temp[1] = 1;
            return temp;
        }
    }
}
