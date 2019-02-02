package com.sun.springcloudzuul.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @className: ZuulFilterToken
 * @description: token过滤器
 * @author: Jay Sun
 * @create: 2018/12/24 9:32
 **/
@Component
public class ZuulFilterToken extends ZuulFilter {

    /**
     * 请求头部标识
     */
    private static final String HEADER_TOKEN = "token";
    /**
     * token值
     */
    private static final String TOKEN_VALUE = "123";

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String token = request.getHeader(HEADER_TOKEN);
        if (StringUtils.isBlank(token)) {
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(401);
            context.setResponseBody("缺少token");
            context.getResponse().setContentType("text/html;charset=UTF-8");
        } else {
            if (!TOKEN_VALUE.equals(token)) {
                context.setSendZuulResponse(false);
                context.setResponseStatusCode(401);
                context.setResponseBody("token错误");
                context.getResponse().setContentType("text/html;charset=UTF-8");
            }
        }

        return null;
    }

}
