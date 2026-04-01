import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CubeRenderer implements GLSurfaceView.Renderer {

    private Cube  mCube;
    private float mTransY = 0.0f;
    private float mAngle  = 0.0f;

    // ===== CONSTRUCTOR =====
    public CubeRenderer() {
        mCube = new Cube();
    }

    // ===== onDrawFrame =====
    @Override
    public void onDrawFrame(GL10 gl) {

        // Curățăm ecranul (culoare fundal + depth buffer)
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        // Resetăm matricea
        gl.glLoadIdentity();

        // Translație: cubul se mișcă sus-jos și e depărtat pe Z
        gl.glTranslatef(0.0f, (float) Math.sin(mTransY), -7.0f);

        // Rotații (aplicate de la ultima la prima):
        // 1. întâi rotație X, apoi rotație Y, apoi translație
        gl.glRotatef(mAngle, 0.0f, 1.0f, 0.0f);  // rotație Y
        gl.glRotatef(mAngle, 1.0f, 0.0f, 0.0f);  // rotație X

        // Desenăm cubul
        mCube.draw(gl);

        // Incrementăm valorile de animație
        mTransY += 0.05f;
        mAngle  += 0.4f;
    }

    // ===== onSurfaceChanged =====
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        // Viewport
        gl.glViewport(0, 0, width, height);

        // Setăm matricea de proiecție (Projection)
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        // Calculăm frustum-ul
        float fieldOfView  = 30.0f / 57.3f;                        // 30 grade → radiani
        float aspectRatio  = (float) width / (float) height;
        float zNear        = 0.1f;
        float zFar         = 1000.0f;
        float size         = zNear * (float)(Math.tan((double)(fieldOfView / 2.0f)));

        gl.glFrustumf(
                -size, size,
                -size / aspectRatio, size / aspectRatio,
                zNear, zFar
        );

        // Revenim la matricea ModelView
        gl.glMatrixMode(GL10.GL_MODELVIEW);
    }

    // ===== onSurfaceCreated =====
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        // Culoare fundal: negru
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // Activăm depth testing (obiectele din față acoperă cele din spate)
        gl.glEnable(GL10.GL_DEPTH_TEST);

        // Dezactivăm dithering (opțional)
        gl.glDisable(GL10.GL_DITHER);

        // Hint pentru perspectivă
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

        // ---- ASSIGNMENT: Backface culling ----
        // Decomentează linia de mai jos pentru a activa culling:
        // gl.glEnable(GL10.GL_CULL_FACE);
        // gl.glCullFace(GL10.GL_FRONT); // sau GL_BACK (implicit)
    }
}