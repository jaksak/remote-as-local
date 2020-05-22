package pl.longhorn.api;

import kong.unirest.HttpRequest;
import kong.unirest.Unirest;
import lombok.val;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.util.LinkedList;

public class RemoteModuleApiFactory {

    public static <API extends ModuleApi> API get(Class<API> apiClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(apiClass);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            HttpRequest requestBodyEntity;
            if (args != null) {
                //val body = args[0];
                requestBodyEntity = Unirest.post("http://127.0.0.1:2677/remote/" + apiClass.getName() + "/" + method.getName())
                        .header("Content-Type", "application/json")
                        .body(args);
            } else {
                requestBodyEntity = Unirest.post("http://127.0.0.1:2677/" + method.getName())
                        .header("Content-Type", "application/json")
                        .body(new LinkedList<>());
            }
            if (method.getDeclaringClass() != Object.class && method.getReturnType() != Object.class) {
                val result = requestBodyEntity.asObject(method.getReturnType());
                return result.getBody();
            } else {
                requestBodyEntity.asEmpty();
                return proxy.invokeSuper(obj, args);
            }
        });
        return (API) enhancer.create();
    }
}
