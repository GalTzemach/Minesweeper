package com.example.galtzemach.minesweeper.logic;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Tal on 13/01/2017.
 */

public class LocalService extends Service implements SensorEventListener {

    ServiceBinder serviceBinder;
    private MyServiceListener listener;
    private SensorManager sensorManager;

    /// So the service will be able to notify, I personally preference using: https://developer.android.com/reference/android/content/BroadcastReceiver.html
    public interface MyServiceListener {
        void onSensorEvent(float[] values);
    }

    // To give access after the binding
    public class ServiceBinder extends Binder {
        public LocalService getService() {
            return LocalService.this;
        }
    }

    public void setListener(MyServiceListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        serviceBinder = new ServiceBinder();
        return serviceBinder;
    }

    public void startListening() {
        // The service is listening to a sensor.
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void stopListening() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            if (listener != null) {
                listener.onSensorEvent(event.values);
            }
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}
