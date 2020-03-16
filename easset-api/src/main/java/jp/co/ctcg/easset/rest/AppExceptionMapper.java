package jp.co.ctcg.easset.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import jp.co.ctcg.easset.template.utils.AppException;

/**
 * アプリケーションエラーのResponseMapper(ステータス400)
 */
@Provider
public class AppExceptionMapper implements ExceptionMapper<AppException>{
    @Override
    public Response toResponse(AppException e) {
        return Response.status(Status.BAD_REQUEST).type("application/json").
                entity(MappingUtils.toJson(e)).
                build();
    }

}
