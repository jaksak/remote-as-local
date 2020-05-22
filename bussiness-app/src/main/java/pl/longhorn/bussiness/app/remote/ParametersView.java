package pl.longhorn.bussiness.app.remote;

import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Data;

import java.io.Serializable;

@Data
public class ParametersView implements Serializable {
    private String[] parameterClassNames;
    private ArrayNode parameterValues;
}
