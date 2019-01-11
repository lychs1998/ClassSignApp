package red.clf.app.afinal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import red.clf.app.afinal.R;
import red.clf.app.afinal.activity.ModifyPwdActivity;

/**
 * Created by HP on 2018/11/20.
 */

public class PrefFragment extends PreferenceFragment {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pre_setting);

    }
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {

        CheckBoxPreference checkBoxPreference = (CheckBoxPreference)findPreference("yesno_save_individual_info");
        EditTextPreference editTextPreference = (EditTextPreference)findPreference("myname");
        //让editTextPreference和checkBoxPreference的状态保持一致
        editTextPreference.setEnabled(checkBoxPreference.isChecked());

        PreferenceScreen ps=(PreferenceScreen)findPreference("intent");
        ps.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent=new Intent(getContext(), ModifyPwdActivity.class);
                startActivity(intent);
                return true;
            }
        });
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
