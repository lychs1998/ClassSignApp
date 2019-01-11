package red.clf.app.afinal.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import java.util.ArrayList;
import java.util.List;

import red.clf.app.afinal.R;
import red.clf.app.afinal.adapter.RecycleViewDivider;
import red.clf.app.afinal.adapter.ResAdapter;
import red.clf.app.afinal.bean.ClassRes;
import red.clf.app.afinal.bean.ItemRes;
import red.clf.app.afinal.bean.SignRes;


public class ResActivity extends AppCompatActivity {
    private List<SignRes> reslist;
    private List<ClassRes> classlist;
    private List<ItemRes> items=new ArrayList<>();
    private List<ItemRes> items2=new ArrayList<>();
    private static final String SS_URL="http://XXXXXX/class/ss.php";

    private int signid;
    private SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        if (pref.getInt("theme",0)!=0) {
            //设置主题
            setTheme(pref.getInt("theme",0));
        }
        setContentView(R.layout.activity_res);
        final RecyclerView recyclerView=(RecyclerView)findViewById(R.id.review);
        final Intent intent=getIntent();
        reslist=JSON.parseArray(intent.getStringExtra("data"),SignRes.class);
        signid=reslist.get(0).getSignid();
        HttpParams p=new HttpParams();
        p.put("courseid",intent.getStringExtra("courseid"));
        RxVolley.post(SS_URL, p, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                if (t.startsWith("[")){
                    classlist=JSON.parseArray(t,ClassRes.class);
                    for (ClassRes classRes:classlist){
                        int type=0;
                        for (SignRes signRes:reslist){
                            if (signRes.getSno().equals(classRes.getSno())){
                                type=1;
                                items2.add(new ItemRes(signRes.getSno(),signRes.getSname(),1));
                                break;
                            }
                        }
                        if (type==0){
                            items.add(new ItemRes(classRes.getSno(),classRes.getSname(),0));
                        }
                    }
                    items.addAll(items2);
                    LinearLayoutManager manager=new LinearLayoutManager(ResActivity.this);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.addItemDecoration(new RecycleViewDivider(ResActivity.this, LinearLayoutManager.VERTICAL));
                    recyclerView.setAdapter(new ResAdapter(ResActivity.this,items,signid));
                }else{
                    Toast.makeText(ResActivity.this,"获取学生列表失败！",Toast.LENGTH_LONG).show();
                    ResActivity.this.finish();
                }
            }
        });
    }

}
