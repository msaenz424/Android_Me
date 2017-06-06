package com.example.android.android_me.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener{

    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.android_me_linear_layout) != null){
            mTwoPane = true;

            //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            // next button is not needed in a two-pane screen
            Button nextButton = (Button) findViewById(R.id.next_button);
            nextButton.setVisibility(View.GONE);

            if (savedInstanceState == null) {
                BodyPartFragment headPartFragment = new BodyPartFragment();
                headPartFragment.setImageIds(AndroidImageAssets.getHeads());

                BodyPartFragment bodyPartFragment = new BodyPartFragment();
                bodyPartFragment.setImageIds(AndroidImageAssets.getBodies());

                BodyPartFragment legPartFragment = new BodyPartFragment();
                legPartFragment.setImageIds(AndroidImageAssets.getLegs());

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.head_container, headPartFragment)
                        .add(R.id.body_container, bodyPartFragment)
                        .add(R.id.leg_container, legPartFragment)
                        .commit();
            }
        }else{
            mTwoPane = false;
        }
    }

    @Override
    public void onImageSelected(int position) {
        Toast.makeText(this, "Clicked " + position, Toast.LENGTH_SHORT).show();

        // this math calculates the position of image selected
        int bodyPartNumber = position / 12;
        int listIndex = position - (bodyPartNumber * 12);

        if (mTwoPane){
            BodyPartFragment newFragment = new BodyPartFragment();

            switch (bodyPartNumber){
                case 0:
                    newFragment.setImageIds(AndroidImageAssets.getHeads());
                    newFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.head_container, newFragment)
                            .commit();
                    break;
                case 1:
                    newFragment.setImageIds(AndroidImageAssets.getBodies());
                    newFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.body_container, newFragment)
                            .commit();
                    break;
                case 2:
                    newFragment.setImageIds(AndroidImageAssets.getLegs());
                    newFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.leg_container, newFragment)
                            .commit();
                    break;
                default:
                    break;
            }
        }else {
            switch (bodyPartNumber) {
                case 0:
                    headIndex = listIndex;
                    break;
                case 1:
                    bodyIndex = listIndex;
                    break;
                case 2:
                    legIndex = listIndex;
                    break;
                default:
                    break;
            }
        }
        // Put this information in a Bundle to attach it to an Intent that will launch an AndroidMeActivity
        Bundle b = new Bundle();
        b.putInt("headIndex", headIndex);
        b.putInt("bodyIndex", bodyIndex);
        b.putInt("legIndex", legIndex);

        // Attach the Bundle to an intent
        final Intent intent = new Intent(this, AndroidMeActivity.class);
        intent.putExtras(b);

        Button nextButton = (Button)findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

    }
}
