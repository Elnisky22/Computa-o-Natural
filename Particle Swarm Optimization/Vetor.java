package pso;

/**
 * Representa a posição e a velocidade.
 */
class Vetor {

    private double x, y, z;
    private double limit = Double.MAX_VALUE;

    Vetor () {
        this(0, 0, 0);
    }

    Vetor (double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    void set (double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    void add (Vetor v) {
        x += v.x;
        y += v.y;
        z += v.z;
        limit();
    }

    void sub (Vetor v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        limit();
    }

    void mul (double s) {
        x *= s;
        y *= s;
        z *= s;
        limit();
    }

    void div (double s) {
        x /= s;
        y /= s;
        z /= s;
        limit();
    }

    void normalize () {
        double m = mag();
        if (m > 0) {
            x /= m;
            y /= m;
            z /= m;
        }
    }

    private double mag () {
        return Math.sqrt(x*x + y*y);
    }

    void limit (double l) {
        limit = l;
        limit();
    }

    private void limit () {
        double m = mag();
        if (m > limit) {
            double ratio = m / limit;
            x /= ratio;
            y /= ratio;
        }
    }

    public Vetor clone () {
        return new Vetor(x, y, z);
    }
    
    private void setX (double x) {this.x = x;}
    private void setY (double y) {this.y = y;}
    private void setZ (double z) {this.z = z;}
    
    double getX () {return x;}
    double getY () {return y;}
    double getZ () {return z;}

    public String toString () {
        return "(" + x + ", " + y + ", " + z + ")";
    }

}