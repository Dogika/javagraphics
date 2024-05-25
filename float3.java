public class float3 {
    public Float x;
    public Float y;
    public Float z;
    
    public static float3 zero = new float3();

    float3() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }

    public float3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float3(float x) {
        this.x = x;
        this.y = x;
        this.z = x;
    }

    public float3(int x, int y, int z) {
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;
    }

    public float3(int x) {
        this.x = (float) x;
        this.y = (float) x;
        this.z = (float) x;
    }

    public float3(double x, double y, double z) {
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;
    }

    public float3(double x) {
        this.x = (float) x;
        this.y = (float) x;
        this.z = (float) x;
    }

    public float3 set(float3 xyz) {
        this.x = xyz.x.floatValue();
        this.y = xyz.y.floatValue();
        this.z = xyz.z.floatValue();
        return this;
    }

    public float3 add(int n) {
        this.x += (float)n;
        this.y += (float)n;
        this.z += (float)n;
        return this;
    }

    public float3 add(double n) {
        this.x += (float)n;
        this.y += (float)n;
        this.z += (float)n;
        return this;
    }

    public float3 add(float n) {
        this.x += n;
        this.y += n;
        this.z += n;
        return this;
    }

    public float3 add(float3 xyz) {
        this.x += xyz.x;
        this.y += xyz.y;
        this.z += xyz.z;
        return this;
    }

    public float3 sub(int n) {
        this.x -= (float)n;
        this.y -= (float)n;
        this.z -= (float)n;
        return this;
    }

    public float3 sub(double n) {
        this.x -= (float)n;
        this.y -= (float)n;
        this.z -= (float)n;
        return this;
    }

    public float3 sub(float n) {
        this.x -= n;
        this.y -= n;
        this.z -= n;
        return this;
    }

    public float3 sub(float3 xyz) {
        this.x -= xyz.x;
        this.y -= xyz.y;
        this.z -= xyz.z;
        return this;
    }

    public float3 mult(float n) {
        this.x *= n;
        this.y *= n;
        this.z *= n;
        return this;
    }

    public float3 mult(float3 xyz) {
        this.x *= xyz.x;
        this.y *= xyz.y;
        this.z *= xyz.z;
        return this;
    }

    public float3 cos() {
        this.x = (float)Math.cos(x);
        this.y = (float)Math.cos(y);
        this.z = (float)Math.cos(z);
        return this;
    }

    public float get(int i) {
        switch(i) {
            case 0: return this.x;
            case 1: return this.y;
            case 2: return this.z;
            default:
                throw new IndexOutOfBoundsException("" + i);
        }
    }
    
    public void set(int i, float a) {
        switch(i) {
            case 0: this.x = a; return;
            case 1: this.y = a; return;
            case 2: this.z = a; return;
            default:
                throw new IndexOutOfBoundsException("" + i);
        }
    }
    
    public void add(int i, float a) {
        switch(i) {
            case 0: this.x += a; return;
            case 1: this.y += a; return;
            case 2: this.z += a; return;
            default:
                throw new IndexOutOfBoundsException("" + i);
        }
    }

    public float3 mod(float n) {
        this.x = (this.x % n + n) % n;
        this.y = (this.y % n + n) % n;
        this.z = (this.z % n + n) % n;
        return this;
    }

    public static float3 add(float3 xyz, float n) {
        return new float3(xyz.x + n, xyz.y + n, xyz.z + n);
    }

    public static float3 add(float3 xyz, int n) {
        return new float3(xyz.x + (float)n, xyz.y + (float)n, xyz.z + (float)n);
    }

    public static float3 add(float3 xyz, double n) {
        return new float3(xyz.x + (float)n, xyz.y + (float)n, xyz.z + (float)n);
    }

    public static float3 add(float3 xyz, float3 abc) {
        return new float3(xyz.x + abc.x, xyz.y + abc.y, xyz.z + abc.z);
    }

    public static float3 sub(float3 xyz, float n) {
        return new float3(xyz.x - n, xyz.y - n, xyz.z - n);
    }

    public static float3 sub(float3 xyz, int n) {
        return new float3(xyz.x - (float)n, xyz.y - (float)n, xyz.z - (float)n);
    }

    public static float3 sub(float3 xyz, double n) {
        return new float3(xyz.x - (float)n, xyz.y - (float)n, xyz.z - (float)n);
    }

    public static float3 sub(float3 xyz, float3 abc) {
        return new float3(xyz.x - abc.x, xyz.y - abc.y, xyz.z - abc.z);
    }

    public static float3 mult(float3 xyz, float n) {
        return new float3(xyz.x * n, xyz.y * n, xyz.z * n);
    }

    public static float3 mult(float3 xyz, double n) {
        return new float3(xyz.x * (float)n, xyz.y * (float)n, xyz.z * (float)n);
    }

    public static float3 mult(float3 xyz, float3 abc) {
        return new float3(xyz.x * abc.x, xyz.y * abc.y, xyz.z * abc.z);
    }

    public float3 div(float n) {
        this.x /= n;
        this.y /= n;
        this.z /= n;
        return this;
    }

    public float3 div(float3 xyz) {
        this.x /= xyz.x;
        this.y /= xyz.y;
        this.z /= xyz.z;
        return this;
    }

    public static float3 div(float3 xyz, float n) {
        return new float3(xyz.x / n, xyz.y / n, xyz.z / n);
    }

    public static float3 div(float3 xyz, float3 abc) {
        return new float3(xyz.x / abc.x, xyz.y / abc.y, xyz.z / abc.z);
    }
    
    public static float3 cross(float3 first, float3 other) {
        return new float3(
            first.y * other.z - first.z * other.y,
            first.z * other.x - first.x * other.z,
            first.x * other.y - first.y * other.x
        );
    }
    
    public static float dot(float3 first, float3 other) {
        return first.x * other.x + first.y * other.y + first.z * other.z;
    }
    
    public float length() {
        return (float) Math.sqrt((double)(x * x + y * y + z * z));
    }

    public float3 normalized() {
        float size = this.length();
        return new float3(x / size, y / size, z / size);
    }
    
    public float3 rounded() {
        return new float3((float)Math.round(this.x), (float)Math.round(this.y), (float)Math.round(this.z));
    }
    
    public float3 sign() {
        float3 out = new float3();
        out.set(this);
        out.x /= Math.abs(out.x);
        out.y /= Math.abs(out.y);
        out.z /= Math.abs(out.z);
        return out;
    }

    public static float3 cos(float3 xyz) {
        return new float3(Math.cos(xyz.x), Math.cos(xyz.y), Math.cos(xyz.z));
    }

    public static float3 mod(float3 xyz, float n) {
        return new float3((xyz.x % n + n) % n, (xyz.y % n + n) % n, (xyz.z % n + n) % n);
    }
    
    public boolean hasZeroDimensions() {
        return x == 0 && y == 0 && z == 0;
    }
}
