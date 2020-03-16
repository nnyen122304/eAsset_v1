<%-- e申請インナーフレームからindex.jspでアクセスされるため --%>
<%@ page pageEncoding="UTF-8" %>
<% response.sendRedirect(request.getContextPath() + (request.getQueryString() == null ? "" : "?" + request.getQueryString())); %>