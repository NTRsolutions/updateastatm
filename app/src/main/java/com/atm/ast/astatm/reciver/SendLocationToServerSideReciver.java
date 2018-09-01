package com.atm.ast.astatm.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * /** * @author AST Inc.  08/04/2016.
 */
public class SendLocationToServerSideReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.v("Background_Service", "Background_Service");
        Intent service1 = new Intent(context, SendLocationToServerSevice.class);
        context.startService(service1);
        //System.out.println("alarm");
    }
}