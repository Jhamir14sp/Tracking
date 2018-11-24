package saenz.jhamir.packagelocatorapp.Objetos;

public class coche {

    String marca;
    String dueño;
    int puertas;
    int ruedas;
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDueño() {
        return dueño;
    }

    public void setDueño(String dueño) {
        this.dueño = dueño;
    }

    public int getPuertas() {
        return puertas;
    }

    public void setPuertas(int puertas) {
        this.puertas = puertas;
    }

    public int getRuedas() {
        return ruedas;
    }

    public void setRuedas(int ruedas) {
        this.ruedas = ruedas;
    }


    public coche() {

    }
    public coche(String marca, String dueño, int piuertas, int ruedas) {
        this.marca = marca;
        this.dueño = dueño;
        this.puertas = puertas;
        this.ruedas = ruedas;
    }

}
