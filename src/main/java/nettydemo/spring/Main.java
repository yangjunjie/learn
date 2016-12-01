package nettydemo.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * Created by yance on 2016/11/7.
 */
public class Main {
    public static void main(String[] args) {
        AbstractApplicationContext ctx=new AnnotationConfigApplicationContext(SpringNettyCfg.class);
        ctx.registerShutdownHook();
    }
}
