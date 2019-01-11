package red.clf.app.afinal.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import red.clf.app.afinal.fragment.PrefFragment;
import red.clf.app.afinal.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HP on 2018/11/20.
 */

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.setbar)
    Toolbar mToolbar;
    private SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        if (pref.getInt("theme",0)!=0) {
            //设置主题
            setTheme(pref.getInt("theme",0));
        }
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        PreferenceFragment fragment=new PrefFragment();
        getFragmentManager().beginTransaction().replace(R.id.setframe, fragment).commit();

        PreferenceManager.setDefaultValues(this, R.xml.pre_setting, false);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
