package GraphicsObjects;


public class Vector4f {

    public float x = 0;
    public float y = 0;
    public float z = 0;
    public float a = 0;

    // If we don't pass the coordinates this will be called
    public Vector4f() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        a = 0.0f;
    }

    //initializing constructor
    public Vector4f(float x, float y, float z, float a) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.a = a;
    }

    //implement Vector plus a Vector
    // A vector plus a vector means make the origin vector change
    // its direction or magnitude or both. In the direction of
    // the additional vector.
    // The new vector is pointing from the start of the original
    // vector and ends at the end of the Additional vector.
    public Vector4f PlusVector(Vector4f Additonal) {
        return new Vector4f(
                this.x + Additonal.x,
                this.y + Additonal.y,
                this.z + Additonal.z,
                this.a + Additonal.a
        );

    }

    //implement Vector minus a Vector
    //This is similar to the plus vector method, the only different is
    //the direction. At this time,this direction is the opposite of the
    //passed in vector.
    public Vector4f MinusVector(Vector4f Minus) {
        return new Vector4f(
                this.x - Minus.x,
                this.y - Minus.y,
                this.z - Minus.z,
                this.a - Minus.a
        );

    }

    //implement Vector plus a Point
    //This method gives us a new point that moves the original point in
    //the direction of the Additional vector. And the magnitude is the
    //length of the additional vector.
    public Point4f PlusPoint(Point4f Additonal) {
        return new Point4f(
                this.x + Additonal.x,
                this.y + Additonal.y,
                this.z + Additonal.z,
                this.a + Additonal.a
        );
    }
    //Do not implement Vector minus a Point as it is undefined

    //Implement a Vector * Scalar
    // This method only change the magnitude of the vector, scale it by a
    // Scalar. It is actually multiply the magnitude by n .
    public Vector4f byScalar(float scale) {
        return new Vector4f(
                this.x * scale,
                this.y * scale,
                this.z * scale,
                this.a * scale
        );
    }

    //implement returning the negative of a  Vector
    //This method will give us a new vector that is pointing the
    //opposite direction of the current and not changing its magnitude.
    public Vector4f NegateVector() {
        return new Vector4f(
                -this.x,
                -this.y,
                -this.z,
                -this.a
        );
    }

    //implement getting the length of a Vector
    //This method gives the magnitude of the current vector
    //by using the length formula
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z + a * a);
    }

    //implement getting the Normal  of a Vector
    // Remember that since you just have one vector, this method is returning a normalized vector with a one unity length
    // This method returns the normalized vector of the current vector
    // which has unit magnitude of 1.
    // I use the 1.0f / length() as the norm, which is like a scale that can change the x,y,z by this scale
    public Vector4f Normal() {
        float l = this.length();
        return new Vector4f(
                this.x / l,
                this.y / l,
                this.z / l,
                this.a / l
        );
    }

    //implement getting the dot product of Vector.Vector
    //This method gives the dot product of two vector.
    //the result is a number(scalar)
    //It is actually projecting Vector A to Vector B and multiply it by the
    //magnitude of B
    public float dot(Vector4f v) {
        return (this.x * v.x + this.y * v.y + this.z * v.z + this.a * v.a);
    }

    // Implemented this for you to avoid confusion
    // as we will not normally  be using 4 float vector
    //The cross product gives us a vector.
    //The vector is actually perpendicular to the Vector A and vector B
    //Here A is "this" vector and B is "v"
    public Vector4f cross(Vector4f v) {
        float u0 = (this.y * v.z - z * v.y);
        float u1 = (z * v.x - x * v.z);
        float u2 = (x * v.y - y * v.x);
        float u3 = 0; //ignoring this for now
        return new Vector4f(u0, u1, u2, u3);
    }

}
	 
	   

/*

										MMMM                                        
										MMMMMM                                      
 										MM MMMM                                    
 										MMI  MMMM                                  
 										MMM    MMMM                                
 										MMM      MMMM                              
  										MM        MMMMM                           
  										MMM         MMMMM                         
  										MMM           OMMMM                       
   										MM             .MMMM                     
MMMMMMMMMMMMMMM                        MMM              .MMMM                   
MM   IMMMMMMMMMMMMMMMMMMMMMMMM         MMM                 MMMM                 
MM                  ~MMMMMMMMMMMMMMMMMMMMM                   MMMM               
MM                                  OMMMMM                     MMMMM            
MM                                                               MMMMM          
MM                                                                 MMMMM        
MM                                                                   ~MMMM      
MM                                                                     =MMMM    
MM                                                                        MMMM  
MM                                4 D                                      MMMMMM 
MM                                                                     MMMMMMMM 
MM                                                                  :MMMMMMMM   
MM                                                                MMMMMMMMM     
MM                                                              MMMMMMMMM       
MM                             ,MMMMMMMMMM                    MMMMMMMMM         
MM              IMMMMMMMMMMMMMMMMMMMMMMMMM                  MMMMMMMM            
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM               ZMMMMMMMM              
MMMMMMMMMMMMMMMMMMMMMMMMMMMMM          MM$             MMMMMMMMM                
MMMMMMMMMMMMMM                       MMM            MMMMMMMMM                  
  									MMM          MMMMMMMM                     
  									MM~       IMMMMMMMM                       
  									MM      DMMMMMMMM                         
 								MMM    MMMMMMMMM                           
 								MMD  MMMMMMMM                              
								MMM MMMMMMMM                                
								MMMMMMMMMM                                  
								MMMMMMMM                                    
  								MMMM                                      
  								MM                                        
                             GlassGiant.com */