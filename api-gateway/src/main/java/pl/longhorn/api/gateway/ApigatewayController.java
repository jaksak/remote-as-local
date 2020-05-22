package pl.longhorn.api.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.longhorn.api.MapModuleApi;
import pl.longhorn.api.Position;
import pl.longhorn.api.UserModuleApi;

import java.util.UUID;

@RestController("apig")
@RequiredArgsConstructor
public class ApigatewayController {

    private final UserModuleApi userModuleApi;
    private final MapModuleApi mapModuleApi;

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

    @PostMapping("message")
    public void addMessage(@RequestBody String message) {
        userModuleApi.inform(message);
    }

    private String getIdFromSession() {
        return UUID.randomUUID().toString();
    }
}
