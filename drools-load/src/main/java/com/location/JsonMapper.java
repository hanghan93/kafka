package com.location;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

@SuppressWarnings({"SameParameterValue", "WeakerAccess", "unused"})
public abstract class JsonMapper {
    private JsonMapper() {}

    private final static ObjectMapper MAPPER = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE)
            .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);


    public static <T> String json(T entity) {
        try {
            return MAPPER.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Cannot convert entity to json", e);
        }
    }

    public static <T> T load(String resourceName, Class<T> valueType) {
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName)) {
            try (Reader reader = new InputStreamReader(stream, "UTF-8")) {
                return getReader(valueType).readValue(reader);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot load json", e);
        }
    }

    private static <T> ObjectReader getReader(Class<T> valueType) {
        return MAPPER.readerFor(valueType)
                .with(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
    }

    @JsonIgnoreType
    private class MixinIgnoreType {}
}