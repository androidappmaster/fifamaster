package es.appmaster.fifamaster.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import es.appmaster.fifamaster.R;
import es.appmaster.fifamaster.api.RestClient;
import es.appmaster.fifamaster.model.Player;

/**
 * Detail fragment
 *
 * @author manolovn
 */
public class DetailFragment extends Fragment {

    int currentPosition = -1;

    private Gson gson;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("current_position");
        }

        return inflater.inflate(R.layout.detail_player, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args != null) {
            updateDetailView(args.getString("POSITION"));
        } else if (currentPosition != -1) {
            //updateDetailView(currentPosition);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("current_position", currentPosition);
    }

    private void makeRequest(String baseId) {
        new GetPlayersTask().execute(baseId);
    }

    public void updateDetailView(String resourceId) {
        makeRequest(resourceId);
    }

    public void updateDetailView(Player player) {

        getActivity().setTitle(player.getFirstName());

        ImageView photo = (ImageView) getView().findViewById(R.id.item_photo);
        TextView title = (TextView) getView().findViewById(R.id.item_title);
        TextView subtitle = (TextView) getView().findViewById(R.id.item_subtitle);

        title.setText(player.getFirstName());
        Picasso.with(getActivity()).load(player.getPlayerPhoto()).into(photo);

        //currentPosition = position;
    }


    private class GetPlayersTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            String result = "";
            RestClient restClient = new RestClient();
            try {

                result = restClient.get("/api/player/" + params[0]);

            } catch (IOException e) {
                Log.e("main activity", e.getMessage());
            }

            Log.d("xxx", "result --> " + result);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObject = new JSONObject(result);
                Player item = null;
                item = gson.fromJson(result, Player.class);

                updateDetailView(item);

            } catch (JSONException e) {
                Log.e("fifa list fragment", e.getMessage());
                Toast.makeText(getActivity(), "ERROR JSON", Toast.LENGTH_SHORT).show();
            }
        }
    }

}