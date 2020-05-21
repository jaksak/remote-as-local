package pl.longhorn.bussiness.app.user;

import org.springframework.stereotype.Service;
import pl.longhorn.api.UserModuleApi;

import java.util.Random;
import java.util.UUID;

@Service
public class UserModuleApiImpl implements UserModuleApi {

    private Random random = new Random();

    @Override
    public Integer getLvl(String id) {
        return random.nextInt();
    }

    @Override
    public String getNick(String id) {
        return UUID.randomUUID().toString();
    }
}
