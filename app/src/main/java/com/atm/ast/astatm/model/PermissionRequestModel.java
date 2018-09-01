package com.atm.ast.astatm.model;


import android.app.Activity;

import com.atm.ast.astatm.listener.PermissionRationaleDialogListener;


public class PermissionRequestModel {
    private String permission;
    private int requestCode;
    private String msgForPermissionRationale;
    private PermissionRationaleDialogListener listener;
    private boolean isToShowRationale;

    public Activity getActivityContext() {
        return (Activity) listener;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public String getMsgForPermissionRationale() {
        return msgForPermissionRationale;
    }

    public void setMsgForPermissionRationale(String msgForPermissionRationale) {
        this.msgForPermissionRationale = msgForPermissionRationale;
    }

    public PermissionRationaleDialogListener getListener() {
        return listener;
    }

    public void setListener(PermissionRationaleDialogListener listener) {
        this.listener = listener;
    }

    public boolean isToShowRationale() {
        return isToShowRationale;
    }

    public void setToShowRationale(boolean isToShowRationale) {
        this.isToShowRationale = isToShowRationale;
    }

}
