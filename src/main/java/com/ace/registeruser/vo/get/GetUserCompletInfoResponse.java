package com.ace.registeruser.vo.get;

import java.util.List;

import com.ace.registeruser.vo.create.UserEmail;
import com.ace.registeruser.vo.create.UserPhone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserCompletInfoResponse {

	private List<UserEmail> emails;

	private List<UserPhone> phones;
}
