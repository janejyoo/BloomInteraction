import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import java.io.IOException;
import java.util.ArrayList;
import gifAnimation.*;

/**
 * @author eitan
 *
 *
 *Mirrors: "Bloom Interaction"
 *243 EM Project 1
 *Fri Oct 27 
 *Collaborators: Jane Yoo, Crystal Seo, Sujin Kim, Onji Bae
 */


public class KinectRenderDemo extends PApplet {

	KinectBodyDataProvider kinectReader;
	
	// number of flowers in ArrayList
	final static int NUM_FLOWERS = 301;
	
	// holds current frame index number of PImage[]
	int frame = 0;
	
	// each array has the frames of flower gif
	PImage[] f1, f2, f3, f4, f5;

	// holds flowers
	ArrayList<PImage[]> flowers;
	
	// true if the PImage arrays reach the last frame
	boolean fullBloom = false;
	
	// projector ratio determined by eitan
	public static float PROJECTOR_RATIO = 1080f/1920.0f;
	
	boolean pause = false;
	
	//handRight variables for getIntensity method
	float HRprevY = 0;
	float HRVelocity = 0;
	boolean HRgoingUp = false;
	float HRcurrY = 0;
	int HRintensity = -1;
	
	//handLeft variables for getIntensity method
	float HLprevY = 0;
	float HLVelocity = 0;
	boolean HLgoingUp = false;
	float HLcurrY = 0;
	int HLintensity = -1;

	// creates window for application
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
		//createWindow(true, true, .5f);
		size(800,800, FX2D);
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

		// add flowers to ArrayList randomly 
		for(int i = 0; i < NUM_FLOWERS; i++){
			int random = (int)(Math.random()*5)+1;
			
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
		}
		
		//LIVE
/*		kinectReader = new KinectBodyDataProvider(8008);
		kinectReader.start();*/
		
		//recorded UDP
		try{
			kinectReader = new KinectBodyDataProvider("test.kinect",6);
		}catch(IOException e){
			
		}
		kinectReader.start();
		
		
	}
		
	public void draw(){
		// makes the window 2x2
		this.scale(width/2.3f, -height/2.3f);
		
		// make positive y up and center of window 0,0
		translate(1,-1);
		noStroke();
		background( 0);
		fill(255,0,255);
		noStroke();
	
		// initialize bodyData object
		KinectBodyData bodyData = kinectReader.getMostRecentData();
		
		// initialize person object
		// use person methods to get coordinates of each body part
		Body person = bodyData.getPerson(0);
		
		// assign body part coordinates to body part PVectors
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
			//added more joints from original box-like flower body
			PVector elbowLeft = person.getJoint(Body.ELBOW_LEFT);
			PVector elbowRight = person.getJoint(Body.ELBOW_RIGHT);
			PVector kneeLeft = person.getJoint(Body.KNEE_LEFT);
			PVector kneeRight = person.getJoint(Body.KNEE_RIGHT);
			

			fill(191, 0, 173);
			fill(255,255,255);
			noStroke();
						
			System.out.println("diff HL " + getIntensity(handRight, handLeft) );

			/* DRAW BODY PARTS */
			drawHead(head);
			drawLimbs(spineBase, kneeLeft);
			drawLimbs(kneeLeft, footLeft);
			drawLimbs(spineBase,kneeRight);
			drawLimbs(kneeRight, footRight);
			drawLimbs(shoulderLeft, elbowLeft);
			drawLimbs(shoulderRight, elbowRight);
			drawLimbs(elbowLeft, handLeft);
			drawLimbs(elbowRight, handRight);
			drawTorso(spine, spineBase, shoulderRight, shoulderLeft);

			System.out.println(frame);
			
			/* IF LEFT/RIGHT HAND INTENSITY > 0, BLOOM */

			if( getIntensity(handRight, handLeft) == 5 && frame <= NUM_FLOWERS - 50)
				frame += 50;
			else if( getIntensity(handRight, handLeft) == 4 && frame <= NUM_FLOWERS - 40)
				frame += 40;
			else if( getIntensity(handRight, handLeft) == 3 && frame <= NUM_FLOWERS - 30)
				frame += 30;
			else if(getIntensity(handRight, handLeft) == 2 && frame <= NUM_FLOWERS - 20)
				frame += 20;
			else if( getIntensity(handRight, handLeft) == 1 && frame <= NUM_FLOWERS - 10)
				frame += 10;
			else if( getIntensity(handRight, handLeft) == -1 && frame >= 10)
				frame -= 10;
			else if( getIntensity(handRight, handLeft) == -2 && frame >= 20)
				frame -= 20;

			if(frame == NUM_FLOWERS)
				fullBloom = true;
			else if ( frame > 0 && fullBloom){
				if(frame == 0)
					fullBloom = false;
			}
		}
	}
	
	//calculate the y difference between current location and the last location
	public float diffVel(float oldVel, float lastY,  float curY){
		
			float diffY = curY - lastY;
			
			if (diffY != 0){

				return (float) ((oldVel*0.8) + (diffY*0.2));
			} else {
			 return  oldVel;
			}
	}
	
	//change the state goingUp to determine if Hand Left is moving up
	public void diffHL(PVector handLeft){
		
		if (handLeft != null){
			HLVelocity = diffVel(HLVelocity, HLprevY, handLeft.y);
			
			if (HLVelocity < 0.004){
				HLgoingUp = false;
			} else{	
				HLgoingUp = true;
			}
			HLprevY =  handLeft.y;
		}

	}
	
	//change the state goingUp to determine if Hand Right is moving up
	public void diffHR(PVector handRight){
		
		if (handRight != null){
			HRVelocity = diffVel(HRVelocity, HRprevY, handRight.y);
			
			if (HRVelocity < 0.004){
				HRgoingUp = false;
			} else{	
				HRgoingUp = true;
			}
			HRprevY =  handRight.y;
		}

	}
	
	// return the average intensity of right and left hand
	public int getIntensity(PVector right, PVector left){
		if(Math.min(getIntensityHR(right), getIntensityHL(left)) == -1)
			return (getIntensityHR(right) + getIntensityHL(left))/2 - 1;
		
		return (getIntensityHR(right) + getIntensityHL(left))/2;
	}
	
	/* TAKES IN COORDINATES OF HAND RIGHT AND RETURNS INTEGER REPRESENTING INTENSITY OF CHANGES IN Y-AXIS
	 * No change --> Return -1
	 * Minimal change --> Return 1
	 * Slight change --> Return 2
	 * Change --> Return 3
	 * More Change --> Return 4
	 * Big change --> Return 5
	 */
	// add flowers to represent head
	public int getIntensityHR(PVector p1){
		
		int intensity = -1;
		
		diffHR(p1);
		
		//HRgoingUp is the previous value
		if (p1!=null){	
			
			if (HRgoingUp){
			
				if (HRVelocity == 0)	{
					return intensity;
				}
				else if (HRVelocity < 0.01){
					 intensity = 1;
				}
				else if (HRVelocity < 0.02){
					 intensity = 2;
				}
				else if (HRVelocity < 0.03 ){
					 intensity = 3;
				}
				else if (HRVelocity <  0.06 ){
					 intensity = 4;
				}
				else if (HRVelocity > 0.06) {
					 intensity = 5;
				}
				
				}
			
			}
		
		return intensity;
	}
	
	/* TAKES IN COORDINATES OF HAND LEFT AND RETURNS INTEGER REPRESENTING INTENSITY OF CHANGES IN Y-AXIS
	 * No change --> Return -1
	 * Minimal change --> Return 1
	 * Slight change --> Return 2
	 * Change --> Return 3
	 * More Change --> Return 4
	 * Big change --> Return 5
	 */
	public int getIntensityHL(PVector p1){
		
		int intensity = -1;
		
		if (p1!=null){	

			diffHL(p1);
			
			if (HLgoingUp){
			
				if (HLVelocity == 0)	{
					return intensity;
				}
				else if (HLVelocity < 0.01){
					 intensity = 1;
				}
				else if (HLVelocity < 0.02){
					 intensity = 2;
				}
				else if (HLVelocity < 0.03 ){
					 intensity = 3;
				}
				else if (HLVelocity <  0.06 ){
					 intensity = 4;
				}
				else if (HLVelocity > 0.06) {
					 intensity = 5;
				}
				
				}
			
			}
		
		
		return intensity;
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

	/* FOLLOWING DRAW METHODS DRAW FLOWERS SO THAT EACH BODY PART IS COMPLETELY FILLED
	 * drawHead
	 * drawLimbs
	 * drawNeck
	 * drawTorso
	 */
	
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
	
	public void drawLimbs(PVector start, PVector end){
		if(start == null || end == null)	
			return;

		float[][] position = points(start, end);

		for(int i = 0; i < position.length; i++){
			// if i is even
			if( i%2 == 0 ){
				image(flowers.get(i)[frame], position[i][0]+.02f, position[i][1]-.02f, .18f, .18f);
				image(flowers.get(flowers.size()-i-1)[frame], position[i][0]+.02f, position[i][1], .11f, .11f);///
			}
		}
	}
	
	public void drawNeck(PVector head, PVector spine){
		if(head == null || spine == null)
		return;
		
		float[][] position = points(head, spine);

		for(int i = 0; i < position.length; i++){
			image(flowers.get(flowers.size()-i-1)[frame], position[i][0]+.01f, position[i][1]-.02f, .08f, .08f);
			image(flowers.get(i)[frame], position[i][0]-.01f, position[i][1], .08f, .08f);
		}
	}
	
	public void drawTorso(PVector spine, PVector spineBase, PVector shoulderLeft, PVector shoulderRight){
		if(spine == null || spineBase == null || shoulderLeft == null || shoulderRight == null)
			return;
		float[][] position = points(spine, spineBase);
		for(int i = 0; i < position.length; i++){
			if( i%2 == 0 ){
				image(flowers.get(i)[frame], position[i][0]+.02f, position[i][1]-.02f, .18f, .18f);
				image(flowers.get(flowers.size()-i-1)[frame], position[i][0]-.02f, position[i][1], .18f, .18f);
			}
			else{
				image(flowers.get(i)[frame], position[i][0]-.02f, position[i][1], .18f, .18f);
				image(flowers.get(flowers.size()-i-1)[frame], position[i][0]+.02f, position[i][1]-.02f, .18f, .18f);
			}
		}
		
		for(int i = 0; i < position.length; i++){
			image(flowers.get(i+10)[frame], position[i][0]+0.1f, position[i][1], .18f, .18f);
			image(flowers.get(flowers.size()-i-1)[frame], position[i][0]+0.07f, position[i][1], .06f, .06f);
			image(flowers.get(i+20)[frame], position[i][0]-0.1f, position[i][1], .18f, .18f);
			image(flowers.get(flowers.size()-i-1)[frame], position[i][0]-0.05f, position[i][1], .06f, .06f);
		}
	}
	
	/* MAIN METHOD */
	public static void main(String[] args) {
		PApplet.main(KinectRenderDemo.class.getName());
	}
	
}
