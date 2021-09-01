package empik.kata.restapi.users.model.domain.policies;

import empik.kata.restapi.users.model.domain.CalculatorPolicy;

public class DefaultCalculator implements CalculatorPolicy {

    @Override
    public double calculate(int followers, int publicRepos) {
        if (followers == 0) {
            return 0;
        }
        return ((double) 6 / followers) * (2 + publicRepos);
    }
}
