package org.lmw.lt.adapter;

import java.util.List;
import java.util.Map;

import org.lmw.lt.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ArtAdapter extends BaseAdapter {
	
	LayoutInflater inflater;
	List<Map<String, String>> rs;
	public ArtAdapter(Context c,List<Map<String, String>> rs) {
		inflater=LayoutInflater.from(c);
		this.rs=rs;
	}
	@Override
	public int getCount() {
		return rs.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View v, ViewGroup arg2) {
		ViewHolder holder=null;
		if(null==v){
			holder=new ViewHolder();
			v=inflater.inflate(R.layout.view_item_art, null);
			holder.title=(TextView)v.findViewById(R.id.item_title);
			holder.time=(TextView)v.findViewById(R.id.item_resource);
			v.setTag(holder);
		}else{
			holder=(ViewHolder) v.getTag();
		}
		
		holder.title.setText(rs.get(arg0).get("title"));
		holder.time.setText(rs.get(arg0).get("time"));
		return v;
	}
	
	private static class ViewHolder
    {
        TextView title;
        TextView time;
    }

}
