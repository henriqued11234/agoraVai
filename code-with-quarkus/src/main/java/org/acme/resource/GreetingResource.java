package org.acme.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Aluno;
import org.acme.model.DTO.AlunoDTO;
import org.acme.repository.AlunoRepository;
import org.acme.service.AlunoService;

import java.sql.SQLException;
import java.util.List;

@Path("/hello") //caminho para que eu consiga chegar nessa classe
public class GreetingResource {

    @Inject
    AlunoService alunoService;


    @GET //verbo
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/caminho")
    public String hello3() {
        return "Hello from Quarkus REST";
    }

    @GET //verbo
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{nome}/{idade}")
    public String hello(@PathParam("nome") String abc, @PathParam("idade") int idade) {
        return "Hello from Quarkus REST" + "valor recebido: " + abc + "idade:" + idade;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/Query")
    public String metodo (@QueryParam("nome") String nome1232213, @QueryParam("idade") int idadefdsfdsafd){
        return "Nome:" + nome1232213 + " idade:" + idadefdsfdsafd;
    }

    @GET //verbo
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{nome}")
    public String exemploHibrido(@PathParam("nome") String abc, @QueryParam("idade") int idade) {
        return "valor recebido: " + abc + "idade:" + idade;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)//consumir o formulário
    public String login(@FormParam("nome") String nome , @FormParam("idade") int idade){
        //através do atributo name recupero os valores
        return "valor recebido: " + nome + "idade:" + idade;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/login")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)//consumir o formulário
    public String login2(@QueryParam("nome") String nome , @QueryParam("idade") int idade){
        //através do atributo name recupero os valores
        return "valor recebido: " + nome + "idade:" + idade;
    }

    @GET //verbo
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello2/{nome}/{idade}")
   public Response hello3(@PathParam("nome") String abc, @PathParam("idade") int idade) {
        String resposta = "valor recebido: " + abc + "idade:" + idade;
        if (idade < 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("deu ruim").build();
        } else {
            return Response.status(Response.Status.OK).entity(resposta).build();
        }

    }

    //primeiro crud que fizemos recebendo um JSON
    //criação de um elemento
    //primeiro crud que fizemos recebendo um JSON
    //criação de um elemento
    @POST //Verbo usado para criação de conteudo Insert
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/cadastrar")
    public Response cadastrarUsuario(AlunoDTO aluno) throws SQLException {
        try{ boolean tratamento = alunoService.cadastroAluno(aluno);
            if (tratamento==true){
                return Response.status(Response.Status.OK).entity("ok").build();
            }
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Nome Vazio ou email vazio").build();
        }catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao adicionar alunos: " + e.getMessage())
                    .build();
        }
    }
    /*@GET // select consumindo
    @POST //inserção de dados na nossa
    @PUT //alteração de dados
    @DELETE //deleção de dados

*/
    @GET
    @Path("/relatorio")//http://localhost:8080/hello/relatorio
    @Produces(MediaType.APPLICATION_JSON)
    public Response Relatorio() {
        try {
            List<Aluno> lista = alunoService.Relatorio();
            return Response.status(Response.Status.OK).entity(lista).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("Erro ao conectar com a base").build();
        }
    }
    //criar método que delete pelo ID
    @DELETE//usar verbo delete para remoção
    @Path("/deletar/{id}") // criação do caminho
    public Response RemoverPeloID (@PathParam("id") int id){
       try {
           boolean sucesso = alunoService.RemoverPeloID(id);
           //true se remove e false caso não tenha removido
           if (sucesso){
               return Response.status(Response.Status.OK)
                       .entity("Deu bom").build();
           }else{
               return Response.status(Response.Status.NOT_FOUND)
                       .entity("Não foi encontrado o ID").build();
           }
       }catch (IllegalArgumentException e){
          return Response.status(Response.Status.BAD_REQUEST)
                   .entity("ID menor do que zero").build();
       }catch(Exception e){
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity("Erro na base de dados").build();
       }

    }

    @PUT
    @Path("/atualizar/{id}/{nome}")

    public Response atualizarNome(@PathParam("id") int id , @PathParam("nome") String nome){
try{
        boolean verificar=alunoService.alterarNome(id, nome);

if(verificar){
    return Response.status(Response.Status.OK).entity("Dados alterdos com sucesso").build();
}else{
    return Response.status((Response.Status.NOT_FOUND)).entity("Deu ruim").build();
}

 }catch (SQLException e){
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); }
    }



    }






