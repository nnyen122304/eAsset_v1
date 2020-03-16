package jp.co.ctcg.easset.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Webサービス実行時のフィルタークラス
 */
public class WebServiceFilter implements Filter {

    /**
     * @see javax.servlet.Filter#init(FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    /**
     * @see javax.servlet.Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new WebServiceRequestWrapper((HttpServletRequest) request), response);
    }

    /**
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
    }

}