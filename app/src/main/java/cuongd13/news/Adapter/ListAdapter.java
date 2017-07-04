package cuongd13.news.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cuongd13.news.Model.News;
import cuongd13.news.R;

/**
 * Created by cuong pc on 7/3/2017.
 */

public class ListAdapter extends ArrayAdapter<News> {

    Context context;
    List<News> items;
    int resource;

    public ListAdapter(Context context, int resource, List<News> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.row_news, null);
        }

        // Anh xa + Gan gia tri
        TextView txt1 = (TextView) view.findViewById(R.id.textView1);
        txt1.setText(String.valueOf(items.get(position).position));
        TextView txt2 = (TextView) view.findViewById(R.id.textView2);
        txt2.setText(items.get(position).title);



        return view;
    }

}
