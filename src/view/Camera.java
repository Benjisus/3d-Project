package view;

import javax.media.opengl.glu.GLU;

public class Camera 
{
    public double ex, ey, ez, cx, cy, cz, ux, uy, uz;
    public double theta = 0.087266463;
        
    public Camera(double ex, double ey, double ez, double cx, double cy, double cz, double ux, double uy, double uz) {
	this.ex = ex;
	this.ey = ey;
	this.ez = ez;
	this.cx = cx;
	this.cy = cy;
	this.cz = cz;
	this.ux = ux;
	this.uy = uy; 
	this.uz = uz;
    }
        
    public void setLookAt(GLU glu) {
	glu.gluLookAt(ex, ey, ez, cx, cy, cz, ux, uy, uz);
    }
        
    public void forward() {
	double dx = cx - ex, dy = cy - ey, dz = cz - ez;
	double m = Math.sqrt(sqr(dx)+sqr(dy)+sqr(dz));
	dx /= m/2;
	dy /= m/2;
	dz /= m/2;
            
	cx += dx;
	cy += dy;
	cz += dz;
	ex += dx;
	ey += dy;
	ez += dz;
    }
        
    public void back() {
	double dx = cx - ex, dy = cy - ey, dz = cz - ez;
	double m = Math.sqrt(sqr(dx)+sqr(dy)+sqr(dz));
	dx /= m/2;
	dy /= m/2;
	dz /= m/2;
            
	cx -= dx;
	cy -= dy;
	cz -= dz;
	ex -= dx;
	ey -= dy;
	ez -= dz;
    }
        
    public void left() {
	double dx = cx - ex, dy = cy - ey, dz = cz - ez;
	double m = Math.sqrt(sqr(dx)+sqr(dy)+sqr(dz));
	dx /= m;
	dy /= m;
	dz /= m;
	double nx = Math.cos(theta)*dx + Math.sin(theta)*dz;
	double ny = dy;
	double nz = -Math.sin(theta)*dx + Math.cos(theta)*dz;
	cx = ex+nx;
	cy = ey+ny;
	cz = ez+nz;
    }

    public void right() {
	double dx = cx - ex, dy = cy - ey, dz = cz - ez;
	double m = Math.sqrt(sqr(dx)+sqr(dy)+sqr(dz));
	dx /= m;
	dy /= m;
	dz /= m;
	double nx = Math.cos(theta)*dx - Math.sin(theta)*dz;
	double ny = dy;
	double nz = Math.sin(theta)*dx + Math.cos(theta)*dz;
	cx = ex+nx;
	cy = ey+ny;
	cz = ez+nz;
    }
    
    private double sqr(double x)
    {
    	return x*x;
    }
}
