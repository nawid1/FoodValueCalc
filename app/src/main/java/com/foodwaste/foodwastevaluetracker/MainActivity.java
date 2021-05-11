package com.foodwaste.foodwastevaluetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.foodwaste.foodwastevaluetracker.Fragments.AddFragment;
import com.foodwaste.foodwastevaluetracker.Fragments.HomeFragment;
import com.foodwaste.foodwastevaluetracker.Fragments.ProfileFragment;


public class MainActivity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;

    /* private BottomNavigationView bottomNavigationView;
     private Fragment selectorFragment;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign variable
        bottomNavigation = findViewById(R.id.bottom_navigation);

        //Add menu items
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_add));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_profile));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //Initialize fragment
                Fragment fragment = null;
                //Check condition
                switch (item.getId()) {
                    case 1:
                        //When id is 1
                        //Initialize home fragment
                        fragment = new HomeFragment();
                        break;
                    case 2:
                        //When id is 2
                        //Initialize add fragment
                        fragment = new AddFragment();
                        break;
                    case 3:
                        //When id is 3
                        //Initialize profile fragment
                        fragment = new ProfileFragment();
                        break;
                }
                //Load fragment
                loadFragment(fragment);
            }
        });

        //Set home count
        bottomNavigation.setCount(1, "");
        //Set add fragment initially selected
        bottomNavigation.show(2, true);
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //Display toast
                Toast.makeText(getApplicationContext()
                        ,"You Clicked " + item.getId()
                        ,Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                //Display toast
                Toast.makeText(getApplicationContext()
                        , "You Clicked " + item.getId()
                        ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        //Replace fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();


    }
}
