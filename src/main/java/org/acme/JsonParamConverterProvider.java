package org.acme;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import org.apache.commons.lang3.ClassUtils;

@Provider
public class JsonParamConverterProvider implements ParamConverterProvider {

	@Context
	private Providers providers;
	final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {

		// only provide a JSON conversion for QueryParams (otherwise HeaderParams will get converted also)
		// Also use this ParamConverter if annotations are null in a best-effort manner since there is a path in
		// Resteasy client where query params are converted without the annotations being present in the context.
		// Otherwise toString() is called otherwise, which is definitely wrong
		// Once https://issues.redhat.com/browse/RESTEASY-3086 is fixed, we can strictly rely on the annotations param
		if (annotations != null && Arrays.stream(annotations)
				.noneMatch(a -> QueryParam.class.isAssignableFrom(a.annotationType())))
		{
			return null;
		}

		// only provide JSON conversion for complex types
		if (!isGeneric(rawType, genericType) && isStringOrPrimitiveOrWrapper(rawType)) {
			return null;
		}

		return new JsonParamConverter<>(objectMapper, rawType, genericType);
	}

	private <T> boolean isGeneric(Class<T> rawType, Type genericType) {
		return genericType != null && !rawType.equals(genericType);
	}

	private <T> boolean isStringOrPrimitiveOrWrapper(Class<T> rawType) {
		try {
			return String.class.getName().equals(rawType.getTypeName()) ||
					ClassUtils.isPrimitiveOrWrapper(Class.forName(rawType.getTypeName()));
		} catch(ClassNotFoundException e) {
			return false;
		}
	}

}
