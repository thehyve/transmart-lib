package org.transmartproject.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.transmartproject.common.resource.DimensionResource;

@FeignClient(value="dimension", url="${transmart-client.transmart-server-url}")
public interface DimensionClient extends DimensionResource {}
