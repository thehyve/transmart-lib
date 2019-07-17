package org.transmartproject.common.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.transmartproject.common.dto.Dimension;
import org.transmartproject.common.dto.DimensionList;

/**
 * Resource for dimensions.
 * Specifies the endpoint URLs and data types for
 * dimension clients and servers.
 */
@RequestMapping("/v2/dimensions")
public interface DimensionResource {

    /**
     * GET  /v2/dimensions
     *
     * List all dimensions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of all dimensions.
     */
    @GetMapping
    ResponseEntity<DimensionList> listDimensions();

    /**
     * GET  /v2/dimensions/:name
     *
     * Get the dimension with the name.
     *
     * @param name the name of the dimension to retrieve.
     * @return the ResponseEntity with status 200 (OK) and the dimension if it exists; status 404 (Not Found) otherwise.
     */
    @GetMapping("/{name}")
    ResponseEntity<Dimension> getDimension(@PathVariable("name") String name);

}
