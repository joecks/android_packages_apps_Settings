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

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.provider.Settings;

import com.android.internal.telephony.TelephonyIntents;
import com.android.internal.telephony.TelephonyProperties;
import com.android.settings.bluetooth.BluetoothEnabler;
import com.android.settings.wifi.WifiEnabler;

public class WirelessSettings extends PreferenceActivity {

    private static final String KEY_TOGGLE_AIRPLANE = "toggle_airplane";
    private static final String KEY_TOGGLE_BLUETOOTH = "toggle_bluetooth";
    private static final String KEY_TOGGLE_WIFI = "toggle_wifi";
    private static final String KEY_WIFI_SETTINGS = "wifi_settings";
    private static final String KEY_BT_SETTINGS = "bt_settings";
    private static final String KEY_VPN_SETTINGS = "vpn_settings";
    private static final String KEY_TOGGLE_TETHERING = "toggle_tethering";
    private static final String KEY_PROXY_SETTING = "proxy_setting";
    public static final String EXIT_ECM_RESULT = "exit_ecm_result";
    public static final int REQUEST_CODE_EXIT_ECM = 1;
    private static final String KEY_TOGGLE_MULTILINK = "toggle_multilink";

    private WifiEnabler mWifiEnabler;
    private AirplaneModeEnabler mAirplaneModeEnabler;
    private BluetoothEnabler mBtEnabler;
<<<<<<< HEAD:src/com/android/settings/WirelessSettings.java
    private CheckBoxPreference mAirplaneModePreference;
    private TetheringEnabler mTetheringEnabler;

=======
    private MultiLinkEnabler mMultiLinkEnabler;
>>>>>>> 3e4a0a2... Added the Enable MultiLink preference to the wifi setting:src/com/android/settings/WirelessSettings.java
    /**
     * Invoked on each preference click in this hierarchy, overrides
     * PreferenceActivity's implementation.  Used to make sure we track the
     * preference click events.
     */
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if ( (preference == mAirplaneModePreference) &&
                (Boolean.parseBoolean(
                    SystemProperties.get(TelephonyProperties.PROPERTY_INECM_MODE))) ) {
            // In ECM mode launch ECM app dialog
            startActivityForResult(
                new Intent(TelephonyIntents.ACTION_SHOW_NOTICE_ECM_BLOCK_OTHERS, null),
                REQUEST_CODE_EXIT_ECM);

            return true;
        }
        else {
            // Let the intents be launched by the Preference manager
            return false;
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.wireless_settings);

        initToggles();
        mAirplaneModePreference = (CheckBoxPreference) findPreference(KEY_TOGGLE_AIRPLANE);
<<<<<<< HEAD:src/com/android/settings/WirelessSettings.java
=======
        mWifiEnabler = new WifiEnabler(this, wifi);
        mBtEnabler = new BluetoothEnabler(this, bt);

	mMultiLinkEnabler = new MultiLinkEnabler(this,
                (CheckBoxPreference) findPreference(KEY_TOGGLE_MULTILINK));

        String toggleable = Settings.System.getString(getContentResolver(),
                Settings.System.AIRPLANE_MODE_TOGGLEABLE_RADIOS);

        // Manually set dependencies for Wifi when not toggleable.
        if (toggleable == null || !toggleable.contains(Settings.System.RADIO_WIFI)) {
            wifi.setDependency(KEY_TOGGLE_AIRPLANE);
            findPreference(KEY_WIFI_SETTINGS).setDependency(KEY_TOGGLE_AIRPLANE);
            findPreference(KEY_VPN_SETTINGS).setDependency(KEY_TOGGLE_AIRPLANE);
        }

        // Manually set dependencies for Bluetooth when not toggleable.
        if (toggleable == null || !toggleable.contains(Settings.System.RADIO_BLUETOOTH)) {
            bt.setDependency(KEY_TOGGLE_AIRPLANE);
            findPreference(KEY_BT_SETTINGS).setDependency(KEY_TOGGLE_AIRPLANE);
        }

        // Disable Bluetooth Settings if Bluetooth service is not available.
        if (ServiceManager.getService(BluetoothAdapter.BLUETOOTH_SERVICE) == null) {
            findPreference(KEY_BT_SETTINGS).setEnabled(false);
        }

        // Disable Tethering if it's not allowed
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (!cm.isTetheringSupported()) {
            getPreferenceScreen().removePreference(findPreference(KEY_TETHER_SETTINGS));
        } else {
            String[] usbRegexs = cm.getTetherableUsbRegexs();
            String[] wifiRegexs = cm.getTetherableWifiRegexs();
            Preference p = findPreference(KEY_TETHER_SETTINGS);
            if (wifiRegexs.length == 0) {
                p.setTitle(R.string.tether_settings_title_usb);
                p.setSummary(R.string.tether_settings_summary_usb);
            } else {
                if (usbRegexs.length == 0) {
                    p.setTitle(R.string.tether_settings_title_wifi);
                    p.setSummary(R.string.tether_settings_summary_wifi);
                } else {
                    p.setTitle(R.string.tether_settings_title_both);
                    p.setSummary(R.string.tether_settings_summary_both);
                }
            }
        }
>>>>>>> 3e4a0a2... Added the Enable MultiLink preference to the wifi setting:src/com/android/settings/WirelessSettings.java
    }
    
    @Override
    protected void onResume() {
        super.onResume();
<<<<<<< HEAD:src/com/android/settings/WirelessSettings.java
        
=======
        mMultiLinkEnabler.resume(); 
        mAirplaneModeEnabler.resume();
>>>>>>> 3e4a0a2... Added the Enable MultiLink preference to the wifi setting:src/com/android/settings/WirelessSettings.java
        mWifiEnabler.resume();
        mBtEnabler.resume();
        mAirplaneModeEnabler.resume();
        mTetheringEnabler.resume();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
<<<<<<< HEAD:src/com/android/settings/WirelessSettings.java
        
=======
	mMultiLinkEnabler.pause();        
        mAirplaneModeEnabler.pause();
>>>>>>> 3e4a0a2... Added the Enable MultiLink preference to the wifi setting:src/com/android/settings/WirelessSettings.java
        mWifiEnabler.pause();
        mAirplaneModeEnabler.pause();
        mBtEnabler.pause();
        mTetheringEnabler.pause();
    }
    
    private void initToggles() {
        
        Preference airplanePreference = findPreference(KEY_TOGGLE_AIRPLANE);
        Preference wifiPreference = findPreference(KEY_TOGGLE_WIFI);
        Preference btPreference = findPreference(KEY_TOGGLE_BLUETOOTH);
        Preference wifiSettings = findPreference(KEY_WIFI_SETTINGS);
        Preference vpnSettings = findPreference(KEY_VPN_SETTINGS);
        Preference proxySetting = findPreference(KEY_PROXY_SETTING);
        
        IBinder b = ServiceManager.getService(BluetoothAdapter.BLUETOOTH_SERVICE);
        if (b == null) {
            // Disable BT Settings if BT service is not available.
            Preference btSettings = findPreference(KEY_BT_SETTINGS);
            btSettings.setEnabled(false);
        }

        mWifiEnabler = new WifiEnabler(
                this, (WifiManager) getSystemService(WIFI_SERVICE),
                (CheckBoxPreference) wifiPreference);
        mAirplaneModeEnabler = new AirplaneModeEnabler(
                this, (CheckBoxPreference) airplanePreference);
        mBtEnabler = new BluetoothEnabler(this, (CheckBoxPreference) btPreference);

        // manually set up dependencies for Wifi if its radio is not toggleable in airplane mode
        String toggleableRadios = Settings.System.getString(getContentResolver(),
                Settings.System.AIRPLANE_MODE_TOGGLEABLE_RADIOS);
        if (toggleableRadios == null || !toggleableRadios.contains(Settings.System.RADIO_WIFI)) {
            wifiPreference.setDependency(airplanePreference.getKey());
            wifiSettings.setDependency(airplanePreference.getKey());
            vpnSettings.setDependency(airplanePreference.getKey());
            proxySetting.setDependency(airplanePreference.getKey());
        }
        
        mTetheringEnabler = new TetheringEnabler(
        		this, (CheckBoxPreference) findPreference(KEY_TOGGLE_TETHERING));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
        case REQUEST_CODE_EXIT_ECM:
            Boolean isChoiceYes =
                data.getBooleanExtra(EXIT_ECM_RESULT, false);
            // Set Airplane mode based on the return value and checkbox state
            mAirplaneModeEnabler.setAirplaneModeInECM(isChoiceYes,
                    mAirplaneModePreference.isChecked());
            break;

        default:
            break;
        }
    }

}
