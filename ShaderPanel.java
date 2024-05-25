import javax.swing.*;
import java.awt.*;

public class ShaderPanel extends JPanel {
    int frame = 0, width = 576, height = 324, max_downscale = 10, downscale = 1;
    
    public ShaderPanel() {
        super();
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        frame++;
        System.out.print("  Frame: " + frame + "\033[H");
        Raytracing.initializeScene(frame);
        for (int y = 0; y < height; y += downscale)
        for (int x = 0; x < width; x += downscale) {
            g.setColor(shader(x, y, frame));
            g.fillRect(x, height - y - downscale, downscale, downscale);
        }
        this.repaint(); // ignore this ;)
    }
    
    public Color shader(int x, int y, int frame) {
        float3 fragColor = new float3();
        
        float beta = frame/150.0f;
        float s = (float)Math.sin(beta);
        float c = (float)Math.cos(beta);
        
        Camera C = new Camera(
            new float3(2.0f*s, 2.0f*c, 1.0f), 
            new float3(0.5f, 0.5f, 0.5f), 
            50.0f, 
            width, 
            height
        );
        
        Ray R = new Ray(C, x, y);
        
        float FARAWAY = 10e30f;
        
        float t = FARAWAY;
        
        fragColor = new float3(0.0, 0.0, 0.0); // background color
        
        float3 rayPos = new float3();
        float3 normalPoint = new float3();
        Material material = new Material();
        
        float3 cumulativeReflection = new float3(1.0, 1.0, 1.0);
        for(int i = 0; i < 2; i++) {
            if (Raytracing.nearestIntersection(R, rayPos, normalPoint, material)) {
                fragColor.add(float3.mult(
                                    cumulativeReflection,
                                    Raytracing.lighting(rayPos, normalPoint, material, R)
                                )
                );
                if(material.reflectiveColor.hasZeroDimensions()) {
                    break;
                }
                cumulativeReflection.mult(material.reflectiveColor);
                R = Raytracing.reflectRay(R, rayPos, normalPoint);
            } else {
                fragColor.add(float3.mult(
                                    cumulativeReflection,
                                    new float3(0.5, 0.5, 1.0)
                                )
                );
                break;
            }
        }
        
        return new Color(Raytracing.clamp(fragColor.x, 0.0f, 1.0f), Raytracing.clamp(fragColor.y, 0.0f, 1.0f), Raytracing.clamp(fragColor.z, 0.0f, 1.0f));
    }
}
