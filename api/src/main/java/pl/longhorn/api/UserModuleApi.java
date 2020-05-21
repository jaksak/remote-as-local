package pl.longhorn.api;

public interface UserModuleApi extends ModuleApi {
    Integer getLvl(String id);

    String getNick(String id);
}
