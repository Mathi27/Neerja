package com.proudcoder.firebase_login;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceBroadcastReciever extends BroadcastReceiver {
    private static final String TAG = "GeofenceBroadcastReciev";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        Toast.makeText(context,"GeoFence Triggered...",Toast.LENGTH_LONG).show();
        NotificationHelper notificationHelper = new NotificationHelper(context);
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);


        if(geofencingEvent.hasError()){
            Log.d(TAG, "onReceive: Error Receiving geofence event..");
            return;
        }
        List<Geofence> geofenceList =  geofencingEvent.getTriggeringGeofences();
        for(Geofence geofence:geofenceList){
            Log.d(TAG, "onReceive: "+ geofence.getRequestId());
        }
        int transitionType = geofencingEvent.getGeofenceTransition();

        switch (transitionType){
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                Toast.makeText(context,"GeoFence Transition enter",Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("Geofence Enabled","Stay Alert,You Entered RedZone in which there is a high risk of harm",MapsActivity.class);
                break;
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                Toast.makeText(context,"GeoFence Transition Dwell",Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("Crossed 50 Meters","Stay Alert,You crossed 50 Meters inside RedZone",MapsActivity.class);
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                Toast.makeText(context,"GeoFence Transition Exit",Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("Exited Disabled","Hurry! You Made It.You successfully exited danger zone",MapsActivity.class);
                break;
        }
    }
}