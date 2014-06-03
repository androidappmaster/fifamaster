package es.appmaster.fifamaster.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.appmaster.fifamaster.R;

/**
 * Detail fragment
 *
 * @author manolovn
 */
public class DetailFragment extends Fragment {

    int currentPosition = -1;

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

    public void updateDetailView(String position) {

        getActivity().setTitle(position);

        TextView name = (TextView) getView().findViewById(R.id.student_name);
        TextView city = (TextView) getView().findViewById(R.id.student_city);

        //currentPosition = position;
    }

}