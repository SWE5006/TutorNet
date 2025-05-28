package com.project.tutornet.controller;

import com.project.tutornet.dto.ApiResponse;
import com.project.tutornet.dto.CreateUserRequest;
import com.project.tutornet.dto.ManageUserRequest;
import com.project.tutornet.service.ManageUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class ManageUserController {

  private final ManageUserService manageUserService;

  @PreAuthorize("hasAuthority('SCOPE_USER')")
  @GetMapping(path = "/all")
  public ResponseEntity<?> getAllEventUsers(Principal principal) {
    log.info("[UserController:getAllUsers]Request to get userlist started");
    try {
      return ResponseEntity.ok(manageUserService.getAllUsers());
    } catch (Exception e) {
      log.error("[UserController:getAllUsers] Failed to get user details", e);
      ApiResponse<String> response = new ApiResponse<>(
          "Failed to get user list",
          HttpStatus.INTERNAL_SERVER_ERROR.value(),
          null);
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('SCOPE_USER')")
  @PostMapping(path = "/create")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> createUser(
      @RequestBody CreateUserRequest userRequest) {
    log.info(
        "[UserController:createUser]Request to create user started");
    try {
      return ResponseEntity.ok(manageUserService.createUser(userRequest));
    } catch (Exception e) {
      log.error("[UserController:createUser] Failed to create user", e);
      ApiResponse<String> response = new ApiResponse<>(
          "Failed to create user",
          HttpStatus.INTERNAL_SERVER_ERROR.value(),
          null);
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('SCOPE_USER')")
  @GetMapping(path = "/{userId}/details")
  public ResponseEntity<?> getUserDetails(
      Principal principal,
      @PathVariable @Valid @NotNull String userId) {
    log.info(
        "[UserController:getUserDetails]Request to get user details started for user: {}",
        userId);
    try {
      return ResponseEntity.ok(manageUserService.getUserDetails(userId));
    } catch (Exception e) {
      log.error(
          "[UserController:getUserDetails] Failed to get user details",
          e);
      ApiResponse<String> response = new ApiResponse<>(
          "Failed to get user details",
          HttpStatus.INTERNAL_SERVER_ERROR.value(),
          null);
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('SCOPE_USER')")
  @PostMapping(path = "/update")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> updateUser(
      Principal principal,
      @RequestBody ManageUserRequest userRequest) {
    log.info(
        "[UserController:updateUser]Request to update user started for user: {}",
        userRequest.getUserId());
    try {
      return ResponseEntity.ok(new ApiResponse<>(
          manageUserService.updateUser(userRequest),
          HttpStatus.OK.value(),
          null));
    } catch (Exception e) {
      log.error("[UserController:updateUser] Failed to update user", e);
      ApiResponse<String> response = new ApiResponse<>(
          "Failed to update user",
          HttpStatus.INTERNAL_SERVER_ERROR.value(),
          null);
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('SCOPE_USER')")
  @DeleteMapping(path = "/{userId}/delete")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> deleteUser(
      @PathVariable @Valid @NotNull String userId) {
    log.info(
        "[UserController:deleteUser]Request to delete user started for user: ");
    try {
      manageUserService.deleteUser(userId);
      return ResponseEntity.ok(new ApiResponse<>(
          "Delete user successfully.",
          HttpStatus.OK.value(),
          null));
    } catch (Exception e) {
      log.error("[UserController:deleteUser] Failed to delete user", e);
      ApiResponse<String> response = new ApiResponse<>(
          "Failed to delete user",
          HttpStatus.INTERNAL_SERVER_ERROR.value(),
          null);
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
