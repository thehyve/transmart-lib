package org.transmartproject.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.transmartproject.common.config.TransmartClientProperties;

@EnableFeignClients
@EnableConfigurationProperties({ TransmartClientProperties.class })
@SpringBootApplication
public class TransmartProxyServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransmartProxyServerApplication.class, args);
	}

}
