package org.transmartproject.common.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.transmartproject.common.dto.Study;
import org.transmartproject.common.dto.StudyList;

/**
 * Resource for studies.
 * Specifies the endpoint URLs and data types for
 * study clients and servers.
 */
@RequestMapping("/v2/studies")
public interface StudyResource {

    /**
     * GET  /v2/studies
     *
     * List all studies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of all studies.
     */
    @GetMapping
    ResponseEntity<StudyList> listStudies();

    /**
     * GET  /v2/studies/:id
     *
     * Get the study with the id.
     *
     * @param id the id of the study to retrieve.
     * @return the ResponseEntity with status 200 (OK) and the study if it exists; status 404 (Not Found) otherwise.
     */
    @GetMapping("/{id}")
    ResponseEntity<Study> getStudy(@PathVariable("id") Long id);

}
