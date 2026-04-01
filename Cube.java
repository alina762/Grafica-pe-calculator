import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class Cube {

    private FloatBuffer mFVertexBuffer;
    private ByteBuffer  mColorBuffer;
    private ByteBuffer  mTFan1;
    private ByteBuffer  mTFan2;

    public Cube() {

        // ===== VERTICES =====
        float vertices[] = {
                -1.0f,  1.0f,  1.0f,   // 0 - față stânga sus
                1.0f,  1.0f,  1.0f,   // 1 - față dreapta sus
                1.0f, -1.0f,  1.0f,   // 2 - față dreapta jos
                -1.0f, -1.0f,  1.0f,   // 3 - față stânga jos

                -1.0f,  1.0f, -1.0f,   // 4 - spate stânga sus
                1.0f,  1.0f, -1.0f,   // 5 - spate dreapta sus
                1.0f, -1.0f, -1.0f,   // 6 - spate dreapta jos
                -1.0f, -1.0f, -1.0f    // 7 - spate stânga jos
        };

        // ===== CULORI (RGBA) =====
        byte maxColor = (byte) 255;
        byte colors[] = {
                maxColor, 0, 0, maxColor,   // vertex 0 - roșu
                maxColor, 0, 0, maxColor,   // vertex 1 - roșu
                maxColor, 0, 0, maxColor,   // vertex 2 - roșu
                maxColor, 0, 0, maxColor,   // vertex 3 - roșu

                0, 0, 0, maxColor,          // vertex 4 - negru
                0, 0, 0, maxColor,          // vertex 5 - negru
                0, 0, 0, maxColor,          // vertex 6 - negru
                0, 0, 0, maxColor           // vertex 7 - negru
        };

        // ===== TRIANGLE FAN 1 (față, dreapta, sus) =====
        byte tFan1[] = {
                1, 0, 3,
                1, 3, 2,
                1, 2, 6,
                1, 6, 5,
                1, 5, 4,
                1, 4, 0
        };

        // ===== TRIANGLE FAN 2 (spate, jos, stânga) =====
        byte tFan2[] = {
                7, 4, 5,
                7, 5, 6,
                7, 6, 2,
                7, 2, 3,
                7, 3, 0,
                7, 0, 4
        };

        // ===== CONVERSIE LA BUFFERE NATIVE =====

        // Vertices: float = 4 bytes
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mFVertexBuffer = vbb.asFloatBuffer();
        mFVertexBuffer.put(vertices);
        mFVertexBuffer.position(0);

        // Culori: byte = 1 byte
        mColorBuffer = ByteBuffer.allocateDirect(colors.length);
        mColorBuffer.put(colors);
        mColorBuffer.position(0);

        // Index arrays
        mTFan1 = ByteBuffer.allocateDirect(tFan1.length);
        mTFan1.put(tFan1);
        mTFan1.position(0);

        mTFan2 = ByteBuffer.allocateDirect(tFan2.length);
        mTFan2.put(tFan2);
        mTFan2.position(0);
    }

    public void draw(GL10 gl) {

        // Activăm vertex arrays și color arrays
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        // Setăm pointerii
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mFVertexBuffer);  // 3 componente: x, y, z
        gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, mColorBuffer); // 4 componente: r, g, b, a

        // Desenăm cele două triangle fans
        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 6 * 3, GL10.GL_UNSIGNED_BYTE, mTFan1);
        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 6 * 3, GL10.GL_UNSIGNED_BYTE, mTFan2);

        // Dezactivăm
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }
}