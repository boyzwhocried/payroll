  package com.lawencon.payroll.constant;

  import lombok.Getter;

  @Getter
  public enum NotificationCodes {
    NT001("New Monthly Payroll"), NT002("New Client Document Has Been Uploaded"), NT003("Documents Required");

    private final String code;

    NotificationCodes(String code) {
      this.code = code;
    }
  }
