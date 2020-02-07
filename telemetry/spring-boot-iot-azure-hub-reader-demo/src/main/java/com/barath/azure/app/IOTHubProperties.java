package com.barath.azure.app;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "iot.hub",ignoreUnknownFields = true, ignoreInvalidFields = true)
public class IOTHubProperties {
	
	private String endpoint;
	
	private String eventHubName;
	
	private String sasKeyName;
	
	private String sasKey;

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getEventHubName() {
		return eventHubName;
	}

	public void setEventHubName(String eventHubName) {
		this.eventHubName = eventHubName;
	}

	public String getSasKeyName() {
		return sasKeyName;
	}

	public void setSasKeyName(String sasKeyName) {
		this.sasKeyName = sasKeyName;
	}

	public String getSasKey() {
		return sasKey;
	}

	public void setSasKey(String sasKey) {
		this.sasKey = sasKey;
	}
	
	

}
