package com.talenttrack.auth.dto;

public record LoginResponse(
  String token,
  String email,
  String name,
  String role
) {}
