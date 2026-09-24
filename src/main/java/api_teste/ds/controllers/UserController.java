package api_teste.ds.controllers;

import java.net.URI; //Importa a classe URI para contruir e manipular HTTP de novos recursos
import org.springframework.beans.factory.annotation.Autowired; //Importa a anotação do Spring para a injeção automática de dependencias
import org.springframework.http.ResponseEntity; //Importa a classe ResponseEntity para manipular respostas HTTP
import org.springframework.validation.annotation.Validated; //Importa a anotação para validar os dados de entrada
import org.springframework.web.bind.annotation.DeleteMapping; //Importa a anotação para mapear requisições HTTP DELETE
import org.springframework.web.bind.annotation.GetMapping; //Importa a anotação para mapear requisições HTTP GET
import org.springframework.web.bind.annotation.PathVariable; //Importa a anotação para extrair variáveis de caminho da URL
import org.springframework.web.bind.annotation.PostMapping; //Importa a anotação para mapear requisições HTTP POST
import org.springframework.web.bind.annotation.PutMapping; //Importa a anotação para mapear requisições HTTP PUT
import org.springframework.web.bind.annotation.RequestBody; //converte JSON em OBJ JAVA
import org.springframework.web.bind.annotation.RequestMapping; //Importa a anotação para mapear requisições HTTP para um caminho específico
import org.springframework.web.bind.annotation.RestController; //Importa a anotação para definir a classe como um controlador REST
import org.springframework.web.servlet.support.ServletUriComponentsBuilder; //Importa a classe para construir URIs de recursos

import api_teste.ds.models.User; //Importa a classe User do pacote models
import api_teste.ds.models.User.UpdateUser;
import api_teste.ds.services.UserService; //Importa a classe UserService do pacote services


@RestController
@RequestMapping("/User")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User obj = this.userService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping //Mapeia requisões HTTP POST na rota base"/user" (criação de novo usuário)
    public ResponseEntity<Void> create(@Validated(User.CreateUser.class) @RequestBody User obj) { //Valida regra de CreateUSer e desserializa o corpo JSON
        this.userService.create(obj);  //Chama a camada de serviço para persistir o novo usuário no bando de dados
        URI url = ServletUriComponentsBuilder.fromCurrentRequest() //Obtém a rota da requisição atual 
                .path("/{id}").buildAndExpand(obj.getId()).toUri(); // Adciona o Id do usuário gerado no final do caminho da URI
        return ResponseEntity.created(url).build(); //Retornna o código HTTP 201(created) contendo a URL no cabealho location
    }

    @PutMapping("/{id}") // Mapeia requisições HTTP PUT na rota base "/user/{id}" (atualização do usuário)
    public ResponseEntity<Void> update(
            @Validated(UpdateUser.class) @RequestBody User obj,
            @PathVariable Long id) { // Aplica a regra do UpdateUser e recebe ID e JSON
        obj.setId(id); // Garante que o ID corresponde ao informado no parâmetro da URL
        this.userService.update(obj); // Executa a atualização do usuário no banco de dados
        return ResponseEntity.noContent().build(); // Retorna HTTP 204 sem corpo de resposta
    }

    @DeleteMapping("/{id}") //Mapeia requisições HTTP DELETE na rota "/user/{id}" (exclusão de usuário)
    public ResponseEntity<Void> delete(@PathVariable Long id){ // Captura o ID da URL a ser deletado
        this.userService.delete(id); // Invoca o método de deleção ao serviço
        return ResponseEntity.noContent().build(); //Retorna códigoHTTP 204 (no content) confirmando a exclusão
    }




}

