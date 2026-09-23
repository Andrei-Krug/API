import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import api_teste.ds.models.User;
import api_teste.ds.models.User.CreateUser;
import api_teste.ds.models.User.UpdateUser;
import api_teste.ds.services.UserService;
import org.springframework.web.bind.annotation.RequestParam;

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
    public ResponseEntity<Void> create(@Validated(CreateUser.class) @RequestBody User obj) { //Valida regra de CreateUSer e desserializa o corpo JSON
        this.userService.create(obj);  //Chama a camada de serviço para persistir o novo usuário no bando de dados
        URI url = ServletUriComponentsBuilder.fromCurrentRequest() //Obtém a rota da requisição atual 
                .path("/{id}").buildAndExpand(obj.getId()).toUri(); // Adciona o Id do usuário gerado no final do caminho da URI
        return ResponseEntity.created(url).build(); //Retornna o código HTTP 201(created) contendo a URL no cabealho location
    }

    @PutMapping (/"{id}")// Mapeia requisições HTTP PUT na rota base "/user/{id}" (atualização do usuário)]
public ResponseEntity<void> update(@validated(UpdateUser.class)@RequestBody User obj, @PathVariable Long id){ //Aplica a regra do UpdateUser e recebe ID e JSON
    obj.setId(id); //Garante que o ID do objeto a ser atualizado corresponde ao ID informado no parametro da URL
    this.userService.update(obj); // Executa a atualização da senha do usuário no banco de dados
    return ResponseEntity.noContent().build(); //Retorno código HTTP 204(No content) indicando sucesso sem corpo de resposta
}

    @DeleteMapping ("/{id}") //Mapeia requisões HTTP DELETE na rota "/user{id}" (exxclusão de usuário)
    public ResponseEntity<Void>.delete(@PathVariable Long id){ // Captura o ID da URL a ser deletado
        this.userService.delete(id); // Invoca o método de deleção ao serviço
        return ResponseEntity.noContent().build(); //Retorna códigoHTTP 204 (no content) confirmando a exclusão
    }




}

