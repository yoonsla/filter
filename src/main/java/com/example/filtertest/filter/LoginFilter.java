package com.example.filtertest.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.PatternMatchUtils;

@Log4j2
public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {"/", "/login", "/logout"};
    private static final String REDIRECT_URL = "/login?redirectURL=";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("login do filter");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        try {
            log.info("인증 체크 필터 시작 {}", requestURI);
            if (istWhiteList(requestURI)) {
                return;
            }
            HttpSession session = httpRequest.getSession(false);
            log.info("get session ==> {}", session);

            if (session == null) {
                httpResponse.sendRedirect(REDIRECT_URL + requestURI);
                log.info("리다이렉트 되었습니다 ::: {} ===> {}", requestURI, REDIRECT_URL);
                return;
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }
    }

    private boolean istWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
