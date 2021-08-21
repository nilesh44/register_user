package com.ace.registeruser.filter;

import com.ace.registeruser.entity.UserSession;
import com.ace.registeruser.repository.UserSessionRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class SessionFilter implements Filter {

    private UserSessionRepository sessionRepository;

    private UserSession userinfoSession;

    private long sessionTimeOut;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String userName = httpRequest.getHeader("userName");

        //validate username
        if (!isUserNameValid(userName)) {
            sendErrorResponse(httpResponse, "invalid user");
            return;
        }

       // validate session
        Optional<UserSession> userSession = sessionRepository.findByUsername(userName.trim());

        if (isSessionExpired(userSession)) {
            sendErrorResponse(httpResponse, "please login again");
            return;
        } else {
            httpRequest.setAttribute("userSession", userSession.get());
        }
        //if everything valid proceed further
        chain.doFilter(httpRequest, httpResponse);

    }

    private boolean isUserNameValid(String userName) {
        if (StringUtils.isNotBlank(userName) && StringUtils.isAlpha(userName)) {
            return true;
        }
        return false;
    }

    public boolean isSessionExpired(Optional<UserSession> userSession) {
        if (!userSession.isPresent()) {
            return true;

        } else if (isSessionTimeOut(userSession.get().getLastLoginTime())) {

            userSession.get().setIsExpired("Y");
            sessionRepository.save(userSession.get());

            return true;
        } else {
            userSession.get().setLastLoginTime(new Timestamp(System.currentTimeMillis()));
            sessionRepository.save(userSession.get());

            return false;
        }
    }


    private void sendErrorResponse(HttpServletResponse response, String errorMessage) {

        Map<String, String> map = new HashMap<>();
        map.put("errorMassage", errorMessage);

        JSONObject jo = new JSONObject(map);

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        try {
            response.getWriter().write(jo.toString());
        } catch (IOException ex) {
            
        log.error("IOException :  ", ex);
        }
    }

    private boolean isSessionTimeOut(Timestamp lastlogInTime){

        return new Timestamp(System.currentTimeMillis()).getTime() >= lastlogInTime.getTime()+sessionTimeOut;
    }

}
