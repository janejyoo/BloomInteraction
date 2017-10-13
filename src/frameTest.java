import processing.core.*;
//import java.util.ArrayList;
import processing.core.PImage;
import gifAnimation.*;

public class frameTest extends PApplet {

	PImage[] f1, f2, f3, f4, f5, f6, f7;
	int frame1 = 0;
	int frame2 = 1;
	
	public void setup() {		
		
		f1 = Gif.getPImages(this, "flowers/f1.gif");
		f2 = Gif.getPImages(this, "flowers/f2.gif");
		f3 = Gif.getPImages(this, "flowers/f3.gif");
		f4 = Gif.getPImages(this, "flowers/f4.gif");
		f5 = Gif.getPImages(this, "flowers/f5.gif");
		f6 = Gif.getPImages(this, "flowers/f6.gif");
		f7 = Gif.getPImages(this, "flowers/f7.gif");
	}
	
	public void settings()
	{
		size(1000,800, FX2D);
	}
	
	public void draw() {
		// background color black
		background(0);
		
		// if mouse is pressed 
		if(mousePressed){
			if(frame1 <= 30)
				// next frame
				frame1++;
			else if( frame2 < f1.length )
				// previous frame
				frame2++;
		}
		
		
		if( frame1 <= f1.length-1 ){
			image(f1[frame1], 0, height / 2 - f1[0].height/2, 100, 70);
		}
		else{
			image(f1[f1.length-frame2], 0, height / 2 - f1[0].height/2, 100, 70);
		}
		
		if( frame1 <= f2.length-1 ){
			image(f2[frame1], 100, height / 2 - f2[0].height/2, 70, 50);
		}
		else{
			image(f2[f2.length-frame2], 100, height / 2 - f2[0].height/2, 70, 50);
		}

		if( frame1 <= f3.length-1 ){
			image(f3[frame1], 300, height / 2 - f1[0].height/2, 90, 90);
		}
		else{
			image(f3[f3.length-frame2], 300, height / 2 - f1[0].height/2, 90, 90);
		}
		
		if( frame1 <= f4.length-1 ){
			image(f4[frame1], 500, height / 2 - f1[0].height/2, 70, 70);
		}
		else{
			image(f4[f4.length-frame2], 500, height / 2 - f1[0].height/2, 70, 70);
		}
		
		if( frame1 <= f5.length-1 ){
			image(f5[frame1], 600, height / 2 - f2[0].height/2, 70, 70);
		}
		else{
			image(f5[f5.length-frame2], 600, height / 2 - f2[0].height/2, 70, 70);
		}

		if( frame1 <= f6.length-1 ){
			image(f6[frame1], 500, 100, 90, 70);
		}
		else{
			image(f6[f6.length-frame2], 500, 100, 90, 70);
		}
		
		if( frame1 <= f7.length-1 ){
			image(f7[frame1], 100, 100, 90, 90);
		}
		else{
			image(f7[f7.length-frame2], 100, 100, 90, 90);
		}
	}	
	
	public static void main(String[] args) {
		PApplet.main(frameTest.class.getName());
	}
}
