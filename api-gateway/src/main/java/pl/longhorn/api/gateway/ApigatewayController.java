package pl.longhorn.api.gateway;

import org.springframework.web.bind.annotation.*;
import pl.longhorn.api.MapModuleApi;
import pl.longhorn.api.Position;
import pl.longhorn.api.RemoteModuleApiFactory;
import pl.longhorn.api.UserModuleApi;

import java.util.UUID;

@RestController("apig")
public class ApigatewayController {

    private UserModuleApi userModuleApi = RemoteModuleApiFactory.get(UserModuleApi.class);
    private MapModuleApi mapModuleApi = RemoteModuleApiFactory.get(MapModuleApi.class);

    @GetMapping("user")
    public String getUserInfo() {
        return "hello " + userModuleApi.getNick(getIdFromSession()) + " " + userModuleApi.getLvl(getIdFromSession());
    }

    @PostMapping("position")
    public boolean movePlayer(@RequestBody Position position) {
        return mapModuleApi.hasCollision(position);
    }

    @PutMapping("img")
    public String changeImg(@RequestParam String imageName) {
        return userModuleApi.changeImg(imageName);
    }

    @PostMapping("img")
    public String prepareImg() {
        return userModuleApi.changeImg();
    }

    private String getIdFromSession() {
        return UUID.randomUUID().toString();
    }
}
