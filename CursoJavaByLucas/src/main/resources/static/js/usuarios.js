// Call the dataTables jQuery plugin
$(document).ready(function() {
  cargarUsuarios();
  $('#usuarios').DataTable();
  actualizarEmailDelUsuario();
});

async function actualizarEmailDelUsuario() {
  const span = document.querySelector('#txt-email-usuario').outerHTML = localStorage.email;

}


async function cargarUsuarios() {

  const response = await fetch('api/usuarios',{
    method: 'GET',
    headers: getHeaders()
   });

    const usuarios = await response.json();

    let htmlFinal ="";

    usuarios.forEach( (usuario) => {

     let botonEliminar ='<a href="#" onclick="eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle btn-sm"> <i class="fas fa-trash"></i></a>';

     if(!usuario.telefono){ usuario.telefono = '-' };
        
     let fila = `
      <tr>
        <td>${usuario.id}</td>
        <td>${usuario.nombre}</td>
        <td>${usuario.email}</td>
        <td>${usuario.telefono}</td>
        <td>`+botonEliminar+`</td>
      </tr>`;
     htmlFinal += fila;
    })

    const tableBody = document.querySelector('#usuarios tbody').outerHTML=htmlFinal;
}

function getHeaders() {
  return {
    'Accept':'application/json',
    'Content-Type':'application/json',
    'Authorization':localStorage.token
    }
}

async function eliminarUsuario(id){

    if(confirm('¿Desea eliminar este usuario')){
        await fetch('api/usuarios/'+id,{
          method:'DELETE',
          headers: getHeaders()
          });
         document.location.reload();
    }
}


