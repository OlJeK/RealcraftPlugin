package my.oljek.rc.util;

public class MathUtil {

    public static int calculatePercentOfTwoNumber(int one, int two) {
        return (int) ((double) one / two * 100);
    }

    public static int calculateNumberOfPercent(int percent, int number) {
        return (int) ((double) number * percent / 100);
    }

    public static double calculateNumberOfPercent(int percent, double number) {
        return (number * percent / 100.F);
    }

    public static int getDifferenceTime(long time) {
        return (int) ((System.currentTimeMillis() - time) / 1000);
    }

    public static String getPercentString(int one, int two) {
        String lineProcent = "--------------------";
        int startPosition = calculateNumberOfPercent(calculatePercentOfTwoNumber(one, two), lineProcent.length());
        return "&a&m" + lineProcent.substring(0, Math.max(0, startPosition)).replace("-", "=") + "&c" + lineProcent.substring(Math.max(0, startPosition));
    }

}
