package red.clf.app.afinal.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import red.clf.app.afinal.R;
import red.clf.app.afinal.thread.StartThread;
import red.clf.app.afinal.thread.ThreadPool;
import red.clf.app.afinal.thread.WifiThread;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HP on 2019/1/1.
 */

public class CourseActivity extends AppCompatActivity {
    private static final String END_URL="http://XXXXXX/sign/end.php";
    private static final String SIGN_URL="http://XXXXXX/sign/sign.php";
    private String teacher;
    private String course;
    private int detailid;
    private int flag;
    private String user;
    SharedPreferences pref;
    private String courseid;
    @BindView(R.id.course_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.sign_btn)
    Button btn;

    @BindView(R.id.course_text)
    TextView course_text;

    @BindView(R.id.teacher_text)
    TextView teacher_text;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        if (pref.getInt("theme",0)!=0) {
            //设置主题
            setTheme(pref.getInt("theme",0));
        }
        setContentView(R.layout.activity_course);
        ButterKnife.bind(this);
        btn.setClickable(true);
        Intent intent=getIntent();
        teacher=intent.getStringExtra("teacher");
        course=intent.getStringExtra("course");
        detailid=intent.getIntExtra("detailid",0);
        courseid=intent.getStringExtra("courseid");

        flag=pref.getInt("flag",1);
        user=pref.getString("no","");
        course_text.setText(course);
        teacher_text.setText(teacher);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (flag==1){
            btn.setText("签到");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ThreadPool.start(new WifiThread(String.valueOf(detailid),CourseActivity.this,signhandler));
                }
            });
        }else if (flag==2){
            if (pref.getInt("sign",0)==0){
                btn.setText("开始签到");
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ThreadPool.start(new StartThread(String.valueOf(detailid), CourseActivity.this, starthandler));
                    }
                });
            }else{
                btn.setText("结束签到");
                btn.setBackgroundColor(getColor(R.color.colorAccent));
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HttpParams p=new HttpParams();
                        //value是课程id
                        p.put("detailid",detailid);
                        RxVolley.post(END_URL, p, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
                                if (t.startsWith("{")){
                                    Toast.makeText(CourseActivity.this,"服务器繁忙，请再次尝试",Toast.LENGTH_LONG).show();
                                }else if (t.startsWith("[")){
                                    btn.setClickable(false);
                                    pref.edit().putInt("sign",0).apply();
                                    Intent intent = new Intent(CourseActivity.this,ResActivity.class);
                                    intent.putExtra("data",t);
                                    intent.putExtra("detailid",detailid);
                                    intent.putExtra("courseid",courseid);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                });
            }
        }
    }

    @SuppressLint("HandlerLeak")
    Handler signhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                HttpParams p=new HttpParams();
                p.put("sno",user);
                p.put("detailid",detailid);
                RxVolley.post(SIGN_URL, p, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        JSONObject json=JSONObject.parseObject(t);
                        if ("1".equals(json.getString("code"))){
                            Toast.makeText(CourseActivity.this,"签到成功",Toast.LENGTH_SHORT).show();
                            btn.setClickable(false);
                            btn.setText("已签到");
                            btn.setBackgroundColor(getColor(R.color.green));
                        }else{
                            Toast.makeText(CourseActivity.this,"未开始签到或签到失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else if (msg.what==-1){
                Toast.makeText(CourseActivity.this,"签到失败",Toast.LENGTH_SHORT).show();
            }else if (msg.what==0){
                Toast.makeText(CourseActivity.this,"请打开wifi后签到",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @SuppressLint("HandlerLeak")
    Handler starthandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                Toast.makeText(CourseActivity.this,"开始签到",Toast.LENGTH_SHORT).show();
                pref.edit().putInt("sign",1).apply();
                btn.setText("结束签到");
                btn.setBackgroundColor(getColor(R.color.colorAccent));
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HttpParams p=new HttpParams();
                        //value是课程id
                        p.put("detailid",detailid);
                        RxVolley.post(END_URL, p, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
                                if (t.startsWith("{")){
                                    Toast.makeText(CourseActivity.this,"服务器繁忙，请再次尝试",Toast.LENGTH_LONG).show();
                                }else if (t.startsWith("[")){
                                    pref.edit().putInt("sign",0).apply();
                                    Intent intent = new Intent(CourseActivity.this,ResActivity.class);
                                    intent.putExtra("data",t);
                                    intent.putExtra("detailid",detailid);
                                    intent.putExtra("courseid",courseid);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                });
            }else if (msg.what==-1){
                Toast.makeText(CourseActivity.this,"失败",Toast.LENGTH_SHORT).show();
            }else if (msg.what==0){
                Toast.makeText(CourseActivity.this,"请打开Wifi", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
