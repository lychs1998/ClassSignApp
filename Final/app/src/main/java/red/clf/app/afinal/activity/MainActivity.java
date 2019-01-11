package red.clf.app.afinal.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import red.clf.app.afinal.DatabaseDao.CourseManager;
import red.clf.app.afinal.DatabaseDao.DetailManager;
import red.clf.app.afinal.adapter.MyPagerAdapter;
import red.clf.app.afinal.fragment.AllSchedule;
import red.clf.app.afinal.fragment.TodaySchedule;
import red.clf.app.afinal.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.main_tablayout)
    TabLayout tabLayout;

    @BindView(R.id.main_schedule)
    ViewPager viewPager;

    private ArrayList<Fragment> fragmentList=new ArrayList<>();
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    TextView headerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        if (pref.getInt("theme",0)!=0) {
            //设置主题
            setTheme(pref.getInt("theme",0));
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        editor=pref.edit();

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main);
                drawer.openDrawer(GravityCompat.START);
            }
        });

        fragmentList.add(new AllSchedule());
        fragmentList.add(new TodaySchedule());
        ArrayList<String> titleDatas=new ArrayList<>();
        titleDatas.add("所有课程");
        titleDatas.add("今日课程");
        MyPagerAdapter myPagerAdapter=new MyPagerAdapter(getSupportFragmentManager(),fragmentList,titleDatas);
        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(myPagerAdapter);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.action_logout:
                        PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("no",null).apply();
                        CourseManager courseManager=new CourseManager(MainActivity.this);
                        DetailManager detailManager=new DetailManager(MainActivity.this);
                        courseManager.deleteAll();
                        detailManager.deleteAll();
                        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                        editor.putString("myname","");
                        editor.apply();
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.action_set:
                        Intent intent2=new Intent(MainActivity.this,SettingActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.action_theme:
                        showSingleChoiceDialog();
                        return true;
                    default:break;
                }
                return false;
            }
        });

        View headerLayout = mNavigationView.getHeaderView(0);
        headerId=headerLayout.findViewById(R.id.header_id);
        String id=pref.getString("no",null);
        headerId.setText(id);
        String name=pref.getString("myname",null);
        if(name!=null&&(!name.equals(""))){
            headerId.setText(name);
        }

    }
    String[] items = {"blue","black","red"};
    int yourChoice;
    int stheme;
    private void showSingleChoiceDialog(){

        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(this);
        singleChoiceDialog.setTitle("请选择主题");
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
                        if(yourChoice==0){
                            stheme=R.style.AppTheme;
                        }else if(yourChoice==1){
                            stheme=R.style.AppThemeDark;
                        }else if(yourChoice==2){
                            stheme=R.style.AppThemeRed;
                        }
                        editor.putInt("theme",stheme);
                        editor.apply();
                        //Toast.makeText(MainActivity.this, pref.getInt("theme",0)+"", Toast.LENGTH_SHORT).show();
                        recreate();
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

    public void onResume(){
        super.onResume();
        String name=pref.getString("myname",null);
        String id=pref.getString("no",null);
        if(name!=null&&(!name.equals(""))){
            headerId.setText(name);
        }else{
            headerId.setText(id);
        }
    }

}