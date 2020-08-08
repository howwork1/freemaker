package com.fm.framework.core.utils;

import java.util.Random;

/**
 * <p>序列号工具</p>
 *
 * @author clufeng
 */
public class CodeUtil {

    private CodeUtil() {}

    private static final String Base32Alphabet = "ABC4DEFGHJ2KLM3NPQRS9TUVWXYZ5678";

    public static final int DEFAULT_CODE_LEN = 9;

    public static final int DEFAULT_FLAG = 0;

    public static final int DEFAULT_FLAG_LEN = 0;

    public static final int DEFAULT_CHECK_LEN = 7;

    /**
     * 生成新的序列号	<br>
     * <p>生成规则：45位的数 （二进制）<br>
     * 		 标识位  + 数据位 + 校验位 <br>
     * 然后将55位的数映射到用 ABCDEFGHJKLMNPQRSTUVWXYZ23456789 表示的序列号，要映射到32个字符中就是每5位代表一个字符(2^5=32)，
     * 所有生成的序列号是 45/5=9位。
     *
     * @param codeLen	code长度
     * @param flag	标识
     * @param flagBitLen 标识长度
     * @param checkBitLen 校验位长度
     * @return 序列号
     */
    public static String generateNewCode(int codeLen, int flag, int flagBitLen, int checkBitLen) {
        long ret = 0L; // 长整形ID
        Random random = new Random();
        int checkModData = 1<<checkBitLen;
        int totalBitLen = codeLen*5;
        int dataBitLen = totalBitLen - checkBitLen - flagBitLen;
        long randData = (long)(1 + (1L<<dataBitLen - 1) * random.nextDouble());

        if(flagBitLen > 0){
            flag = flag & ((1<<flagBitLen) - 1);					//防止越位，若16位标识则是 0xffff
            ret += (long)flag << (totalBitLen - flagBitLen);		//高位标志位
        }

        ret += randData << checkBitLen; 						// 中位数据位
        long checkNum = (ret >> checkBitLen) % checkModData;	//低位校验位
        ret += checkNum; // 1 - 7位 校验位
        return convertToBase32SerialCode(ret, codeLen);
    }

    /**
     * 将随机数转换成BASE32编码 序列码
     *
     * @return 序列码
     */
    private static String convertToBase32SerialCode(long longRandValue, int codeLen) {
        StringBuilder codeSerial = new StringBuilder(16);
        long tmpRandValue = longRandValue;
        for (int i = 0; i < codeLen; i++) {
            int code = (int) (tmpRandValue & 0x1F);
            char convertCode = Base32Alphabet.charAt(code);
            codeSerial.append(convertCode);
            tmpRandValue = tmpRandValue >> 5;
        }
        return codeSerial.reverse().toString();
    }

    /**
     * 将兑换码序列字符转化成数字。
     *
     * @return 数字
     */
    private static int convertBase32CharToNum(char ch) {
        return Base32Alphabet.indexOf(ch);
    }

    /**
     * 将序列号转成长整数
     *
     * @return 长度
     */
    public static long convertBase32CharToNum(String serialCode) {
        long id = 0;

        for (int i = 0; i < serialCode.length(); i++) {
            int originNum = convertBase32CharToNum(serialCode.charAt(i));
            if(originNum == -1){
                return 0;
            }
            id = id << 5;
            id += originNum;
        }
        return id;
    }


    public static String generateNewCode(int flag, int flagBitLen) {
        return generateNewCode(DEFAULT_CODE_LEN, flag, flagBitLen, DEFAULT_CHECK_LEN);		//生成码9位
    }

    public static String generateNewCode(int flag) {
        int flagBitLen = 0;
        if (flag != 0) {
            flagBitLen = Integer.toBinaryString(flag).length();
        }
        return generateNewCode(DEFAULT_CODE_LEN, flag, flagBitLen, DEFAULT_CHECK_LEN);		//生成码9位
    }

    public static String generateNewCode() {
        return generateNewCode(DEFAULT_CODE_LEN, DEFAULT_FLAG, DEFAULT_FLAG_LEN, DEFAULT_CHECK_LEN);   //生成码9位
    }


    /**
     * 校验序列号是否合法
     *
     * @param code
     * @return
     */
    public static boolean checkCodeValid(String code, int checkBitLen) {
        long id = 0;
        int checkModData = 1<<checkBitLen;
        for (int i = 0; i < code.length(); ++i) {
            long originNum = convertBase32CharToNum(code.charAt(i));
            if (originNum >= 32)
                return false; // 字符非法
            id = id<<5;
            id += originNum;
        }

        long data = id >> checkBitLen;
        long checkNum = id & (checkModData-1); // 最后7位是校验码

        return data % checkModData == checkNum;
    }

    public static boolean checkCodeValid(String code) {
        if(code == null || code.length() == 0){
            return false;
        }
        return checkCodeValid(code, 7);
    }
    /**
     * 从序列号提取标识
     *
     * @param code	序列号
     * @param flagBitLen	标识位长度
     * @return 标识
     */
    public static Long getFlagFromCode(String code, int flagBitLen){
        long id = convertBase32CharToNum(code);
        return id >> (code.length()*5-flagBitLen);
    }

    public static void main(String[] args) {
        System.out.println(generateNewCode());
    }


}
