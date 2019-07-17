package org.transmartproject.common.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.transmartproject.common.dto.Forest;

/**
 * Resource for the ontology tree.
 * Specifies the endpoint URLs and data types for
 * ontology tree clients and servers.
 */
@RequestMapping("/v2/tree_nodes")
public interface TreeResource {

    /**
     * GET /v2/tree_nodes
     *
     * Fetches all ontology nodes the satisfy the specified criteria (all nodes by default)
     * as a forest.
     *
     * @param root (Optional) the root element from which to fetch.
     * @param depth (Optional) the maximum number of levels to fetch.
     * @param constraints flag if the constraints should be included in the result
     *   (defaults: true)
     * @param counts flag if counts should be included in the result (default: false)
     * @param tags flag if tags should be included in the result (default: false)
     *
     * @return a forest of ontology nodes.
     */
    @GetMapping
    ResponseEntity<Forest> getForest(
        @RequestParam(value = "root", required = false) String root,
        @RequestParam(value = "depth", required = false) Integer depth,
        @RequestParam(value = "constraints", required = false) Boolean constraints,
        @RequestParam(value = "counts", required = false) Boolean counts,
        @RequestParam(value = "tags", required = false) Boolean tags);

}
