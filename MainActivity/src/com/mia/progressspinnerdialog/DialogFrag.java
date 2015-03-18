package com.mia.progressspinnerdialog;
   
   import android.app.DialogFragment;
   import android.app.ProgressDialog;
   import android.os.Bundle;
   import android.os.Handler;
   import android.os.Message;
   import android.util.Log;
   import android.view.LayoutInflater;
   import android.view.View;
   import android.view.ViewGroup;
   import android.widget.ProgressBar;
   import android.content.Context;
   import android.content.Intent;
   
   
   public class DialogFrag extends DialogFragment {
   
         ProgressDialog progDialog;
         ProgressBar pbar;
         public static Context context;
         int barType;
         View v;
         private ProgressThread progThread;
         private static final int delay = 20;         
         private int maxBarValue;                     
         boolean threadStopped = false;
         boolean lightTheme = true;                   
	 public DialogFrag() {
		
	 }
         static DialogFrag newInstance(int num) {
                  DialogFrag f = new DialogFrag();
                  Bundle args = new Bundle();
                  args.putInt("num", num);
                  f.setArguments(args);
   
                  return f;
         }
   
         public void onCreate(Bundle savedInstanceState){
                  super.onCreate(savedInstanceState);
                  barType = getArguments().getInt("num");
                  int style = DialogFragment.STYLE_NO_TITLE;
                  int theme = android.R.style.Theme_Holo_Dialog;
                  if(lightTheme){
                           theme = android.R.style.Theme_Holo_Light_Dialog;
                  }
                  this.setStyle(style, theme);  
   
         }
         @Override
         public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
                  if(barType == 0){
                           v = inflater.inflate(R.layout.activity_progressbar, container, false);
                           pbar = (ProgressBar) v.findViewById(R.id.progress_bar_only);
   
                  } 
                  maxBarValue = pbar.getMax();
                  progThread = new ProgressThread(handler);
                  progThread.start();
   
                  return v;
         }
         @Override
         public void onDestroy(){
                  super.onDestroy();
         }

         private void closeDialog(){
                  if(!threadStopped){
                           Intent i = new Intent(context, FinalPage.class);
                           startActivity(i);
                           if(!this.isDetached()) dismiss();
                  }
         }
 
         final Handler handler = new Handler() {
                  public void handleMessage(Message msg) {
                           int total = msg.getData().getInt("total");
   
                           pbar.setProgress(total);
                           if (total > maxBarValue){
                                 progThread.setState(ProgressThread.DONE);
                                 pbar.setVisibility(ProgressBar.INVISIBLE);
                                 v.setVisibility(View.INVISIBLE);
                                 closeDialog();
                                 threadStopped = true;
                           }
                  }
         };
         private class ProgressThread extends Thread {	
                  final static int DONE = 0;
                  final static int RUNNING = 1;
   
                  Handler mHandler;
                  int mState;
                  int total;
                  ProgressThread(Handler h) {
                           mHandler = h;
                  }
                  @Override
                  public void run() {
                           mState = RUNNING;   
                           total = 0;
                           threadStopped = false;
   
                           while (mState == RUNNING) {
                                 try {
                                          Thread.sleep(delay);
                                 } catch (InterruptedException e) {
                                          Log.e("ERROR", "Thread was Interrupted");
                                 }
                                 Message msg = mHandler.obtainMessage();
                                 Bundle b = new Bundle();
                                 b.putInt("total", total);
                                 msg.setData(b);
                                 mHandler.sendMessage(msg);
   
                                 total++; 
                           }
                  }
                  public void setState(int state) {
                           mState = state;
                  }
         }
   }