package com.insta.dto;

import com.insta.model.AppUserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDataDTO {

    @ApiModelProperty(position = 2, hidden = true, notes = "used to display user name")
    List<AppUserRole> appUserRoles;
    @ApiModelProperty()
    private String username;
    @ApiModelProperty(position = 1)
    private String password;

}
