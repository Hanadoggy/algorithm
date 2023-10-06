package level2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Hashing {

    public static void main(String[] args) {

    }

    private static boolean phoneBook(String[] phone_book) {
        Set<String> books = new HashSet<>(List.of(phone_book));

        for (String phone : phone_book) {
            for (int i = 1; i < phone.length(); i++) {
                if (books.contains(phone.substring(0, i))) {
                    return false;
                }
            }
        }
        return true;
    }

}
