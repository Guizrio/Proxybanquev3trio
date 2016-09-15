package com.trio.proxibanquev3.presentation;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Guillaume on 12/09/2016.
 */
public class LoginFilter implements Filter {

    @Inject
    LoginBean loginBean;

    /**
     * On vérifie ici que l'utilisateur est loggué, s'il ne l'est pas ou plus, on redirige vers la page de login.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        LoginBean loginBean = (LoginBean) ((HttpServletRequest) request).getSession().getAttribute("loginBean");

        if (loginBean == null || !loginBean.isLoggedIn()) {
            String contextPath = ((HttpServletRequest) request).getContextPath();
            ((HttpServletResponse) response).sendRedirect(contextPath + "/login.xhtml");
        }

        filterChain.doFilter(request, response);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
