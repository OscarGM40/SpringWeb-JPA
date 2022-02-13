// Call the dataTables jQuery plugin
$(document).ready(function() {


});


async function registrarUsuario() {

  let datos = {};
  datos.nombre = document.getElementById('txtNombre').value;
  datos.apellido = document.getElementById('txtApellido').value;
  datos.email = document.getElementById('txtEmail').value;
  datos.password = document.getElementById('txtPassword').value;

  if(datos.nombre.length === 0 ||
     datos.apellido.length === 0 ||
     datos.email.length === 0 ||
     datos.password.length === 0
  ){
   return alert('debe completar cada campo')
  }
  let repeatPassword = document.getElementById('txtRepeatPassword').value;

    if(repeatPassword !== datos.password){
      return alert('Las contraseñas no coinciden')
    }
  const response = await fetch('api/usuarios',{
    method: 'POST',
    body:JSON.stringify(datos),
    headers: {
      'Accept':'application/json',
      'Content-Type':'application/json'
    }});
    alert('Cuenta creada satisfactoriamente');
    window.location.href="login.html";
}



