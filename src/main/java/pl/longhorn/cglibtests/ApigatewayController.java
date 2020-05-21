package pl.longhorn.cglibtests;

import org.springframework.web.bind.annotation.*;
import pl.longhorn.cglibtests.map.MapModuleApi;
import pl.longhorn.cglibtests.map.Position;
import pl.longhorn.cglibtests.user.UserModuleApi;

import java.util.UUID;

@RestController("apig")
public class ApigatewayController {

    private RemoteModuleApiFactory remoteModuleApiFactory = new RemoteModuleApiFactory();
    private UserModuleApi userModuleApi = remoteModuleApiFactory.get(UserModuleApi.class);
    private MapModuleApi mapModuleApi = remoteModuleApiFactory.get(MapModuleApi.class);

    @GetMapping("user")
    public String getUserInfo(){
        return "hello " + userModuleApi.getNick(getIdFromSession()) + " " + userModuleApi.getLvl(getIdFromSession());
    }

    @PostMapping("position")
    public boolean movePlayer(@RequestBody Position position){
        return mapModuleApi.hasCollision(position);
    }

    private String getIdFromSession() {
        return UUID.randomUUID().toString();
    }
}
