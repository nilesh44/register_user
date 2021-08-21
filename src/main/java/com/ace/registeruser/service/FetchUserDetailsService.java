package com.ace.registeruser.service;

import com.ace.registeruser.entity.UserSession;
import com.ace.registeruser.vo.get.GetEmailResponse;
import com.ace.registeruser.vo.get.GetUserCompletInfoResponse;
import com.ace.registeruser.vo.get.GetUserPhoneResponse;

public interface FetchUserDetailsService {

    GetUserCompletInfoResponse getUserCompleteInfo(String user,UserSession userSession);
}
