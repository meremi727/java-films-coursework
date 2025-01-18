package ru.tde.films.Views.Util.Annotation;

import java.lang.reflect.Field;

public class AnnotationProcessor {
    public static String getTranslation(Class<?> _class, String fieldName) {
        Field field = null;
        try {
            field = _class.getDeclaredField(fieldName);
            return field.isAnnotationPresent(Translation.class) ?
                 field.getAnnotation(Translation.class).value() : null;

        } catch (NoSuchFieldException e) {
            return null;
        }
    }
}
