package red.clf.app.afinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import red.clf.app.afinal.ListItem;
import red.clf.app.afinal.R;
import red.clf.app.afinal.activity.CourseActivity;

import java.util.ArrayList;

/**
 * Created by HP on 2018/11/11.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<ListItem> mListItem;
    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view=null;
        view= LayoutInflater.from(parent.getContext()).inflate( R.layout.list_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                Intent intent=new Intent(context,CourseActivity.class);
                intent.putExtra("teacher",mListItem.get(holder.getAdapterPosition()).getTeachername());
                intent.putExtra("course",mListItem.get(holder.getAdapterPosition()).getClassName());
                intent.putExtra("detailid",mListItem.get(holder.getAdapterPosition()).getDetailid());
                intent.putExtra("courseid",mListItem.get(holder.getAdapterPosition()).getCourseid());
                context.startActivity(intent);
            }
        });
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem item=mListItem.get(position);
        ((ViewHolder)holder).courseName.setText(item.getClassName());
        String msg="今天 "+item.getBegin()+"-"+item.getEnd()+"节，"+"在 "+item.getClassRoom();
        ((ViewHolder)holder).courseMsg.setText(msg);

    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView courseName;
        TextView courseMsg;
        View itemView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.courseName=(TextView)itemView.findViewById(R.id.course_name);
            this.courseMsg=(TextView)itemView.findViewById(R.id.course_msg);
            this.itemView=itemView;
        }
    }


    public MyAdapter(ArrayList<ListItem> listItem, Context context){
        this.mListItem=listItem;
        this.context=context;
    }

}
