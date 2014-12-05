package controller;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;
import javax.swing.JApplet;

import view.Camera;
import model.Ball;
import model.Vector3d;
import model.World;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;


public class Game extends JApplet implements GLEventListener
{
    int winWidth=800, winHeight=600;
    FPSAnimator animator;
    GLU glu;
    GLUT glut;
    
    Thread updateThread;
    Camera camera;
    InputHandler inputHandler;
    Ball ball;
    World world;
    
    public Game()
    {
    	updateThread = new Thread(new Runnable()
    	{
			@Override
			public void run() 
			{
				while(true)
				{
					update();
					
					try 
					{
						Thread.sleep(16);
					}
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
			}
    	});
    }
    
    /**
     * Main update method. 
     * @param delta - time in seconds since last frame was called. 
     */
    public synchronized void update()
    {
    	world.update();
    }
    
    public void display (GLAutoDrawable gld)
    {
        final GL2 gl = gld.getGL().getGL2();

        // Clear the buffer, need to do both the color and the depth buffers
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Load the identity into the Modelview matrix
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();
        // Setup the camera.  The camera is located at the origin, looking along the positive z-axis, with y-up
        //glu.gluLookAt(0,0,0, 0,0,1, 0,1,0);
        camera.setLookAt(glu);
        
        // set the position and diffuse/ambient terms of the light
        float [] pos = {1, 1, -1, 0};
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_POSITION, pos, 0);
        float [] diffuse = {0.7f, 0.7f, 0.7f, 1.0f};
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_DIFFUSE, diffuse, 0);
        float [] ambient = {0.2f, 0.2f, 0.2f, 1.0f};
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_AMBIENT, ambient, 0);
        float [] specular = {1.0f, 1.0f, 1.0f, 1.0f};
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_SPECULAR, specular, 0);
        
        // set the color
        float [] color1 = {1.0f, 0.0f, 0.0f, 1.0f};
        float [] color3 = {1.0f, 1.0f, 1.0f, 1.0f};
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_AMBIENT_AND_DIFFUSE, color1, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_SPECULAR, color3, 0);
        gl.glMaterialf(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_SHININESS, 64.0f);
        
        //left wall
        gl.glColor3d(1.0, 1.0, 1.0);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3d(World.leftWall, World.floor, World.nearWall); //bottom left corner
        gl.glVertex3d(World.leftWall, World.ceiling, World.nearWall); //top left corner
        gl.glVertex3d(World.leftWall, World.ceiling, World.farWall); //top right corner
        gl.glVertex3d(World.leftWall, World.floor, World.farWall); //bottom right corner
        gl.glEnd();
        
        world.draw(gld, glut);
        gl.glEnd();
    }
    
    public void displayChanged (GLAutoDrawable gld, boolean arg1, boolean arg2)
    {
    }
    
    public void reshape (GLAutoDrawable gld, int x, int y, int width, int height)
    {
        GL gl = gld.getGL();
        winWidth = width;
        winHeight = height;
        gl.glViewport(0,0, width, height);
    }

    public void init (GLAutoDrawable gld)
    {
        glu = new GLU();
        glut = new GLUT();
        final GL2 gl = gld.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        // setup the projection matrix
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60.0, (double)winWidth/(double)winHeight, 0.0001, 1000000000);
        
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glEnable(GLLightingFunc.GL_NORMALIZE); // automatically normalizes stuff
        gl.glEnable(GL.GL_CULL_FACE); // cull back faces
        gl.glEnable(GL.GL_DEPTH_TEST); // turn on z-buffer
        gl.glEnable(GLLightingFunc.GL_LIGHTING); // turn on lighting
        gl.glEnable(GLLightingFunc.GL_LIGHT0); // turn on light
        gl.glShadeModel(GLLightingFunc.GL_SMOOTH); // smooth normals
    }
    
    public void init() {
        setLayout(new FlowLayout());
        // create a gl drawing canvas
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);
        canvas.setPreferredSize(new Dimension(winWidth, winHeight));
        
        // add gl event listener
        canvas.addGLEventListener(this);
        add(canvas);
        setSize(winWidth, winHeight);
        // add the canvas to the frame
        animator = new FPSAnimator(canvas, 30);
        
        world = new World();
        camera = new Camera(0,0,0, 0,0,1, 0,1,0);
        inputHandler = new InputHandler(camera, world);
        canvas.addKeyListener(inputHandler);
        canvas.setFocusable(true);
        
//        for(int count = 0; count < 10; count++)
//        {
//        	world.add(new Ball(new Vector3d(0,0,10), new Vector3d(Math.random()/2,Math.random()/2,-Math.random()/2), World.ballRadius));
//        }
        world.add(new Ball(new Vector3d(0,10,1), new Vector3d(0,0,0), World.ballRadius));
    }
    
    public void start() {
        animator.start();
        updateThread.start();
    } 
    
    public void stop() {
        animator.stop();
        updateThread.interrupt();
    }

    /* (non-Javadoc)
     * @see javax.media.opengl.GLEventListener#dispose(javax.media.opengl.GLAutoDrawable)
     */
    public void dispose (GLAutoDrawable arg0)
    {
        // TODO Auto-generated method stub
        
    }
}