package com.barath.azure.app;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.sdk.iot.device.DeviceClient;
import com.microsoft.azure.sdk.iot.device.IotHubEventCallback;
import com.microsoft.azure.sdk.iot.device.IotHubStatusCode;
import com.microsoft.azure.sdk.iot.device.Message;

@SpringBootApplication
@RestController
public class AzureIOTSimulator {
	
	@Autowired
	private MessageSender messageSender;
	

	public static void main(String[] args) {
		SpringApplication.run(AzureIOTSimulator.class, args);
	}
	
	@PostMapping("/send")
	public void postToHub(@RequestBody Object payload) {
		
			this.messageSender.sendMessage(payload);
	}
	
	@Component
	protected static class MessageSender {
		
		private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

		private static final ObjectMapper mapper = new ObjectMapper();
		
		@Autowired
		private DeviceClient deviceClient;
		
		public void sendMessage(Object payload) {
				
			 if(logger.isInfoEnabled()) {
				 logger.info("Sending message to IOT hub {}",payload);
			 }
			Message message=null;
			try {
				message = new Message(mapper.writeValueAsString(payload));
				deviceClient.open();
				 deviceClient.sendEventAsync(message, new IotHubEventCallback() {
						
						@Override
						public void execute(IotHubStatusCode responseStatus, Object callbackContext) {
							
							 logger.info("IOT Response || STATUS CODE || {}",responseStatus.ordinal());
							 
						}
					},null);
			  deviceClient.close();
			} catch (JsonProcessingException e) {
				logger.error("Exception in converting object to string");
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("Exception in sending message object to IOT hub");
				e.printStackTrace();
			}
			
			
		}
	}

}
