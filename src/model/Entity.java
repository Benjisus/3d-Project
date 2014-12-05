package model;

import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.gl2.GLUT;

public class Entity
{
	Vector3d position, velocity;
	boolean isActive;
	
	public Entity(Vector3d position, Vector3d velocity)
	{
		this.position = position;
		this.velocity = velocity;
		isActive = true;
	}
	
	/**
	 * Moves the position by the velocity.
	 */
	public void update()
	{
		if(isActive)
			position.add(velocity);
	}
	
	public void draw(GLAutoDrawable gld, GLUT glut)
	{
		
	}
	
	public void onCollide(Entity collider)
	{
		
	}
}
