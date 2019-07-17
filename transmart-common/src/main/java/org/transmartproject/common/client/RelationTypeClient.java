package org.transmartproject.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.transmartproject.common.resource.RelationTypeResource;

@FeignClient(value="relationtype", url="${transmart-client.transmart-server-url}")
public interface RelationTypeClient extends RelationTypeResource {}
