package tofu.mishazawa.poppi_pong.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

  private SurfaceHolder holder;
  private GameState gameState;
  private Paint paint;

  GameThread (SurfaceHolder h, Context c) {
    holder = h;
    gameState = new GameState(c);
    paint = new Paint();
  }

  @SuppressWarnings("InfiniteLoopStatement")
  @Override
  public void run () {
    while (true) {
      Canvas canvas = holder.lockCanvas();
      gameState.update();
      gameState.draw(canvas, paint);
      holder.unlockCanvasAndPost(canvas);
    }
  }

  public GameState getGameState() {
    return gameState;
  }
}
