package com.ace.registeruser.vo.phone;

import com.ace.registeruser.validation.PhoneAlradyPresentInDB;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddSecondaryPhoneNumber {

    @Schema(description = "secondary phone number for contact",example = "6767676767",required = true)
    @NotEmpty(message = "secondaryPhoneNumber must not blank")
    @Digits(fraction = 0, integer = 10, message = "secondaryPhoneNumber should be number")
    @Size(min = 10, max = 10, message = "please provide 10 digit secondaryPhoneNumber")
    @PhoneAlradyPresentInDB
    private String secondaryPhoneNumber;
}
