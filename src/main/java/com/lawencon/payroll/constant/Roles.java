package com.lawencon.payroll.constant;

import lombok.Getter;

@Getter
public enum Roles {
  RL001("Super Admin"), RL002("Payroll Service"), RL003("Client");

  private final String roleName;

  Roles(String roleName) {
    this.roleName = roleName;
  }
}