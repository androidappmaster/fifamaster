package es.appmaster.fifamaster;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Toast;

import es.appmaster.fifamaster.fragment.DetailFragment;
import es.appmaster.fifamaster.fragment.FifaListFragment;


public class MainActivity extends ActionBarActivity implements FifaListFragment.OnPlayerSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FifaListFragment fragment = (FifaListFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_list);
        fragment.setOnPlayerSelectedListener(this);
    }

    @Override
    public void OnPlayerSelected(String resourceId) {
        Toast.makeText(this, "SELECTED PLAYER " + resourceId, Toast.LENGTH_LONG).show();

        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment);

        Bundle args = new Bundle();
        args.putString("POSITION", resourceId);

        if ( detailFragment != null && detailFragment.isInLayout() ) {

            detailFragment.updateDetailView(resourceId);

        } else {

            detailFragment = new DetailFragment();
            detailFragment.setArguments(args);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            fragmentTransaction.replace(R.id.container, detailFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }

}
