// Call the dataTables jQuery plugin
$(document).ready(function () {});

async function iniciarSesion() {
  let datos = {};
  datos.email = document.getElementById("txtEmail").value;
  datos.password = document.getElementById("txtPassword").value;

  if (datos.email.length === 0 || datos.password.length === 0) {
    return alert("debe completar cada campo");
  }

  const response = await fetch("api/login", {
    method: "POST",
    body: JSON.stringify(datos),
    headers: {
      "Accept": "application/json",
      "Content-Type": "application/json",
    },
  });
  const resp = await response.text();

  // console.log(resp);
     if (resp !== "FAIL") {
      //  console.log('moko')
      localStorage.token = resp;
      localStorage.email = datos.email;
      window.location.href = "usuarios.html";
     
    } else {
      alert("Credenciales erronéas");
    } 
}
