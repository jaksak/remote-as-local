package pl.longhorn.api.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.longhorn.api.MapModuleApi;
import pl.longhorn.api.RemoteModuleApiFactory;
import pl.longhorn.api.UserModuleApi;

@Configuration
public class ApiGatewayRemoteConfig {

    @Bean
    public UserModuleApi userModuleApi() {
        return RemoteModuleApiFactory.get(UserModuleApi.class);
    }

    @Bean
    public MapModuleApi mapModuleApi() {
        return RemoteModuleApiFactory.get(MapModuleApi.class);
    }
}