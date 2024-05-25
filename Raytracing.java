public class Raytracing {
    /**
     * @brief The scene is stored in a global array
     */
    public static Obj scene[] = new Obj[23];
    
    /**
     * @brief Computes a Ray Sphere intersection
     * @param[in] R the Ray
     * @param[in] S the Sphere
     * @param[out] t the intersection parameter
     * @retval true if there was an intersection
     * @retval false otherwise
     */
    public static boolean intersectSphere(Ray R, Sphere S, float[] t) {
        float3 CO = float3.sub(
                        R.Origin,
                        S.Center
                    );
        float a = float3.dot(R.Dir, R.Dir);
        float b = 2.0f * float3.dot(R.Dir, CO);
        float c = float3.dot(CO, CO) - S.R * S.R;
        float delta = b * b - 4.0f * a * c;
        if (delta < 0.0f) 
            return false;
        t[0] = (float)(-b-Math.sqrt(delta)) / (2.0f*a);
        return true;
    }
    
    public static boolean isLight(Material M) {
        return !M.emissiveColor.hasZeroDimensions(); 
    }
    
    /**
     * @brief Tests whether a Ray is in shadow
     * @param[in] R a Ray that connects a point to a lightsource
     * @retval true if the point is in shadow w.r.t. the lightsource
     * @retval false otherwise
     */
    public static boolean shadow(Ray R) {
        float result = 0.0f;
        for(int i = 0; i < scene.length; i++) {
            float[] t = new float[1];
            if(
                !isLight(scene[i].material)
                && intersectSphere(R, scene[i].sphere, t) 
                && t[0] > 0.0 
            ) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @brief Computes the lighting
     * @param[in] rayPos      the intersection point
     * @param[in] normalPoint the normal to the intersected surface at P
     * @param[in] material    the material
     * @param[in] Ray         the incident Ray
     * @return the computed color
     */
    public static float3 lighting(float3 rayPos, float3 normalPoint, Material material, Ray R) {
        
        // If it is a lightsource, then return its color
        // (and we are done) 
        if (isLight(material)) return material.emissiveColor;
        
        float3 result = new float3(0.0, 0.0, 0.0);
        
        // Compute the influence of all lightsources
        for(int i = 0; i < scene.length; i++) {
            if(isLight(scene[i].material)) {
                Ray secondaryRay = new Ray(rayPos, scene[i].sphere.Center);
                if(!shadow(secondaryRay)) {
                    float3 emissiveDifference = float3.sub(
                                                    scene[i].sphere.Center,
                                                    rayPos
                                                );
                    // Diffuse lighting
                    float resultingIntensity = Math.max(0.0f, float3.dot(emissiveDifference, normalPoint) / emissiveDifference.length());
                    float3 diffuseColor = material.diffuseColor;
                    if(material.checkerboardSize != 0.0f && Math.sin(rayPos.x/material.checkerboardSize)*Math.sin(rayPos.y / material.checkerboardSize) > 0.0) {
                        diffuseColor = float3.sub(
                                            new float3(1.0, 1.0, 1.0),
                                            diffuseColor
                                        );
                    }
                    result.add(float3.mult(
                                    float3.mult(
                                        diffuseColor,
                                        scene[i].material.emissiveColor
                                    ),
                                    resultingIntensity
                                )
                    );
                    
                    // Specular lighting
                    if(!material.specular.hasZeroDimensions()) {
                        float3 Er = float3.sub(
                                        float3.mult(
                                            normalPoint,
                                            2.0f*float3.dot(normalPoint, emissiveDifference)
                                        ),
                                    emissiveDifference
                                        );
                        float3 View = float3.sub(
                                            R.Origin,
                                            rayPos
                                        );
                        float spec = Math.max(0.0f, float3.dot(Er, View));
                        spec /= Math.sqrt(float3.dot(Er, Er)*float3.dot(View,View));
                        spec = (float)Math.pow(spec, material.specularFactor);
                        result.add(float3.mult(
                                        float3.mult(
                                            material.specular, 
                                            scene[i].material.emissiveColor
                                        ),
                                        spec
                                    )
                        );
                    }
                }
            }
        }
        return result;
    }

    
    /**
     * @brief Computes a reflected Ray
     * @param[in] inRay the incident Ray
     * @param[in] rayPos the point at which the reflection occurs
     * @param[in] normalPoint the normal at P
     * @return the reflected Ray
     */
    public static Ray reflectRay(Ray inRay, float3 rayPos, float3 normalPoint) {
        return new Ray(
            rayPos,
            float3.add(
                inRay.Dir,
                float3.mult(
                    normalPoint,
                    -2.0f*float3.dot(normalPoint, inRay.Dir)
                )
            )
        );
    }
    
    /**
     * @brief Computes the nearest intersection along a Ray
     * @param[in]  R           the ray
     * @param[out] rayPos      the intersection point
     * @param[out] normalPoint the normal to the intersected surface at rayPos
     * @param[out] material    the material of the intersected object
     */
    public static boolean nearestIntersection(Ray R, float3 rayPos, float3 normalPoint, Material material) {
        float FARAWAY = 1e30f; 
        float time = FARAWAY;
        
        for(int i = 0; i < scene.length; i++) {
            float[] current_time = new float[1];
            if(
                intersectSphere(R, scene[i].sphere, current_time) 
                && current_time[0] < time
                && current_time[0] > 0.0f
                && current_time[0] < 1.0f
            ) {
                time = current_time[0];
                rayPos.set(float3.add(
                                R.Origin,
                                float3.mult(
                                    R.Dir,
                                    time
                                )
                            )
                );
                normalPoint.set(float3.sub(
                                    rayPos,
                                    Raytracing.scene[i].sphere.Center
                                ).normalized()
                );
                material.set(Raytracing.scene[i].material);
            } 
        }
        return (time != FARAWAY);
    }

    /**
     * @brief Creates a diffuse material
     * @param[in] color the diffuse color
     * @return the created Material
     */
    public static Material diffuse(float3 color) {
        return new Material(color, float3.zero, float3.zero, 0.0f, float3.zero, 0.0f);
    }
    
     /**
      * @brief Creates a light (emissive) material
      * @param[in] color the color of the light
      * @return the created Material
      */
    public static Material light(float3 color) {
        return new Material(float3.zero, color, float3.zero, 0.0f, float3.zero, 0.0f);
    }
    
    /**
     * @brief Creates a mirror material
     * @param[in] color           the diffuse color
     * @param[in] reflectiveColor the reflection coefficient
     */
    public static Material mirror(float3 color, float3 reflectiveColor) {
        return new Material(color, float3.zero, reflectiveColor, 0.0f, new float3(1.0f, 1.0f, 1.0f), 30.0f);
    }
    
    /**
     * @brief Creates a shiny material
     * @param[in] color           the diffuse color
     * @param[in] specularColor the specular coefficient
     */
    public static Material shiny(float3 color, float3 specularColor) {
        return new Material(color, float3.zero, float3.zero, 0.0f, specularColor, 30.0f);
    }
    
    /**
     * @brief Creates a checkerboard material
     * @param[in] color the diffuse color
     * @param[in] size  the size of the checkers
     */
    public static Material checkerboard(float3 color, float size) {
        return new Material(color, float3.zero, float3.zero, size, float3.zero, 0.0f);
    }
    
    /**
     * @brief Initializes the scene
     */
    public static void initializeScene(int iFrame) { 
        
        scene[0] = new Obj(
            new Sphere(new float3(0.0, 0.0, 0.0), 0.5f), 
            mirror(new float3(0.2, 0.5, 0.2), new float3(0.5, 0.5, 0.5))
        );

        scene[1] = new Obj(
            new Sphere(new float3(0.0, 0.0, -10000.0), 9999.5f),
            checkerboard(new float3(1.0, 0.2, 0.5), 0.5f)
        );

        scene[2] = new Obj(
            new Sphere(new float3(1.0, 0.0, 1.0), 0.02f),
            light(new float3(1.0, 1.0, 1.0)) 
        );

        for(int i = 0; i < 20; i++) {
            float beta = iFrame/30.0f + i*0.33052631578f;
            float s = (float)Math.sin(beta);
            float c = (float)Math.cos(beta); 
            
            scene[i + 3] = new Obj(
                new Sphere(new float3(0.7*s, 0.7*c, 0.0), 0.1f), 
                mirror(new float3(1.0, 0.7, 0.7), new float3(0.3, 0.3, 0.3))
            );
        }
    }
    
    public static float smoothstep(float x) {
        float t = clamp(x, 0.0f, 1.0f);
        t = 1.0f - (float)Math.pow((1.0f - t), 1.5);
        return t * t * (3.0f - 2.0f * t);
    }
    
    public static float clamp(float v, float mn, float mx) {
        return Math.max(mn, Math.min(mx, v));
    }
}
