package pl.longhorn.bussiness.app.remote;

import lombok.Value;

import java.lang.reflect.Method;

@Value
public class MethodDefinition {
    private Class[] arguments;
    private Method api;
}
