package co.ninjan.startplayer;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by TFE on 15/7/2015.
 */
public class drawBall extends View {

    public drawBall(Context context) {

        super(context);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint blue = new Paint();
        blue.setColor(Color.BLUE);
        blue.setStyle(Paint.Style.FILL);

        Paint white = new Paint();
        white.setColor(Color.WHITE);


        Rect test = new Rect();
        test.set(0, 0, canvas.getWidth()/2, canvas.getHeight()/2);

        canvas.drawRect(test, blue);
        canvas.drawOval(0, 0, canvas.getWidth()/4, test.width(), white);
         /*OvalShape test = new OvalShape();
         canvas.drawOval(10,10,10,10,blue);*/
    }
}
