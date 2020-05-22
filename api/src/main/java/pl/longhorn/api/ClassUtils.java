package pl.longhorn.api;

public class ClassUtils {
    public static String[] getParameterClassNames(Class<?>[] parameterTypes) {
        String[] names = new String[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            names[i] = parameterTypes[i].getName();
        }
        return names;
    }
}
