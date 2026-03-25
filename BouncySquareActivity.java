package com.example.laborator2;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;

public class BouncySquareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fullscreen
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        GLSurfaceView view = new GLSurfaceView(this);

        // IMPORTANT: OpenGL ES 1.0
        view.setRenderer(new SquareRenderer());

        setContentView(view);
    }
}