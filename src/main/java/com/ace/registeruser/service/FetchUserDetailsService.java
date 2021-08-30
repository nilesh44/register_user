package com.ace.registeruser.service;

import com.ace.registeruser.entity.UserSession;
import com.ace.registeruser.vo.get.GetUserCompletInfoResponse;

public interface FetchUserDetailsService {

    GetUserCompletInfoResponse getUserCompleteInfo(String user,UserSession userSession);
}
