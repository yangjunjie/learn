package proxydemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by thinkpad on 2016/5/3.
 */
public class Client {

    public static void main(String[] args) {
        Souerce souerce = new Souerce();
        InvocationHandler handler = new Proxyobj( souerce );
        Sourceable sourceable = (Sourceable) Proxy.newProxyInstance( handler.getClass().getClassLoader(), souerce.getClass().getInterfaces(), handler );
        sourceable.pro();
    }
}
