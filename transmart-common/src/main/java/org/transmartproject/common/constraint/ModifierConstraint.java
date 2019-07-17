package org.transmartproject.common.constraint;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.transmartproject.common.validation.ValidModifierConstraint;

import javax.validation.Valid;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@JsonTypeName("modifier")
@ValidModifierConstraint
public class ModifierConstraint extends Constraint {
    private String modifierCode;
    private String path;
    private String dimensionName;
    private @Valid ValueConstraint values;
}
