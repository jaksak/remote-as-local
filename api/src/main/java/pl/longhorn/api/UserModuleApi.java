package pl.longhorn.api;

public interface UserModuleApi extends ModuleApi {
    Integer getLvl(String id);

    String getNick(String id);

    String changeImg();

    String changeImg(String url);

    boolean isNull(Object object);
}
