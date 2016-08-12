package bg.ansr.magicmapper.core.field;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.function.Function;

/**
 * @author Ivan Yonkov (RoYaL)
 */
public class CollectionField implements FieldMap {

    private String sourceName;

    private String targetName;

    private Function<Object, Object> valueDelegate;

    public CollectionField(String sourceName, String targetName, Function<Object, Object> valueDelegate) {
        this.sourceName = sourceName;
        this.targetName = targetName;
        this.valueDelegate = valueDelegate;
    }

    public String getSourceName() {
        return this.sourceName;
    }

    public String getTargetName() {
        return this.targetName;
    }

    public Function<Object, Object> getValueDelegate() {
        return c -> {
            try {
                Field field = c.getClass().getDeclaredField(this.sourceName);
                field.setAccessible(true);
                Collection iterable = (Collection)field.get(c);
                Collection result = iterable.getClass().newInstance();

                for (Object source : iterable) {
                    Object target = this.valueDelegate.apply(source);
                    result.add(target);
                }

                return result;
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }

            return null;
        };
    }
}
