package primitives;

public class Material {
    double _kT;
    double _kR;
    double _kD;
    double _kS;
    int _nShininess;

    public Material(double _kT, double _kR, double _kD, double _kS, int _nShininess)
    {
        this._kT = _kT;
        this._kR = _kR;
        this._kD = _kD;
        this._kS = _kS;
        this._nShininess = _nShininess;
    }

    public double get_kT() {
        return _kT;
    }

    public double get_kR() {
        return _kR;
    }

    /**
     * constructor for initializing fields of material
     * @param _kD
     * @param _kS
     * @param _nShininess
     */
    public Material(double _kD, double _kS, int _nShininess) {
this(0.0,0.0,_kD,_kS,_nShininess);
    }

    /**
     * returns kd
     * @return double
     */
    public double get_kD() {
        return _kD;
    }

    /**
     * return ks
     * @return double
     */
    public double get_kS() {
        return _kS;
    }

    /**
     * returns shininess of material
     * @return double
     */
    public int get_nShininess() {
        return _nShininess;
    }
}
