package pl.longhorn.bussiness.app.remote;

import lombok.Value;

@Value
public class MethodKey {
    private String apiName;
    private String methodName;
    private String[] classNames;
}
