let usuarioSeleccionado = null; // Declaración global para mantener el ID seleccionado

// Función global para cargar usuarios
function cargarUsuarios() {
    $.ajax({
        url: '/api/usuarios/listar',
        method: 'GET',
        success: function(data) {
            const usuarios = JSON.parse(data);
            $('#usuariosTableBody').empty(); // Limpiar tabla antes de agregar nuevos usuarios
            usuarios.forEach(usuario => {
                $('#usuariosTableBody').append(`
                    <tr onclick="seleccionarUsuario(${usuario.idUsuario}, '${usuario.usuario}', '${usuario.nombre}', '${usuario.apellidos}', '${usuario.perfil || ''}', '${usuario.contrasenia}')">
                        <td>${usuario.usuario}</td>
                        <td>${usuario.nombre}</td>
                        <td>${usuario.apellidos}</td>
                        <td>${usuario.perfil || 'No definido'}</td>
                        <td>${usuario.contrasenia}</td>
                    </tr>
                `);
            });
        },
        error: function() {
            alert('Error al cargar los usuarios.');
        }
    });
}

$(document).ready(function() {
    cargarUsuarios();

    // Deshabilitar botones de modificar y eliminar inicialmente
    $('#btnModificar').prop('disabled', true);
    $('#btnEliminar').prop('disabled', true);

    // Formulario para agregar un nuevo usuario
    $('#usuarioForm').on('submit', function(e) {
        e.preventDefault();

        const nuevoUsuario = {
            usuario: $('#usuario').val(),
            nombre: $('#nombre').val(),
            apellidos: $('#apellidos').val(),
            perfil: $('#perfil').val(),
            contrasenia: $('#contrasenia').val()
        };

        $.ajax({
            url: '/api/usuarios/crear', // Cambiado a /crear
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(nuevoUsuario),
            success: function() {
                alert('Usuario agregado exitosamente.');
                $('#usuarioForm')[0].reset(); // Limpiar el formulario
                cargarUsuarios(); // Recargar la lista de usuarios
            },
            error: function() {
                alert('Error al agregar el usuario.');
            }
        });
    });
});

// Función para eliminar un usuario
function eliminarUsuario() {
    if (usuarioSeleccionado === null) {
        alert('Por favor, selecciona un usuario para eliminar.');
        return;
    }

    if (confirm('¿Estás seguro de que deseas eliminar este usuario?')) {
        $.ajax({
            url: '/api/usuarios/eliminar/' + usuarioSeleccionado,
            method: 'DELETE',
            success: function() {
                alert('Usuario eliminado exitosamente.');
                cargarUsuarios();
                usuarioSeleccionado = null;
                $('#btnModificar').prop('disabled', true);
                $('#btnEliminar').prop('disabled', true);
            },
            error: function() {
                alert('Error al eliminar el usuario.');
            }
        });
    }
}

// Función para modificar un usuario
function modificarUsuario() {
    if (usuarioSeleccionado === null) {
        alert('Por favor, selecciona un usuario para modificar.');
        return;
    }

    const usuarioModificado = {
        usuario: $('#usuario').val(),
        nombre: $('#nombre').val(),
        apellidos: $('#apellidos').val(),
        perfil: $('#perfil').val(),
        contrasenia: $('#contrasenia').val()
    };

    $.ajax({
        url: `/api/usuarios/actualizar/${usuarioSeleccionado}`,
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(usuarioModificado),
        success: function() {
            alert('Usuario modificado exitosamente.');
            $('#usuarioForm')[0].reset(); // Limpiar el formulario
            cargarUsuarios(); // Recargar la lista de usuarios después de la modificación
            usuarioSeleccionado = null;
            $('#btnModificar').prop('disabled', true);
            $('#btnEliminar').prop('disabled', true);
        },
        error: function() {
            alert('Error al modificar el usuario.');
        }
    });
}

// Función para seleccionar un usuario y llenar el formulario
function seleccionarUsuario(idUsuario, usuario, nombre, apellidos, perfil, contrasenia) {
    usuarioSeleccionado = idUsuario;
    $('#usuario').val(usuario);
    $('#nombre').val(nombre);
    $('#apellidos').val(apellidos);
    $('#perfil').val(perfil);
    $('#contrasenia').val(contrasenia);
    $('#btnModificar').prop('disabled', false);
    $('#btnEliminar').prop('disabled', false);
}
