package cu.daxyel.amiscore;

public class Utils
{
    public final static int SCAN_REQUEST_CODE=27;

    public static String[] parseScanResult(String scan)
    {
        String[] info = new String[2];
        String name = scan.substring(scan.indexOf("N:") + 2, scan.indexOf("A:") - 1);
        String last= scan.substring(scan.indexOf("A:") + 2, scan.indexOf("CI:") - 1);
        String id=scan.substring(scan.indexOf("CI:") + 3, scan.indexOf("FV:") - 2);

        info[0] = name + last;
        info[1] = id;
        return info;
    }
}
