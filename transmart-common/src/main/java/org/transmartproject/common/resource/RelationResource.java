package org.transmartproject.common.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.transmartproject.common.dto.RelationList;

/**
 * Resource for relations.
 * Specifies the endpoint URLs and data types for
 * relation clients and servers.
 */
@RequestMapping("/v2/relations")
public interface RelationResource {

    /**
     * GET  /v2/relations
     *
     * List all relations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of all relations.
     */
    @GetMapping
    ResponseEntity<RelationList> listRelations();

}
