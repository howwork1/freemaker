package com.fm.framework.core;


/**
 * 上下文
 * @author clufeng
 * @version 1.0.0
 **/
public class Context {

    private static ThreadLocal<Long> loginUserThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<String> loginUserCodeThreadLocal = new ThreadLocal<>();


    public static void setCurrUser(Long user) {
        if(user != null) {
            loginUserThreadLocal.set(user);
        }
    }

    public static void setCurrUserCode(String userCode) {
        if(userCode != null) {
            loginUserCodeThreadLocal.set(userCode);
        }
    }

    public static Long getCurrUser() {
        return loginUserThreadLocal.get();
    }

    public static String getCurrUserCode() {
        return loginUserCodeThreadLocal.get();
    }

    public static void removeCurrUserCode() {
        loginUserCodeThreadLocal.remove();
    }

    public static void removeCurrUser() {
        loginUserThreadLocal.remove();
    }

}
