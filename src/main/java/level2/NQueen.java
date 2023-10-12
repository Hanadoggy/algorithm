package level2;

public class NQueen {
    public boolean[] check = new boolean[30];
    public boolean[] crossL = new boolean[30];
    public boolean[] crossR = new boolean[30];
    public int answer = 0;

    public int solution(int n) {
        calcQueen(0, n);
        return answer;
    }

    public void calcQueen(int cur, int n) {
        if (cur == n) {
            answer++;
            return;
        }
        for (int i = 0; i < n; i++) {
            if (!(check[i] || crossL[i + cur] || crossR[cur + n - i - 1])) {
                check[i] = true;
                crossL[i + cur] = true;
                crossR[cur + n - i - 1] = true;
                calcQueen(cur+1, n);
                check[i] = false;
                crossL[i + cur] = false;
                crossR[cur + n - i - 1] = false;
            }
        }
    }
}
