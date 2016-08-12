package bg.ansr.magicmapper.core;

import bg.ansr.magicmapper.core.field.FieldMap;
import bg.ansr.magicmapper.core.rule.RuleLambda;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Ivan Yonkov (RoYaL)
 */
public class ANSRMagicMapper implements MagicMapper {

    private Map<Class, Map<Class, Function<Object, Object>>> definedMappings;

    public ANSRMagicMapper() {
        this.definedMappings = new HashMap<>();
    }

    public Function<Object, Object> getExistingBinding(Class from, Class to) {
        return this.definedMappings.get(from).get(to);
    }

    public <T> BindProcessStarted<T, Object> from(Class<T> from) {
        return new BindProcessStarted<>(from);
    }

    public <T> T map(Object from, Class<T> to) {
        if (!this.definedMappings.containsKey(from.getClass())) {
            return (T)this.getStandardMappingLambda(to).apply(from);
        }

        if (!this.definedMappings.get(from.getClass()).containsKey(to)) {
            return (T)this.getStandardMappingLambda(to).apply(from);
        }

        return (T)this.definedMappings.get(from.getClass())
                .get(to)
                .apply(from);
    }

    @Override
    public <T, R extends Collection<T>> R toCollection(R targetCollection, Collection<T> source) {
        source.stream().forEach(targetCollection::add);

        return targetCollection;
    }

    @Override
    public <T, C, R extends Collection<C>> R toCollection(R targetCollection, Class<C> targetType, Collection<T>
            source) {
        source.stream().forEach(el -> targetCollection.add(this.map(el, targetType)));

        return targetCollection;
    }

    @Override
    public <T, C, S extends Collection<T>, R extends Class<C>, V extends Collection<C>> V toCollection(R targetType, S source) throws IllegalAccessException, InstantiationException {
        V targetCollection = (V)source.getClass().newInstance();

        return this.toCollection(targetCollection, targetType, source);
    }

    private Function<Object, Object> getStandardMappingLambda(Class resultClass) {
        return sourceObject -> {
            try {
                Object result = resultClass.newInstance();
                for (Field sourceField : sourceObject.getClass().getDeclaredFields()) {
                    for (Field targetField : resultClass.getDeclaredFields()) {
                        if (!targetField.getName().equals(sourceField.getName()) ||
                                targetField.getType() != sourceField.getType()) {
                            continue;
                        }

                        targetField.setAccessible(true);
                        sourceField.setAccessible(true);
                        targetField.set(result, sourceField.get(sourceObject));
                    }
                }

                return result;
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }

            return null;
        };
    }

    public class BindProcessStarted<T, R> {

        private Class<T> from;
        private Class<R> to;

        BindProcessStarted(Class<T> from) {
            this.from = from;
        }

        BindProcessStarted(Class<T> from, Class<R> to) {
            this.from = from;
            this.to = to;
        }

        public <N extends R> BindProcessStarted<T, N> to(Class<N> to) {
            return new BindProcessStarted<>(this.from, to);
        }

        public <N extends R> ANSRMagicMapper toFinal(Class<N> to) {
            BindProcessStarted<T, N> process = this.to(to);

            return process.ready();
        }

        public ANSRMagicMapper ready() {
            return this.ready(ANSRMagicMapper.this.getStandardMappingLambda(this.to));
        }

        public ANSRMagicMapper forRule(FieldMap... rules) {
            Function<Object, Object> toApply
                    = sourceObject -> {
                try {
                    Object result = this.to.newInstance();

                    for (FieldMap rule : rules) {
                        String fromFieldName = rule.getSourceName();
                        Field sourceField = sourceObject.getClass().getDeclaredField(fromFieldName);
                        String toFieldName = rule.getTargetName();
                        Field targetField = this.to.getDeclaredField(toFieldName);
                        targetField.setAccessible(true);
                        sourceField.setAccessible(true);
                        if (!sourceField.getType().isPrimitive()
                                && ANSRMagicMapper.this.definedMappings.containsKey(sourceField.getType())) {
                            targetField.set(result, rule.getValueDelegate().apply(sourceField.get(sourceObject)));
                        } else {
                            targetField.set(result, rule.getValueDelegate().apply(sourceObject));
                        }
                    }

                    return result;
                } catch (ReflectiveOperationException e) {
                    return null;
                }
            };

            return this.ready(toApply);
        }

        public ANSRMagicMapper forRule(RuleLambda<T, R> rule) {
            Function<Object, Object> toApply
                    = sourceObject -> {
                try {
                    R result = (R)this.to.newInstance();
                    rule.applyRule((T)sourceObject, result, ANSRMagicMapper.this);
                    return result;
                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                }

                return null;
            };

            ANSRMagicMapper mapper = ANSRMagicMapper.this;
            mapper.definedMappings.putIfAbsent(this.from, new HashMap<>());
            mapper.definedMappings.get(this.from).put(
                    this.to,
                    toApply
            );

            return mapper;
        }

        ANSRMagicMapper ready(Function<Object, Object> applyDelegate) {
            ANSRMagicMapper mapper = ANSRMagicMapper.this;
            mapper.definedMappings.putIfAbsent(this.from, new HashMap<>());
            mapper.definedMappings.get(this.from).put(this.to, applyDelegate);

            return mapper;
        }

    }
}
