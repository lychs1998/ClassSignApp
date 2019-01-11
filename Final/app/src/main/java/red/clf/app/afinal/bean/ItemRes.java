package red.clf.app.afinal.bean;

public class ItemRes {
    private String sno;
    private String sname;
    private int type;

    public ItemRes(){

    }

    public ItemRes(String sno,String sname,int type){
        this.sname=sname;
        this.sno=sno;
        this.type=type;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
