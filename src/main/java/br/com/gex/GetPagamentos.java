/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gex;

import br.com.giex.conexao.ConectaMysql;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author jjunior
 */
public class GetPagamentos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {

            Connection conn = new ConectaMysql().conecta();
            PreparedStatement psmt = null;

            String query = "SELECT "
                    + "    C.CRED_DS_CREDOR \"Credor\","
                    + "    INSCRICAO \"Inscrição\","
                    + "    PESSOA \"Pessoa\","
                    + "    NEGOCIACAO \"Negociação\","
                    + "    NUMERO \"Número\","
                    + "    PARCELA \"Parcela\","
                    + "    VALOR_NEGOCIADO \"Valor negociado\","
                    + "    PANN_NR_NOSSO_NUMERO \"Nosso número\","
                    + "    GUIA_DT_EMISSAO \"Data de emissão\","
                    + "    VALOR_PARCELA \"Valor da parcela\","
                    + "    HONORARIO \"Honorários\","
                    + "    DESPESAS \"Despesas\","
                    + "    CUSTAS \"Custas\","
                    + "    GUIA_DT_VENCIMENTO \"Data de vencimento\","
                    + "    PAPG_DT_PAGAMENTO \"Data de pagamento\","
                    + "    PAPG_VL_PAGO_TOTAL \"Valor do pagamento\""
                    + " FROM"
                    + "    giex.BI_PAGAMENTOS P"
                    + "    INNER JOIN giex.BI_CREDORES C ON P.CRED_ID_CREDOR = C.CRED_ID_CREDOR "
                    + " WHERE P.CRED_ID_CREDOR=?";

            psmt = conn.prepareStatement(query);
            psmt.setInt(1, Integer.parseInt(request.getParameter("idCredor")));

            ResultSet resultSet = psmt.executeQuery();

            response.setContentType("text/html; charset=UTF-8");
            response.setContentType("application/json");

            JSONArray jsonArray = new JSONArray();
            while (resultSet.next()) {
                int total_rows = resultSet.getMetaData().getColumnCount();
                JSONObject obj = new JSONObject();
                for (int i = 1; i <= total_rows; i++) {

                    obj.put(resultSet.getMetaData().getColumnLabel(i), resultSet.getObject(i));

                }
                jsonArray.put(obj);
            }

            OutputStream os = response.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            jsonArray.write(osw);
            osw.flush();
            osw.close();
            os.close();

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
