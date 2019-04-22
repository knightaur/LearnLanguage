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

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<User> dataList;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<User> objects) {
        super(context, resource, objects);
        this.parent_context = context;
        this.layout_id = resource;
        this.dataList = objects;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) parent_context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout_id, parent, false);

        TextView tvUsername = rowView.findViewById(R.id.tvUsername);
        TextView tvEmail = rowView.findViewById(R.id.tvEmail);

        User selectedTrainer = dataList.get(position);
        tvUsername.setText(selectedTrainer.getUsername());
        tvEmail.setText(selectedTrainer.getEmail());

        return rowView;
    }

}
