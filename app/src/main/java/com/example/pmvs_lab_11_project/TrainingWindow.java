package com.example.pmvs_lab_11_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;


public class TrainingWindow extends AppCompatActivity {

  Intent intent_profile;
  ImageView[] imageList;
  Handler handler;
  TextView scoring,killno;
  int score;
  ImageView imageView,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9;
  Runnable runnable;
  SharedPreferences pref;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // hiding Action bar
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getSupportActionBar().hide();
    setContentView(R.layout.activity_main);

    // assigning variables
    scoring=findViewById(R.id.score);
    killno=findViewById(R.id.time);
    imageView=findViewById(R.id.image_view1);
    imageView2=findViewById(R.id.image_view2);
    imageView3=findViewById(R.id.image_view3);
    imageView4=findViewById(R.id.image_view4);
    imageView5=findViewById(R.id.image_view5);
    imageView6=findViewById(R.id.image_view6);
    imageView7=findViewById(R.id.image_view7);
    imageView8=findViewById(R.id.image_view8);
    imageView9=findViewById(R.id.image_view9);

    intent_profile = new Intent(TrainingWindow.this, com.example.pmvs_lab_11_project.Profile.class);
    pref = getSharedPreferences(getResources().getString(R.string.DB), MODE_PRIVATE);

    imageList=new ImageView[]{imageView,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9};
    makeitgone();

    // setting timer to play game
    new CountDownTimer(10000,1000)
    {

      // increasing time
      @Override
      public void onTick(long l) {
        killno.setText("Time : "+l/1000);
      }

      // When time is finished
      @Override
      public void onFinish() {
        killno.setText("Time Over");
        handler.removeCallbacks(runnable);

        // using for loop
        for (ImageView image:imageList)
        {
          image.setVisibility(View.INVISIBLE);
        }

        SharedPreferences.Editor editor = pref.edit();
        Bundle arguments = getIntent().getExtras();
        String user = arguments.get("username").toString();
        String pwd = arguments.get("password").toString();
        int curr_best_score = Integer.parseInt(arguments.get("record").toString());
        if (score > curr_best_score)
        {
          curr_best_score = score;
        }
        int curr_count = Integer.parseInt(arguments.get("count").toString());
        curr_count++;
        editor.putString(user, (pwd + "/" + curr_best_score + "/" + curr_count));
        editor.apply();

        // dialog box to ask user's input
        AlertDialog.Builder alert=new AlertDialog.Builder(TrainingWindow.this);
        alert.setTitle("Try Again!");
        alert.setMessage("Do you want to restart?");

        // if user want to restart game
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            Intent intent=getIntent();
            finish();
            startActivity(intent);
          }
        });

        // When user not want to play again
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            startActivity(intent_profile);
          }
        });
        alert.show();
      }
    }.start();
  }

  private void makeitgone() {
    handler=new Handler();
    runnable=new Runnable() {
      @Override
      public void run() {
        for(ImageView image:imageList)
        {
          image.setImageResource(R.drawable.pepega);
          final Handler handler=new Handler(Looper.getMainLooper());
          handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              image.setImageResource(R.drawable.pepega);
            }
          },900);
          image.setVisibility(View.INVISIBLE);
        }

        // making image visible at random positions
        Random random=new Random();
        int i=random.nextInt(9);
        imageList[i].setVisibility(View.VISIBLE);
        handler.postDelayed(this,600);
      }
    };
    handler.post(runnable);
  }

          // increasing score
          public void increaseScore(View view) {
    score=score+1;
    scoring.setText("Score : "+score);
  }
}
