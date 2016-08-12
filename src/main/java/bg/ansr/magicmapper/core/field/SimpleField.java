package bg.ansr.magicmapper.core.field;

import java.lang.reflect.Field;
import java.util.function.Function;

/**
 * @author Ivan Yonkov (RoYaL)
 */
public class SimpleField implements FieldMap {

    private String sourceName;

    private String targetName;

    private Function<Object, Object> valueDelegate;

    public SimpleField(String sourceName, String targetName) {
        this.sourceName = sourceName;
        this.targetName = targetName;
        this.valueDelegate = c -> {
            try {
                Field field = c.getClass().getDeclaredField(this.sourceName);
                field.setAccessible(true);

                return field.get(c);
            } catch (Exception e) {
                return null;
            }
        };
    }

    public String getSourceName() {
        return this.sourceName;
    }

    public String getTargetName() {
        return this.targetName;
    }

    public Function<Object, Object> getValueDelegate() {
        return this.valueDelegate;
    }
}
