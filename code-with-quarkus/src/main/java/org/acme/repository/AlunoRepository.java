package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Aluno;
import org.acme.model.DTO.AlunoDTO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AlunoRepository { //equivalente ao DAO
    //Responsavel pela comunicação com a base
    @Inject  //
    DataSource dataSource;//gerenciado pelo Quarkus com as
    // informações que passei no application.properties
    //identico ao que fazem no DAO das aulas anteriores

    public void inserir (AlunoDTO aluno) throws SQLException { //preparedStatement
        String sql = "Insert into alunos (nome,email) values (?,?)";
        try(Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, aluno.getNome());
            ps.setString(2, aluno.getEmail());
            ps.executeUpdate();
        }
        catch (SQLException e){ //lança exceção
            throw new SQLException();
        }
    }

    public List<Aluno> Relatorio (){
      String sql= "select * from alunos";
      List<Aluno> lista = new ArrayList<>();
      try(Connection con = dataSource.getConnection();
      PreparedStatement ps = con.prepareStatement(sql))
       {
           ResultSet rs = ps.executeQuery();
            while (rs.next()){//para cada tupla
            Aluno aluno = new Aluno();
            aluno.setId(rs.getInt(1));
            aluno.setNome(rs.getString(2));
            aluno.setEmail(rs.getString(3));
            lista.add(aluno);
            }
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
      return lista;
    }

    public boolean RemoverPeloID(int id){
    String sql= "Delete from alunos where id = ?";
    try( Connection con = dataSource.getConnection();//essa linha tem dataSource
         PreparedStatement ps = con.prepareStatement(sql)){
         ps.setInt(1,id);
         int linhasAfetadas= ps.executeUpdate();
         return linhasAfetadas>0;//boolean
         //se linhas afetadas for maior que 0 retorna true
        //se linhas afetadas for menor que 0 retorna false
    }catch( SQLException e){
        throw new RuntimeException("Erro de conexão",e);
    }

    }
    public boolean atualizarNome(int id, String nome )throws SQLException{
        String sql = "update alunos set nome= ? where id= ?";
        try (Connection con = dataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1,nome);
            ps.setInt(2,id);
            int linhasAfetas = ps.executeUpdate();
            return linhasAfetas>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



}
