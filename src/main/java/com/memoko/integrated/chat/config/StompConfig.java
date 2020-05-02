package com.memoko.integrated.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import com.memoko.integrated.chat.interceptor.HandshakeInterceptor;


@Configuration
@EnableWebSocketMessageBroker
public class StompConfig extends AbstractWebSocketMessageBrokerConfigurer{

	@Autowired
	private HandshakeInterceptor handshakeInterceptor;

	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
		// TODO Auto-generated method stub
		registration.setSendTimeLimit(15 * 1000).setSendBufferSizeLimit(512 * 1024);
		super.configureWebSocketTransport(registration);
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// TODO Auto-generated method stub
		registry.enableSimpleBroker("/subscribe");			// subscribe prefix 설정
		registry.setApplicationDestinationPrefixes("/");	// send prefix 설정
	}
	
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// TODO Auto-generated method stub
		registry.addEndpoint("/endpoint").withSockJS().setInterceptors(handshakeInterceptor);
		
	}
	
}
