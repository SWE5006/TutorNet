package com.project.tutornet.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.project.tutornet.component.EncryptAttributeConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserInfoEntity {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private UUID id;
	private String username;
	private String password;
	@Convert(converter = EncryptAttributeConverter.class)
	private String emailAddress;
	private String activeStatus;
	@Convert(converter = EncryptAttributeConverter.class)
	private String mobileNumber;
	private String roles;
	private LocalDateTime createDatetime;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<RefreshTokenEntity> refreshTokens;

	// Explicit getter/setter methods
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public LocalDateTime getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(LocalDateTime createDatetime) {
		this.createDatetime = createDatetime;
	}

	public List<RefreshTokenEntity> getRefreshTokens() {
		return refreshTokens;
	}

	public void setRefreshTokens(List<RefreshTokenEntity> refreshTokens) {
		this.refreshTokens = refreshTokens;
	}
}
