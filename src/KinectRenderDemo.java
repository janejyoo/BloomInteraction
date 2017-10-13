import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import java.io.IOException;
import gifAnimation.*;

/**
 * @author eitan
 * 
 */

public class KinectRenderDemo extends PApplet {

	KinectBodyDataProvider kinectReader;
	
	Gif flower1; 
	Gif flower2; 
	Gif flower3;
	Gif flower4; 
	Gif flower5; 
	Gif flower6;
	Gif flower7; 
	
	boolean pause = false;

	public void settings() {
		fullScreen();

	}

	public void setup(){

		  frameRate(100);

		/*
		 * use this code to run your PApplet from data recorded by UPDRecorder 
		 */

//		try {
//			kinectReader = new KinectBodyDataProvider("recordedData.kinect",6);
//		} catch (IOException e) {
//			System.out.println("Unable to creat e kinect producer");
//		}
		 
		try {
			kinectReader = new KinectBodyDataProvider("test.kinect", 5);
		} catch (IOException e) {
			System.out.println("Unable to creat e kinect producer");
		}
		//kinectReader = new KinectBodyDataProvider(8008);
		kinectReader.start();
		
		// set up gifs
		setupGifs();
	}
	
	public void setupGifs(){
		flower1 = new Gif(this, "flowers/f1.gif");
		flower1.loop();
		flower2 = new Gif(this, "flowers/f2.gif");
		flower2.loop();
		flower3 = new Gif(this, "flowers/f3.gif");
		flower3.loop();
		flower4 = new Gif(this, "flowers/f4.gif");
		flower4.loop();
		flower5 = new Gif(this, "flowers/f5.gif");
		flower5.loop();
		flower6 = new Gif(this, "flowers/f6.gif");
		flower6.loop();
		flower7 = new Gif(this, "flowers/f7.gif");
		flower7.loop();
	}
	
	public void draw(){
		//makes the window 2x2
		this.scale(width/2.5f, -height/2.5f);

		//make positive y up and center of window 0,0
		translate(1,-1);
		noStroke();

		background(0);

		// leave trails instead of clearing background \ 
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
			
			System.out.println("head" + head);


			fill(255,255,255);
			noStroke();
			drawIfValid(head, 1);
			drawIfValid(spine, 2);
			drawIfValid(spineBase, 3);
			drawIfValid(shoulderLeft, 4);
			drawIfValid(shoulderRight, 5);
			drawIfValid(footLeft, 6);
			drawIfValid(footRight, 7);
			drawIfValid(handLeft, 8);
			drawIfValid(handRight, 1);


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
	public void drawIfValid(PVector vec, int flower) {
		if(vec != null) {

			if( flower == 1 ){
				image(flower1, vec.x, vec.y, .2f, .2f);
			}
			else if( flower == 2 ){
				image(flower2, vec.x, vec.y, .3f, .3f);
			}
			else if( flower == 3 ){
				image(flower3, vec.x, vec.y, .3f, .3f);
			}
			else if( flower == 4 ){
				image(flower4, vec.x, vec.y, .3f, .3f);
			}
			else if( flower == 5 ){
				image(flower5, vec.x, vec.y, .3f, .3f);
			}
			else if( flower == 6 ){
				image(flower6, vec.x, vec.y, .3f, .3f);
			}
			else if( flower == 7 ){
				image(flower7, vec.x, vec.y, .3f, .3f);
			}

			
			//drawFlowers(vec, .07f);
			//ellipse(vec.x, vec.y, .1f,.1f);
		}

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
		PApplet.main(KinectRenderDemo.class.getName());
	}

}
