package red.clf.app.afinal.bean;

public class SignRes {
    //学生签到编号
    private int ssid;
    //学生学号
    private String sno;
    //签到ID号（代表此次签到，所有学生相同）
    private int signid;
    //签到时间
    private int signtime;
    //名字
    private String sname;

    public int getSsid() {
        return ssid;
    }

    public void setSsid(int ssid) {
        this.ssid = ssid;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public int getSignid() {
        return signid;
    }

    public void setSignid(int signid) {
        this.signid = signid;
    }

    public int getSigntime() {
        return signtime;
    }

    public void setSigntime(int signtime) {
        this.signtime = signtime;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}
