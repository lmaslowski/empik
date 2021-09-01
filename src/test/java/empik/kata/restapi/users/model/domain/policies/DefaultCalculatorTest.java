package empik.kata.restapi.users.model.domain.policies;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultCalculatorTest {

    @Test
    void givenParams_returnCalculation() {
        assertEquals(0.015220700152207, (new DefaultCalculator()).calculate(3942, 8));
        assertEquals(2, (new DefaultCalculator()).calculate(6, 0));
        assertEquals(0, (new DefaultCalculator()).calculate(0, 0));
    }
}