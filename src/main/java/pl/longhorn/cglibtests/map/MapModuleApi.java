package pl.longhorn.cglibtests.map;

import pl.longhorn.cglibtests.ModuleApi;

public interface MapModuleApi extends ModuleApi {
    Boolean hasCollision(Position position);
}
