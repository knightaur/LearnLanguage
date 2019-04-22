package sg.edu.rp.c346.learnlanguage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AnotherAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<String> dataList;

    public AnotherAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        this.parent_context = context;
        this.layout_id = resource;
        this.dataList = objects;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) parent_context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout_id, parent, false);

        TextView tvReq = rowView.findViewById(R.id.tvReq);

        String currentReq = dataList.get(position);
        tvReq.setText(tvReq.getText() + currentReq);

        return rowView;
    }

}
