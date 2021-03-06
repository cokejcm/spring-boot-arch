package com.demo.app.configuration.hateoas;

import java.io.IOException;

import javax.ws.rs.Path;

import com.demo.app.domain.Entity;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class EntitySerializer extends JsonSerializer<Entity> {

	@Override
	public void serialize(Entity value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		Class<?> currClass = value.getClass();
		StringBuilder uri = new StringBuilder();
		// Get the controller associated to this entity
		if (currClass.getAnnotation(ControllerClass.class) != null) {
			Class<?> controllerClass = currClass.getAnnotation(ControllerClass.class).value();
			String entityUrl = controllerClass.getAnnotation(Path.class).value();
			// Build the uri
			uri.append(entityUrl).append("/").append(value.getId());
			// Write the uri
			jgen.writeStartObject();
			jgen.writeStringField("href", uri.toString());
			jgen.writeEndObject();
		} else {
			jgen.writeNull();
		}
	}
}
