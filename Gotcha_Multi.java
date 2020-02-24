/*
Name: Mariah Harilall
Date: 5 May 2019
*/

package gotcha;

import processing.core.PApplet;
import processing.core.PFont;
import java.util.ArrayList;

public class Gotcha_Multi extends PApplet {
    // Timer
    int timer;
    
    // Game length in milliseconds
    int gameDuration = 10 * 1000;
    
    // Keep track of current score
    static int score = 0;

    // Canvas size
    final int canvasWidth  = 500;
    final int canvasHeight = 500;
    
    final int maxDisks = 4;


    // Declare y values for starting point
    float[] yValue = {200,300,100,400};

    // Create disks
    // With ArrayList:
    ArrayList<Disk> dList = new ArrayList<Disk>();

    public static void main(String[] args){
        PApplet.main("gotcha.Gotcha_Multi");
    }

    public void settings() {
        size(canvasWidth, canvasHeight);
        smooth();
    }

    // Setup runs one time at the beginning of your program
    public void setup() {        
        // Set time now
        timer = millis() + gameDuration;
        
        for (int i = 0; i < maxDisks; i++) {
            // With ArrayList
            dList.add(new Disk(random(0, 255), random(0, 255), random(0, 255),
                    random(0, 255), yValue[i], 2 + (2 * i)));

            System.out.println("\nNumber of Disks: " + dList.size() + "\n");
        }
    }
    
    // Draw is called in an infinite loop.
    // By default, it is called about 60 times per second.
    public void draw() {
        // Erase the background, if you don't, the previous shape will 
        // still be displayed
        eraseBackground();

        // Move the shape, i.e. calculate the next x and y position
        // where the shape will be drawn. Draw the shape, display point
        // value.
        for (int j = 0; j < maxDisks; j++) {
                dList.get(j).calcCoords();
                dList.get(j).drawShape();
                dList.get(j).displayPointValue();
        }
        
        // Display player's score
        //TODO: Display score
        textSize(25);
        fill(0,0,0);
        textAlign(CENTER);
        text("Score: " + score, 250, 20);
        
        if (millis() >= timer) {  // Game over
            // Clear the canvas
            background(0, 0, 255);
            
            // Output the final score
            System.out.printf("Your score is %d. \n", score);
         // Let the user click when finished reading score
                System.out.printf("Hope you enjoyed the game :) Click the screen to exit. \n");
                textSize(20);
             fill(255,255,255);
             textAlign(CENTER);
             text("Final score: "+ score, 250, 250);
                textSize(18);
             fill(255,255,255);
             textAlign(CENTER);
             text("Hope you enjoyed the game :) Click the screen to exit.", 250, 200);
      
            if (this.mousePressed) {        
              // Exit
              System.exit(0);
            }
        }
    }

    public void eraseBackground() {
        background(255);
    }
    
    // mousePressed is a PApplet method that you can override.
    // This method is designed to be called one time when the mouse is pressed
    public void mousePressed() {
      // Draw a circle wherever the mouse is
      int mouseWidth  = 20;
      int mouseHeight = 20;
      fill(0, 255, 0);
      ellipse(this.mouseX, this.mouseY, mouseWidth, mouseHeight);

      // Check whether the click occurred within range of a shape
      for (int i = 0; i < maxDisks; i++) {
            if ((this.mouseX >= (dList.get(i).x - dList.get(i).targetRange)) &&
               (this.mouseX <= (dList.get(i).x + dList.get(i).targetRange)) &&
               (this.mouseY <= (dList.get(i).y + dList.get(i).targetRange)) &&
               (this.mouseY >= (dList.get(i).y - dList.get(i).targetRange)) ){

//          //TODO: Player clicked on shape, increase score
                 score += dList.get(i).pointValue;
          }
        }
      }
    

    class Disk {
        // Shape size
        final int shapeWidth  = 80;
        final int shapeHeight = 50;

        // Shape value
        final static int defaultValue = 10;
        int pointValue = defaultValue;

        // Keep track of ball's x and y position
        float x = 300;
        float y = 250;

        // Horizontal speed
        float xSpeed = 2;

        // It's hard to click a precise position, to make it easier, 
        // require the click to be somewhere on or very near the shape
        int targetRange = Math.round((min(shapeWidth, shapeHeight)) / 2);

        float red;
        float green;
        float blue;

        Disk(float red, float green, float blue, float x, float y, 
             float xSpeed) {
            // TODO: Initialize instance variables as needed
                this.red   = red;
                this.green = green;
                this.blue  = blue;
                this.x = x;
                this.y = y;
                this.xSpeed = xSpeed;

            // TODO: Assign pointValue, possibly based on hit difficulty?
            pointValue = 10 * (int)xSpeed;
            System.out.println("Constructor pointValue = " + this.pointValue);
        }

        Disk() {
            this(0, 0, 255, 300, 250, 2);
        }

        public void calcCoords() {      
            // Compute the x position where the shape will be drawn
            this.x += this.xSpeed; 

            // If the x position is off the canvas, reverse direction of 
            // movement
            if (this.x > canvasWidth) {
                System.out.println("<===  Change direction, go left because x = " + this.x);
                this.xSpeed = -1 * this.xSpeed;
            }

            // If the x position is off the canvas, reverse direction of 
            // movement
            if (this.x < 0) {
                System.out.println("     ===> Change direction, go right because x = " + this.x + "\n");
                this.xSpeed = -1 * this.xSpeed;
            } 
        }

        public void drawShape() {
            // Select color, then draw the shape at computed x, y location
            //TODO: Add fill color
            fill(blue, green, red);
            ellipse(this.x, this.y, this.shapeWidth, this.shapeHeight);
        }

        public void displayPointValue() {
            // Draw the text at computed x, y location
            //TODO:
            textSize(20);
            fill(255, 255, 255);
            textAlign(CENTER);
            text(this.pointValue, this.x, this.y);
        }
    }
}
