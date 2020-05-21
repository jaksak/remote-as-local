package pl.longhorn.bussiness.app.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.longhorn.api.UserModuleApi;

@RestController
@RequiredArgsConstructor
public class UserController implements UserModuleApi {

    private final UserModuleApi userModuleApiImpl;

    @Override
    @PostMapping("getLvl")
    public Integer getLvl(@RequestBody String id) {
        return userModuleApiImpl.getLvl(id);
    }

    @Override
    @PostMapping("getNick")
    public String getNick(String id) {
        return userModuleApiImpl.getNick(id);
    }
}
