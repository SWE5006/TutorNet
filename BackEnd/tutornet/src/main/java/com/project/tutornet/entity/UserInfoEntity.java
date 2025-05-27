package com.project.tutornet.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.project.tutornet.component.EncryptAttributeConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<RefreshTokenEntity> refreshTokens;

}
