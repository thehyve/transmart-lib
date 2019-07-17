package org.transmartproject.common.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.transmartproject.common.dto.PatientSetList;
import org.transmartproject.common.dto.PatientSetResult;
import org.transmartproject.common.constraint.Constraint;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * Resource for patient sets.
 * Specifies the endpoint URLs and data types for
 * patient set clients and servers.
 */
@RequestMapping("/v2/patient_sets")
public interface PatientSetResource {

    /**
     * GET /v2/patient_sets
     *
     * Lists all the patient sets that User has access to.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of all patient sets.
     */
    @GetMapping
    ResponseEntity<PatientSetList> listPatientSets();

    /**
     * GET /v2/patient_sets/:id
     *
     * Retrieves the patient sets with the id.
     *
     * @param id the id of the patient set to retrieve.
     * @return the ResponseEntity with status 200 (OK) and the patient set if it exists; status 404 (Not Found) otherwise.
     */
    @GetMapping("/{id}")
    ResponseEntity<PatientSetResult> getPatientSet(@PathVariable("id") Long id);

    /**
     * POST /v2/patient_sets?name=${name}&reuse=${reuse}</code>
     *
     * Creates a patient set ({@link PatientSetResult}) based on
     * the request body of type {@link Constraint}.
     *
     * @param name a name for the patient set
     * @param reuse (default: false)
     *
     * @return a map with the query result id, description, size, status, constraints and api version.
     */
    @PostMapping
    ResponseEntity<PatientSetResult> createPatientSet(
        @NotBlank @RequestParam("name") String name,
        @RequestParam("reuse") Boolean reuse,
        @RequestBody @Valid Constraint constraint
    );

}
