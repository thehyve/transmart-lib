package org.transmartproject.common.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.transmartproject.common.dto.RelationTypeList;

/**
 * Resource for relation types.
 * Specifies the endpoint URLs and data types for
 * relation type clients and servers.
 */
@RequestMapping("/v2/relation_types")
public interface RelationTypeResource {

    /**
     * GET  /v2/relation_types
     *
     * List all relation types.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of all relation types.
     */
    @GetMapping
    ResponseEntity<RelationTypeList> listRelationTypes();

}
