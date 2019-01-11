package red.clf.app.afinal.schedule;

/**
 * Created by HP on 2018/12/23.
 */
public class CourseModel {

    private String id; //课程ID
    private String courseName;
    private int section; //从第几节课开始
    private int sectionSpan; //跨几节课
    private int week; //周几
    private String classRoom; //教室
    private int courseFlag; //课程背景颜色
    private int detailid;
    private String teacher;

    public CourseModel() {
    }

    public CourseModel(String id, String courseName, int section, int sectionSpan,int week,String classRoom,int courseFlag,int detailid,String teacher) {
        this.id = id;
        this.courseName = courseName;
        this.section = section;
        this.sectionSpan = sectionSpan;
        this.week = week;
        this.classRoom = classRoom;
        this.courseFlag = courseFlag;
        this.teacher=teacher;
        this.detailid=detailid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getSectionSpan() {
        return sectionSpan;
    }

    public void setSectionSpan(int sectionSpan) {
        this.sectionSpan = sectionSpan;
    }

    public int getCourseFlag() {
        return courseFlag;
    }

    public void setCourseFlag(int courseFlag) {
        this.courseFlag = courseFlag;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public int getDetailid() {
        return detailid;
    }

    public void setDetailid(int detailid) {
        this.detailid = detailid;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}

