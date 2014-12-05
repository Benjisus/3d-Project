package model;

public class Vector3d
{
	public double x, y, z;
	
	public Vector3d(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void add(Vector3d vector)
	{
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;
	}
	
	public static Vector3d scale(Vector3d vec, double scalar)
	{
		return new Vector3d(vec.x * scalar,vec.y * scalar,vec.y * scalar);
	}
}
