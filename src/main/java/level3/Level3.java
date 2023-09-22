package level3;

import java.util.*;

public class Level3 {

    public static void main(String[] args) {

        System.out.println(aircon(11, 8, 10, 10, 1, new int[]{0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1}));
    }

    private static int aircon(int temperature, int t1, int t2, int a, int b, int[] onboard) {

        // 그리디 풀이는 아닌듯, 완전탐색 아니면 DP?
        List<Integer> list = new ArrayList<>();
        int answer = 0;
        int diff = 0;
        int evenCounts = 0;
        int backup = 0;
        boolean flag = (a < 2 * b); // 키고끄는게 유지보다 저렴
        if (temperature < t1) {
            diff = t1 - temperature;
        } else if (temperature > t2) {
            diff = temperature - t2;
        }

        for (int i = 1, sum = 1; i <= onboard.length; i++) {
            if (i == onboard.length || onboard[i] != onboard[i - 1]) {
                list.add(sum);
                sum = 1;
            } else {
                sum++;
            }
        }
        int[] counts = list.stream().mapToInt(i->i).toArray();

        for (int i = 0; i < counts.length; i++) {
            if (i == 0) {
                answer += diff * a;
            } else if (i + 2 < counts.length) { // 뒤에 탑승이 더 있는 경우
                if (counts[i + 1] >= 2 * diff) {
                    if (counts[i] % 2 == 1) {
                        evenCounts += counts[i] / 2;
                    } else { // 탑승한 시간이 짝수
                        evenCounts += (counts[i] - 1) / 2;
                        answer += Math.min(a, b);
                        if (diff * a >= (counts[i + 1] + 1) * b && a < b) answer -= b;
                    }
                    answer += Math.min(diff * a, (counts[i + 1] + 1) * b);
                } else { // 탑승 간격이 가까운 경우 = 온도 계속 유지
                    int sum = counts[i] + counts[i + 1];
                    if (sum % 2 == 1) {
                        if (a < b) {
                            answer += (backup != 1) ? a : 0;
                            backup = 2;
                        } else {
                            answer += b;
                        }
                    }
                    evenCounts += sum / 2;
                }
                i++;
                backup--;
            } else { // 마지막 탑승인 경우
                if (counts[i] % 2 == 1) {
                    evenCounts += counts[i] / 2;
                } else { // 탑승한 시간이 짝수
                    evenCounts += (counts[i] - 1) / 2;
                    if (a < b) {
                        answer += (backup != 1) ? a : 0;
                    } else {
                        answer += b;
                    }
                }
            }
        }
        answer += (evenCounts) * ((flag) ? a : 2 * b);

        return answer;
    }

}
