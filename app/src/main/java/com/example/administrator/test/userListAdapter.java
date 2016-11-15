package com.example.administrator.test;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/14.
 */
public class userListAdapter extends BaseAdapter {
    //适配的用户列表
    private ArrayList<User> list;
    private Context context;

    public userListAdapter(Context context, ArrayList list){
        this.list=list;
        this.context=context;
    }

    @Override
    public int getCount() {
        return list.size()>0?list.size():0;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return getItem(i);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view;
        final ViewHolder viewHolder;
        if (null==convertView){
            viewHolder=new ViewHolder();
            view = View.inflate(context, R.layout.list_item, null);
            viewHolder.iv_icon=(ImageView) view.findViewById(R.id.iv_icon);
            viewHolder.tv_name=(TextView)view.findViewById(R.id.tv_name);
            viewHolder.tv_privator=(TextView)view.findViewById(R.id.tv_privator);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }
        //接口回调的方法，完成图片的读取;
        DownImage downImage = new DownImage(list.get(i).getIcon());
        downImage.loadImage(new DownImage.ImageCallBack() {

            @Override
            public void getDrawable(Drawable drawable) {
                viewHolder.iv_icon.setImageDrawable(drawable);
            }
        });
        viewHolder.tv_name.setText(list.get(i).getName());
        viewHolder.tv_privator.setText(list.get(i).getPrivator());
        return view;
    }

    class ViewHolder{
        public ImageView iv_icon;
        public TextView tv_name;
        public TextView tv_privator;
    }
}
