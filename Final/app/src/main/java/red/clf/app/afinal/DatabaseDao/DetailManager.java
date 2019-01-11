package red.clf.app.afinal.DatabaseDao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import red.clf.app.afinal.MyDatabaseHelper;
import red.clf.app.afinal.bean.BeanCourse;
import red.clf.app.afinal.bean.BeanDetail;

import java.util.ArrayList;

/**
 * Created by HP on 2018/12/28.
 */

public class DetailManager {
    private Context mcontext;
    public DetailManager(Context mcontext){
        this.mcontext=mcontext;
    }
    public BeanDetail addDetail(BeanDetail detail){
        SQLiteDatabase conn= MyDatabaseHelper.getConnection(mcontext);
        ContentValues values=new ContentValues();
        values.put("detailId",detail.getDetailId());
        values.put("courseId",detail.getCourse().getCourseId());
        values.put("section",detail.getSection());
        values.put("sectionSpan",detail.getSectionSpan());
        values.put("week",detail.getWeek());
        values.put("classRoom",detail.getClassRoom());
        conn.replace("Detail",null,values);
        values.clear();
        return detail;
    }
    public void deleteAll(){
        SQLiteDatabase conn= MyDatabaseHelper.getConnection(mcontext);
        conn.delete("Detail",null,null);
    }
    public ArrayList<BeanDetail> loadAll(){
        SQLiteDatabase conn= MyDatabaseHelper.getConnection(mcontext);
        ArrayList<BeanDetail> detailList=new ArrayList<>();
        Cursor cursor=conn.query("Detail",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int detailId=cursor.getInt(cursor.getColumnIndex("detailId"));
                String courseId=cursor.getString(cursor.getColumnIndex("courseId"));
                int section=cursor.getInt(cursor.getColumnIndex("section"));
                int sectionSpan=cursor.getInt(cursor.getColumnIndex("sectionSpan"));
                int week=cursor.getInt(cursor.getColumnIndex("week"));
                String classRoom=cursor.getString(cursor.getColumnIndex("classRoom"));

                CourseManager cm=new CourseManager(mcontext);
                BeanCourse course=cm.searchById(courseId);
                BeanDetail detail=new BeanDetail(detailId,course,section,sectionSpan,week,classRoom);
                detailList.add(detail);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return detailList;
    }
}
