package tofu.mishazawa.poppi_pong.components;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class GameState {

  private Point screen;
  private Point halfScreen;
  private Point ballCoords;
  private Point p1Coords;
  private Point p2Coords;

  private Point ballVelocity = new Point(5, 5);
  private Point ballSize = new Point(15, 15);
  private Point pSize = new Point(50, 10);


  private Rect p1; // bottom
  private Rect p2; // top
  private Rect ball;

  GameState (Context c) {
    screen = getScreenSize(c);
    halfScreen = new Point(screen.x / 2, screen.y / 2);
    final int OFFSET = 250;

    ballCoords = new Point(halfScreen);

    p1Coords = new Point(halfScreen.x, screen.y - OFFSET);
    p2Coords = new Point(halfScreen.x, OFFSET);

    ball = new Rect();
    p1 = new Rect();
    p2 = new Rect();

    setRectCoords(ball, ballCoords, ballSize);
    setRectCoords(p1, p1Coords, pSize);
    setRectCoords(p2, p2Coords, pSize);
  }

  public void update () {

    if (ballCoords.y > screen.y || ballCoords.y < 0) {
      ballCoords.set(halfScreen.x, halfScreen.y);
    }

    if (ballCoords.x > screen.x - ballSize.x || ballCoords.x < 0) {
      ballVelocity.set(ballVelocity.x * -1, ballVelocity.y);
    }

    if (ball.intersect(p1) || ball.intersect(p2)) {
      ballVelocity.y *= -1;
    }

    ballCoords.set(ballCoords.x + ballVelocity.x, ballCoords.y + ballVelocity.y);

    setRectCoords(ball, ballCoords, ballSize);
    setRectCoords(p1, p1Coords, pSize);
    setRectCoords(p2, p2Coords, pSize);
  }

  public void draw (Canvas canvas, Paint paint) {
    canvas.drawRGB(200, 200, 200);

    paint.setARGB(200, 0, 200, 0);
    canvas.drawRect(p1, paint);

    paint.setARGB(200, 0, 0, 200);
    canvas.drawRect(p2, paint);

    paint.setARGB(200, 200, 0, 0);
    canvas.drawRect(ball, paint);
  }

  public boolean onTouch (View view, MotionEvent event) {
    int pointers = event.getPointerCount();
    for (int i = 0; i < pointers; i++) {
      if (i >= 2) break; // Crutch )))0))0)))
      if (event.getY(i) > halfScreen.y) {
        p1Coords.set((int)event.getX(i), p1Coords.y);
      } else {
        p2Coords.set((int)event.getX(i), p2Coords.y);
      }
    }
    return true;
  }

  private Point getScreenSize (Context c) {
    Point point = new Point();
    try {
      ((WindowManager) c.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(point);
    } catch (NullPointerException e) {
      Log.d("Mixa", "Null pointer exception no display");
    }
    return point;
  }

  private void setRectCoords (Rect r, Point coords, Point size) {
    r.set(coords.x, coords.y, coords.x + size.x, coords.y + size.y);
  }
}
