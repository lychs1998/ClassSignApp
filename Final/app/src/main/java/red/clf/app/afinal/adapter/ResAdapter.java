package red.clf.app.afinal.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import red.clf.app.afinal.R;
import red.clf.app.afinal.activity.ResActivity;
import red.clf.app.afinal.bean.ItemRes;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import java.util.List;


public class ResAdapter extends RecyclerView.Adapter<ResAdapter.ViewHolder> {
    private int signid;
    private Context context;
    private List<ItemRes> data;
    private static final String SIGN_URL="http://sign.myzju.cn/sign/add.php";

    public ResAdapter(Context context,List<ItemRes> data,int signid){
        this.context = context;
        this.data = data;
        this.signid=signid;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,int i) {
        final int position=i;
        holder.sname.setText(data.get(position).getSname());
        holder.sno.setText(data.get(position).getSno());
        holder.flag.setText((data.get(position).getType()==1)?"已签到":"未签到");

        if (data.get(position).getType()==0){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context)
                            .setTitle("确认")
                            .setMessage("是否要修改其签到信息？")
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    HttpParams p=new HttpParams();
                                    p.put("sno",data.get(position).getSno());
                                    p.put("signid",signid);
                                    RxVolley.post(SIGN_URL, p, new HttpCallback() {
                                        @Override
                                        public void onSuccess(String t) {
                                            JSONObject json=JSONObject.parseObject(t);
                                            if ("1".equals(json.getString("code"))){
                                                Toast.makeText(context,"签到成功",Toast.LENGTH_SHORT).show();
                                                holder.itemView.setOnClickListener(null);
                                                holder.flag.setText("已签到");
                                                data.get(position).setType(1);
                                            }else{
                                                Toast.makeText(context,"修改失败",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder{

        private TextView sname;
        private TextView sno;
        private TextView flag;

        ViewHolder(View itemView) {
            super(itemView);
            sname = itemView.findViewById(R.id.sname);
            sno=itemView.findViewById(R.id.sno);
            flag=itemView.findViewById(R.id.signflag);
        }
    }
}
