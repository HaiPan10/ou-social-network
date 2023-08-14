package com.ou.pojo;

import lombok.Data;

@Data
public class Notification {
    private String target;
    private String title;
    private String body;
}
