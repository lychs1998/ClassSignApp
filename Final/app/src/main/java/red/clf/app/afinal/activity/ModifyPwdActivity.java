package red.clf.app.afinal.activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import red.clf.app.afinal.R;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * @author clf
 */

public class ModifyPwdActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private String user;
    private int flag;
    @BindView(R.id.oldpwd)
    EditText oldpwd;

    @BindView(R.id.newpwd)
    EditText newpwd;

    @BindView(R.id.pwd_btn)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        if (pref.getInt("theme",0)!=0) {
            //设置主题
            setTheme(pref.getInt("theme",0));
        }
        setContentView(R.layout.activity_modify_pwd);
        ButterKnife.bind(this);
        user=pref.getString("no","");
        flag=pref.getInt("flag",1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpParams p2=new HttpParams();
                p2.put("user",user);
                p2.put("flag",flag);
                p2.put("pwd",oldpwd.getText().toString());
                p2.put("newpwd",newpwd.getText().toString());
                RxVolley.post("http://XXXXXX/user/change.php", p2, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        JSONObject json=JSONObject.parseObject(t);
                        if (json.getIntValue("id")==1){
                            Toast.makeText(ModifyPwdActivity.this,"修改密码成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ModifyPwdActivity.this,"修改密码失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                finish();
            }
        });
    }
}
