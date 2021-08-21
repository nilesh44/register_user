package com.ace.registeruser.service;

import com.ace.registeruser.entity.UserSession;
import com.ace.registeruser.vo.expire.EmailExpireRequest;
import com.ace.registeruser.vo.get.GetEmailResponse;
import com.ace.registeruser.vo.update.EmailUpdateRequest;

public interface EmailService {

    GetEmailResponse getEmail(String user, UserSession userSession);

   void updateEmail(EmailUpdateRequest emailUpdateRequest, UserSession userinfoSession);

   void expireEmail(EmailExpireRequest expireEmail, UserSession userSession);
}
