package pl.longhorn.cglibtests.map;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MapModuleApiImpl implements MapModuleApi {

    private Random random = new Random();

    @Override
    public Boolean hasCollision(Position position) {
        return random.nextBoolean();
    }
}
