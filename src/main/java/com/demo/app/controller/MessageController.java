package com.demo.app.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.app.domain.Message;
import com.demo.app.service.MessageService;

@Path("/")
@Component
public class MessageController {

	@Autowired
	private MessageService messageService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/messages")
	public List<Message> messages() {
		return messageService.getMessages();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/message/{id}")
	public Message messageById(@PathParam("id") String id) {
		return messageService.getMessage(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/saveMessage")
	public void saveMessage(Message message) {
		messageService.saveMessage(message);
	}
}