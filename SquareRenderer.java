package com.example.laborator2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;

import android.opengl.GLSurfaceView;

public class SquareRenderer implements GLSurfaceView.Renderer {

    private FloatBuffer vertexBuffer;
    private ShortBuffer indexBuffer;

    private float mTransY = 0.0f;
    private boolean up = true;

    // 🔹 vertices (MODIFICAT: -2.0f)
    float vertices[] = {
            -2.0f, -1.0f,
            1.0f, -1.0f,
            -1.0f,  1.0f,
            1.0f,  1.0f
    };

    short indices[] = {0, 1, 2, 1, 2, 3};

    public SquareRenderer() {

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        // 🔹 background color (modificat)
        gl.glClearColor(0.5f, 0.7f, 1.0f, 1.0f);

        // 🔹 dithering activ
        gl.glEnable(GL10.GL_DITHER);

        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();

        // mișcare sus-jos
        if (up) {
            mTransY += 0.3f;
        } else {
            mTransY -= 0.3f;
        }

        if (mTransY > 3) up = false;
        if (mTransY < -3) up = true;

        gl.glTranslatef(0.0f, mTransY, -5.0f);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);

        // 🔹 culoare (ALBASTRU - poți schimba)
        gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);

        // 🔹 tip desen (poți schimba pentru exercițiu)
        gl.glDrawElements(
                GL10.GL_TRIANGLES,
                indices.length,
                GL10.GL_UNSIGNED_SHORT,
                indexBuffer
        );

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        gl.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
}
