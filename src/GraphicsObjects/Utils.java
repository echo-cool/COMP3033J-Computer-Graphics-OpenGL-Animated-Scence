package GraphicsObjects;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Utils {

	
	public static FloatBuffer ConvertForGL(float[] ToConvert)

	{
		FloatBuffer Any4 = BufferUtils.createFloatBuffer(4);
		Any4.put(ToConvert[0]).put(ToConvert[1]).put(ToConvert[2]).put(ToConvert[3]).flip();
		return Any4;
	}

	public static FloatBuffer initFloat() {
		// TODO Auto-generated method stub
		return null;
	}
}
