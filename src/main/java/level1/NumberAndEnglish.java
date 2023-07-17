package level1;

public class NumberAndEnglish {

    public static int solution(String s) {

        int[] answer = new int[50];
        int j = 0;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                answer[j++] = Character.getNumericValue(s.charAt(i));
            } else {
                String temp = String.valueOf(s.charAt(i)) + s.charAt(i + 1);
                switch (temp.toUpperCase()) {
                    case "ZE" -> { answer[j++] = 0; i += 3; }
                    case "ON" -> { answer[j++] = 1; i += 2; }
                    case "TW" -> { answer[j++] = 2; i += 2; }
                    case "TH" -> { answer[j++] = 3; i += 4; }
                    case "FO" -> { answer[j++] = 4; i += 3; }
                    case "FI" -> { answer[j++] = 5; i += 3; }
                    case "SI" -> { answer[j++] = 6; i += 2; }
                    case "SE" -> { answer[j++] = 7; i += 4; }
                    case "EI" -> { answer[j++] = 8; i += 4; }
                    case "NI" -> { answer[j++] = 9; i += 3; }
                }
            }
        }

        int output = answer[--j];
        for (int i = j - 1; i >= 0; i--) {
            output += answer[i] * Math.pow(10, (j - i));
        }

        return output;
    }

    public static void main(String[] args) {
        solution("123");
    }

}
