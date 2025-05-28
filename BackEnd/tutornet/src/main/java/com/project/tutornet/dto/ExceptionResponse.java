package com.project.tutornet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponse {
  private String message;
  private int code;
  private String errorCode;
}
