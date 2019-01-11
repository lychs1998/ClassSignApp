package red.clf.app.afinal.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import red.clf.app.afinal.DatabaseDao.CourseManager;
import red.clf.app.afinal.DatabaseDao.DetailManager;
import red.clf.app.afinal.R;
import red.clf.app.afinal.bean.BeanCourse;
import red.clf.app.afinal.bean.BeanDetail;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by HP on 2018/12/23.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String LOGIN_URL="http://XXXXXX/user/login.php";
    private static final int RC_LOC = 100;
    @BindView(R.id.login_stu)
    RadioButton stu_btn;

    @BindView(R.id.login_teacher)
    RadioButton tea_btn;

    @BindView(R.id.login_id)
    EditText edit_id;

    @BindView(R.id.student_pwd)
    EditText edit_pwd;

    @BindView(R.id.login_btn)
    Button login_btn;

    @BindView(R.id.txtil)
    TextInputLayout txtil;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        if (pref.getInt("theme",0)!=0) {
            //设置主题
            setTheme(pref.getInt("theme",0));
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        checkWifiPermissions();

        if (pref.getString("no",null)!=null){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        txtil.setHint("学号");
        stu_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                txtil.setHint("学号");
            }
        });
        tea_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                txtil.setHint("教师编号");
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                editor=pref.edit();
                if(stu_btn.isChecked()){
                    final String id=edit_id.getText().toString();
                    final String pwd=edit_pwd.getText().toString();
                    final String flag="1";
                    HttpParams p=new HttpParams();
                    p.put("user",id);
                    p.put("pwd",pwd);
                    p.put("flag",flag);

                    RxVolley.post(LOGIN_URL, p, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            JSONObject json=JSONObject.parseObject(t);
                            if (json.getIntValue("id")==1){
                                editor.putString("no",id);
                                editor.putInt("flag",1);
                                editor.apply();
                                HttpParams p2=new HttpParams();
                                p2.put("user",id);
                                p2.put("flag",1);
                                RxVolley.post("http://XXXXXX/class/all.php", p2, new HttpCallback() {
                                    @Override
                                    public void onSuccess(String t) {
                                        if (t.startsWith("[")){
                                            CourseManager courseManager=new CourseManager(LoginActivity.this);
                                            DetailManager detailManager=new DetailManager(LoginActivity.this);
                                            List<JSONObject> list= JSON.parseArray(t,JSONObject.class);
                                            for (JSONObject json:list){
                                                BeanCourse course=new BeanCourse(json.getString("courseId"),json.getString("cname"),json.getString("des"),json.getString("tname"));
                                                courseManager.addCourse(course);
                                                detailManager.addDetail(new BeanDetail(Integer.valueOf(json.getString("detailId")),course,Integer.parseInt(json.getString("section")),Integer.parseInt(json.getString("sectionSpan")),Integer.parseInt(json.getString("week")),json.getString("classRoom")));
                                            }
                                        }else{
                                            Toast.makeText(LoginActivity.this,"服务器繁忙或不存在课程",Toast.LENGTH_SHORT).show();
                                        }
                                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                            }else {
                                Toast.makeText(LoginActivity.this,"登陆失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else if(tea_btn.isChecked()){
                    final String id=edit_id.getText().toString();
                    final String pwd=edit_pwd.getText().toString();
                    final String flag="2";
                    HttpParams p=new HttpParams();
                    p.put("user",id);
                    p.put("pwd",pwd);
                    p.put("flag",flag);
                    RxVolley.post(LOGIN_URL, p, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            JSONObject json=JSONObject.parseObject(t);
                            if (json.getIntValue("id")==1){
                                editor.putString("no",id);
                                editor.putInt("flag",2);
                                editor.apply();
                                HttpParams p2=new HttpParams();
                                p2.put("user",id);
                                p2.put("flag",2);
                                RxVolley.post("http://XXXXXX/class/all.php", p2, new HttpCallback() {
                                    @Override
                                    public void onSuccess(String t) {
                                        if (t.startsWith("[")){
                                            CourseManager courseManager=new CourseManager(LoginActivity.this);
                                            DetailManager detailManager=new DetailManager(LoginActivity.this);
                                            List<JSONObject> list= JSON.parseArray(t,JSONObject.class);
                                            for (JSONObject json:list){
                                                BeanCourse course=new BeanCourse(json.getString("courseId"),json.getString("cname"),json.getString("des"),json.getString("tname"));
                                                courseManager.addCourse(course);
                                                detailManager.addDetail(new BeanDetail(Integer.valueOf(json.getString("detailId")),course,Integer.parseInt(json.getString("section")),Integer.parseInt(json.getString("sectionSpan")),Integer.parseInt(json.getString("week")),json.getString("classRoom")));
                                            }
                                        }else{
                                            Toast.makeText(LoginActivity.this,"服务器繁忙或不存在课程",Toast.LENGTH_SHORT).show();
                                        }
                                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        LoginActivity.this.finish();
                                    }
                                });
                            }else {
                                Toast.makeText(LoginActivity.this,"登陆失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }


    @AfterPermissionGranted(RC_LOC)
    private void checkWifiPermissions(){
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this,"请授予应用定位权限",RC_LOC,perms);
        }
    }
}
