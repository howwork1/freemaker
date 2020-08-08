package com.fm.framework.core.utils;

import java.util.Random;

/**
 * 树形内码工具
 * @author clufeng
 * @version 1.0.0
 **/
public class TreeIncodeUtil {

    private final static String codeLib = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final static int LENGTH = 4;

    private final static Random ran = new Random();

    /**
     * 根据父级编码创建Incode
     * @param parentCode 父级编码
     */
    public static String create(String parentCode) {

        String incode;

        if(parentCode != null) {
            incode = parentCode + genCode();
        } else {
            incode = genCode();
        }

        return incode;

    }

    private static String genCode() {

        StringBuilder incode = new StringBuilder();
        int tmpIdx;
        for ( int idx = 0; idx < LENGTH; idx++ ) {
            tmpIdx = ran.nextInt(codeLib.length());
            incode.append(codeLib, tmpIdx, tmpIdx + 1);
        }

        return incode.toString();
    }

}
