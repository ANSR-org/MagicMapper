package bg.ansr.magicmapper.core;

import java.util.Collection;
import java.util.function.Function;

/**
 * @author Ivan Yonkov (RoYaL)
 */
public interface MagicMapper {

    Function<Object, Object> getExistingBinding(Class from, Class to);

    <T> ANSRMagicMapper.BindProcessStarted<T, Object> from(Class<T> from);

    <T> T map(Object from, Class<T> to);

    <T, R extends Collection<T>> R toCollection(R targetCollection, Collection<T> source);

    <T, C, R extends Collection<C>> R toCollection(R targetCollection, Class<C> targetType, Collection<T> source);

    <T, C, S extends Collection<T>, R extends Class<C>, V extends Collection<C>> V toCollection(R targetType, S source) throws IllegalAccessException,
            InstantiationException;

}
