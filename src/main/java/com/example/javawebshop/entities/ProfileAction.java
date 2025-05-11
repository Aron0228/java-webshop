package com.example.javawebshop.entities;

public class ProfileAction {
    private String actionName;
    private String href;

    public ProfileAction() {}

    public ProfileAction(String actionName, String href) {
        this.actionName = actionName;
        this.href = href;
    }

    public String getActionName() {
        return actionName;
    }
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
    public String getHref() {
        return href;
    }
    public void setHref(String href) {
        this.href = href;
    }
}
