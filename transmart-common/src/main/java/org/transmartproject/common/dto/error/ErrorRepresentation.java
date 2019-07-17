package org.transmartproject.common.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorRepresentation {
    private final String messsage;
    private List<FieldErrorRepresentation> fieldErrors;

    public void add(String objectName, String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(new FieldErrorRepresentation(objectName, field, message));
    }
}
