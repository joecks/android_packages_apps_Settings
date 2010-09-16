/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings;

import com.android.internal.telephony.PhoneStateIntentReceiver;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.provider.Settings;
import android.telephony.ServiceState;

public class MultiLinkEnabler implements Preference.OnPreferenceChangeListener {

    private final Context mContext;

    private PhoneStateIntentReceiver mPhoneStateReceiver;
    
    private final CheckBoxPreference mCheckBoxPref;

    private static final int EVENT_SERVICE_STATE_CHANGED = 3;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_SERVICE_STATE_CHANGED:
                    onMultiLinkModeChanged();
                    break;
            }
        }
    };

    public MultiLinkEnabler(Context context, CheckBoxPreference multiLinkCheckBoxPreference) {
        
        mContext = context;
        mCheckBoxPref = multiLinkCheckBoxPreference;
        
        multiLinkCheckBoxPreference.setPersistent(false);
    
       // mPhoneStateReceiver = new PhoneStateIntentReceiver(mContext, mHandler);
       // mPhoneStateReceiver.notifyServiceState(EVENT_SERVICE_STATE_CHANGED);
    }

    public void resume() {
        
        // This is the widget enabled state, not the preference toggled state
        mCheckBoxPref.setEnabled(true);
        mCheckBoxPref.setChecked(isMultiLinkModeOn(mContext));

       // mPhoneStateReceiver.registerIntent();
        mCheckBoxPref.setOnPreferenceChangeListener(this);
    }
    
    public void pause() {
        //mPhoneStateReceiver.unregisterIntent();
        mCheckBoxPref.setOnPreferenceChangeListener(null);
    }
    
    static boolean isMultiLinkModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.MULTILINK_ON, 0) != 0;
    }

    private void setMultiLinkModeOn(boolean enabling) {
        
        mCheckBoxPref.setEnabled(false);
        mCheckBoxPref.setSummary(enabling ? R.string.multilink_turning_on
                : R.string.multilink_turning_off);
        
        // Change the system setting
        Settings.System.putInt(mContext.getContentResolver(), Settings.System.MULTILINK_ON, 
                                enabling ? 1 : 0);
        
       //Its not nessary for now
        // Post the intent
       // Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
       // intent.putExtra("state", enabling);
       // mContext.sendBroadcast(intent);
    }

    /**
     * Called when we've received confirmation that the multilink mode was set.
     */
    private void onMultiLinkModeChanged() {
       // ServiceState serviceState = mPhoneStateReceiver.getServiceState();
        boolean lmipModeEnabled = true;
        mCheckBoxPref.setChecked(lmipModeEnabled);
        mCheckBoxPref.setSummary(lmipModeEnabled ? null : 
                mContext.getString(R.string.multilink_summary));            
        mCheckBoxPref.setEnabled(true);
    }
    
    /**
     * Called when someone clicks on the checkbox preference.
     */
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        setMultiLinkModeOn((Boolean) newValue);
        return true;
    }

}
