document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Evitar que el formulario se envíe automáticamente

    // Obtener valores de los campos de usuario y contraseña
    const usuario = document.getElementById('usuario').value;
    const contrasenia = document.getElementById('contrasenia').value;

    // Crear objeto JSON para enviar al backend
    const loginData = {
        usuario: usuario,
        contrasenia: contrasenia
    };

    // Hacer la solicitud fetch al backend
    fetch('http://localhost:8080/api/login/ingresar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
    })
    .then(response => {
        if (response.status === 200) {
            return response.json(); // Si la respuesta es exitosa
        } else {
            throw new Error('Usuario o contraseña incorrectos');
        }
    })
    .then(data => {
        // Verificar el perfil del usuario y redirigir a la página correspondiente
        if (data.perfil === "Administrador") {
            window.location.href = "Usuarios.html";
        } else if (data.perfil === "Alumno") {
            window.location.href = "Alumnos.html";
        } else if (data.perfil === "Bibliotecario") {
            window.location.href = "Libros.html";
        } else {
            throw new Error('Perfil no reconocido');
        }
    })
    .catch(error => {
        // Mostrar mensaje de error si hay un problema con el login
        document.getElementById('errorMsg').style.display = 'block';
        document.getElementById('errorMsg').innerText = error.message;
    });
});
