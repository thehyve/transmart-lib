package org.transmartproject.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.transmartproject.common.resource.StudyResource;

@FeignClient(value="study", url="${transmart-client.transmart-server-url}")
public interface StudyClient extends StudyResource {}
