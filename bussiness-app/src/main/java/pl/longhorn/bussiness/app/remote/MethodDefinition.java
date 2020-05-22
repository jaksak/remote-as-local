package pl.longhorn.bussiness.app.remote;

import lombok.Value;
import pl.longhorn.api.ModuleApi;

import java.lang.reflect.Method;

@Value
public class MethodDefinition {
    private Method method;
    private ModuleApi moduleApi;
    private Class[] parameterClasses;
}
