package es.appmaster.fifamaster.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import es.appmaster.fifamaster.R;
import es.appmaster.fifamaster.adapter.PlayerAdapter;
import es.appmaster.fifamaster.api.RestClient;
import es.appmaster.fifamaster.model.Player;

/**
 * Fifa list fragment
 *
 * @author manolovn
 */
public class FifaListFragment extends ListFragment {

    private static final String TERM_INDEX = "term_index";

    OnPlayerSelectedListener listener;
    public interface OnPlayerSelectedListener {
        public void OnPlayerSelected(String resourceId);
    }
    public void setOnPlayerSelectedListener(OnPlayerSelectedListener listener) {
        this.listener = listener;
    }

    private PlayerAdapter adapter;
    private ArrayList<Player> players = new ArrayList<Player>();

    private RestClient restClient;
    private Gson gson;

    private int termIndex;

    public static FifaListFragment newInstance(int term) {

        FifaListFragment fragment = new FifaListFragment();
        Bundle args = new Bundle();
        args.putInt(TERM_INDEX, term);
        fragment.setArguments(args);
        return fragment;
    }

    public FifaListFragment() {
        // empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.termIndex = getArguments().getInt(TERM_INDEX);

        adapter = new PlayerAdapter(getActivity(), players);
        setListAdapter(adapter);

        restClient = new RestClient();
        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        getPlayersFromWebservice();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        listener.OnPlayerSelected(String.valueOf(id));
    }

    private void getPlayersFromWebservice() {
        String terms[] = getResources().getStringArray(R.array.action_list);
        new FifaTask().execute(terms[this.termIndex].toLowerCase());
    }

    class FifaTask extends AsyncTask<String, Integer, ArrayList<Player>> {

        @Override
        protected ArrayList<Player> doInBackground(String... params) {

            String result = "";
            try {

                result = restClient.get("/api/topten/" + params[0]);
                Log.d("REST", "--> " + result);

                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    players.add(gson.fromJson(jsonObject.toString(), Player.class));
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {

            }

            return players;
        }

        @Override
        protected void onPostExecute(ArrayList<Player> players) {
            super.onPostExecute(players);

            adapter.notifyDataSetChanged();
        }
    }

}
