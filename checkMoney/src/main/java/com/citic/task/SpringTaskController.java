package com.citic.task;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Spring调度，指定时间执行
 * 利用了spring中使用注解的方式来进行任务调度。 
 */
@Component
@Lazy(false)
public class SpringTaskController {

}