package red.clf.app.afinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HP on 2018/12/28.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREAT_COURSE="CREATE TABLE Course ( " +
            "courseId text primary key," +
            "courseName text," +
            "teacherName text," +
            "des text)";

    public static final String CREAT_DETAILS="CREATE TABLE Detail ( " +
            "detailId integer primary key autoincrement," +
            "courseId text," +
            //第几节课开始
            "section int," +
            //跨几节课
            "sectionSpan int," +
            "week int," +
            "classRoom text," +
            "FOREIGN KEY (courseId) REFERENCES Course(courseId))";
    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT_COURSE);
        db.execSQL(CREAT_DETAILS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists Course");
        db.execSQL("drop table if exists Detail");
        onCreate(db);
    }

    public static SQLiteDatabase getConnection(Context context){
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(context,"Final.db",null,2);
        return dbHelper.getWritableDatabase();
    }

}
