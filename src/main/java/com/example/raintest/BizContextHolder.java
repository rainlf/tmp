package com.example.raintest;

import lombok.Data;

import java.util.UUID;

/***
 * @author : Rain
 * @date : 2021/9/5 7:02 PM
 */
public class BizContextHolder {
    private static final ThreadLocal<BizContext> bizContextThreadLocal = new ThreadLocal<>();

    public static void initBizContext() {
        BizContext bizContext = new BizContext();
        bizContext.setBizId(UUID.randomUUID().toString());
        bizContextThreadLocal.set(bizContext);
    }

    public static void cleanBizContext() {
        bizContextThreadLocal.remove();
    }

    public static void setBizContext(BizContext bizContext) {
        bizContextThreadLocal.set(bizContext);
    }

    public static BizContext getBizContext() {
        return bizContextThreadLocal.get();
    }

}
