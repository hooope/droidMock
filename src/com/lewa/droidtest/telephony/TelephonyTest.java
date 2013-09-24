package com.lewa.droidtest.telephony;

import android.content.Context;
import android.os.Bundle;
import android.provider.Telephony.SIMInfo;
import android.util.Log;

import com.lewa.droidtest.test.Test;

public class TelephonyTest extends Test{

    private static final String TAG = "Telephony";

    public TelephonyTest(Context context, Bundle extras) {
        super(context, extras);
    }

    @Override
    public void test() {
        
    }
    
    public void siminfo() {
        Log.e(TAG, "insertedSize: "  + SIMInfo.getInsertedSIMList(mContext).size());
    }
    
}