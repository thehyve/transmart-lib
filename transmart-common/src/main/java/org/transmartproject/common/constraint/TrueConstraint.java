package org.transmartproject.common.constraint;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor
@JsonTypeName("true")
public class TrueConstraint extends Constraint {

}
