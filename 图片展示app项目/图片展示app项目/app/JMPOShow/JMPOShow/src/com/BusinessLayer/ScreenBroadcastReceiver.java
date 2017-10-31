package com.BusinessLayer;


import com.WebLayer.MainActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;
  

/**这个监听广播没有使用因为http://www.th7.cn/Program/Android/201404/190738.shtml*/
public  class ScreenBroadcastReceiver {
private String action = null;
static final String action_boot ="android.intent.action.BOOT_COMPLETED";
Context contexts;

//
//@Override
//public void onReceive(Context context, Intent intent) {
//	contexts=context;
//	//开机启动功能
//	  Intent i = new Intent(context, MainActivity.class);  
//      i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
//      context.startActivity(i);  
//	//监听屏幕亮暗功能
//      final IntentFilter filter = new IntentFilter();  
//      filter.addAction(Intent.ACTION_SCREEN_OFF); 
//      filter.addAction(Intent.ACTION_SCREEN_ON); 
//      filter.addAction(Intent.ACTION_USER_PRESENT); 
//
//}
//		/**这是一个广播，主要判断黑屏和锁屏功能*/
//		public BroadcastReceiver testReceiver = new BroadcastReceiver() {
//		        @Override
//        public void onReceive(Context context, Intent intent) {
//        	 String action = intent.getAction();  
//             if (Intent.ACTION_SCREEN_ON.equals(action)) { 
//               Toast.makeText(contexts, "screen on",  Toast.LENGTH_SHORT).show();
//             } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { 
//            	 Toast.makeText(contexts, "screen off",  Toast.LENGTH_SHORT).show();
//            	 Intent alarmIntent = new Intent(context, MainActivity.class); 
//            	 alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
//            	 context.startActivity(alarmIntent);
//             } else if (Intent.ACTION_USER_PRESENT.equals(action)) { 
//                 Toast.makeText(contexts, "screen unlock",  Toast.LENGTH_SHORT).show();
//             } 
//        }
//    };
//


}