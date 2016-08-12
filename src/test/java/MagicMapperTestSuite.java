import collections.complex_types.ComplexTypesFieldNameAsString;
import collections.complex_types.ComplexTypesMapUsingDelegate;
import collections.perfect_match.PerfectMatchTest;
import name_differences.NameDifferencesFieldNameAsStringTest;
import name_differences.NameDifferencesMapUsingDelegate;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PerfectMatchTest.class,
        perfect_match.PerfectMatchTest.class,
        NameDifferencesFieldNameAsStringTest.class,
        NameDifferencesMapUsingDelegate.class,
        ComplexTypesFieldNameAsString.class,
        ComplexTypesMapUsingDelegate.class,
        complex_types.ComplexTypesFieldNameAsString.class,
        complex_types.ComplexTypesMapUsingDelegate.class
})
public class MagicMapperTestSuite {

}
