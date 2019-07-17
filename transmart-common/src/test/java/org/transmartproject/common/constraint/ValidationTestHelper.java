package org.transmartproject.common.constraint;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationTestHelper {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> void validate(T object) {
        if (object == null) {
            return;
        }
        Set<ConstraintViolation<T>> errors = validator.validate(object);
        if (errors != null && !errors.isEmpty()) {
            String sErrors = errors.stream()
                .map((ConstraintViolation<T> error) ->
                    String.format("%s: %s", error.getPropertyPath().toString(), error.getMessage()))
                .collect(Collectors.joining("; "));
            throw new ValidationException(String.format("%d error(s): %s", errors.size(), sErrors));
        }
    }

}
