/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author f3295813
 */
public class ConsultaSQL {
    
     private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    
    
      public void inserir(String npj, String matricula, String tipoSumula) throws Throwable {

     

        Conector con = new Conector();
        String sql = "INSERT INTO subsidio_cartao.tb_subsidiocartao_log (npj, tipo_sumula, funci, data) "
                + " VALUES ('" + npj + "','" + tipoSumula + "', '" + matricula + "', NOW());";

       
       
        try {
            
            PreparedStatement stInsert = con.conectar().prepareStatement(sql);

            try {

                stInsert.execute(sql);

            } catch (SQLException e) {
                System.out.println(e);
            }

            stInsert.close();
            con.fechar();

        } catch (SQLException e) {
                            System.out.println(e);
        }

    }
    
}
