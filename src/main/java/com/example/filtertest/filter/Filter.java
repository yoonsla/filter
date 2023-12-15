package com.example.filtertest.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;

public interface Filter extends jakarta.servlet.Filter {

    // 필터 초기화 메서드
    default void init(FilterConfig filterConfig) {}

    // 요청이 올 때마다 호출, 필터의 조건을 구현하는 곳
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException;

    // 필터 종료 메서드, 서블릿 컨테이너가 종료될 때 호출
    default void destroy() {}
}
