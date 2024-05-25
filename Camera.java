public class Camera {
    /**
     * The viewing parameters
     * Gathers all parameters needed to launch primary rays
     *
     * @field Obs   the position of the observer
     * @field View  unit vector, points to the target
     * @field Up    unit vector, positive vertical direction
     * @field Horiz unit vector, positive horizontal direction
     * @field H     screen height in pixels
     * @field W     screen width in pixel
     * @field z     offset of the screen along View
     */
    public float3 Obs;
    public float3 View;
    public float3 Up;
    public float3 Horiz;
    public float H;
    public float W;
    public float z;
    
    /**
     * @brief Initializes a Camera
     * @param Obs      the position of the observer
     * @param Target   the point that will be in the center
     * @param aperture the aperture angle in degrees
     * @return the initialized Camera
     */
    Camera(float3 Obs, float3 LookAt, float aperture, int width, int height) {
        this.Obs = Obs;
        this.View = float3.sub(LookAt, Obs).normalized();
        this.Horiz = float3.cross(new float3(0.0, 0.0, 1.0), this.View).normalized();
        this.Up = float3.cross(this.View, this.Horiz);
        this.H = (float)height;
        this.W = (float)width;
        this.z = (this.H/2.0f) / (float)Math.tan(aperture * 0.00872664625); // pi / 360
    }
}
