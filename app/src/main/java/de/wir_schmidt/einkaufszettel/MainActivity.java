package de.wir_schmidt.einkaufszettel;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = "Sel 1";
                break;
            case 2:
                mTitle = "sel 2";
                break;
            case 3:
                mTitle = "sel 3";
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);


            doTheStuff(inflater, container, savedInstanceState, rootView);
            return rootView;
        }

        private void doTheStuff(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, final View rootView) {
            {// Liste initialisieren.
                // http://www.i-programmer.info/programming/android/7849-android-adventures-listview-and-adapters.html?start=1
                ArrayList<String> myStringArray = new ArrayList<String>();
                //   myStringArray.add("A");
                //   myStringArray.add("B");
                ArrayAdapter<String> myAdapter = new
                        ArrayAdapter<String>(
                        rootView.getContext(),
                        android.R.layout.simple_list_item_1,
                        myStringArray);
                ListView myList = (ListView)
                        rootView.findViewById(R.id.toByListView);
                myList.setAdapter(myAdapter);
            }


            Button btHinzu = (Button) rootView.findViewById(R.id.buttonHinzufuegen);
            btHinzu.setOnClickListener(new View.OnClickListener() {
                int count = 0;

                @Override
                public void onClick(View view) {
                    System.out.println("OnClickListener.onClick(.)");
                    ListView toByList = (ListView) rootView.findViewById(R.id.toByListView);
                    ArrayAdapter myAdapt = (ArrayAdapter) toByList.getAdapter();
                    String item = "new data " + count++;
                    myAdapt.add(item);
                    myAdapt.notifyDataSetChanged();

                    int position = myAdapt.getPosition(item);
                    toByList.smoothScrollToPosition(position);
                    toByList.setSelection(position);
                    System.out.println("OnClickListener.onClick(.)");
                }
            });
            System.out.println("OnClickListener - registriert.");
        }


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
