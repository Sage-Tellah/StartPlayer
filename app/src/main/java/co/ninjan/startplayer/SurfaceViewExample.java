package co.ninjan.startplayer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.*;

import static android.graphics.Color.*;
//edit
/**
 * Created by TFE on 15/7/2015.
 */
public class SurfaceViewExample extends Activity implements View.OnTouchListener {

    public boolean kill = false;
    public int random = 99;
    OurView v;
    float[] xarray = new float[] {0, 0, 0, 0, 0, 0, 0};
    float[] yarray = new float[] {0, 0, 0, 0, 0, 0, 0};
    float[] sizearray = new float[] {0, 0, 0, 0, 0, 0, 0};
    List<Float> x = new ArrayList<>();
    List<Float> y = new ArrayList<>();
    List<Float> size = new ArrayList<>();

    public static GoogleAnalytics analytics;
    public static Tracker tracker;

    public float sizedone = 0;
    public boolean initialText = false;

    public int cHeight;

    public boolean alphastarted = false;
    public boolean chosen = false;
    public int pointerC;
    public static int timeri = 0;

    Paint green = new Paint();
    Paint red = new Paint();
    Paint yellow = new Paint();
    Paint blue = new Paint();
    Paint purple = new Paint();
    Paint orange = new Paint();
    Paint font = new Paint();
    Paint font2 = new Paint();
    Paint arcs = new Paint();
    Paint arcYellow = new Paint();


    public Vibrator vibrate;

    public float sAngle = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        for (int fds = 0; fds <= 6; fds ++) {
            x.add((float) 0);
            y.add((float) 0);
            size.add((float) 0);
        }
        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);

        tracker = analytics.newTracker("UA-65408418-1"); // Replace with actual tracker/property Id
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);

        font.setColor(argb(0, 0, 0, 0));
        font2.setColor(argb(128, 255, 255, 255));
        vibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_choose);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        v = new OurView(this);
        v.setOnTouchListener(this);
        setContentView(v);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }


    }

    @Override
     protected void onPause() {

        super.onPause();
        v.pause();

    }

    @Override
    protected void onResume() {

        super.onResume();
        v.resume();

    }


    /*@Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putExtra("font", font);


    }*/


    public static double ease2(double t, double b, double c, double d) {
        t /= d/2;
        if (t < 1) return c/2 * Math.pow( 2, 10 * (t - 1) ) + b;
        t--;
        return c/2 * ( -Math.pow( 2, -10 * t) + 2 ) + b;
    }

    public class alpha extends Thread {

            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 20; i++) {
                    if (random != 3) {
                        font.setColor(Color.argb((int) (i * 12.75), 255, 255, 255));
                    }
                    else
                    {
                        font.setColor(Color.argb((int) (i * 12.75), 0, 0, 0));
                    }
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    public class OurView extends SurfaceView implements Runnable{

        Thread t = null;
        SurfaceHolder holder;
        boolean isItOk = false;

        public  OurView(Context context) {
            super(context);
            holder = getHolder();
        }



        public void run() {
            green.setColor(rgb(76, 175, 80));
            green.setAntiAlias(true);
            red.setColor(rgb(175, 76, 80));
            red.setAntiAlias(true);
            yellow.setColor(rgb(255, 255, 13));
            yellow.setAntiAlias(true);
            blue.setColor(rgb(13, 53, 255));
            blue.setAntiAlias(true);
            purple.setColor(rgb(174, 32, 255));
            purple.setAntiAlias(true);
            orange.setColor(rgb(255, 163, 32));
            orange.setAntiAlias(true);

            font.setTextAlign(Paint.Align.CENTER);
            Typeface lm = Typeface.createFromAsset(getAssets(), "fonts/Nilland.ttf");
            font.setTypeface(lm);
            font.setAntiAlias(true);
            font2.setTextAlign(Paint.Align.CENTER);
            font2.setTypeface(lm);
            font2.setAntiAlias(true);
            Rect bounds = new Rect();
            arcs.setColor(argb(48,255,255,255));
            arcs.setAntiAlias(true);
            arcYellow.setColor(argb(48,255,128,0));
            arcYellow.setAntiAlias(true);

            RectF[] oval = {new RectF(), new RectF(), new RectF(), new RectF(), new RectF(), new RectF(), new RectF()};

            while (isItOk) {
                if (!holder.getSurface().isValid()) {
                    continue;
                }

                Paint white = new Paint();
                white.setColor(rgb(255, 255, 255));

                Canvas c = holder.lockCanvas();
                font.setTextSize(c.getHeight()/12);
                font2.setTextSize(c.getHeight()/18);
                cHeight = c.getHeight();
                c.drawARGB(255, 25, 25, 25);

                int i = 0;
                font2.getTextBounds("Place your fingers", 0, "Place your fingers".length(), bounds);
                //Log.d("", Integer.toString(bounds.height()));
                c.drawText("Place your fingers", c.getWidth() / 2, (c.getHeight() / 2) - bounds.height() / 2 - 15, font2);
                c.drawText("here to begin", c.getWidth() / 2, (c.getHeight() / 2) + bounds.height()/2 + 15, font2);
                oval[0].set(c.getWidth()/2 - 500,c.getHeight()/2 - 500,c.getWidth()/2 + 500,c.getHeight()/2 + 500);

                c.drawCircle(x.get(i), y.get(i), size.get(i), green);

                oval[i].set(x.get(i) - size.get(i), y.get(i) - size.get(i), x.get(i) + size.get(i), y.get(i) + size.get(i));
                c.drawArc(oval[i], 270, sAngle, true, arcs);

                i++;
                c.drawCircle(x.get(i), y.get(i), size.get(i) , red);

                oval[i].set(x.get(i) - size.get(i), y.get(i) - size.get(i), x.get(i) + size.get(i), y.get(i) + size.get(i));
                c.drawArc(oval[i], 270, sAngle, true, arcs);

                i++;
                c.drawCircle(x.get(i), y.get(i), size.get(i) , yellow);

                oval[i].set(x.get(i) - size.get(i), y.get(i) - size.get(i), x.get(i) + size.get(i), y.get(i) + size.get(i));
                c.drawArc(oval[i], 270, sAngle, true, arcYellow);

                i++;
                c.drawCircle(x.get(i), y.get(i), size.get(i) , blue);

                oval[i].set(x.get(i) - size.get(i), y.get(i) - size.get(i), x.get(i) + size.get(i), y.get(i) + size.get(i));
                c.drawArc(oval[i], 270, sAngle, true, arcs);

                i++;
                c.drawCircle(x.get(i), y.get(i), size.get(i) , purple);

                oval[i].set(x.get(i) - size.get(i), y.get(i) - size.get(i), x.get(i) + size.get(i), y.get(i) + size.get(i));
                c.drawArc(oval[i], 270, sAngle, true, arcs);

                i++;
                c.drawCircle(x.get(i), y.get(i), size.get(i) , orange);

                oval[i].set(x.get(i) - size.get(i), y.get(i) - size.get(i), x.get(i) + size.get(i), y.get(i) + size.get(i));
                c.drawArc(oval[i], 270, sAngle, true, arcs);
                if (random != 99) {
                    while (i <= 20) {
                        sizedone = (float) ease2(i, sizedone, cHeight/5 - i, 50);
                        //size = (float) ease(i, y, me.getY() - y, 10);

                        try {
                            Thread.sleep(0);
                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                        i++;
                    }
                    switch (random) {
                        case 99:
                        case 1:
                            c.drawCircle(x.get(random - 1), y.get(random - 1), sizedone / 2, green);
                            c.drawText("Green Starts!", c.getWidth() / 2, c.getHeight() / 2, font);
                            font.setTextSize(font.getTextSize()/3);
                            c.drawText("Tap to start again.", c.getWidth()/2, (float) (c.getHeight()*0.95), font);
                            if (!alphastarted) {
                                alphastarted = true;
                                alpha alpha = new alpha();
                                alpha.start();
                            }
                            break;
                        case 2:
                            c.drawCircle(x.get(random - 1), y.get(random - 1), sizedone / 2, red);
                            c.drawText("Red Starts!", c.getWidth()/2, c.getHeight()/2, font);
                            font.setTextSize(font.getTextSize()/3);
                            c.drawText("Tap to start again.", c.getWidth()/2, (float) (c.getHeight()*0.95), font);
                            if (!alphastarted) {
                                alphastarted = true;
                                alpha alpha = new alpha();
                                alpha.start();
                            }
                            break;
                        case 3:
                            c.drawCircle(x.get(random - 1), y.get(random - 1), sizedone / 2, yellow);
                            c.drawText("Yellow Starts!", c.getWidth()/2, c.getHeight()/2, font);
                            font.setTextSize(font.getTextSize()/3);
                            c.drawText("Tap to start again.", c.getWidth()/2, (float) (c.getHeight()*0.95), font);
                            if (!alphastarted) {
                                alphastarted = true;
                                alpha alpha = new alpha();
                                alpha.start();
                            }
                            break;
                        case 4:
                            c.drawCircle(x.get(random - 1), y.get(random - 1), sizedone / 2, blue);
                            c.drawText("Blue Starts!", c.getWidth()/2, c.getHeight()/2, font);
                            font.setTextSize(font.getTextSize()/3);
                            c.drawText("Tap to start again.", c.getWidth()/2, (float) (c.getHeight()*0.95), font);
                            if (!alphastarted) {
                                alphastarted = true;
                                alpha alpha = new alpha();
                                alpha.start();
                            }
                            break;
                        case 5:
                            c.drawCircle(x.get(random - 1), y.get(random - 1), sizedone / 2, purple);
                            c.drawText("Purple Starts!", c.getWidth()/2, c.getHeight()/2, font);
                            font.setTextSize(font.getTextSize()/3);
                            c.drawText("Tap to start again.", c.getWidth()/2, (float) (c.getHeight()*0.95), font);
                            if (!alphastarted) {
                                alphastarted = true;
                                alpha alpha = new alpha();
                                alpha.start();
                            }
                            break;
                        case 6:
                            c.drawCircle(x.get(random - 1), y.get(random - 1), sizedone / 2, orange);
                            c.drawText("Orange Starts!", c.getWidth() / 2, c.getHeight() / 2, font);
                            font.setTextSize(font.getTextSize()/3);
                            c.drawText("Tap to start again.", c.getWidth()/2, (float) (c.getHeight()*0.95), font);
                            if (!alphastarted) {
                                alphastarted = true;
                                alpha alpha = new alpha();
                                alpha.start();
                            }
                            break;
                    }

                }
                holder.unlockCanvasAndPost(c);
            }
        }

        public void pause() {
            isItOk = false;
            while(true) {
                try {
                    t.join();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            t=null;
            //recreate();
        }

        public void resume() {

            isItOk = true;
            t = new Thread(this);
            t.start();
            //recreate();
        }
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public class timer extends Thread {

        @Override
        public void run() {
            try {
                //int i = 0;
                //Thread.sleep(1000);
                timeri = 0 - timeri;
                //Log.d("1", "" + timeri);
                kill = false;
                    while (timeri < 800 && !kill) {
                        Thread.sleep(5);
                        sAngle = timeri * (float) 0.46;
                        if (timeri > 752) {
                           //Log.d("Arc", "" + arcs.getAlpha() + "" + (int) 0.96);
                           arcs.setAlpha(arcs.getAlpha() - 1);
                           arcYellow.setAlpha(arcYellow.getAlpha() - 1);
                        }

                        timeri++;

                        if (kill) {
                            //Log.d("", ""+ kill);
                            timeri = 0;
                            Log.d("2", "" + timeri);
                            break;
                        }
                }
                if (kill) {
                    //Log.d("", ""+kill+" 2");
                    //Log.d("3", "" + timeri);
                    return;
                }
                if (!kill && !chosen && pointerC > 1) {
                    vibrate.vibrate(40);
                    chosen = true;
                    random = randInt(1, pointerC);
                    //Log.d("Test", Integer.toString(random));
                }

            }
            catch (InterruptedException e) {
                //Log.d("INTERRUPT", "");
                return;
            }

        }
    }

    public class firstText extends Thread {

        @Override
        public void run() {

            for (int i = 0; i <= 20; i++) {

                font2.setColor(Color.argb((int) (128 - (i * 6.4)), 255, 255, 255));
                try {
                    Thread.sleep(10);
                } catch (Exception e) {

                }

            }
            //Log.d("","");

        }

    }



    public boolean onTouch(View v, final MotionEvent me) {
        switch (MotionEvent.ACTION_MASK & me.getAction()){

            case MotionEvent.ACTION_POINTER_DOWN:
                try {
                    Float.toString(x.get(pointerC - 1));
                    pointerC = me.getPointerCount();
                    //Log.d("", Integer.toString(pointerC - 1));
                    x.set(pointerC - 1, me.getX(pointerC - 1));
                    y.set(pointerC - 1, me.getY(pointerC - 1));
                    size.set(pointerC - 1, (float) 0);
                    sAngle = 0;
                    timeri = 0;
                    kill = true;

                    Thread sizeEase = new Thread(new Runnable() {

                        double ease(double t, double b, double c, double d) {
                            t /= d / 2;
                            if (t < 1) return c / 2 * Math.pow(2, 10 * (t - 1)) + b;
                            t--;
                            return c / 2 * (-Math.pow(2, -10 * t) + 2) + b;
                        }

                        ;

                        @Override
                        public void run() {
                            try {
                                Float.toString(x.get(pointerC - 1));
                                int currentP = pointerC;
                                size.set(currentP, (float) 0);
                                //Log.d("b", Integer.toString(currentP) + " " + Integer.toString(pointerC));
                                int i = 0;
                                while (i <= 20) {
                                    //Log.d("", Integer.toString(currentP));
                                    size.set(currentP - 1, (float) ease(i, size.get(currentP - 1), cHeight / 180, 15));
                                    pointerC = me.getPointerCount();

                                    try {
                                        Thread.sleep(5);
                                    } catch (Exception e) {


                                    }

                                    i++;
                                }
                                //Log.d("a", Integer.toString(currentP) + " " + Integer.toString(pointerC));
                            } catch (IndexOutOfBoundsException e) {
                                x.add((float) 0);
                                y.add((float) 0);
                                size.add((float) 0);
                            }
                        }
                    });
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {


                    }
                    sizeEase.start();
                    //sizeEase.join();
                    //kill2 = true;
                    timer timer = new timer();
                    timer.join();
                    sAngle = 0;
                    timeri = 0;
                    timer.start();
                    /*sAngleTimer sAngleTimer = new sAngleTimer();
                    sAngleTimer.start();*/
                    Thread.sleep(10);

                } catch (IndexOutOfBoundsException e) {

                    x.add((float) 0);
                    y.add((float) 0);
                    size.add((float) 0);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case MotionEvent.ACTION_DOWN:
                if (!initialText) {
                    firstText firstText = new firstText();
                    firstText.start();
                    initialText = true;
                }
                if (random != 99) {
                    recreate();/*
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);*/
                }
                else {
                        pointerC = me.getPointerCount();
                        x.set(0, me.getX());
                        y.set(0, me.getY());
                        size.set(0, (float) 0);
                    Thread tss = new Thread(new Runnable() {

                        double ease(double t, double b, double c, double d) {
                            t /= d / 2;
                            if (t < 1) return c / 2 * Math.pow(2, 10 * (t - 1)) + b;
                            t--;
                            return c / 2 * (-Math.pow(2, -10 * t) + 2) + b;
                        }

                        ;

                        @Override
                        public void run() {
                            int i = 0;
                            while (i <= 20) {
                                size.set(0, (float) ease(i, size.get(0), cHeight / 180, 15));
                                //size = (float) ease(i, y, me.getY() - y, 10);

                                try {
                                    Thread.sleep(5);
                                } catch (Exception e) {


                                }

                                i++;
                            }
                            //Log.d("", Float.toString(size[0]) + " " + cHeight + " " + cHeight/100);
                        }
                    });

                    tss.start();

                    //(new Thread(new sizeEase())).run();
                }
                break;

            case MotionEvent.ACTION_UP:
                pointerC = me.getPointerCount();


                Thread sizeEase = new Thread(new Runnable() {



                    double ease(double t, double b, double c, double d) {

                        t /= d;
                        t--;
                        return -c * (t*t*t*t - 1) + b;
                    }

                    ;

                    @Override
                    public void run() {
                        try {
                            Float.toString(x.get(0));
                            int i = 0;
                            while (i <= 15) {
                                size.set(0, (float) ease(i, size.get(0), -20 + i, 15));
                                pointerC = me.getPointerCount();

                                try {
                                    Thread.sleep(7);
                                } catch (Exception e) {


                                }

                                i++;
                            }
                            size.set(0, (float) 0);
                            for(int is = 0; is < size.size(); is++) {
                                size.set(is, (float) 0);
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {

                        }
                    }
                });

                sizeEase.start();

                break;

            case MotionEvent.ACTION_POINTER_UP:
                try {
                    pointerC = me.getPointerCount();
                    Float.toString(x.get(pointerC - 1));
                    sAngle = 0;
                    timeri = 0;
                    kill = true;
                    Thread sizeEase2 = new Thread(new Runnable() {

                        double ease(double t, double b, double c, double d) {

                            t /= d;
                            t--;
                            return -c * (t*t*t*t - 1) + b;
                        }

                        ;

                        @Override
                        public void run() {
                            try {
                                Float.toString(x.get(pointerC - 1));
                                int currentP = pointerC;
                                int i = 0;
                                while (i <= 15) {
                                    size.set(currentP - 1, (float) ease(i, size.get(currentP - 1), -20 + i, 15));
                                    pointerC = me.getPointerCount();

                                    try {
                                        Thread.sleep(7);
                                    } catch (Exception e) {


                                    }

                                    i++;
                                }
                                size.set(currentP - 1, (float) 0);
                            } catch (ArrayIndexOutOfBoundsException e) {

                            }
                        }
                    });

                    sizeEase2.start();
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {


                    }
                    pointerC = me.getPointerCount();
                /*kill = true;
                kill2 = true;*/
                timer timer = new timer();
                //kill = true;
                timer.join();
                sAngle = 0;
                timeri = 0;
                //Log.d("", ""+ sAngle);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (pointerC > 2) {
                    /*Log.d("", "" + sAngle);
                    sAngleTimer sAngleTimer = new sAngleTimer();
                    sAngleTimer.start();*/
                    timer.start();
                } else {
                        arcYellow.setAlpha(48);
                        arcs.setAlpha(48);
                        kill = true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                try {
                    Float.toString(x.get(pointerC - 1));
                    for (int i = 0; i < pointerC; ++i) {
                        //Log.d("i", Integer.toString(i) + " " + Integer.toString(pointerC));
                        Integer pointerIndex = i;
                        if (i != 0) {

                            x.set(pointerIndex, me.getX(pointerIndex));
                            y.set(pointerIndex, me.getY(pointerIndex));
                        } else {
                            x.set(0, me.getX(0));
                            y.set(0, me.getY(0));

                        }
                        pointerC = me.getPointerCount();
                    }
                } catch (IndexOutOfBoundsException e) {
                    x.add((float) 0);
                    y.add((float) 0);
                    size.add((float) 0);
                }
                break;
                }
        v.invalidate();
        return true;
    }

}
