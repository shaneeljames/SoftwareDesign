package com.example.tutor;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */


public class FragmentDrawer extends Fragment {

    private static String TAG = FragmentDrawer.class.getSimpleName();

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private static String[] titles = null;
    private FragmentDrawerListener drawerListener;
    String tutor_id = null;
    ImageView pp ;

    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();


        // preparing navigation drawer items
        for (int i = 0; i < titles.length; i++) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(titles[i]);
            data.add(navItem);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // drawer labels
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);



    }

    Context context ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

        TextView txtName = (TextView) layout.findViewById(R.id.txtName);
        final TextView txtBalance = (TextView) layout.findViewById(R.id.txtBalance);
         pp = (ImageView) layout.findViewById(R.id.imgPP)  ;
        RatingBar rating = (RatingBar) layout.findViewById(R.id.ratingBar2) ;

        SharedPreferences myprefs =  this.getContext().getSharedPreferences("user", MODE_PRIVATE);
        String tutor_fname = myprefs.getString("tutor_fname", null);
        String tutor_lname = myprefs.getString("tutor_lname", null);
         tutor_id = myprefs.getString("tutor_student_num", null);
        final String tutor_balance = myprefs.getString("tutor_balance", null);
        final String tutor_rating = myprefs.getString("tutor_rating", null);

       txtName.setText(tutor_lname + " " + tutor_fname);
      //  txtName.setText(tutor_id);
        txtBalance.setText(tutor_balance);

      //  if(!(tutor_rating.equals("null")))
        rating.setRating(Float.parseFloat(tutor_rating));


        Random r = new Random();
        int i1 = r.nextInt(999999 - 111111) + 111111;

        String ran =    Integer.toString(i1) ;



        try {
            Picasso.with(getContext()).load("http://neural.net16.net/pictures/t" + tutor_id.toString() + "JPG?"+ ran).into(pp);
        }catch (Exception e)
        {
            pp.setImageResource(R.drawable.session);
        }



        pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 // Toast.makeText(, "Index position is: "+i, Toast.LENGTH_SHORT).show();
                Intent noti = new Intent(getActivity(), AccountSettingsActivity2.class);
                startActivity(noti);
               // txtBalance.setText("Test");
            }
        });

        adapter = new NavigationDrawerAdapter(getActivity(), getData());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return layout;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
               // adapter.notifyDataSetChanged();
              //  recyclerView.setAdapter(adapter);


                Random r = new Random();
                int i1 = r.nextInt(999999 - 111111) + 111111;

                String ran =    Integer.toString(i1) ;



                try {
                    Picasso.with(getContext()).load("http://neural.net16.net/pictures/t" + tutor_id.toString() + "JPG?"+ ran).into(pp);
                }catch (Exception e)
                {
                    pp.setImageResource(R.drawable.session);
                }

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
               // adapter.notifyDataSetChanged();
              //  recyclerView.setAdapter(adapter);

                Random r = new Random();
                int i1 = r.nextInt(999999 - 111111) + 111111;

                String ran =    Integer.toString(i1) ;



                try {
                    Picasso.with(getContext()).load("http://neural.net16.net/pictures/t" + tutor_id.toString() + "JPG?"+ ran).into(pp);
                }catch (Exception e)
                {
                    pp.setImageResource(R.drawable.session);
                }
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }

    public void updateDrawer() {
        adapter.notifyDataSetChanged();
        // OR
       // mListView.setAdapter(new AdapterShowingTheRightTitles());
    }

}
