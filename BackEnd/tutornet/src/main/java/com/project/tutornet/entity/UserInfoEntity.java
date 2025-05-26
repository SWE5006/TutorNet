package com.project.tutornet.entity;

import com.project.tutornet.component.EncryptAttributeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserInfoEntity {

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "name", nullable = false)
	private String username;

	@Column(name = "password", nullable = true)
	private String password;

	@Convert(converter = EncryptAttributeConverter.class)
	@Column(name = "emailAddress", nullable = false, unique = true)
	private String emailAddress;

	@Column(name = "activeStatus", nullable = false)
	private String activeStatus;

	@Convert(converter = EncryptAttributeConverter.class)
	@Column(name = "mobileNumber", nullable = true)
	private String mobileNumber;

	@Column(name = "roles", nullable = false)
	private String roles;

	@Column(name = "create_datetime", nullable = false)
	private LocalDateTime createDatetime;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<RefreshTokenEntity> refreshTokens;

}
