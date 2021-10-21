package com.insta.dto;

import com.insta.model.AppUserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {

    @ApiModelProperty(position = 3)
    List<AppUserRole> appUserRoles;
    @ApiModelProperty()
    private Integer id;
    @ApiModelProperty(position = 1)
    private String username;
    @ApiModelProperty(position = 2)
    private String email;

}
