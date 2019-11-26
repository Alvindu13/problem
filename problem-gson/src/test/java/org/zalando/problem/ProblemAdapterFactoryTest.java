package org.zalando.problem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProblemAdapterFactoryTest {

    @Test
    void defaultConstructorShouldBuildIndexCorrectly() {
        assertDoesNotThrow((ThrowingSupplier<ProblemAdapterFactory>) ProblemAdapterFactory::new);
    }

    @Test
    void shouldThrowForDuplicateStatusCode() {
        assertThrows(IllegalArgumentException.class, () -> new ProblemAdapterFactory(Status.class, CustomStatus.class));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void shouldNotThrowForDuplicateSubtype() {
        assertDoesNotThrow(() -> {
            ProblemAdapterFactory factory = new ProblemAdapterFactory();
            factory.registerSubtype(OutOfStockException.class, OutOfStockException.TYPE)
                    .registerSubtype(OutOfStockException.class, InsufficientFundsProblem.TYPE);
        });
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void shouldThrowForDuplicateURI() {
        assertThrows(IllegalArgumentException.class, () -> {
            ProblemAdapterFactory factory = new ProblemAdapterFactory();
            factory.registerSubtype(OutOfStockException.class, OutOfStockException.TYPE)
                    .registerSubtype(InsufficientFundsProblem.class, OutOfStockException.TYPE);
        });
    }
}