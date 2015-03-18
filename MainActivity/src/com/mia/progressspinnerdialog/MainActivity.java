package com.mia.progressspinnerdialog;
   
   import android.app.Activity;
   import android.os.Bundle;
   import android.widget.Button;
   import android.widget.ProgressBar;
   import android.view.View;
   import android.view.View.OnClickListener;
   
   public class MainActivity extends Activity {
   
         int typeBar;                                 // Type bar: 0=spinner, 1=horizontal
         ProgressBar progDialog;
         Button button1;
         DialogFrag fragment;
   
         @Override
         public void onCreate(Bundle savedInstanceState) {
                  super.onCreate(savedInstanceState);
                  setContentView(R.layout.activity_main);
                  button1 = (Button) findViewById(R.id.Button01);
                  button1.setOnClickListener(new OnClickListener(){
                           public void onClick(View v) {
                                 typeBar = 0;
                                 DialogFrag.context = getApplicationContext();
                                 fragment = DialogFrag.newInstance(typeBar);  
                                 fragment.show(getFragmentManager(), "Task 1");            
                           }
                  }); 
   

         }
   
        
   }