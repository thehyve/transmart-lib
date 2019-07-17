package org.transmartproject.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.transmartproject.common.resource.RelationResource;

@FeignClient(value="relation", url="${transmart-client.transmart-server-url}")
public interface RelationClient extends RelationResource {}
