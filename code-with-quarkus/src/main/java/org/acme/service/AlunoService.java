package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Aluno;
import org.acme.model.DTO.AlunoDTO;
import org.acme.repository.AlunoRepository;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class AlunoService {

    @Inject
    AlunoRepository alunoRepository;

    public boolean cadastroAluno(AlunoDTO aluno) throws SQLException {
        if (aluno.getNome().isEmpty()){
            return false;
        }
        else if (aluno.getEmail().isEmpty()){
            return false;
        }
        alunoRepository.inserir(aluno);
        return true;
        //regras de negócio
    }
public List<Aluno> Relatorio(){
        // não preciso tratar dados de regra de negócio
     return alunoRepository.Relatorio();
}


public boolean RemoverPeloID(int id) throws SQLException{
        if (id<0){ // verifico se id é maior que 0
        throw new IllegalArgumentException("ID menor do que 0");
        }
       return alunoRepository.RemoverPeloID(id);
}
public boolean alterarNome(int id , String nome)throws SQLException{
        if (id<0){
            return false;
        }
        if(nome.isEmpty()){
            return false;
        }
        try {
           return  alunoRepository.atualizarNome(id, nome);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
}
}
