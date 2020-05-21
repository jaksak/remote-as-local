package pl.longhorn.api.gateway;

import kong.unirest.HttpRequest;
import kong.unirest.Unirest;
import lombok.val;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import pl.longhorn.api.ModuleApi;

public class RemoteModuleApiFactory {

    public <API extends ModuleApi> API get(Class<API> apiClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(apiClass);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            HttpRequest requestBodyEntity = null;
            if (args != null && args.length == 1) {
                val body = args[0];
                requestBodyEntity = Unirest.post("http://127.0.0.1:2677/" + method.getName())
                        .header("Content-Type", "application/json")
                        .body(body);
            } else {
                requestBodyEntity = Unirest.post("http://127.0.0.1:2677/" + method.getName())
                        .header("Content-Type", "application/json");
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
