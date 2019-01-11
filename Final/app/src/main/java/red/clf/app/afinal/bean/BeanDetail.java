package red.clf.app.afinal.bean;

/**
 * Created by HP on 2018/12/28.
 */

public class BeanDetail {
    private int detailId;
    private BeanCourse course;
    private int section; //从第几节课开始
    private int sectionSpan; //跨几节课
    private int week; //周几
    private String classRoom; //教室
    public BeanDetail(){}
    public BeanDetail(int detailId,BeanCourse course,int section,int sectionSpan,int week,String classRoom){
        this.detailId=detailId;
        this.course=course;
        this.section=section;
        this.sectionSpan=sectionSpan;
        this.week=week;
        this.classRoom=classRoom;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public BeanCourse getCourse() {
        return course;
    }

    public void setCourseId(BeanCourse course) {
        this.course = course;
    }

    public int getSectionSpan() {
        return sectionSpan;
    }

    public void setSectionSpan(int sectionSpan) {
        this.sectionSpan = sectionSpan;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    private String more; //详情

}
