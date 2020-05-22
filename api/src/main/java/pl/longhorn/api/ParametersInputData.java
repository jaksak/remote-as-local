package pl.longhorn.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ParametersInputData implements Serializable {
    private String[] parameterClassNames;
    private Object[] parameterValues;
}
