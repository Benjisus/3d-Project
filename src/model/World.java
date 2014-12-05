package model;

import java.util.ArrayList;

import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.gl2.GLUT;

public class World
{
	public static final double leftWall = 10;
	public static final double rightWall = -10;
	public static final double farWall = 20;
	public static final double nearWall = 0;
	public static final double floor = 0;
	public static final double ceiling = 20;
	public static final double ballRadius = .5;
	public static final Vector3d gravity = new Vector3d(0,-.002,0);
	
	private ArrayList<Ball> balls;
	
	public World()
	{
		balls = new ArrayList<Ball>();
	}
	
	public void update()
	{
		for(int count = 0; count < balls.size(); count++)
		{
			balls.get(count).update();
		}
		
		applyGravity();
		bounce();
		//checkCollisions();
	}
	
	public void draw(GLAutoDrawable gld, GLUT glut)
	{
		for(int count = 0; count < balls.size(); count++)
		{
			balls.get(count).draw(gld, glut);
		}
	}
	
	private void checkCollisions()
	{
		for(int count = 0; count < balls.size(); count++)
		{
			Ball current = balls.get(count);
			if(!current.isActive)
				continue;
			
			for(int num = 0; num < balls.size(); num++)
			{
				Ball next = balls.get(num);
				if(!next.isActive)
					continue;
				
				double xDist = Math.abs(current.position.x - next.position.x);
				double yDist = Math.abs(current.position.y - next.position.y);
				double zDist = Math.abs(current.position.z - next.position.z);
				double sqrDist = (xDist*xDist) + (yDist*yDist) + (zDist*zDist);
				double sqrRange = (current.radius*current.radius) + (next.radius*next.radius);
				
				if(sqrDist <= sqrRange) //collision between these two balls.
				{
					System.out.println("Collision");
					current.isActive = false;
					next.isActive = false;
				}
			}
		}
	}
	
	/**
	 * Bounces the balls off of the walls
	 */
	private void bounce()
	{
		for(int count = 0; count < balls.size(); count++)
		{
			Ball current = balls.get(count);
			
			//left wall
			if(current.position.x + current.radius > leftWall)
			{
				current.position.x = leftWall - current.radius; //fix collision
				current.velocity.x *= -.9; //bounce
			}
			else if(current.position.x - current.radius < rightWall)//right wall
			{
				current.position.x = rightWall + current.radius; //fix collision
				current.velocity.x *= -.9; //bounce
			}
			
			//far wall
			if(current.position.z + current.radius > farWall)
			{
				current.position.z = farWall - current.radius; //fix collision
				current.velocity.z *= -.9; //bounce
			}
			else if(current.position.z - current.radius < nearWall && current.velocity.z < 0)//near wall
			{
				current.position.z = nearWall + current.radius; //fix collision
				current.velocity.z *= -.9; //bounce
			}
			
			//floor
			if(current.position.y - current.radius < floor)
			{
				current.position.y = floor + current.radius; //fix collision
				current.velocity.y *= -.9; //bounce
			}
			else if(current.position.y + current.radius > ceiling)//ceiling
			{
				current.position.y = ceiling - current.radius;//fix collision
				current.velocity.y *= -.9; //bounce
			}
		}
	}
	
	/**
	 * Accelerates all of the balls downwards. 
	 */
	private void applyGravity()
	{
		for(int count = 0; count < balls.size(); count++)
		{
			Ball current = balls.get(count);
			
			current.velocity.add(gravity);
		}
	}
	
	public synchronized void add(Ball ball)
	{
		balls.add(ball);
	}
	
	public synchronized void remove(Entity ball)
	{
		balls.remove(ball);
	}
}
