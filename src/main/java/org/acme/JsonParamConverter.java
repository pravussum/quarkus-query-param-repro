package org.acme;

import java.lang.reflect.Type;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ParamConverter;

public class JsonParamConverter<T> implements ParamConverter<T> {

	private final ObjectMapper objectMapper;
	private final Class<T> rawType;
	private final JavaType genericType;

	public JsonParamConverter(ObjectMapper objectMapper, Class<T> rawType, Type genericType) {
		this.objectMapper = objectMapper;
		this.genericType = genericType != null ? TypeFactory.defaultInstance().constructType(genericType) : null;
		this.rawType = rawType;
	}

	@Override
	public T fromString(String value) {
		if(rawType.isAssignableFrom(String.class)) {
			//noinspection unchecked
			return (T)value;
		}
		try {
			return genericType != null ? objectMapper.readValue(value, genericType) : objectMapper.readValue(value, rawType);
		} catch (JsonProcessingException e) {
			throw(new RuntimeException(e));
		}
	}

	@Override
	public String toString(T value) {
		// TODO remove once fixed in Resteasy
		// This is a hack until https://issues.redhat.com/browse/RESTEASY-3086 is resolved to prevent the JSON
		// encoding of the accept header parameter
		if(MediaType.class.equals(value.getClass())) {
			//noinspection RedundantCast
			return ((MediaType)value).toString();
		}
		if (rawType.isAssignableFrom(String.class)) {
			return (String)value;
		}
		try {
			return objectMapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			throw(new RuntimeException(e));
		}
	}
}
