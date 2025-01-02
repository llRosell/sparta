package com.example.scheduledevtesterlv1.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    // 화이트리스트 URL 패턴 (로그인 없이 접근 가능한 URL들)
    private static final String[] WHITE_LIST = {"/", "/signup", "/login", "/logout"};

    @Override
    public void doFilter(
            ServletRequest request,  // 클라이언트의 요청을 처리하는 객체
            ServletResponse response,  // 클라이언트에게 응답을 보내는 객체
            FilterChain chain  // 필터 체인을 통해 요청과 응답을 처리하는 객체
    ) throws IOException, ServletException {

        // HttpServletRequest로 요청을 캐스팅하여 요청 URI를 추출
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        // HttpServletResponse로 응답을 캐스팅
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 화이트리스트에 포함된 URL은 필터를 거치지 않음
        if (!isWhiteList(requestURI)) {

            // 세션에서 "email" 속성을 확인하여 로그인 상태 여부 확인
            HttpSession session = httpRequest.getSession(false);
            if (session == null || session.getAttribute("email") == null) {
                log.warn("로그인 안됨: 세션 정보가 없습니다.");  // 로그인 안 된 상태에서 접근할 경우 경고 로그 출력
                httpResponse.sendRedirect("/login");  // 로그인 페이지로 리다이렉트
                return;  // 로그인되지 않은 상태에서는 필터 처리를 중단하고, 다음 필터나 서블릿이 호출되지 않도록 차단
            }

            // 로그인 상태인 경우, 로그 기록
            log.info("로그인에 성공했습니다.");
        }

        // 필터 로직을 통과한 후 다음 필터나 서블릿을 호출
        chain.doFilter(request, response);
    }

    // 화이트리스트에 해당하는 URL인지 확인하는 메서드
    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);  // 화이트리스트에 요청 URI가 포함되어 있는지 체크
    }
}