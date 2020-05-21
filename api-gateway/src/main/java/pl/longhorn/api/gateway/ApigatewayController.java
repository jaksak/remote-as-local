package pl.longhorn.api.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.longhorn.api.MapModuleApi;
import pl.longhorn.api.Position;
import pl.longhorn.api.UserModuleApi;

import java.util.UUID;

@RestController("apig")
public class ApigatewayController {

    private RemoteModuleApiFactory remoteModuleApiFactory = new RemoteModuleApiFactory();
    private UserModuleApi userModuleApi = remoteModuleApiFactory.get(UserModuleApi.class);
    private MapModuleApi mapModuleApi = remoteModuleApiFactory.get(MapModuleApi.class);

    @GetMapping("user")
    public String getUserInfo() {
        return "hello " + userModuleApi.getNick(getIdFromSession()) + " " + userModuleApi.getLvl(getIdFromSession());
    }

    @PostMapping("position")
    public boolean movePlayer(@RequestBody Position position) {
        return mapModuleApi.hasCollision(position);
    }

    private String getIdFromSession() {
        return UUID.randomUUID().toString();
    }
}
