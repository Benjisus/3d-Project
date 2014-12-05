package model;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.gl2.GLUT;

public class Ball extends Entity
{
	double radius;
	
	public Ball(Vector3d position, Vector3d velocity, double radius)
	{
		super(position, velocity);
		this.radius = radius;
	}
	
	@Override
	public void update() 
	{
		if(isActive)
		{
			super.update();
		}
	}
	
	@Override
	public void draw(GLAutoDrawable gld, GLUT glut) 
	{
		if(isActive)
		{
			super.draw(gld, glut);
			
			final GL2 gl = gld.getGL().getGL2();
			
			gl.glPushMatrix(); //remember current matrix
			gl.glTranslated(position.x, position.y, position.z);//translate to position of ball
			glut.glutSolidSphere(radius, 10, 10); //draw sphere
			gl.glPopMatrix(); //restore matrix
		}
	}
	
	@Override
	public void onCollide(Entity collider) 
	{
		if(isActive)
		{
			super.onCollide(collider);
		}
	}
}
