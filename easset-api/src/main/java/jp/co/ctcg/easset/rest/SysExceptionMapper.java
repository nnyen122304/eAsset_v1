package jp.co.ctcg.easset.rest;

import java.util.Optional;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import jp.co.ctcg.easset.dto.NonObjectResponse;

/**
 * システムエラーのResponseMapper(ステータス500)
 */
@Provider
public class SysExceptionMapper implements ExceptionMapper<Throwable>{

    @Override
    public Response toResponse(Throwable e) {
        StringBuffer message = new StringBuffer();

        Throwable th = e;
        do {
            message.append(th.getClass().getName() + ":" + Optional.ofNullable(th.getMessage()).orElse("")  + "\n");
            th = th.getCause();
        } while(th != null);

        return Response.status(Status.INTERNAL_SERVER_ERROR).type("application/json").
                entity(new NonObjectResponse<>(message.toString())).
                build();
    }

}
