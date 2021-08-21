package com.ace.registeruser.vo.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPersonalInfo {

    @NotEmpty(message = "please provide Firstname")
    private String firstName;

    @NotEmpty(message = "please provide LastName")
    private String lastName;

}
