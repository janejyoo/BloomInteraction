import processing.core.PApplet;
import processing.core.PImage;
import processing.opengl.PGraphics2D;
import gifAnimation.*;

public class gifTest2 extends PApplet {
	

	PImage[] animation1, animation2, animation3;
	Gif myAnimation;
	Gif loopingGif;
	Gif nonLoopingGif;
	boolean pause = false;
	
	public void setup() {
	  
	  frameRate(100);
	  
//	  // create the GifAnimation object for playback
//	  loopingGif = new Gif(this, "flowers/f4.gif");
//	  loopingGif.loop();
//	  nonLoopingGif = new Gif(this, "flowers/f2.gif");
//	  nonLoopingGif.play();
//	  nonLoopingGif.ignoreRepeat();
	  // create the PImage array for the interactive display
	  animation1 = Gif.getPImages(this, "flowers/f4.gif");
	  animation2 = Gif.getPImages(this, "flowers/f5.gif");
	  animation3 = Gif.getPImages(this, "flowers/f3.gif");
	  myAnimation = new Gif(this, "flowers/f3.gif");
	  myAnimation.play();
	}
	
	public void settings()
	{
		size(1000,800, FX2D);
	}

	public void draw() {
		
	  background(0);
	  //background(255 / (float)height * mouseY);
	  //image(loopingGif, 10, height / 2 - loopingGif.height / 2);
	  //image(nonLoopingGif, width/2 - nonLoopingGif.width/2, height / 2 - nonLoopingGif.height / 2);
	  //image(animation3[(int) (animation3.length / (float) (width) * mouseX)], width - 10 - animation3[0].width, height / 2 - animation3[0].height / 2);
	  image(myAnimation, 10, height / 2 - animation1[0].height / 2);
	  
	}

	public void mousePressed() {
	  //nonLoopingGif.play();
	}

	public void keyPressed() {
	  if (key == ' ') {
	    if (pause) {
	      //nonLoopingGif.play();
	      //loopingGif.play();
	      pause = false;
	    } 
	    else {
	      //nonLoopingGif.pause();
	      //loopingGif.pause();
	      pause = true;
	    }
	  }
	}
	
	public static void main(String[] args) {
		PApplet.main(gifTest2.class.getName());
	}
}
