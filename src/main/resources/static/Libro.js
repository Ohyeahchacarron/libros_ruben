$(document).ready(function() {
    // Cargar todos los libros al cargar la página
    cargarLibros();

    // Manejar el envío del formulario
    $('#formLibro').on('submit', function(event) {
        event.preventDefault(); // Evitar el envío por defecto del formulario
        
        const libro = {
            nombreLibro: $('#nombreLibro').val(),
            autor: $('#autor').val(),
            genero: $('#genero').val(),
            estatus: $('#estatus').val() === '1' ? '1' : '0'
        };

        const formData = new FormData();
        formData.append('libro', new Blob([JSON.stringify(libro)], { type: 'application/json' }));

        const archivoInput = $('#archivo')[0];
        if (archivoInput.files.length > 0) {
            formData.append('archivo', archivoInput.files[0]);
        }

        // Hacer la petición al servicio para crear el libro
        $.ajax({
            url: '/api/libros/crear',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function(response) {
                alert('Libro agregado con éxito');
                cargarLibros(); // Recargar la lista de libros
                $('#formLibro')[0].reset(); // Limpiar el formulario
            },
            error: function() {
                alert('Error al agregar el libro');
            }
        });
    });
});

// Función para cargar libros desde el servidor
function cargarLibros() {
    $.ajax({
        url: "/api/libros/listar",
        method: "GET",
        dataType: "json",
        success: function(response) {
            console.log(response);
            if (Array.isArray(response)) {
                $('#tablaLibros tbody').empty();
                
                response.forEach(libro => {
                    const fila = `
                        <tr onclick="seleccionarLibro(${libro.idLibro}, '${libro.nombreLibro}', '${libro.autor}', '${libro.genero}', '${libro.estatus}')">
                            <td>${libro.idLibro}</td>
                            <td>${libro.nombreLibro}</td>
                            <td>${libro.autor}</td>
                            <td>${libro.genero}</td>
                            <td>${libro.estatus}</td>
                            <td>
                                <button class="btn btn-info" onclick="abrirPdf(${libro.idLibro})">Ver PDF</button>
                                <button class="btn btn-danger" onclick="eliminarLibro(${libro.idLibro})">Eliminar</button>
                            </td>
                        </tr>
                    `;
                    $('#tablaLibros tbody').append(fila);
                });
            } else {
                alert('Error: La respuesta del servidor no es un array.');
            }
        },
        error: function(xhr, status, error) {
            console.error("Error en la solicitud:", error);
            alert("Error al cargar los libros. Intenta de nuevo.");
        }
    });
}

// Función para abrir el PDF del libro
function abrirPdf(idLibro) {
    window.open(`/api/libros/pdf/${idLibro}`, '_blank');
}

// Función para seleccionar un libro al hacer clic en una fila
function seleccionarLibro(idLibro, nombreLibro, autor, genero, estatus) {
    $('#idLibro').val(idLibro); 
    $('#nombreLibro').val(nombreLibro);
    $('#autor').val(autor);
    $('#genero').val(genero);
    $('#estatus').val(estatus);
}

// Función para eliminar un libro
function eliminarLibro(idLibro) {
    if (confirm('¿Estás seguro de que deseas eliminar este libro?')) {
        $.ajax({
            url: `/api/libros/eliminar/${idLibro}`,
            type: 'DELETE',
            success: function(response) {
                alert('Libro eliminado con éxito');
                cargarLibros(); 
            },
            error: function() {
                alert('Error al eliminar el libro');
            }
        });
    }
}

$('#btnActualizar').on('click', function(event) {
    event.preventDefault(); // Prevenir el comportamiento predeterminado del botón

    const idLibro = $('#idLibro').val(); // Obtener el ID del libro
    if (!idLibro) {
        alert('Por favor, selecciona un libro para actualizar.');
        return;
    }

    const libro = {
        nombreLibro: $('#nombreLibro').val(),
        autor: $('#autor').val(),
        genero: $('#genero').val(),
        estatus: $('#estatus').val() === '1' ? '1' : '0'
    };

    const formData = new FormData();
    formData.append('libro', new Blob([JSON.stringify(libro)], { type: 'application/json' }));

    const archivoInput = $('#archivo')[0];
    if (archivoInput.files.length > 0) {
        formData.append('archivo', archivoInput.files[0]);
    }

    // Hacer la solicitud para actualizar el libro
    $.ajax({
        url: `/api/libros/actualizar/${idLibro}`,
        type: 'PUT',
        data: formData,
        contentType: false,
        processData: false,
        success: function(response) {
            alert('Libro actualizado con éxito');
            cargarLibros(); // Recargar la lista de libros
            $('#formLibro')[0].reset(); // Limpiar el formulario
            $('#idLibro').val(''); // Limpiar el campo oculto después de la actualización
        },
        error: function() {
            alert('Error al actualizar el libro');
        }
    });
});

