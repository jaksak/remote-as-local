package pl.longhorn.cglibtests.user;

import pl.longhorn.cglibtests.ModuleApi;

public interface UserModuleApi extends ModuleApi {
    Integer getLvl(String id);

    String getNick(String id);
}
