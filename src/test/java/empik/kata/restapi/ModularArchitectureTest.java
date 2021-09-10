package empik.kata.restapi;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "empik.kata.restapi")
public class ModularArchitectureTest {

    @ArchTest
    public static final ArchRule common_should_not_depend_on_counting =
            noClasses()
                    .that()
                    .resideInAPackage("..common..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..counting..");

    @ArchTest
    public static final ArchRule common_should_not_depend_on_users =
            noClasses()
                    .that()
                    .resideInAPackage("..common..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..users..");

    @ArchTest
    public static final ArchRule users_should_not_depend_on_counting =
            noClasses()
                    .that()
                    .resideInAPackage("..users..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..counting..");

}