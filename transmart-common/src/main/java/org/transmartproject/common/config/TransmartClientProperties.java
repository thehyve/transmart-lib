package org.transmartproject.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
    prefix = "transmart-client",
    ignoreUnknownFields = false
)
@Data
public class TransmartClientProperties {
    private String transmartServerUrl;
}
