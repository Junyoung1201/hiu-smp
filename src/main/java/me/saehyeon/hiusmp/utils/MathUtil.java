package me.saehyeon.hiusmp.utils;

import java.util.Random;

public class MathUtil {
    private static final Random random = new Random();

    /**
     * num1과 num2 사이의 랜덤한 정수를 반환합니다 (양 끝 포함)
     * @param num1 범위의 시작값
     * @param num2 범위의 끝값
     * @return num1과 num2 사이의 랜덤한 정수
     */
    public static int randInt(int num1, int num2) {
        int min = Math.min(num1, num2);
        int max = Math.max(num1, num2);
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * 주어진 확률(퍼센트)로 true를 반환합니다
     * @param percent 확률 (0.0-100.0)
     * @return percent% 확률로 true, 나머지 확률로 false
     */
    public static boolean chance(float percent) {
        if (percent <= 0) return false;
        if (percent >= 100) return true;
        return random.nextFloat() * 100 < percent;
    }
}
