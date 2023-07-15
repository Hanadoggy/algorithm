package level1;

public class MBTITest {

    public String solution(String[] survey, int[] choices) {

        int[] score = new int[]{0, 0, 0, 0}; // RCJA + TFMN -
        for (int i = 0; i < survey.length; i++) {
            switch (survey[i]) {
                case "RT", "TR" -> score[0] += survey[i].equals("RT") ? (4 - choices[i]) : -(4 - choices[i]);
                case "CF", "FC" -> score[1] += survey[i].equals("CF") ? (4 - choices[i]) : -(4 - choices[i]);
                case "JM", "MJ" -> score[2] += survey[i].equals("JM") ? (4 - choices[i]) : -(4 - choices[i]);
                case "AN", "NA" -> score[3] += survey[i].equals("AN") ? (4 - choices[i]) : -(4 - choices[i]);
            }
        }

        return String.format("%c%c%c%c",
                score[0] >= 0 ? 'R' : 'T',
                score[1] >= 0 ? 'C' : 'F',
                score[2] >= 0 ? 'J' : 'M',
                score[3] >= 0 ? 'A' : 'N');
    }

}
