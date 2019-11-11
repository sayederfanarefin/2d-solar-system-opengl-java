/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solar_system;



import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;
import static java.lang.Thread.sleep;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;
import static java.lang.Thread.sleep;
import javax.media.opengl.GL;
import static javax.media.opengl.GL.GL_LINE_LOOP;
import static javax.media.opengl.GLProfile.GL2ES1;

/**
 *
 * @author erfan
 */
public class main implements GLEventListener{

    /**
     * @param args the command main arguments
     */
    
    
    static GLProfile profile=GLProfile.get(GLProfile.GL2);
    static GLCapabilities capabilities = new GLCapabilities(profile);
    static GLCanvas glcanvas = new GLCanvas(capabilities);
    
    static float orbits[] = new float[8];
    //static float moon_orbits[] = new float[8];
    static float angles[] = new float[8];
    static float moon_angle[] = {0,0,0,0,0,0,0,0};
    static float moon_speed[] = {0.06f,0.08f,0.15f,0.05f,0.03f,0.07f,0.04f,0.02f};
    static float orbiting_speed[] = {0.015f,0.006f,0.008f,0.007f,0.004f,0.005f,0.003f,0.002f};
    static float planet_radius[] = {0.012f,0.019f,0.02f,0.015f,0.04f,0.03f,0.025f,0.023f};
    static boolean rings[] = {false,false,false,false,true,true,true,true};
    static boolean moon[] = {false,false,true,true,true,true,true,true};
    
    /*colors:
    mercury: dark grey
    venus: pale yellow
    earth: blue
    mars: raddis brown
    jupitar: orange
    saturn: palel gold
    uranus: pale-greenis blue
    neptune: pale-blue
    
    */ 
    static double planet_rgb_r[] = {.8,.8,0,1,.7,.85,0,0};
    static double planet_rgb_g[] = {.8,.8,0,.1,.5,.4,.9,.8};
    static double planet_rgb_b[] = {.8,0,1,.1,0,.06,0,.5};
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        main l=new main();
        
        glcanvas.setSize(800, 800);
        
        final JFrame frame = new JFrame ("Solar System");
       frame.getContentPane().add(glcanvas);
       frame.setSize(frame.getContentPane().getPreferredSize());
       frame.setVisible(true);   
       FPSAnimator animator = new FPSAnimator(glcanvas, 60);
        //animator.add(glcanvas);
        animator.start();
       glcanvas.addGLEventListener(l);
    }

    public main() {
        
    }

    @Override
    public void init(GLAutoDrawable glad) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        glad.getGL().setSwapInterval(1);
    }

    @Override
    public void dispose(GLAutoDrawable glad) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void display(GLAutoDrawable xx) {
        draw_it(xx);
        angle_update();
       
    }
    public static void angle_update(){
        for(int ii =0; ii <angles.length; ii++){
            angles[ii] = angles[ii] + orbiting_speed[ii];
            moon_angle[ii]= moon_angle[ii] + moon_speed[ii];
        }
        
    }
    public static void draw_it(GLAutoDrawable glad){
        final GL2 gl = glad.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);    
           
        //now we will draw the 8 planetery orbits (sorry pluto :v)
        //mercury,venus,earth,mars,jupitar,saturn,uranus,neptun
        float orbit_radius = .15f;
        for(int i=0;i<8;i++){
            draw_orbit(gl, orbit_radius);
            orbits[i] = orbit_radius;
            orbit_radius=orbit_radius+0.1f;
        }
        //now the sun
        draw_sun(gl,.08f);
        
        for(int j =0; j <8;j++){
            draw_planets(j,gl, planet_radius[j], 
                    planet_rgb_r[j],planet_rgb_g[j],planet_rgb_b[j], 
                    ((float)(orbits[j]*Math.cos(angles[j]))),
                    ((float)(orbits[j]*Math.sin(angles[j]))), 
                    rings[j],
                    moon[j]);
        }
        
    }
    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void draw_orbit(GL2 gl, float r){
        gl.glBegin (GL2.GL_LINE_LOOP );
        float delta_theta = 0.1f;
          
        for( float angle = 0; angle < 2*Math.PI; angle += delta_theta ){
            float x = (float) ((float) r*Math.cos(angle));
            float y = (float) ((float) r*Math.sin(angle));
            gl.glColor3d(1, 1, 1);
            gl.glVertex2f( x, y);
        }
        gl.glEnd();
    }
    
     public static void draw_sun(GL2 gl, float r){
        gl.glBegin (GL2.GL_POLYGON );
        float delta_theta = 0.1f;
          
        for( float angle = 0; angle < 2*Math.PI; angle += delta_theta ){
            float x = (float) ((float) r*Math.cos(angle));
            float y = (float) ((float) r*Math.sin(angle));
            gl.glColor3d(1, 1, 0);
            gl.glVertex2f( x, y);
        }
        gl.glEnd();
    }
     
     public static void draw_planets(int planet_number, GL2 gl, float r, double rr, double g, double b, float xx, float yy, boolean ring, boolean moon){
        gl.glBegin (GL2.GL_POLYGON );
        float delta_theta = 0.01f;
        for( float angle = 0; angle < 2*Math.PI; angle += delta_theta ){
            float x = xx+(float) ((float) r*Math.cos(angle));
            float y = yy+(float) ((float) r*Math.sin(angle));
            gl.glColor3d(rr, g, b);
            gl.glVertex2f( x, y);
        }
        gl.glEnd();
        if(ring){
            gl.glBegin (GL2.GL_LINE_LOOP );
        for( float angle = 0; angle < 2*Math.PI; angle += delta_theta ){
            float x = xx+(float) ((float) (r+0.01)*Math.cos(angle));
            float y = yy+(float) ((float) (r+0.01)*Math.sin(angle));
            //moon_orbits[planet_number] = (float)(r+0.1);
            gl.glColor3d(1,1,1);
            gl.glVertex2f( x, y);
        }
        if(planet_number==5){
            for( float angle = 0; angle < 2*Math.PI; angle += delta_theta ){
            float x = xx+(float) ((float) (r+0.02)*Math.cos(angle));
            float y = yy+(float) ((float) (r+0.02)*Math.sin(angle));
            //moon_orbits[planet_number] = (float)(r+0.2);
            gl.glColor3d(1,1,1);
            gl.glVertex2f( x, y);
            
        }
              for( float angle = 0; angle < 2*Math.PI; angle += delta_theta ){
            float x = xx+(float) ((float) (r+0.015)*Math.cos(angle));
            float y = yy+(float) ((float) (r+0.015)*Math.sin(angle));
            //moon_orbits[planet_number] = (float)(r+0.2);
            gl.glColor3d(1,1,1);
            gl.glVertex2f( x, y);
        }
                 for( float angle = 0; angle < 2*Math.PI; angle += delta_theta ){
            float x = xx+(float) ((float) (r+0.017)*Math.cos(angle));
            float y = yy+(float) ((float) (r+0.017)*Math.sin(angle));
            //moon_orbits[planet_number] = (float)(r+0.2);
            gl.glColor3d(1,1,1);
            gl.glVertex2f( x, y);
        }
        }
        gl.glEnd();
        }
        
        
        if(moon){
          gl.glBegin (GL2.GL_POLYGON );
        
        for( float angle = 0; angle < 2*Math.PI; angle += delta_theta ){
            float x = xx+(float)((r+0.01)*Math.cos(moon_angle[planet_number]))+(float) ((float) 0.005f*Math.cos(angle));
            float y = yy+(float)((r+0.01)*Math.sin(moon_angle[planet_number]))+(float) ((float) 0.005f*Math.sin(angle));
            gl.glColor3d(1, 1, 0.2);
            gl.glVertex2f( x, y);
        }
        gl.glEnd();
        }
    }
}
