package com.example.raintest;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/***
 * @author : Rain
 * @date : 2021/9/5 6:44 PM
 */
@Slf4j
@Component
public class Runner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        MDC.put("bizId", UUID.randomUUID().toString());
        log.info("{}: service start", Thread.currentThread().getName());

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("sub-");
        executor.setTaskDecorator((runnable) -> {
            // 父线程区
            Map<String, String> mdcMap = MDC.getCopyOfContextMap();
            BizContext bizContext = BizContextHolder.getBizContext();
            return () -> {
                try {
                    // 子线程区
                    MDC.setContextMap(mdcMap);
                    BizContextHolder.setBizContext(bizContext);
                    runnable.run();
                } catch (Exception e) {
                    log.error("sub thread failed", e);
                } finally {
                    MDC.clear();
                }
            };
        });
        executor.initialize();

        executor.submit(this::subService);

        log.info("{}: service done", Thread.currentThread().getName());
    }

    private void subService() {
        log.info("{}: sub service start", Thread.currentThread().getName());

        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println(1 / 0);
        } catch (InterruptedException e) {
            log.error("exception for subService", e);
        }
        log.info("{}: sub service done", Thread.currentThread().getName());
    }
}
