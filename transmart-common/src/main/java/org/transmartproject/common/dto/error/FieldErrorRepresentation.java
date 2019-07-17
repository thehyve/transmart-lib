package org.transmartproject.common.dto.error;

import lombok.Data;

@Data
public class FieldErrorRepresentation {
    private final String objectName;
    private final String field;
    private final String message;
}
