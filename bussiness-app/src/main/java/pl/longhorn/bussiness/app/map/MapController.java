package pl.longhorn.bussiness.app.map;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.longhorn.api.MapModuleApi;
import pl.longhorn.api.Position;

@RestController
@RequiredArgsConstructor
public class MapController implements MapModuleApi {

    private final MapModuleApi mapModuleApiImpl;

    @Override
    @PostMapping("/hasCollision")
    public Boolean hasCollision(@RequestBody Position position) {
        return mapModuleApiImpl.hasCollision(position);
    }
}
