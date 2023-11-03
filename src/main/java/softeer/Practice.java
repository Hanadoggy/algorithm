package softeer;

import java.util.*;
import java.io.*;

public class Practice {

    public static void main(String[] args) {
        gta();
    }

    public static void scoreAverage() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int students = Integer.parseInt(st.nextToken());
        int conditions = Integer.parseInt(st.nextToken());
        List<Integer> sums = new ArrayList<>();
        StringBuilder answer = new StringBuilder();

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < students; i++) {
            if (sums.isEmpty()) {
                sums.add(Integer.parseInt(st.nextToken()));
            } else {
                sums.add(Integer.parseInt(st.nextToken()) + sums.get(i - 1));
            }
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < conditions; i++) {
            int startIndex = Integer.parseInt(st.nextToken()) - 2;
            int endIndex = Integer.parseInt(st.nextToken()) - 1;
            if (startIndex == -1) {
                answer.append(String.format("%.2f", (double) sums.get(endIndex) / (double) (endIndex + 1)));
                answer.append("\n");
            } else {
                answer.append(String.format("%.2f", (double) sums.get(endIndex) - sums.get(startIndex) / (double) (endIndex - startIndex)));
                answer.append("\n");
            }
        }
        br.close();
        System.out.println(answer);
    }

    public static void gta() {
        Scanner scanner = new Scanner(System.in);
        TreeMap<Integer, Integer> items = new TreeMap<>(Comparator.reverseOrder());

        int remainWeight = scanner.nextInt();
        int types = scanner.nextInt();
        int answer = 0;

        while (types > 0) {
            int weight = scanner.nextInt();
            int price = scanner.nextInt();

            items.put(price, items.getOrDefault(price, 0) + weight);
            types--;
            System.out.printf("%d%d\n", price, weight);
        }
        for (int price : items.keySet()) {
            int weight = items.get(price);

            if (remainWeight > weight) {
                remainWeight -= weight;
                answer += (weight * price);
                System.out.printf("%d, %d\n", remainWeight, answer);
            } else {
                answer += ((weight - remainWeight) * price);
                System.out.printf("%d, %d\n", remainWeight, answer);
                break;
            }
        }
        System.out.println(answer);
    }

}
