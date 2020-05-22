package pl.longhorn.bussiness.app.remote;

import lombok.Value;
import pl.longhorn.api.ModuleApi;

import java.util.List;

@Value
public class MethodValue {
    private List<MethodDefinition> definitions;
    private ModuleApi moduleApi;
}
