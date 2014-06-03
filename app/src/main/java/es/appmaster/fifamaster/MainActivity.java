package es.appmaster.fifamaster;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import es.appmaster.fifamaster.fragment.DetailFragment;
import es.appmaster.fifamaster.fragment.FifaListFragment;


public class MainActivity extends ActionBarActivity implements FifaListFragment.OnPlayerSelectedListener, android.support.v7.app.ActionBar.OnNavigationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("");
        setContentView(R.layout.activity_main);

        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.action_list, android.R.layout.simple_spinner_dropdown_item);

        getSupportActionBar().setListNavigationCallbacks(mSpinnerAdapter, this);

        if (findViewById(R.id.container) != null && savedInstanceState == null) {

            FifaListFragment fifaListFragment = FifaListFragment.newInstance(0);
            fifaListFragment.setOnPlayerSelectedListener(this);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fifaListFragment)
                    .commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
    }


    @Override
    public void OnPlayerSelected(String resourceId) {

        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment);

        Bundle args = new Bundle();
        args.putString("POSITION", resourceId);

        if (detailFragment != null && detailFragment.isInLayout()) {

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

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

    }

    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        FifaListFragment fifaListFragment = FifaListFragment.newInstance(i);
        fifaListFragment.setOnPlayerSelectedListener(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fifaListFragment)
                .commit();
        return false;
    }
}
