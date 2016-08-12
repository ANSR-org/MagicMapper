package bg.ansr.magicmapper.core.field;

import java.util.function.Function;

/**
 * @author Ivan Yonkov (RoYaL)
 */
public interface FieldMap {

    String getSourceName();

    String getTargetName();

    Function<Object, Object> getValueDelegate();

}
