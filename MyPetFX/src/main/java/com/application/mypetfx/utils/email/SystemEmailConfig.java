package com.application.mypetfx.utils.email;

import com.application.mypetfx.utils.properties.AppProperties;

public class SystemEmailConfig {

    private final String email = AppProperties.getInstance().getProperty("EMAIL_USERNAME");
    private final String watchword = AppProperties.getInstance().getProperty("EMAIL_WATCHWORD");

    public String getEmail() {
        return this.email;
    }

    public String getWatchword() {
        return this.watchword;
    }

}
