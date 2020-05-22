package pl.longhorn.bussiness.app.remote;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.val;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.web.bind.annotation.*;
import pl.longhorn.api.ModuleApi;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("remote")
public class RemoteController {

    private Map<MethodKey, MethodValue> methodsByKeys;
    private ObjectMapper objectMapper = new ObjectMapper();

    public RemoteController(ListableBeanFactory beanFactory) {
        Map<String, ModuleApi> apisByNames = beanFactory.getBeansOfType(ModuleApi.class);
        methodsByKeys = new HashMap<>();
        for (ModuleApi moduleApi : apisByNames.values()) {
            var apiClass = moduleApi.getClass();
            var methods = apiClass.getDeclaredMethods();
            String apiName = moduleApi.getClass().getInterfaces()[0].getName();
            for (Method method : methods) {
                MethodKey key = new MethodKey(apiName, method.getName());
                MethodValue value = methodsByKeys.getOrDefault(key, new MethodValue(new LinkedList<>(), moduleApi));
                value.getDefinitions().add(new MethodDefinition(method.getParameterTypes(), method));
                methodsByKeys.put(key, value);
            }
        }
    }

    @PostMapping("/{apiName}/{serviceName}")
    // TODO: mozliwa optymalizacja: parameters jako [value, className]
    public void runRemote(@PathVariable String apiName, @PathVariable String serviceName, @RequestBody(required = false) ArrayNode parameters, HttpServletResponse response) {
        MethodKey key = new MethodKey(apiName, serviceName);
        MethodValue value = methodsByKeys.get(key);
        List<MethodDefinition> possibleMethods = new LinkedList<>(value.getDefinitions());
        int parametersSize = parameters.size();
        possibleMethods = removeWithArgumentSizeOther(parametersSize, possibleMethods);
        Iterator<JsonNode> parameterIterator = parameters.iterator();
        Object[] parametersValue = new Object[parametersSize];
        for (int i = 0; parameterIterator.hasNext(); i++) {
            JsonNode node = parameterIterator.next();
            parametersValue[i] = removeNotMatch(i, node, possibleMethods);
        }
        if (possibleMethods.size() == 1) {
            val selectedMethod = possibleMethods.get(0);
            try {
                val result = selectedMethod.getApi().invoke(value.getModuleApi(), parametersValue);
                if (result != null) {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.print(objectMapper.writeValueAsString(result));
                    out.flush();
                }
            } catch (IllegalAccessException | InvocationTargetException | IOException e) {
                e.printStackTrace();
            }
        } else {
            // method with selected argument not exist...
        }
    }

    private Object removeNotMatch(int i, JsonNode node, List<MethodDefinition> possibleMethods) {
        Iterator<MethodDefinition> definitionIterator = possibleMethods.iterator();
        Object parameterValue = null;
        while (definitionIterator.hasNext()) {
            MethodDefinition definition = definitionIterator.next();
            parameterValue = convertToObject(node, definition.getArguments()[i]);
            if (parameterValue != null) {
                definitionIterator.remove();
            }
        }
        return parameterValue;
    }

    private List<MethodDefinition> removeWithArgumentSizeOther(int parametersSize, List<MethodDefinition> possibleMethods) {
        return possibleMethods.stream()
                .filter(definition -> definition.getArguments().length == parametersSize)
                .collect(Collectors.toList());
    }

    public <CLASS> CLASS convertToObject(JsonNode jsonStr, Class<CLASS> valueType) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.treeToValue(jsonStr, valueType);
        } catch (IOException e) {
            return null;
        }

    }
}
