package red.clf.app.afinal.DatabaseDao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import red.clf.app.afinal.MyDatabaseHelper;
import red.clf.app.afinal.bean.BeanCourse;

import java.util.ArrayList;

/**
 * Created by HP on 2018/12/28.
 */

public class CourseManager {
    private Context mcontext;
    public CourseManager(Context mcontext){
        this.mcontext=mcontext;
    }
    public BeanCourse addCourse(BeanCourse course){
        SQLiteDatabase conn= MyDatabaseHelper.getConnection(mcontext);
        ContentValues values=new ContentValues();
        values.put("courseId",course.getCourseId());
        values.put("courseName",course.getCourseName());
        values.put("teacherName",course.getTeacherName());
        values.put("des",course.getDes());
        conn.replace("Course",null,values);
        values.clear();
        return course;
    }
    public BeanCourse searchById(String courseId){
        SQLiteDatabase conn= MyDatabaseHelper.getConnection(mcontext);
        BeanCourse course=new BeanCourse();
        Cursor cursor=conn.query("Course",null,"courseId=?",new String[]{courseId},null,null,null);
        if(cursor.moveToFirst()){
            String courseName=cursor.getString(cursor.getColumnIndex("courseName"));
            String teacherName=cursor.getString(cursor.getColumnIndex("teacherName"));
            String des=cursor.getString(cursor.getColumnIndex("des"));
            course.setCourseId(courseId);
            course.setCourseName(courseName);
            course.setTeacherName(teacherName);
            course.setDes(des);
        }
        return course;
    }
    public void deleteAll(){
        SQLiteDatabase conn= MyDatabaseHelper.getConnection(mcontext);
        conn.delete("Course",null,null);
    }
    public ArrayList<BeanCourse> loadAll(){
        SQLiteDatabase conn= MyDatabaseHelper.getConnection(mcontext);
        ArrayList<BeanCourse> courseList=new ArrayList<>();
        Cursor cursor=conn.query("Course",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String courseId=cursor.getString(cursor.getColumnIndex("courseId"));
                String courseName=cursor.getString(cursor.getColumnIndex("courseName"));
                String teacherName=cursor.getString(cursor.getColumnIndex("teacherName"));
                String des=cursor.getString(cursor.getColumnIndex("des"));
                BeanCourse course=new BeanCourse(courseId,courseName,des,teacherName);
                courseList.add(course);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return courseList;
    }
}
