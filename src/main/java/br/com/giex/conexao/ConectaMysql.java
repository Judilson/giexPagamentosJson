/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.giex.conexao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author jjunior
 */
public class ConectaMysql {

    public Connection conecta() {

        Connection conn = null;
        try {
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName);
            String serverName = "10.10.40.179:3306";    //caminho do servidor do BD
            String mydatabase ="giex";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
            String username = "giex";        //nome de um usuário de seu BD      
            String password = "7xt3Dw87";      //sua senha de acesso

            conn = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao tentar se conectar ao banco");
        }
        return conn;
    }
}
