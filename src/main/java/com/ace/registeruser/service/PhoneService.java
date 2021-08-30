package com.ace.registeruser.service;

import com.ace.registeruser.entity.UserSession;
import com.ace.registeruser.vo.expire.PhoneExpireRequest;
import com.ace.registeruser.vo.get.GetUserPhoneResponse;
import com.ace.registeruser.vo.phone.AddSecondaryPhoneNumber;
import com.ace.registeruser.vo.update.ChangePrimaryPhoneNumber;

public interface PhoneService {

     void addAddSecondaryPhoneNumber(AddSecondaryPhoneNumber addSecondaryPhoneNumber, UserSession userSession);
     void addNewPrimaryPhoneNumber(ChangePrimaryPhoneNumber changePrimaryPhoneNumber, UserSession userSession);
     void expirePhone(PhoneExpireRequest expirePhone, UserSession userSession);
     GetUserPhoneResponse getPhone(String user, UserSession userSession);

}
