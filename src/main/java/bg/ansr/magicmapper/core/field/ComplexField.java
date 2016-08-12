package bg.ansr.magicmapper.core.field;

import java.util.function.Function;

/**
 * @author Ivan Yonkov (RoYaL)
 */
public class ComplexField implements FieldMap {

    private String sourceName;

    private String targetName;

    private Function<Object, Object> valueDelegate;

    public ComplexField(String sourceName, String targetName, Function<Object, Object> valueDelegate) {
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
        return this.valueDelegate;
    }
}
