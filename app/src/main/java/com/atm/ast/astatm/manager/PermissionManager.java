package com.atm.ast.astatm.manager;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.atm.ast.astatm.listener.AlertDialogBoxClickInterface;
import com.atm.ast.astatm.listener.PermissionRationaleDialogListener;
import com.atm.ast.astatm.model.PermissionRequestModel;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.atm.ast.astatm.utils.ASTUtil;

public class PermissionManager {
    public static boolean handleRequestForPermission(PermissionRequestModel permissionRequestModel)//Activity activityContext, String permission, int requestCode, String msgForPermissionRationale, PermissionRationaleDialogListener listener, boolean isToShowRationale)
    {
        boolean checkPermission = false;
        if (ASTUtil.isAboveLollipop()) {
            Activity activityContext = permissionRequestModel.getActivityContext();
            String permission = permissionRequestModel.getPermission();
            int requestCode = permissionRequestModel.getRequestCode();
            String msgForPermissionRationale = permissionRequestModel.getMsgForPermissionRationale();
            boolean isToShowRationale = permissionRequestModel.isToShowRationale();
            PermissionRationaleDialogListener listener = permissionRequestModel.getListener();

            if (!isPermissionGranted(permission, activityContext)) {
                boolean shouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(activityContext, permission);
                if (isToShowRationale && shouldShowRequestPermissionRationale)    //if the app has requested this permission previously and the user denied the request without checking "Never ask again".
                {
                    //Show the message that why we need this permission.
                    OnPermissionRationaleClickListener alertDialogBoxClickListener = new OnPermissionRationaleClickListener(activityContext, requestCode, permission, listener);
                    ASTUIUtil.showCustomConfirmDialog(activityContext, msgForPermissionRationale, -1, "OK, go ahead", "Not now", alertDialogBoxClickListener);
                } else    //First time asking for the permission
                {
                    requestForPermission(activityContext, requestCode, permission);
                }
            } else    //Permission Granted.
            {
                checkPermission = true;
            }
        } else {
            checkPermission = true;
        }

        return checkPermission;
    }


    public static boolean handleRequestForMultiplePermissions(String[] permissions, Activity activity, int requestCode) {
        int count = 0;
        boolean checkPermission = false;
        if (ASTUtil.isAboveLollipop()) {
            for (int i = 0; i < permissions.length; i++) {
                if (isPermissionGranted(permissions[i], activity)) {
                    count++;
                    permissions[i] = "";
                }
            }
            if (count == permissions.length) {
                checkPermission = true;
            } else {
                requestForPermission(activity, requestCode, permissions);
            }
        } else {
            checkPermission = true;
        }
        return checkPermission;
    }

    private static class OnPermissionRationaleClickListener implements AlertDialogBoxClickInterface {
        private Activity activityContext;
        private int requestCode;
        private String permission;
        private PermissionRationaleDialogListener listener;

        public OnPermissionRationaleClickListener(Activity activityContext, int requestCode, String permission, PermissionRationaleDialogListener listener) {
            this.activityContext = activityContext;
            this.requestCode = requestCode;
            this.permission = permission;
            this.listener = listener;
        }

        @Override
        public void onButtonClicked(boolean isPositiveButtonClicked) {
            if (isPositiveButtonClicked) {
                requestForPermission(activityContext, requestCode, permission);
            } else {
                listener.onCancelPermissionRationale(requestCode);
            }
        }
    }

    /**
     * requestForPermission
     * This method is requesting for particular given permission.
     *
     * @param activityContext
     * @param requestCode
     * @param permission
     */
    private static void requestForPermission(Activity activityContext, int requestCode, String permission) {
        String[] permissionStringArray = {permission};
        ActivityCompat.requestPermissions(activityContext, permissionStringArray, requestCode);
    }


    private static void requestForPermission(Activity activityContext, int requestCode, String permission[]) {
        ActivityCompat.requestPermissions(activityContext, permission, requestCode);
    }


    public static boolean isPermissionGranted(String permission, Context context) {
        boolean isRequestGranted = false;
        if (ASTUtil.isAboveLollipop()) {
            int permissionStatus = ContextCompat.checkSelfPermission(context, permission);
            if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                isRequestGranted = true;
            }
        } else {
            isRequestGranted = true;
        }
        return isRequestGranted;
    }
}
