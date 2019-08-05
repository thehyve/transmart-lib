package org.transmartproject.proxy.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.transmartproject.common.dto.Dimension;
import org.transmartproject.common.dto.DimensionList;
import org.transmartproject.common.resource.DimensionResource;
import org.transmartproject.proxy.security.CurrentUser;
import org.transmartproject.proxy.service.DimensionClientService;

/**
 * Proxy server for dimensions.
 */
@RestController
@Validated
@CrossOrigin
@Api(value="dimensions", description="Dimensions")
public class DimensionProxyServer implements DimensionResource {

    private Logger log = LoggerFactory.getLogger(DimensionProxyServer.class);

    private DimensionClientService dimensionClientService;

    DimensionProxyServer(DimensionClientService dimensionClientService) {
        log.info("Dimension proxy server initialised.");
        this.dimensionClientService = dimensionClientService;
    }

    @Override
    @ApiOperation(value = "List all dimensions", produces = "application/json")
    public ResponseEntity<DimensionList> listDimensions() {
        log.info("List all dimension for user {}", CurrentUser.getLogin());
        return ResponseEntity.ok(this.dimensionClientService.fetchDimensions());
    }

    @Override
    @ApiOperation(value = "Get dimension by name", produces = "application/json")
    public ResponseEntity<Dimension> getDimension(String name) {
        log.info("Get dimension with name '{}' for user {}", name, CurrentUser.getLogin());
        return ResponseEntity.ok(this.dimensionClientService.fetchDimension(name));
    }

}
