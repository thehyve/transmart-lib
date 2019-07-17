package org.transmartproject.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.transmartproject.common.resource.TreeResource;

@FeignClient(value="tree", url="${transmart-client.transmart-server-url}")
public interface TreeClient extends TreeResource {}
