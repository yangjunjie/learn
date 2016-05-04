package proxydemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理类
 */
public class Proxyobj implements InvocationHandler {

    private  Object object;
   public Proxyobj(Object object){
        this.object=object;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("执行"+method+"方法之前");
       Object res= method.invoke(object,args);
        System.out.println("obj:"+object+"args:"+args+"res"+res);
        System.out.println("执行"+method+"方法之后");
        return res;
    }
}
