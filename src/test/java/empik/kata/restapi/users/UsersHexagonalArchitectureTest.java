package empik.kata.restapi.users;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "empik.kata.restapi.users")
public class UsersHexagonalArchitectureTest {

    @ArchTest
    public static final ArchRule model_should_not_depend_on_application =
            noClasses()
                    .that()
                    .resideInAPackage("..model..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..application..");

    @ArchTest
    public static final ArchRule model_should_not_depend_on_infrastructure =
            noClasses()
                    .that()
                    .resideInAPackage("..model..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..infrastructure..");

    @ArchTest
    public static final ArchRule model_should_not_depend_on_web =
            noClasses()
                    .that()
                    .resideInAPackage("..model..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..web..");

    @ArchTest
    public static final ArchRule application_should_not_depend_on_infrastructure =
            noClasses()
                    .that()
                    .resideInAPackage("..application..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..infrastructure..");

    @ArchTest
    public static final ArchRule application_should_not_depend_on_web =
            noClasses()
                    .that()
                    .resideInAPackage("..application..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..web..");
}