import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.longhorn.api.MapModuleApi;
import pl.longhorn.api.Position;
import pl.longhorn.api.RemoteModuleApiFactory;
import pl.longhorn.api.UserModuleApi;
import pl.longhorn.bussiness.app.BussinessApp;

import java.util.UUID;

@SpringBootTest(classes = BussinessApp.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class RemoteModuleApiTest {

    private MapModuleApi mapModuleApi = RemoteModuleApiFactory.get(MapModuleApi.class);
    private UserModuleApi userModuleApi = RemoteModuleApiFactory.get(UserModuleApi.class);

    @Test
    void shouldSendObject() {
        Position position = new Position(2, 33);

        Boolean result = mapModuleApi.hasCollision(position);

        Assertions.assertNotNull(result);
    }

    @Test
    void shouldSelectSignatureWhenApiHasManyMethods() {
        String id = UUID.randomUUID().toString();

        Integer lvl = userModuleApi.getLvl(id);

        Assertions.assertNotNull(lvl);
    }

    @Test
    void shouldSendParameters() {
        String imageName = "aaa";
        String fileName = imageName + ".png";

        String result = userModuleApi.changeImg(imageName);

        Assertions.assertEquals(fileName, result);
    }

    @Test
    void shouldSelectSignatureWhenMethodsDifferOnlyParameters() {
        String result = userModuleApi.changeImg();

        Assertions.assertEquals("RANDOM.png", result);
    }

    @Test
    void shouldSendNullValues() {
        Assertions.assertTrue(userModuleApi.isNull(null));
    }

    @Test
    void shouldSendVoidMethod() {
        userModuleApi.inform("TEST_MESSAGE");
    }
}
