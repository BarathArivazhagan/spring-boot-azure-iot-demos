package com.barath.azure.app;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microsoft.azure.eventhubs.ConnectionStringBuilder;
import com.microsoft.azure.eventhubs.EventHubClient;
import com.microsoft.azure.eventhubs.EventHubException;

@Configuration
public class IOTConfiguration {
	
	private final IOTHubProperties hubProps;	
	
	public IOTConfiguration(IOTHubProperties hubProps) {
		super();
		this.hubProps = hubProps;
	}

	@Bean
	public EventHubClient  eventHubClient() throws URISyntaxException, EventHubException, IOException {
		ConnectionStringBuilder connStr = new ConnectionStringBuilder()
		        .setEndpoint(new URI(hubProps.getEndpoint()))
		        .setEventHubName(hubProps.getEventHubName())
		        .setSasKeyName(hubProps.getSasKeyName())
		        .setSasKey(hubProps.getSasKey());
		return EventHubClient.createFromConnectionStringSync(connStr.toString(), Executors.newScheduledThreadPool(1));
	}

}
