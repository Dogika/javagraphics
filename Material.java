/**
 * @brief Material gathers all shading properties
 */
public class Material {
    float3 diffuseColor;
    float3 emissiveColor;
    float3 reflectiveColor;
    float checkerboardSize;
    float3 specular;
    float specularFactor;
    
    public Material() {
        diffuseColor = new float3();
        emissiveColor = new float3();
        reflectiveColor = new float3();
        checkerboardSize = 0.0f;
        specular = new float3();
        specularFactor = 0.0f;
    }
    
    public Material(float3 diffuseColor, float3 emissiveColor, float3 reflectiveColor, float checkerboardSize, float3 specular, float specularFactor) {
        this.diffuseColor = diffuseColor;
        this.emissiveColor = emissiveColor;
        this.reflectiveColor = reflectiveColor;
        this.checkerboardSize = checkerboardSize;
        this.specular = specular;
        this.specularFactor = specularFactor;
    }
    
    public void set(Material material) {
        this.diffuseColor = material.diffuseColor;
        this.emissiveColor = material.emissiveColor;
        this.reflectiveColor = material.reflectiveColor;
        this.checkerboardSize = material.checkerboardSize;
        this.specular = material.specular;
        this.specularFactor = material.specularFactor;
    }
}
