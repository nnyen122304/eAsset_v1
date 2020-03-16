package jp.co.ctcg.easset.rest;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class RestConfig implements ContextResolver<ObjectMapper> {

    private ObjectMapper mapper = new ObjectMapper();

    public RestConfig() {
        mapper.setDateFormat(MappingUtils.getJsonDateFormat());
    }

    @Override
    public ObjectMapper getContext(Class<?> arg0) {
        return mapper;
    }
}