
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
	
	// number of flowers in ArrayList
	final static int NUM_FLOWERS = 50;
	
	// frame number of PImage[]
	int frame = 0;
	
	// each array has the frames of flower gif
	PImage[] f1, f2, f3, f4, f5, f6, f7;
	
	// holds flowers
	ArrayList<PImage[]> flowers;
	
	// true if the PImage arrays reach the last frame
	boolean fullBloom = false;
	
	public static float PROJECTOR_RATIO = 1080f/1920.0f;
	
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
		createWindow(true, false, .8f);
		//size(800,800, FX2D);
	}

	public void setup(){
		
		setScale(.6f);
		
		flowers = new ArrayList<PImage[]>();
		// get each frame from flower gifs
		f1 = Gif.getPImages(this, "flowers/f1.gif");
		f2 = Gif.getPImages(this, "flowers/f2.gif");
		f3 = Gif.getPImages(this, "flowers/f3.gif");
		f4 = Gif.getPImages(this, "flowers/f4.gif");
		f5 = Gif.getPImages(this, "flowers/f5.gif");
		f6 = Gif.getPImages(this, "flowers/f6.gif");
		f7 = Gif.getPImages(this, "flowers/f7.gif");
		
		// add flowers to ArrayList randomly 
		for(int i = 0; i < NUM_FLOWERS; i++){
			int random = (int)(Math.random()*7)+1;
			
			if(random == 1)
				flowers.add(f1);	
			else if(random == 2)
				flowers.add(f2);
			else if(random == 3)
				flowers.add(f3);
			else if(random == 4)
				flowers.add(f4);
			else if(random == 5)
				flowers.add(f5);
			else if(random == 6)
				flowers.add(f6);
			else if(random == 7)
				flowers.add(f7);
		}
		
		
		try{
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

			fill(191, 0, 173);
			noStroke();
			
			// draw circles 
//			drawIfValid(head);
//			drawIfValid(spine);
//			drawIfValid(spineBase);
//			drawIfValid(shoulderLeft);
//			drawIfValid(shoulderRight);
//			drawIfValid(footLeft);
//			drawIfValid(footRight);
//			drawIfValid(handLeft);
//			drawIfValid(handRight);

			
//			if( (shoulderLeft != null) &&
//					(shoulderRight != null) &&
//					(handLeft != null) &&
//					(handRight != null) ) {
//				stroke(255,0,0, 100);
//				noFill();
//				strokeWeight(.05f); // because of scale weight needs to be much thinner
//				curve(
//						handLeft.x, handLeft.y, 
//						shoulderLeft.x, shoulderLeft.y, 
//						shoulderRight.x, shoulderRight.y,
//						handRight.x, handRight.y
//						);
//			}
			
			// head
			drawHead(head);
			// neck
			drawNeck(head, spine);
			// left leg
			drawLimbs(spineBase, footLeft);
			// right leg
			drawLimbs(spineBase, footRight);
			// left arm
			drawLimbs(shoulderLeft, handLeft);
			// right arm
			drawLimbs(shoulderRight, handRight);
			// torso
			drawTorso(spine, spineBase, shoulderRight, shoulderLeft);
			
			
			// make the flowers bloom and wither  
			if(frame < f1.length-1 && !fullBloom){
				frame++;
				if(frame == f1.length-1)
					fullBloom = true;
			}
			else if ( frame > 0 && fullBloom){
				frame--;
				if(frame == 0)
					fullBloom = false;
			}
		}

	}
	
	public void drawIfValid(PVector vec) {
		if(vec != null) {
			ellipse(vec.x, vec.y, .1f,.1f);
		}

	}

	public void drawHead(PVector vec){
		if(vec == null)
			return;
		
		image(flowers.get(10)[frame], vec.x, vec.y, .1f, .1f);
		image(flowers.get(11)[frame], vec.x+.07f, vec.y, .1f, .1f);
		image(flowers.get(12)[frame], vec.x+.035f, vec.y+.035f, .08f, .08f);
		image(flowers.get(13)[frame], vec.x+.035f, vec.y-.035f, .08f, .08f);
		image(flowers.get(14)[frame], vec.x-.035f, vec.y+.035f, .08f, .08f);
		image(flowers.get(15)[frame], vec.x-.035f, vec.y-.035f, .08f, .08f);
		image(flowers.get(16)[frame], vec.x-.07f, vec.y, .1f, .1f);
		image(flowers.get(17)[frame], vec.x, vec.y-0.07f, .1f, .1f);
		image(flowers.get(18)[frame], vec.x, vec.y+0.07f, .1f, .1f);
		
	}
	
	// get coordinate points between two coordinates
	public float[][] points(PVector vec1, PVector vec2){
		float[][] position = new float[10][2];
		
		float f = 0;
		
		for(int i = 0; i < 10; i++){
				position[i][0] = lerp(vec1.x, vec2.x, f);
				position[i][1] = lerp(vec1.y, vec2.y, f);
				f += .1f;
		}
		return position;
	}

	public void drawLimbs(PVector start, PVector end){
		if(start == null || end == null)	
			return;

		float[][] position = points(start, end);

		for(int i = 0; i < position.length; i++){
			// if i is even
			if( i%2 == 0 ){
				image(flowers.get(i)[frame], position[i][0]+.02f, position[i][1]-.02f, .09f, .09f);
				image(flowers.get(flowers.size()-i-1)[frame], position[i][0]-.02f, position[i][1], .09f, .09f);
			}
			// if i is odd
			else{
				image(flowers.get(i)[frame], position[i][0]-.02f, position[i][1], .09f, .09f);
				image(flowers.get(flowers.size()-i-1)[frame], position[i][0]+.02f, position[i][1]-.02f, .09f, .09f);
			}
		}
	}
	
	public void drawNeck(PVector head, PVector spine){
		if(head == null || spine == null)
		return;
		
		float[][] position = points(head, spine);

		for(int i = 0; i < position.length; i++){
			image(flowers.get(flowers.size()-i-1)[frame], position[i][0]+.01f, position[i][1]-.02f, .05f, .05f);
			image(flowers.get(i)[frame], position[i][0]-.01f, position[i][1], .05f, .05f);
		}
	}
	
	public void drawTorso(PVector spine, PVector spineBase, PVector shoulderLeft, PVector shoulderRight){
		if(spine == null || spineBase == null || shoulderLeft == null || shoulderRight == null)
			return;
		float[][] position = points(spine, spineBase);
		for(int i = 0; i < position.length; i++){
			if( i%2 == 0 ){
				image(flowers.get(i)[frame], position[i][0]+.02f, position[i][1]-.02f, .09f, .09f);
				image(flowers.get(flowers.size()-i-1)[frame], position[i][0]-.02f, position[i][1], .09f, .09f);
			}
			else{
				image(flowers.get(i)[frame], position[i][0]-.02f, position[i][1], .09f, .09f);
				image(flowers.get(flowers.size()-i-1)[frame], position[i][0]+.02f, position[i][1]-.02f, .09f, .09f);
			}
		}
		
		for(int i = 0; i < position.length; i++){
			image(flowers.get(i+10)[frame], position[i][0]+0.1f, position[i][1], .09f, .09f);
			image(flowers.get(flowers.size()-i-1)[frame], position[i][0]+0.07f, position[i][1], .06f, .06f);
			image(flowers.get(i+20)[frame], position[i][0]-0.1f, position[i][1], .09f, .09f);
			image(flowers.get(flowers.size()-i-1)[frame], position[i][0]-0.05f, position[i][1], .06f, .06f);
		}
	}
	
	
	public static void main(String[] args) {
		PApplet.main(KinectRenderDemo.class.getName());
	}

}
