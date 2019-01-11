package red.clf.app.afinal.schedule;

import android.content.Context;

import red.clf.app.afinal.DatabaseDao.CourseManager;
import red.clf.app.afinal.DatabaseDao.DetailManager;
import red.clf.app.afinal.bean.BeanCourse;
import red.clf.app.afinal.bean.BeanDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2018/12/23.
 */
public class CourseDao {
    private Context mContext;
    public CourseDao(Context mContext){
        this.mContext=mContext;
    }

    public List<CourseModel>[] initDB(){
        List<CourseModel> courseModels[] = new ArrayList[7];
        for (int i = 0; i < courseModels.length; i++) {
            courseModels[i] = new ArrayList<>();
        }
        DetailManager dm=new DetailManager(mContext);
        ArrayList<BeanDetail> details=dm.loadAll();
        for(int i=0;i<courseModels.length;i++){
            for(int j=0;j<details.size();j++){
                if(details.get(j).getWeek()==i+1){
                    String id=details.get(j).getCourse().getCourseId();
                    String courseName=details.get(j).getCourse().getCourseName();
                    int section=details.get(j).getSection();
                    int sectionSpan=details.get(j).getSectionSpan();
                    int week=details.get(j).getWeek();
                    String classRoom=details.get(j).getClassRoom();
                    courseModels[i].add(new CourseModel(id,courseName,section,sectionSpan,week,classRoom,(int) (Math.random() * 10),details.get(j).getDetailId(),details.get(j).getCourse().getTeacherName()));
                }
            }

        }
        return courseModels;
    }
}

