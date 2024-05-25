/**
 * A ray, in parametric form. 
 */
public class Ray {
    public float3 Origin;
    public float3 Dir;
    
    public Ray(float3 Origin, float3 Dir) {
        this.Origin = Origin;
        this.Dir = Dir;
    }
    
    /**
     * @brief Launches a primary Ray
     * @param C  the Camera
     * @param XY the pixel coordinates in [0,W-1] x [0,H-1]
     */
    public Ray(Camera C, int x, int y) {
        this.Origin = C.Obs;
        this.Dir = float3.add(
            float3.add(
                float3.mult(
                    C.View, 
                    C.z
                ), 
                float3.mult(
                    C.Horiz, 
                    (float)x-C.W/2.0f
                )
            ), 
            float3.mult(
                C.Up, 
                (float)y-C.H/2.0f
            )
        );
    }
}
