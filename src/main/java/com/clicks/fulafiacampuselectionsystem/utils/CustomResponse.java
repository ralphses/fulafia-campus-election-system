package com.clicks.fulafiacampuselectionsystem.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomResponse {

    private boolean success;
    private Object data;
}
