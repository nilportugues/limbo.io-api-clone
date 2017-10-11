package io.bandit.limbo.limbo.modules.shared.middleware;

import io.bandit.limbo.limbo.modules.shared.annotation.FilterModel;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterProperty;
import io.bandit.limbo.limbo.infrastructure.converters.TypeConverter;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;

import javax.persistence.Id;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class FilterTransformer
{
    private static final String ANNOTATION = FilterProperty.class.getCanonicalName();
    private static final String ANNOTATION_FILTER_FIELD = "filterName";
    private static final String ANNOTATION_MODEL_FIELD = "modelProperty";
    private static final String ANNOTATION_MODEL_INNER_FIELD = "innerValue";

    private Map<String, String> transformations = new HashMap<>();
    private Map<String, String> innerTransformations = new HashMap<>();

    private Object modelInstance;
    private TypeConverter typeConverter;

    public FilterTransformer(TypeConverter typeConverter, Object presenter) {
        this.typeConverter = typeConverter;
        parseClassAnnotation(presenter);
        parsePropertyAnnotations(presenter);
    }

    /**
     * Resolved the column name for a presenter key that refers to a "JOIN".
     *
     * @param presenterKey   The presenter's property name.
     * @return      The modelProperty's property name.
     */
    public String getTransformedKey(String presenterKey) {

        if (null == modelInstance) {
            return null;
        }

        if (transformations.containsKey(presenterKey)
            && !innerTransformations.containsKey(transformations.get(presenterKey))) {
            return transformations.get(presenterKey);
        }

        try {
            String modelKey = transformations.get(presenterKey);

            // Get the entity name... and get the SQL column name.
            final String modelPropertyType = innerTransformations.get(modelKey);
            final PropertyAccessor modelAccessor = PropertyAccessorFactory.forDirectFieldAccess(modelInstance);
            final String className = modelAccessor.getPropertyTypeDescriptor(modelPropertyType).getObjectType().getName();

            // Get the @Id column name from the referred class. Reflection magic
            final Class<?> clazz = Class.forName(className);
            final Constructor<?> constructor = clazz.getConstructor();

            for (Field field: constructor.newInstance().getClass().getDeclaredFields()) {

                for(Annotation annotation : field.getDeclaredAnnotations()) {
                    if (annotation.annotationType().equals(Id.class)) {
                        return modelPropertyType;
                    }
               }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param presenterKey       The models's property name.
     * @param presenterValue The presenter's property value.
     * @return               The modelProperty's types value.
     */
    public Object getTransformationValue(String presenterKey, Object presenterValue) {

        if (null == modelInstance) {
            return null;
        }

        final PropertyAccessor accessor = PropertyAccessorFactory.forDirectFieldAccess(modelInstance);

        if (transformations.containsKey(presenterKey) &&
            !innerTransformations.containsKey(transformations.get(presenterKey))) {

            final String className = accessor.getPropertyType(transformations.get(presenterKey)).getName();
            return typeConverter.convert(className, presenterValue);
        }

        return getTransformationInnerValue(transformations.get(presenterKey), presenterValue);
    }


    private Object getTransformationInnerValue(String modelKey, Object presenterValue) {

        // Get the entity name... and get the SQL column name.
        final String modelPropertyType = innerTransformations.get(modelKey);
        final PropertyAccessor modelAccessor = PropertyAccessorFactory.forDirectFieldAccess(modelInstance);
        final String className = modelAccessor.getPropertyTypeDescriptor(modelPropertyType).getObjectType().getName();

        try {

            // Get the @Id column name from the referred class. Reflection magic
            final Class<?> clazz = Class.forName(className);
            final Constructor<?> constructor = clazz.getConstructor();
            final Object newInstance = constructor.newInstance();

            for (Field field: newInstance.getClass().getDeclaredFields()) {

                for (Annotation annotation : field.getDeclaredAnnotations()) {
                    if (annotation.annotationType().equals(Id.class)) {

                        final Object data = typeConverter.convert(field.getType().getCanonicalName(), presenterValue);

                        final PropertyAccessor property = PropertyAccessorFactory.forDirectFieldAccess(newInstance);
                        property.setPropertyValue(field.getName(), data);

                        return newInstance;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void parseClassAnnotation(Object presenter) {

        try {
            final FilterModel[] annotations = presenter.getClass().getDeclaredAnnotationsByType(FilterModel.class);
            final String className = annotations[0].model().getCanonicalName();

            final Class<?> aClass = Class.forName(className);
            final Constructor<?> constructor = aClass.getConstructor();

            this.modelInstance = constructor.newInstance();
        } catch (Exception ignored) {

        }
    }

    private void parsePropertyAnnotations(Object presenter) {
        for(Field field: presenter.getClass().getDeclaredFields()) {

            for (Annotation presenterMethodAnnotation: field.getAnnotations()) {

                if (presenterMethodAnnotation.annotationType().getName().equals(ANNOTATION)) {
                    try {
                        final Method filterMethod = presenterMethodAnnotation
                                .annotationType()
                                .getDeclaredMethod(ANNOTATION_FILTER_FIELD);

                        final Method fieldMethod = presenterMethodAnnotation
                                .annotationType()
                                .getDeclaredMethod(ANNOTATION_MODEL_FIELD);

                        final Method fieldInnerMethod = presenterMethodAnnotation
                                .annotationType()
                                .getDeclaredMethod(ANNOTATION_MODEL_INNER_FIELD);

                        final String fieldInnerMethodField = fieldInnerMethod
                                .invoke(presenterMethodAnnotation, (Object[]) null)
                                .toString();

                        final String filterField = filterMethod
                                .invoke(presenterMethodAnnotation, (Object[]) null)
                                .toString();

                        final String modelField = fieldMethod
                                .invoke(presenterMethodAnnotation, (Object[]) null)
                                .toString();

                        if (0 == fieldInnerMethodField.length()) {
                            transformations.put(filterField, modelField);
                        } else {
                            // The class property. Eg: Conversation
                            transformations.put(filterField, fieldInnerMethodField);

                            // The class' inner property. Eg: <Conversation>.<conversationId>.
                            innerTransformations.put(fieldInnerMethodField, modelField);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
