package com.app.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String userName;
	private String fullName;
	private String phone;
	private RoleDTO roleObj	;
	private Boolean forcePasswordChange;

}
