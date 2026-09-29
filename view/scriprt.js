//Url base da API Spring boot para buscar as tarefas do usuário de ID 1
const url = "http://localhost:8080/task/user/1"

//Função responsável por ocultar o icone de carregamento 
function hideLoader(){

    // Busca o elemento HTML com o id 'loading' e altera seu etilo de exibição para oculta-lo
    document.getElementById("loading").style.display = "none";
}

// Função responsável por construir o THML da tabela e prenche-lo com as tarefas
function show(task){
    //Cria uma string contendo o cabeçalho da tabela utilizando template Literais
    let tab =`
    <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Descrição</th>
            <th scope="col">Usuário</th>
            <th scope="col">User</th>
        </tr>
    </thead>
    `;

    for(let task of tasks){

        tab += `
        <tr>
            <td scope="row">${task.id}</td>
            <td>${task.description}</td>
            <td>${task.user.username}</td>
            <td>${task.user.id}</td>
        </tr>
        `;
    }

    document.getElementById("tasks").innerHTML = tab;

    async function getAPI(url) {

        const response = await fetch(url,{method:"GET"});

        var data = await response.json();

        if(response){
            hideLoader();
        }
        
    }

}
