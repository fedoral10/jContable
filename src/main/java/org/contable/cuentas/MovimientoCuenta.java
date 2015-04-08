/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contable.cuentas;

/**
 *
 * @author Jorge Potosme
 */
public class MovimientoCuenta {

    private Cuenta cuenta;
    private double debito;
    private double credito;

    public static class Builder {

        private MovimientoCuenta movCuenta;
        private double credito;
        private double debito;
        private Cuenta cuenta;

        public Builder setCuenta(Cuenta cuenta) {
            this.cuenta = cuenta;
            return this;
        }
        
        
        public Builder setCredito(double credito) {
            this.credito = credito;
            return this;
        }

        public Builder setDebito(double debito) {
            this.debito = debito;
            return this;
        }

        public MovimientoCuenta build() {
             movCuenta = new MovimientoCuenta(this.cuenta);
             movCuenta.setDebito(this.debito);
             movCuenta.setCredito(this.credito);
             if(movCuenta.getDebito()>0 && movCuenta.getCredito() >0){
                 throw new IllegalArgumentException("Debito y credito no pueden ser mayor que 0 a la vez");
             }
             return movCuenta;
        }

    }

    private MovimientoCuenta(Cuenta cuenta) {
        this.cuenta=cuenta;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public double getDebito() {
        return debito;
    }

    public void setDebito(double debito) {
        this.debito = debito;
    }

    public double getCredito() {
        return credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }

    public static MovimientoCuenta nuevo(Cuenta cuenta) {
        return new MovimientoCuenta(cuenta);
    }
}
