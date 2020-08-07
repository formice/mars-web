package com.formice.mars.web.tool;

public class MathUtils {
    /**
     * 取制定范围内随机数
     * @param start
     * @param end
     * @return
     */
    public static int getRandom(int start,int end) {
        int num=(int) (Math.random()*(end-start+1)+start);
        return num;
    }
}
