/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contable.cuentas;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import org.joda.time.DateTime;

/**
 *
 * @author Jorge Potosme
 */
public class Asiento {

    private final List<MovimientoCuenta> debitos;
    private final List<MovimientoCuenta> creditos;
    private String descripcion;
    private double montoDebitos;
    private double montoCreditos;
    private DateTime fecha;

    public DateTime getFecha() {
        return fecha;
    }

    public void setFecha(DateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Cuenta\t\t\t\tDebitos\t\tCreditos\n");
        builder.append("----------------------------------------------------------\n");
        Formatter formater = new Formatter();
        String formato = "%-32s%-16s%-16s\n";

        for (MovimientoCuenta movDeb : this.debitos) {
            Cuenta cuenta = movDeb.getCuenta();
            //builder.append(formater.format(formato, cuenta.getNombre(),movDeb.getDebito(),movDeb.getCredito()));
            formater.format(formato, cuenta.getNombre(), movDeb.getDebito(), movDeb.getCredito());
            //formater.flush();
            //formater= new Formatter();
        }
        for (MovimientoCuenta movCred : this.creditos) {
            Cuenta cuenta = movCred.getCuenta();
            //builder.append(formater.format(formato, cuenta.getNombre(),movCred.getDebito(),movCred.getCredito()));
            formater.format(formato, cuenta.getNombre(), movCred.getDebito(), movCred.getCredito());
            //formater.flush();
            //formater= new Formatter();
        }
        builder.append(formater.toString());
        return builder.toString();
    }

    public Asiento(String descripcion) {
        debitos = new ArrayList<>();
        creditos = new ArrayList<>();
        this.descripcion = descripcion;
    }

    public double getMontoDebitos() {
        if (montoDebitos == 0) {
            this.obtenerTotalDebitos();
        }
        return montoDebitos;
    }

    public double getMontoCreditos() {
        if (montoCreditos == 0) {
            this.obtenerTotalCreditos();
        }
        return montoCreditos;

    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void addCredito(MovimientoCuenta movCuenta) {
        this.creditos.add(movCuenta);
    }

    public void addDebito(MovimientoCuenta movCuenta) {
        this.debitos.add(movCuenta);
    }

    public boolean asientoBalanceado() {
        return this.obtenerTotalCreditos() == this.obtenerTotalDebitos();
    }

    public boolean asientoEstaCompleto() {
        return !(this.debitos.isEmpty() || this.creditos.isEmpty());
    }

    private boolean verificaAsientoCorrecto() {
        return asientoBalanceado() && asientoEstaCompleto();
    }

    public void contabilizar() throws Exception {

        /*if (this.debitos.isEmpty() || this.creditos.isEmpty()) {
         throw new IllegalArgumentException("Las listas debitos y creditos no pueden estar vacias");
         }
         if (!this.asientoBalanceado()) {
         throw new IllegalArgumentException("El total del debito " + this.obtenerTotalDebitos() + " debe ser igual al total del credito " + this.obtenerTotalCreditos());
         }*/
        if (!this.asientoEstaCompleto()) {
            throw new IllegalArgumentException("Las listas debitos y creditos no pueden estar vacias");
        }
        if (!this.asientoBalanceado()) {
            throw new IllegalArgumentException("El total del debito " + this.obtenerTotalDebitos() + " debe ser igual al total del credito " + this.obtenerTotalCreditos());
        }

        for (MovimientoCuenta mov : debitos) {
            modificaSaldoCuenta(mov);
        }
        for (MovimientoCuenta mov : creditos) {
            modificaSaldoCuenta(mov);
        }
    }

    private void modificaSaldoCuenta(MovimientoCuenta movCuenta) {
        /*
         Si cuenta es deudora sube en el debe(debito, debitar), baja en el haber(credito,acreditar)
         */
        Cuenta cuenta = movCuenta.getCuenta();
        if (cuenta.getNaturaleza() == Cuenta.Naturaleza.DEUDORA) {
            cuenta.setSaldo(cuenta.getSaldo() + movCuenta.getDebito());
            cuenta.setSaldo(cuenta.getSaldo() - movCuenta.getCredito());
            Cuenta padre = cuenta.getPadre();

            while (padre != null) {
                padre.setSaldo(padre.getSaldo() + movCuenta.getDebito());
                padre.setSaldo(padre.getSaldo() - movCuenta.getCredito());
                padre = padre.getPadre();
            }
        } else {
            /*Si es accreedora*/
            cuenta.setSaldo(cuenta.getSaldo() - movCuenta.getDebito());
            cuenta.setSaldo(cuenta.getSaldo() + movCuenta.getCredito());
            Cuenta padre = cuenta.getPadre();
            while (padre != null) {
                padre.setSaldo(padre.getSaldo() - movCuenta.getDebito());
                padre.getPadre().setSaldo(padre.getSaldo() + movCuenta.getCredito());
                padre = padre.getPadre();
            }
        }
    }

    private double obtenerTotalDebitos() {
        double monto = 0;
        for (MovimientoCuenta mov : this.debitos) {
            monto = monto + mov.getDebito();
        }
        this.montoDebitos = monto;
        return monto;
    }

    private double obtenerTotalCreditos() {
        double monto = 0;
        for (MovimientoCuenta mov : this.creditos) {
            monto = monto + mov.getCredito();
        }
        this.montoCreditos = monto;
        return monto;
    }

}
