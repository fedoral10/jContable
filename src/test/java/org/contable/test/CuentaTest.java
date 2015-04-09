/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contable.test;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.contable.cuentas.Asiento;
import org.contable.cuentas.Cuenta;
import org.contable.cuentas.MovimientoCuenta;
import org.junit.Test;

/**
 *
 * @author Jorge Potosme
 */
public class CuentaTest {

    @Test
    public void pruebaTotal() {

        try {
            Cuenta activo = new Cuenta("Activo",Cuenta.Naturaleza.DEUDORA);
            Cuenta caja = new Cuenta("Caja",Cuenta.Naturaleza.DEUDORA);
            Cuenta banco = new Cuenta("Banco",Cuenta.Naturaleza.DEUDORA);
            Cuenta cuentaCorriente = new Cuenta("Cuenta Corriente",Cuenta.Naturaleza.DEUDORA);
            Cuenta pasivo = new Cuenta("Pasivo",Cuenta.Naturaleza.ACREEDORA);
            Cuenta capital = new Cuenta("Capital",Cuenta.Naturaleza.ACREEDORA);
            Cuenta cuentaAhorro = new Cuenta("Ahorro",Cuenta.Naturaleza.DEUDORA);
            
            banco.setPadre(activo);
            cuentaCorriente.setPadre(banco);
            caja.setPadre(activo);
            cuentaAhorro.setPadre(banco);

            MovimientoCuenta creditoCapital = MovimientoCuenta.nuevo(capital);
            MovimientoCuenta debitoCuentaCorriente = MovimientoCuenta.nuevo(cuentaCorriente);
            MovimientoCuenta debitoCaja = MovimientoCuenta.nuevo(caja);
            MovimientoCuenta debitoCuentaAhorro = MovimientoCuenta.nuevo(cuentaAhorro);
            
            creditoCapital.setCredito(15000);
            debitoCuentaCorriente.setDebito(5000);
            debitoCaja.setDebito(5000);
            debitoCuentaAhorro.setDebito(5000);
            
            Asiento asientoInicial = new Asiento("Asiento de prueba");
            asientoInicial.addCredito(creditoCapital);
            asientoInicial.addDebito(debitoCuentaCorriente);
            asientoInicial.addDebito(debitoCaja);
            asientoInicial.addDebito(debitoCuentaAhorro);

            asientoInicial.contabilizar();
            

            //System.out.println("Saldo en Activo: " + activo.getSaldo());
            //System.out.println("Saldo en Activo-->banco: " + banco.getSaldo());
            //System.out.println("Saldo en Activo-->banco-->cuenta corriente: " + cuentaCorriente.getSaldo());
            //System.out.println("Saldo en Activo-->banco-->cuenta ahorro: " + cuentaCorriente.getSaldo());
            System.out.println(activo.getJerarquia() +"=" + activo.getSaldo());
            System.out.println(banco.getJerarquia()+"=" + banco.getSaldo());
            System.out.println(cuentaCorriente.getJerarquia()+"=" + cuentaCorriente.getSaldo());
            System.out.println(cuentaAhorro.getJerarquia()+"=" + cuentaAhorro.getSaldo());
            System.out.println(caja.getJerarquia()+"=" +  caja.getSaldo());
            System.out.println( capital.getJerarquia() +"=" + capital.getSaldo());
            
            System.out.println(asientoInicial.toString());
        } catch (Exception ex) {
            Logger.getLogger(CuentaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
