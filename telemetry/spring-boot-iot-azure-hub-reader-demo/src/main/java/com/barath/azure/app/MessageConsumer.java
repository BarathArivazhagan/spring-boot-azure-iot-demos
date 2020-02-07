package com.barath.azure.app;

import java.lang.invoke.MethodHandles;
import java.time.Instant;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.microsoft.azure.eventhubs.EventData;
import com.microsoft.azure.eventhubs.EventHubClient;
import com.microsoft.azure.eventhubs.EventHubException;
import com.microsoft.azure.eventhubs.EventPosition;

@Service
public class MessageConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private final EventHubClient eventHubClient;

	public MessageConsumer(EventHubClient eventHubClient) {
		super();
		this.eventHubClient = eventHubClient;
	}
	
	public void receiveMessage() {
		
	}
	
	@PostConstruct
	public void init() throws EventHubException {
		eventHubClient.createReceiver(EventHubClient.DEFAULT_CONSUMER_GROUP_NAME, "0", EventPosition.fromEnqueuedTime(Instant.now()))
             .thenAcceptAsync( receiver -> {
            	 try {
					Iterable<EventData> eventsData =  receiver.receiveSync(100);
					eventsData.forEach( event -> {
						logger.info("Event Data received || {}",new String(event.getBytes()));
					});
				} catch (EventHubException e) {
					
					e.printStackTrace();
				}
             });
	}
	
	
	

}
