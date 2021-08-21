package com.ace.registeruser.vo.get;

import java.util.List;

import com.ace.registeruser.vo.create.UserPhone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserPhoneResponse {

	private List<UserPhone> userPhones;
}
