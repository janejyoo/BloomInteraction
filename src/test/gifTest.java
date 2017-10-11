import processing.core.PApplet;
import processing.core.PImage;
import processing.opengl.PGraphics2D;
import gifAnimation.*;

public class gifTest extends PApplet {
	

	PImage[] animation;
	Gif loopingGif;
	Gif nonLoopingGif;
	boolean pause = false;

	public void setup() {
	  
	  frameRate(100);
	  
	  // create the GifAnimation object for playback
	  loopingGif = new Gif(this, "giphy.gif");
	  loopingGif.loop();
	  nonLoopingGif = new Gif(this, "giphy.gif");
	  nonLoopingGif.play();
	  nonLoopingGif.ignoreRepeat();
	  // create the PImage array for the interactive display
	  animation = Gif.getPImages(this, "giphy.gif");
	}
	
	public void settings()
	{
		size(1000,800, FX2D);
	}

	public void draw() {
	  background(255 / (float)height * mouseY);
	  image(loopingGif, 10, height / 2 - loopingGif.height / 2);
	  image(nonLoopingGif, width/2 - nonLoopingGif.width/2, height / 2 - nonLoopingGif.height / 2);
	  image(animation[(int) (animation.length / (float) (width) * mouseX)], width - 10 - animation[0].width, height / 2 - animation[0].height / 2);
	}

	public void mousePressed() {
	  nonLoopingGif.play();
	}

	public void keyPressed() {
	  if (key == ' ') {
	    if (pause) {
	      nonLoopingGif.play();
	      loopingGif.play();
	      pause = false;
	    } 
	    else {
	      nonLoopingGif.pause();
	      loopingGif.pause();
	      pause = true;
	    }
	  }
	}
	
	public static void main(String[] args) {
		PApplet.main(gifTest.class.getName());
	}
}
