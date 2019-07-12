package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rafael Vargas Mesquita
 */
public class Conector {

    private Connection connection;
    
    
    
    //abre e retorna a conexao com o mysql
    public Connection conect;

    
    public Connection conectar()throws Throwable{
//        String servidor = "localhost";
//        String usuario = "root";
//        String senha = "fl@v1@12";
//        String banco = "subsidio_proativo";
        
        String servidor = "10.105.87.250";
        String usuario = "f3295813";
        String senha = "7xJU@UEr";
        String banco = "subsidio_cartao";
        
        String url = "jdbc:mysql://"+servidor+":3306/"+banco;
        conect = DriverManager.getConnection(url,usuario,senha);
        return  conect;
    } 

     public void fechar() throws SQLException
    {
        conect.close();
    }

//    public Connection conectar() {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            this.connection = DriverManager.getConnection("jdbc:mysql://10.105.87.250:3306/subsidio_proativo","f3295813","7xJU@UEr");
//            return this.connection;
//        } catch (SQLException | ClassNotFoundException ex) {
//            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//    }

 
//    public void desconectar(Connection connection) {
//        try {
//            connection.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
}
