package com.example.saurabh.mytouristguide.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.saurabh.mytouristguide.Pojo.TouristPojo;
import com.example.saurabh.mytouristguide.R;
import com.example.saurabh.mytouristguide.helper.DownloadHelper;

import java.util.ArrayList;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Saurabh on 07-Jul-17.
 */

public class TouristAdapter extends ArrayAdapter{

    Context context;
    int resource;
    ArrayList<TouristPojo> arrayList = new ArrayList<>();
    LayoutInflater inflater;

    public TouristAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<TouristPojo> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource=resource;
        arrayList=objects;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = inflater.inflate(resource,null);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.circle_image);

        TouristPojo pojo =arrayList.get(position);
        textView.setText(pojo.getName());

        DownloadHelper.loadImageWithGlideURL(context,pojo.getImage(),circleImageView);
        return view;

    }
}
