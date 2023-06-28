package com.app.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer roleId;
	private String roleName;
	
	 
}
