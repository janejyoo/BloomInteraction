import processing.core.PApplet;
import processing.core.PVector;
import java.io.IOException;
import gifAnimation.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.opengl.PGraphics2D;

/**
 * @author eitan
 * 
 * Jane's Version of KinectRenderDemo
 * Focus:
 * 
 * 
 */

public class bloom_KRD extends PApplet {

	KinectBodyDataProvider kinectReader;
	
	PImage[] animation1, animation2, animation3; //animation elements
	Gif myAnimation; 
	Gif myAnimation1;

	public void settings() {
		//size(1000,1000);//, P2D);
		fullScreen(); //native resolution is 1920x1080
	}

	public void setup(){
		// use this code to run your PApplet from data recorded by UPDRecorder 
//		try {
//			kinectReader = new KinectBodyDataProvider("recordedData.kinect",5);
//		} catch (IOException e) {
//			System.out.println("Unable to create kinect producer");
//		}
		kinectReader = new KinectBodyDataProvider(8008);
		 //
		animation1 = Gif.getPImages(this, "flowers/f4.gif");
		animation2 = Gif.getPImages(this, "flowers/f5.gif");
		myAnimation = new Gif(this, "flowers/f3.gif");
		myAnimation1 = new Gif(this, "flowers/f5.gif");
		myAnimation.play();
		myAnimation1.play();

//		try {
//			kinectReader = new KinectBodyDataProvider("test.kinect", 15); //15 seconds?
//		} catch (IOException e) {
//			System.out.println("Unable to create kinect producer");
//		}
		kinectReader.start();
	}
	
	public void draw(){
		
		background(0); // make background black

		//makes the window 2x2
		this.scale(width/2.0f, -height/2.0f);

		//make positive y up and center of window 0,0
		translate(1,-1);
		noStroke();


		// leave trails instead of clearing background 
		//noStroke();
		//fill(0,0,0, 10);
		//rect(-1,-1, 2, 2); //draw transparent rect of the window

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


			fill(255,255,255);
			noStroke();
			drawToPurple(head);
			drawIfValid(spine);
			drawIfValid(spineBase);
			drawIfValid(shoulderLeft);
			drawIfValid(shoulderRight);
			drawIfValid(footLeft);
			drawIfValid(footRight);
			drawIfValid(handLeft);
			drawIfValid(handRight);

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
		}
	}

	/**
	 * Draws an ellipse in the x,y position of the vector (it ignores z).
	 * Will do nothing is vec is null.  This is handy because get joint 
	 * will return null if the joint isn't tracked. 
	 * @param vec
	 */
	public void drawIfValid(PVector vec) {
		if(vec != null) {
			ellipse(vec.x, vec.y, .1f,.1f);
		}

	}
	
	public void drawToPurple(PVector vec){
		if(vec != null){

			fill(153,50,204);
			ellipse(vec.x, vec.y, .1f, .1f);
		}
	}


	public static void main(String[] args) {
		PApplet.main(bloom_KRD.class.getName());
	}

}
