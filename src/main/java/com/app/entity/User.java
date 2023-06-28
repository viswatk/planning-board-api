package com.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.app.enumeration.UserType;
import com.app.util.PasswordUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="users")
public class User extends RecordModifier  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Integer userId;
	
	@Column(name = "reference_id")
	private Integer referenceId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="user_type")
	private UserType userType;
	
	@Column(name="user_name")
	private String userName;
	
	@JsonIgnore
	@Column(name="password")
	private String password; 
	
	@Column(name="full_name")
	private String fullName;
	
	@Column(name="email")
	private String email; 
	
	@Column(name="phone_no")
	private String phoneNo;
	
	@OneToOne()
	@JoinColumn(name = "user_role")
	private Role roleObj;
	
	@Column(name = "force_password_change")
	private Boolean forcePasswordChange;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;
	
	@Column(name = "is_locked")
	private Boolean isLocked;

	@Column(name = "otp_verified")
	private Boolean isOtpVerified;
	
	@JsonIgnore
	@Column(name="deleted_on")
	private	Date deletedOn;
	 
	@JsonIgnore
	@Column(name="deleted_by")
	private	String deletedBy;
	
	@Transient
	@NotNull(message = "Password")
    @ApiModelProperty(value = "Valid password", required = true, allowableValues = "String")
	private String reqPassword; 
	
	@Transient
	private Integer roleId;
	 
	public void setAndEncryptPassword(String password) {
		setPassword(PasswordUtil.getEncryptedPassword(password));
	}
	 
	public Integer getRoleId() {
		if(userId != null && roleObj != null) {
			roleId = roleObj.getId();
		}
		return roleId;
	}
	 
}