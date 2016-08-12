package bg.ansr.magicmapper.core.rule;

import bg.ansr.magicmapper.core.MagicMapper;

/**
 * @author Ivan Yonkov (RoYaL)
 */
@FunctionalInterface
public interface RuleLambda<T1, T2> {

    void applyRule(T1 arg1, T2 arg2, MagicMapper mapper);

}
