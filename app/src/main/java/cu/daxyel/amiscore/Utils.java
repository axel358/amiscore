package cu.daxyel.amiscore;

import android.content.Context;


import java.util.ArrayList;
import java.util.Objects;

import cu.daxyel.amiscore.models.Criteria;

public class Utils {

    public final static int SCAN_REQUEST_CODE = 27;
    public static int index = 0;


    public static String[] parseScanResult(String scan) {
        String[] info = new String[2];
        String name = scan.substring(scan.indexOf("N:") + 2, scan.indexOf("A:") - 1);
        String last = scan.substring(scan.indexOf("A:") + 2, scan.indexOf("CI:") - 1);
        String id = scan.substring(scan.indexOf("CI:") + 3, scan.indexOf("FV:") - 2);

        info[0] = name + last;
        info[1] = id;
        return info;
    }

    public static int dpToPx(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public static boolean isAnyChekBoxIsCheked(ArrayList<Criteria> criteriaArrayList) {
        for (Criteria criteria : criteriaArrayList) {
            if (criteria.getSelected()) {
                return true;
            }
        }
        return false;
    }
}
