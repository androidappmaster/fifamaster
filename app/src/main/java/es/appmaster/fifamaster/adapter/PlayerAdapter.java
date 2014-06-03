package es.appmaster.fifamaster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import es.appmaster.fifamaster.R;
import es.appmaster.fifamaster.model.Player;


public class PlayerAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Player> players;

    public PlayerAdapter(Context context, ArrayList<Player> players) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.players = players;
    }

    @Override
    public int getCount() {
        return players.size();
    }

    @Override
    public Player getItem(int position) {
        return players.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(players.get(position).getResourceId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            // create view
            convertView = inflater.inflate(R.layout.item_player, null);
            holder = new ViewHolder();
            holder.studentPhoto = (ImageView) convertView.findViewById(R.id.student_photo);
            holder.studentName = (TextView) convertView.findViewById(R.id.student_name);
            holder.studentCity = (TextView) convertView.findViewById(R.id.student_city);
            holder.flag = (ImageView) convertView.findViewById(R.id.nation_falg);
            holder.pace = (TextView) convertView.findViewById(R.id.pace_value);

            convertView.setTag(holder);
        } else {
            // bind view
            holder = (ViewHolder) convertView.getTag();
        }

        Player player = players.get(position);

        holder.studentName.setText(player.getFirstName());
        holder.studentCity.setText(player.getLastName());
        Picasso.with(context).load(player.getPlayerPhoto()).into(holder.studentPhoto);
        Picasso.with(context).load(player.getCountryFlag()).into(holder.flag);
        holder.pace.setText("" + player.getPace());

        return convertView;
    }

    class ViewHolder {
        ImageView studentPhoto;
        ImageView flag;
        TextView studentName;
        TextView studentCity;
        TextView pace;
    }

}
