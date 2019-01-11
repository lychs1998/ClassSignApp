package red.clf.app.afinal.bean;

/**
 * Created by HP on 2018/12/28.
 */

public class BeanCourse {
    private String courseId; //课程ID
    private String courseName; //课程名
    private String des; //课程介绍
    private String teacherName; //教师姓名

    public BeanCourse(){}
    public BeanCourse(String courseId,String courseName,String des,String teacherName){
        this.courseId=courseId;
        this.courseName=courseName;
        this.des=des;
        this.teacherName=teacherName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

}
