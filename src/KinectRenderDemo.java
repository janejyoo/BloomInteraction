import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import java.io.IOException;
import java.io.IOException;
import java.util.ArrayList;
import processing.core.*;
import gifAnimation.*;

/**
 * @author eitan
 *
 */
public class KinectRenderDemo extends PApplet {

	KinectBodyDataProvider kinectReader;
	
	int frame = 0;
	// Store Flowers
	ArrayList<PImage[]> flowers;
	
	PImage[] f1, f2, f3, f4, f5, f6, f7;

<<<<<<< Updated upstream
	public static float PROJECTOR_RATIO = 1080f/1920.0f;
=======
	boolean pause = false;
	
	//hand Right
	float HRprevY = 0;
	float HRlastFrame = 0;
	
	//handLeft
	float HLprevY = 0;
	float HLlastFrame = 0;
>>>>>>> Stashed changes

	public void createWindow(boolean useP2D, boolean isFullscreen, float windowsScale) {
		if (useP2D) {
			if(isFullscreen) {
				fullScreen(FX2D);  			
			} else {
				size((int)(1920 * windowsScale), (int)(1080 * windowsScale), FX2D);
			}
		} else {
			if(isFullscreen) {
				fullScreen();  			
			} else {
				size((int)(1920 * windowsScale), (int)(1080 * windowsScale), FX2D);
			}
		}		
	}
	
	// use lower numbers to zoom out (show more of the world)
	// zoom of 1 means that the window is 2 meters wide and appox 1 meter tall.
	public void setScale(float zoom) {
		scale(zoom* width/2.0f, zoom * -width/2.0f);
		translate(1f/zoom , -PROJECTOR_RATIO/zoom );		
	}
		
	public void settings() {
		//fullScreen(FX2D);
		createWindow(true, true, .5f);
		//size(800,800, FX2D);
	}

	public void setup(){
		
		setScale(.5f);
		// get each frame from flower gifs
		f1 = Gif.getPImages(this, "flowers/f1.gif");
		f2 = Gif.getPImages(this, "flowers/f2.gif");
		f3 = Gif.getPImages(this, "flowers/f3.gif");
		f4 = Gif.getPImages(this, "flowers/f4.gif");
		f5 = Gif.getPImages(this, "flowers/f5.gif");
		f6 = Gif.getPImages(this, "flowers/f6.gif");
		f7 = Gif.getPImages(this, "flowers/f7.gif");

		  frameRate(100);

		try {
			kinectReader = new KinectBodyDataProvider("test.kinect", 5);
		}catch(IOException e){
			
		}
			kinectReader.start();		
	}
		
	public void draw(){
		//makes the window 2x2
		this.scale(width/2.3f, -height/2.3f);
		
		//make positive y up and center of window 0,0
		translate(1,-1);
		noStroke();

		background(0);
		
		fill(255,0,255);
		noStroke();
	
		KinectBodyData bodyData = kinectReader.getMostRecentData();
		Body person = bodyData.getPerson(0);
		
		if(person != null){
			PVector head = person.getJoint(Body.HEAD);
			PVector spine = person.getJoint(Body.SPINE_SHOULDER);
			PVector spineBase = person.getJoint(Body.SPINE_BASE);
			PVector shoulderLeft = person.getJoint(Body.SHOULDER_LEFT);
			PVector shoulderRight = person.getJoint(Body.SHOULDER_RIGHT);
			PVector footLeft = person.getJoint(Body.FOOT_LEFT);
			PVector footRight = person.getJoint(Body.FOOT_RIGHT);
			PVector handLeft = person.getJoint(Body.HAND_LEFT);
			PVector handRight = person.getJoint(Body.HAND_RIGHT);
<<<<<<< Updated upstream

			fill(191, 0, 173);
=======
			
			System.out.println("diff HR " + getIntensity(handRight, HRprevY, HRlastFrame) );
			//System.out.println("diff HL " + diff(handLeft, HLprevY, HLlastFrame) );

			fill(255,255,255);
>>>>>>> Stashed changes
			noStroke();
//			drawIfValid(head, 1);
//			drawIfValid(spine, 2);
//			drawIfValid(spineBase, 3);
//			drawIfValid(shoulderLeft, 4);
//			drawIfValid(shoulderRight, 5);
//			drawIfValid(footLeft, 6);
//			drawIfValid(footRight, 7);
//			drawIfValid(handLeft, 8);
//			drawIfValid(handRight, 1);

			
			if( (shoulderLeft != null) &&
					(shoulderRight != null) &&
					(handLeft != null) &&
					(handRight != null) ) {
				stroke(255,0,0, 100);
				noFill();
				strokeWeight(.05f); // because of scale weight needs to be much thinner
				curve(
						handLeft.x, handLeft.y, 
						shoulderLeft.x, shoulderLeft.y, 
						shoulderRight.x, shoulderRight.y,
						handRight.x, handRight.y
						);
			}
			
			
			if(head != null){
				// add flowers
				drawHead(head);
				
				if(frame < f1.length-1){
					frame++;
				}
			}
			
		}
<<<<<<< Updated upstream
=======
	}
	
	//velocity and change in y
	//90% of the old change (change in the last frame) + 10% of the new change (current and last)
	public float getCurrentChange(float lastframe, float changeinY){
		
		float result = (float) ((lastframe*0.8) + (changeinY*0.2));
		
		return result;
	}
	
	public boolean diff(PVector currV, float prevY, float lastFrame){
		
		boolean goingDown = false;
		
		if (currV != null){
			
			float diffY = currV.y - prevY ;
			
			float currY = getCurrentChange(lastFrame, diffY);
				
				//don't change the intensity if there's no change
				if (currY != 0){
				
					if ( currY >= 0.05 ){
						goingDown = true;
					}
					
					//if the difference is positive, don't change anything
					else{
						goingDown = false;
					}
				}
			prevY = currV.y;
			lastFrame = currY;
			
		}
		
		return goingDown;
	}
	
	public int getIntensity(PVector p1, float prevY, float lastFrame){
		
		boolean curVal = diff(p1, prevY, lastFrame);
		
		int intensity = -1;
		
		if (curVal){
			
			float diff = p1.y - prevY;
			
			System.out.println("diff : " + diff);
			
			if (0 == diff){
				 intensity = 0;
			}
			else if (diff > 0 && diff < 0.5){
				 intensity = 1;
			}
			else if (diff > 0 && diff < 0.8 ){
				 intensity = 2;
			}
			else{
				intensity = 3;
			}
		}
		
		return intensity;
	}
>>>>>>> Stashed changes

	}
	
<<<<<<< Updated upstream
	// add flowers to represent head
	public void drawHead(PVector vec){
		
		image(f1[frame], vec.x+0.03f, vec.y+0.01f, .15f, .15f);
		image(f2[frame], vec.x+0.02f, vec.y+0.02f, .15f, .15f);
		image(f3[frame], vec.x-0.1f, vec.y-0.03f, .15f, .15f);
		image(f4[frame], vec.x-0.1f, vec.y, .15f, .15f);
		image(f2[frame], vec.x, vec.y, .15f, .15f);
		image(f3[frame], vec.x, vec.y-0.1f, .15f, .15f);
		image(f4[frame], vec.x, vec.y-0.2f, .15f, .15f);
		image(f5[frame], vec.x-0.2f, vec.y, .15f, .15f);
		image(f6[frame], vec.x-0.1f, vec.y-0.1f, .15f, .15f);
		image(f7[frame], vec.x, vec.y, .1f, .1f);
=======
	public void drawToPurple(PVector vec){
		if(vec != null){

			fill(153,50,204);
			//ellipse(vec.x, vec.y, .1f, .1f);

			image(myAnimation, vec.x, vec.y, 0.3f, 0.3f);
		}
>>>>>>> Stashed changes
	}
	
	

	public static void main(String[] args) {
		PApplet.main(KinectRenderDemo.class.getName());
	}

}
