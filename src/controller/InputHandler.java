package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Ball;
import model.Vector3d;
import model.World;
import view.Camera;

public class InputHandler implements KeyListener
{
	private Camera camera;
	private World world;
	
	public InputHandler(Camera camera, World world)
	{
		this.camera = camera;
		this.world = world;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		switch(key)
		{
			case(KeyEvent.VK_W):
				camera.forward();
				break;
			case(KeyEvent.VK_A):
				camera.left();
				break;
			case(KeyEvent.VK_S):
				camera.back();
				break;
			case(KeyEvent.VK_D):
				camera.right();
				break;
			case(KeyEvent.VK_SPACE):
				world.add(new Ball(new Vector3d(camera.ex,camera.ey,camera.ez+World.ballRadius), new Vector3d(0,0,.1), World.ballRadius));
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		
	}
}
