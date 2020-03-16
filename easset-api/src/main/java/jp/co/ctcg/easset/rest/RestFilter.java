package jp.co.ctcg.easset.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Optional;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import jp.co.ctcg.easset.dto.NonObjectResponse;
import jp.co.ctcg.easset.flex_common.Logging;

/**
 *
 * @author garapon
 */
@Provider
public class RestFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private static int OBJ_OUT_MAX_SIZE_IN = 10000;
    private static int OBJ_OUT_MAX_SIZE_OUT = 10000;

    /**
     * リクエストフィルター
     * リクエストをログ出力する
     */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(" - User: ").append(requestContext.getSecurityContext().getUserPrincipal() == null ? "unknown"
                        : requestContext.getSecurityContext().getUserPrincipal());
        sb.append(" - Header: ").append(requestContext.getHeaders());
        sb.append("\n - Method: ").append(requestContext.getMethod());
        sb.append(" - Path: ").append(requestContext.getUriInfo().getPath());
        if(requestContext.getUriInfo().getQueryParameters() != null) {
            Optional<String> qp = requestContext.getUriInfo().getQueryParameters().entrySet().stream().map(item -> (item.getKey() + "=" + item.getValue())).reduce((val1, val2) -> (val1 + "," + val2));
            sb.append("\n - QueryParameters: ").append("{" + qp.orElse("")  + "}");
        } else {
            sb.append("\n - QueryParameters: ").append("{}");
        }

        StringBuilder entitySB = new StringBuilder(Optional.ofNullable(getEntityBody(requestContext)).orElse("").toString());
        if(entitySB.length() > OBJ_OUT_MAX_SIZE_IN) { // 長すぎる文字はカット
            entitySB.delete(OBJ_OUT_MAX_SIZE_IN, entitySB.length());
            entitySB.append("...");
        }
        sb.append("\n - Entity: ").append(entitySB);
        Logging.info("HTTP REQUEST : " + sb.toString());
	}

    /**
     * レスポンスフィルター
     * ・非オブジェクト型のレスポンスをラップする
     * ・レスポンスをログ出力する
     */
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(" - Header: ").append(responseContext.getHeaders());

        if(responseContext.getStatus() == 200) {
            Object response = responseContext.getEntity();
            if(response instanceof String || response instanceof Number || response instanceof Boolean || response instanceof Character || response instanceof Byte) {
                responseContext.setEntity(new NonObjectResponse<>(response));
            } else if(response instanceof Date) {
                responseContext.setEntity(new NonObjectResponse<>(MappingUtils.getJsonDateFormat().format((Date) response)));
            }
        }

        StringBuilder entitySB = new StringBuilder(Optional.ofNullable(responseContext.getEntity()).orElse("").toString());
        if(entitySB.length() > OBJ_OUT_MAX_SIZE_OUT) { // 長すぎる文字はカット
            entitySB.delete(OBJ_OUT_MAX_SIZE_OUT, entitySB.length());
            entitySB.append("...");
        }
        sb.append("\n - Entity: ").append(entitySB);
        Logging.info("HTTP RESPONSE : " + sb.toString());
    }

    private String getEntityBody(ContainerRequestContext requestContext) throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = requestContext.getEntityStream();

        final StringBuilder b = new StringBuilder();
        byte[] buffer = new byte[8192];
        int bytesRead;
        while((bytesRead = in.read(buffer)) != -1){
            out.write(buffer, 0, bytesRead);
        }

        byte[] requestEntity = out.toByteArray();
        if (requestEntity.length == 0)
        {
            b.append("");
        }
        else
        {
            b.append(new String(requestEntity));
        }
        requestContext.setEntityStream( new ByteArrayInputStream(requestEntity) );
        return b.toString();
    }
}