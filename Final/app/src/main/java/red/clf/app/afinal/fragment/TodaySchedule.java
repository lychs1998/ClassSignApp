package red.clf.app.afinal.fragment;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import red.clf.app.afinal.DatabaseDao.CourseManager;
import red.clf.app.afinal.DatabaseDao.DetailManager;
import red.clf.app.afinal.ListItem;
import red.clf.app.afinal.R;
import red.clf.app.afinal.adapter.MyAdapter;
import red.clf.app.afinal.bean.BeanCourse;
import red.clf.app.afinal.bean.BeanDetail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HP on 2019/1/4.
 */

public class TodaySchedule  extends Fragment {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.now_week)
    TextView tvNow;

    int yourChoice=0;
    String[] items = new String[20];
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private ArrayList<ListItem> list=new ArrayList<>();
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_today_schedule, container, false);
        ButterKnife.bind(this,view);

        initList();
        MyAdapter adapter=new MyAdapter(list,getContext());
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);

        tvNow.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                showSingleChoiceDialog();
            }
        });
        for(int i=0;i<20;i++){
            items[i]="第"+(i+1)+"周";
        }
        pref= PreferenceManager.getDefaultSharedPreferences(getContext());
        int w = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        if (w <= 0) {
            w = 7;
        }

        int week=pref.getInt("week",0);
        tvNow.setText(items[week]);
        boolean isEdit=pref.getBoolean("isEdit",false);
        editor=pref.edit();
        if(isEdit==false&&w==1){
            if(week!=19){
                tvNow.setText(items[week+1]);
                editor.putInt("week",week+1);
            }
            else{
                tvNow.setText(items[0]);
                editor.putInt("week",0);
            }
            editor.putBoolean("isEdit",true);
        }else if(isEdit==true&&w!=1){
            editor.putBoolean("isEdit",false);
        }

        editor.apply();
        return view;
    }

    private void showSingleChoiceDialog(){

        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(getContext());
        singleChoiceDialog.setTitle("请选择第几周");
        // 第二个参数是默认选项
        singleChoiceDialog.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg=new Message();
                                msg.what=1;
                                handler.sendMessage(msg);
                            }
                        }).start();

                    }
                });
        singleChoiceDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        singleChoiceDialog.show();
    }

    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            if(msg.what==1){
                editor.putInt("week",yourChoice);
                editor.apply();
                tvNow.setText(items[yourChoice]);
            }
        }
    };

    public void initList(){
        int w = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        if (w <= 0) {
            w = 7;
        }

        DetailManager dm=new DetailManager(getContext());
        ArrayList<BeanDetail> allDetail=dm.loadAll();
        ArrayList<BeanDetail> todayDetail=new ArrayList<>();
        for(BeanDetail bd:allDetail){
            if(bd.getWeek()==w)
                todayDetail.add(bd);
        }

        for(BeanDetail detail:todayDetail){
            ListItem item=new ListItem();
            item.setBegin(detail.getSection());
            item.setClassName(detail.getCourse().getCourseName());
            item.setClassRoom(detail.getClassRoom());
            item.setEnd(detail.getSection()+detail.getSectionSpan()-1);
            item.setCourseid(detail.getCourse().getCourseId());
            item.setDetailid(detail.getDetailId());
            item.setTeachername(detail.getCourse().getTeacherName());
            list.add(item);
        }

        for(int i=0;i<list.size();i++){
            for(int j=i+1;j<list.size();j++){
                if(list.get(i).getBegin()>list.get(j).getBegin()){
                    Collections.swap(list,i,j);
                }
            }
        }

    }
}
