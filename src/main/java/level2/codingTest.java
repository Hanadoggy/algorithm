package level2;

import java.util.*;

public class codingTest {

    private static int[] searchRanking(String[] info, String[] query) {
        List<Developer> volunteers = new ArrayList<>();
        Map<String, Integer> indexMap = new HashMap<>();
        Map<String, Integer> lengthMap = new HashMap<>();
        int[] answer = new int[query.length];

        for (String person : info) {
            String[] split = person.split(" ");
            volunteers.add(new Developer(split[0], split[1], split[2], split[3],
                    Integer.parseInt(split[4])));
        }
        volunteers.sort(Comparator.comparing((Developer i) -> i.lang)
                .thenComparing(i -> i.job)
                .thenComparing(i -> i.career)
                .thenComparing(i -> i.food)
                .thenComparing(i -> i.score));
        for (int i = 0; i <= volunteers.size(); i++) {
            if (i == 0) {
                indexMap.put(volunteers.get(i).getAbbreviation(), i);
            } else if (i == volunteers.size()) {
                lengthMap.put(volunteers.get(i-1).getAbbreviation(), i);
            } else if (!volunteers.get(i).isSame(volunteers.get(i-1))) {
                indexMap.put(volunteers.get(i).getAbbreviation(), i);
                lengthMap.put(volunteers.get(i-1).getAbbreviation(), i);
            }
        }
        for (int j = 0; j < query.length; j++) {
            String[] cond = getCondition(query[j]);
            int count = 0;
            for (String key : indexMap.keySet()) {
                if (checkCondition(key, cond)) {
                    int target = Integer.parseInt(cond[4]);
                    int index = indexMap.get(key);
                    int length = lengthMap.get(key);
                    while ((length - index) > 32) {
                        int mid = (index + ((length - index) / 2));
                        if (volunteers.get(mid).score < target) {
                            index = mid - 1;
                        } else if (volunteers.get(mid).score > target) {
                            length = mid + 1;
                        } else {
                            break;
                        }
                    }
                    while (index < lengthMap.get(key) &&
                            volunteers.get(index).score < target) {
                        index++;
                    }
                    count += (lengthMap.get(key) - index);
                }
            }
            answer[j] = count;
        }
        return answer;
    }

    public static boolean checkCondition(String key, String[] options) {
        boolean check = options[0].length() < 2 || key.contains(options[0]);
        check = (options[1].length() < 2) ? check : check && key.contains(options[1]);
        check = (options[2].length() < 2) ? check : check && key.contains(options[2]);
        check = (options[3].length() < 2) ? check : check && key.contains(options[3]);
        return check;
    }

    public static String[] getCondition(String cond) {
        String[] split = cond.split(" ");
        return new String[]{
                (split[0].length() < 2) ? "-" : split[0].substring(0,2),
                (split[2].length() < 2) ? "-" : split[2].substring(0,2),
                (split[4].length() < 2) ? "-" : split[4].substring(0,2),
                (split[6].length() < 2) ? "-" : split[6].substring(0,2),
                split[7]};
    }

    static class Developer {
        public String lang;
        public String job;
        public String career;
        public String food;
        public int score;
        public Developer (String lang, String job, String career, String food, int score) {
            this.lang = lang;
            this.job = job;
            this.career = career;
            this.food = food;
            this.score = score;
        }
        public boolean isSame(Developer diff) {
            return (diff.lang.equals(this.lang) && diff.job.equals(this.job) &&
                    diff.career.equals(this.career) && diff.food.equals(this.food));
        }
        public String getAbbreviation() {
            return String.format("%s%s%s%s", this.lang.substring(0, 2), this.job.substring(0, 2),
                    this.career.substring(0, 2), this.food.substring(0, 2));
        }
    }
}
