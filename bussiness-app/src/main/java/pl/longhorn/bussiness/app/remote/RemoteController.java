package pl.longhorn.bussiness.app.remote;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.val;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.web.bind.annotation.*;
import pl.longhorn.api.ClassUtils;
import pl.longhorn.api.ModuleApi;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@RestController
@RequestMapping("remote")
public class RemoteController {

    private Map<MethodKey, MethodDefinition> methodsByKeys;
    private ObjectMapper objectMapper = new ObjectMapper();

    public RemoteController(ListableBeanFactory beanFactory) {
        Map<String, ModuleApi> apisByNames = beanFactory.getBeansOfType(ModuleApi.class);
        methodsByKeys = new HashMap<>();
        for (ModuleApi moduleApi : apisByNames.values()) {
            var apiClass = moduleApi.getClass();
            var methods = apiClass.getDeclaredMethods();
            String apiName = moduleApi.getClass().getInterfaces()[0].getName();
            for (Method method : methods) {
                MethodKey key = new MethodKey(apiName, method.getName(), ClassUtils.getParameterClassNames(method.getParameterTypes()));
                methodsByKeys.put(key, new MethodDefinition(method, moduleApi, method.getParameterTypes()));
            }
        }
    }

    @PostMapping("/{apiName}/{serviceName}")
    public void runRemote(@PathVariable String apiName, @PathVariable String serviceName, @RequestBody(required = false) ObjectNode parameterDto, HttpServletResponse response) {
        String[] parameterClassNames = getParameterClassNames(parameterDto);
        MethodKey key = new MethodKey(apiName, serviceName, parameterClassNames);
        MethodDefinition definition = methodsByKeys.get(key);
        JsonNode parameterValueAsNode = parameterDto.get("parameterValues");
        Iterator<JsonNode> parameterIterator = parameterValueAsNode.iterator();
        int parameterSize = parameterValueAsNode.size();
        Object[] parametersValue = new Object[parameterSize];
        val parameterClasses = definition.getParameterClasses();
        for (int i = 0; parameterIterator.hasNext(); i++) {
            JsonNode node = parameterIterator.next();
            parametersValue[i] = node == null ? null : convertToObject(node, parameterClasses[i]);
        }
        try {

            val result = definition.getMethod().invoke(definition.getModuleApi(), parametersValue);
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
    }

    public <CLASS> CLASS convertToObject(JsonNode jsonStr, Class<CLASS> valueType) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.treeToValue(jsonStr, valueType);
        } catch (IOException e) {
            return null;
        }

    }

    private String[] getParameterClassNames(ObjectNode parameterDto) {
        List<String> names = new LinkedList<>();
        for (JsonNode node : parameterDto.get("parameterClassNames")) {
            try {
                names.add(objectMapper.treeToValue(node, String.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return names.toArray(new String[0]);
    }
}
