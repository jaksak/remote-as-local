package pl.longhorn.bussiness.app.map;

import org.springframework.stereotype.Service;
import pl.longhorn.api.MapModuleApi;
import pl.longhorn.api.Position;

import java.util.Random;

@Service
public class MapModuleApiImpl implements MapModuleApi {

    private Random random = new Random();

    @Override
    public Boolean hasCollision(Position position) {
        return random.nextBoolean();
    }
}
