package com.example.scheduledevlv8.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {"/", "/signup", "/login", "/logout"};

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        // 요청 URI 추출
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 화이트리스트에 포함된 경우, 필터 로직을 건너뛴다
        if (!isWhiteList(requestURI)) {

            // 세션 확인
            HttpSession session = httpRequest.getSession(false);
            if (session == null || session.getAttribute("email") == null) {
                log.warn("로그인 안됨: 세션 정보가 없습니다.");
                httpResponse.sendRedirect("/login"); // 로그인 페이지로 리다이렉트
                return; // 로그인 안된 상태로 계속 진행하지 않도록 차단
            }

            // 로그인 성공한 경우 로깅
            log.info("로그인에 성공했습니다.");
        }

        // 1번 경우 : WHITE LIST에 등록된 URL 요청이라면 chain.doFilter() 호출
        // 2번 경우 : WHITE LIST가 아닌 경우 위 필터로직을 통과 후에 chain.doFilter() 다음 필터나 servlet을 호출한다.
        // 다음 필터가 없으면 Servlet -> Controller, 다음 필터가 있으면 다음 filterf를 호출한다.
        chain.doFilter(request, response);

    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
