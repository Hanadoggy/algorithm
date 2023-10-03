package level2;

import java.util.HashSet;
import java.util.Set;

public class FindPrimeNumbers {
    static Set<Integer> primes = new HashSet<>();
    static int answer = 0;

    public int solution(String numbers) {
        final int MAX_NUM = 10000000;
        final int MAX_SQRT = (int) Math.sqrt(MAX_NUM);
        boolean[] primeCheck = new boolean[MAX_NUM + 1];

        for (int i = 2; i <= MAX_NUM; i++) {
            if (!primeCheck[i] && i <= MAX_SQRT) {
                for (int j = i * i; j < MAX_NUM; j += i) {
                    primeCheck[j] = true;
                }
                primes.add(i);
            } else if (!primeCheck[i]) {
                primes.add(i);
            }
        }
        for (int i = 0; i < numbers.length(); i++) {
            check("", new boolean[numbers.length()], numbers);
        }
        return answer;
    }

    static void check(String number, boolean[] check, String original) {
        if (!number.isEmpty() && primes.contains(Integer.parseInt(number))) {
            answer++;
            primes.remove(Integer.parseInt(number));
        } else {
            for (int i = 0; i < check.length; i++) {
                if (!check[i]) {
                    check[i] = true;
                    check((number + original.charAt(i)), check, original);
                    check[i] = false;
                }
            }
        }
    }
}
