// Call the dataTables jQuery plugin
$(document).ready(function() {
  cargarUsuarios();
  $('#usuarios').DataTable();
});

async function cargarUsuarios() {

  const response = await fetch('usuarios',{
    method: 'GET',
    headers: {
    'Accept':'application/json',
    'Content-Type':'application/json'
    }});

    const usuarios = await response.json();

    let htmlFinal ="";

    usuarios.forEach( (usuario) => {
     let fila = `<tr><td>${usuario.id}</td><td>${usuario.nombre}</td><td>${usuario.email}</td><td>`+ usuario.telefono+`</td><td><a href="#" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a></td></tr>`;
     htmlFinal += fila;
    })

    const tableBody = document.querySelector('#usuarios tbody').outerHTML=htmlFinal;

}

