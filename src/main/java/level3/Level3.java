package level3;

import java.util.*;

public class Level3 {

    public static void main(String[] args) {

        // level 3
        var k = new Kakao2023();
        System.out.println(Arrays.toString(k.mergeTable(
                new String[]{"MERGE 1 1 2 2",
                        "UPDATE 1 1 A",
                        "UNMERGE 1 1",
                        "PRINT 1 1",
                        "PRINT 2 2"}
        )));

    }

}
