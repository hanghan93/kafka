package com.location;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class RuleRunnerTest extends RuleRunner<Measurement> {

    Measurement container;

    @Test
    @DisplayName("No pnr")
    void fired_01()  {
        Supplier<Measurement> factory = () -> JsonMapper.load("json/Measurement", Measurement.class);
        container = factory.get();
        run(container);
        Assertions.assertEquals(1,1);
    }
}