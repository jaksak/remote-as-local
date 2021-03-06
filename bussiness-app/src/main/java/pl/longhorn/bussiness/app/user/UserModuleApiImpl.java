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

    @Override
    public String changeImg() {
        return changeImg("RANDOM");
    }

    @Override
    public String changeImg(String url) {
        return url + ".png";
    }

    @Override
    public boolean isNull(Object object) {
        return object == null;
    }

    @Override
    public void inform(String message) {
        System.out.println(message);
    }
}
