package base.GraphicsObjects;

import java.nio.FloatBuffer;

/*
 * Created by Owen Grogan on 06/05/2010.
 *  Copyright 2010 School of Computer Science and Informatics. All rights reserved.
 *  
 *  Update for Java by Abraham Campbell on 14/10/2015
 Updated again by Abraham Campbell on 14/10/2019 with Huang Yuyang fix to stop Arc Ball issues ( well spotted )
 */
public class quat {
 

	private 	float real =1f;
	private  Vector4f imag = new Vector4f( 0.0f,0.0f,0.0f,0.0f);
	
	public  quat()
	{
		
	}
	 
	public  quat(float[] matrix)
	{	
	real = (float) (Math.sqrt( 1.0+matrix[0]+matrix[5]+matrix[10])   /2.0);
	float w4 = (float) (4.0*real);
    imag= new Vector4f((matrix[6] - matrix[9])/w4,
				 (matrix[8] - matrix[2])/w4,
				 (matrix[1] - matrix[4])/w4,0.0f);
	}
	
	public  quat(float w,float x, float y, float z)
	{
    imag.x = x;
    imag.y = y;
    imag.z = z;
    real = w;
	}
	
	public  quat(Vector4f v, float w)
	{
	imag = v;
	real = w;
	}
	
	public  quat(float w, Vector4f v){
	imag = v;
	real = w;
	}
	
	public float getReal() 
	{
		return real;
	}
	 
	public Vector4f getImag() 
	{
		return imag;
	}
	  
	public quat  mult(quat q)
	{ 
		float T =   (real*q.getReal()) - (imag.dot(q.getImag()));
		//System.out.println("T =" + T);
		Vector4f P = q.getImag().byScalar(real); 
		Vector4f Q = imag.byScalar(q.getReal());
		Vector4f W = imag.cross(q.getImag());
		Vector4f Combine = P.PlusVector(Q);
		Combine = Combine.PlusVector(W);
		
    return new quat(T,Combine); 
    		 
}
	public quat conj()
{	
    return new quat(real,-imag.x,-imag.y,-imag.z);
}

float sumSquare(){
    return (real*real+imag.x*imag.x+
            imag.y*imag.y+imag.z*imag.z);
}

public  quat inverse(){
	float T= real/sumSquare();
	Vector4f W = conj().getImag().byScalar(1/sumSquare()); 
	return new quat(T,W);
}

void  toMatrix(FloatBuffer currentMatrix){
	float treal = real;
	 
	float[] ToConvert = new float[16];
	
	 //identity matrix 
	ToConvert[0] = 1.0f;
	ToConvert[1] = 0.0f;
	ToConvert[2] = 0.0f;
	ToConvert[3] = 0.0f;
	ToConvert[4] = 0.0f;
	ToConvert[5] = 1.0f;
	ToConvert[6] = 0.0f;
	ToConvert[7] = 0.0f;
	ToConvert[8] = 0.0f;
	ToConvert[9] = 0.0f;
	ToConvert[10] = 1.0f;;
	ToConvert[11] = 0.0f;
	ToConvert[12] = 0.0f;
	ToConvert[13] = 0.0f;
	ToConvert[14] = 0.0f;
	ToConvert[15] = 1.0f;;
	 
	
	 
	
//	currentMatrix.
	//System.out.println("TEst = "+  (1.0f-((2.0f*imag.y*imag.y)+(2.0f*imag.z*imag.z))));
	ToConvert[0] = 1.0f-((2.0f*imag.y*imag.y)+(2.0f*imag.z*imag.z));
	ToConvert[4] = 2.0f*((imag.x*imag.y)-(imag.z*treal));
	ToConvert[8] = 2.0f*((imag.x*imag.z)+(imag.y*treal));
					  
	ToConvert[1] = 2.0f*((imag.x*imag.y)+(imag.z*treal));
	ToConvert[5] = 1.0f-((2.0f*imag.x*imag.x)+(2.0f*imag.z*imag.z));
	ToConvert[9] = 2.0f*((imag.y*imag.z)-(imag.x*treal));
					  
	ToConvert[2] = 2.0f*((imag.x*imag.z)-(imag.y*treal));
	ToConvert[6] = 2.0f*((imag.y*imag.z)+(imag.x*treal));
	ToConvert[10] = 1.0f-((2.0f*imag.y*imag.y)+(2.0f*imag.x*imag.x));
	
	  /*
	System.out.println("Matrix " + ToConvert[0] +" "+ ToConvert[4] +" "+  ToConvert[8] +" "+  ToConvert[12] + "\n"
			 +"       " + ToConvert[1] +" "+ ToConvert[5] +" "+  ToConvert[9] +" "+  ToConvert[13] + "\n"
			 +"       " +  ToConvert[2] +" "+ ToConvert[6] +" "+  ToConvert[10] +" "+  ToConvert[14] + "\n"
			 +"       " +  ToConvert[3] +" "+ ToConvert[7] +" "+  ToConvert[11] +" "+  ToConvert[15] + "\n");
*/

	
	currentMatrix.put(
	ToConvert[0]).put
	(ToConvert[1]).put
	(ToConvert[2]).put
	(ToConvert[3]).put
	(ToConvert[4]).put
	(ToConvert[5]).put
	(ToConvert[6]).put
	(ToConvert[7]).put
	(ToConvert[8]).put
	(ToConvert[9]).put
	(ToConvert[10]).put
	(ToConvert[11]).put
	(ToConvert[12]).put
	(ToConvert[13]).put
	(ToConvert[14]).put  
	(ToConvert[15]).flip();
	 
	
}
	 

}
