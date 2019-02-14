package tofu.mishazawa.poppi_pong.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
  private GameThread gameThread;

  public GameView (Context context, AttributeSet attrs) {
    super(context, attrs);

    SurfaceHolder holder = getHolder();
    holder.addCallback(this);
    setFocusable(true);
    gameThread = new GameThread(holder, context);

    setOnTouchListener(new View.OnTouchListener () {
      @Override
      public boolean onTouch (View v, MotionEvent ev) {
        return gameThread.getGameState().onTouch(v, ev);
      }
    });
  }


  @Override
  public void surfaceCreated (SurfaceHolder holder) {
    gameThread.start();
  }

  @Override
  public void surfaceChanged (SurfaceHolder holder, int format, int width, int height) {}

  @Override
  public void surfaceDestroyed (SurfaceHolder holder) {
    gameThread.interrupt();
  }
}
