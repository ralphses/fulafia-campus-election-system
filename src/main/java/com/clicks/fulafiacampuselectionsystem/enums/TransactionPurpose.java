package com.clicks.fulafiacampuselectionsystem.enums;

public enum TransactionPurpose {

    FORM_PURCHASE("purchase form"),
    REGISTER_CLIENT("Registration fee");

    private String value;
    TransactionPurpose(String value) {
        this.value = value;
    }
}
