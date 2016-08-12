package bg.ansr.magicmapper.core;

import java.util.function.Function;

/**
 * @author Ivan Yonkov (RoYaL)
 */
public interface MagicMapper {

    Function<Object, Object> getExistingBinding(Class from, Class to);

    <T> ANSRMagicMapper.BindProcessStarted<T, Object> from(Class<T> from);

    <T> T map(Object from, Class<T> to);

}
