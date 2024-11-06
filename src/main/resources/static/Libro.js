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
                $('#btnEliminar').hide(); // Ocultar el botón de eliminar
            },
            error: function() {
                alert('Error al agregar el libro');
            }
        });
    });

    // Configurar el botón de eliminar
    $('#btnEliminar').on('click', function() {
        const idLibro = $('#idLibro').val();
        if (idLibro && confirm('¿Estás seguro de que deseas eliminar este libro?')) {
            eliminarLibro(idLibro);
        }
    });
});

// Función para cargar libros desde el servidor
function cargarLibros() {
    $.ajax({
        url: "/api/libros/listar",
        method: "GET",
        dataType: "json",
        success: function(response) {
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
            alert("Error al cargar los libros. Intenta de nuevo.");
        }
    });
}

// Función para seleccionar un libro al hacer clic en una fila
function seleccionarLibro(idLibro, nombreLibro, autor, genero, estatus) {
    $('#idLibro').val(idLibro); 
    $('#nombreLibro').val(nombreLibro);
    $('#autor').val(autor);
    $('#genero').val(genero);
    $('#estatus').val(estatus);
    $('#btnEliminar').show(); // Mostrar el botón de eliminar
}

// Función para eliminar un libro
function eliminarLibro(idLibro) {
    $.ajax({
        url: `/api/libros/eliminar/${idLibro}`,
        type: 'DELETE',
        success: function(response) {
            alert('Libro eliminado con éxito');
            cargarLibros(); 
            $('#formLibro')[0].reset(); // Limpiar el formulario
            $('#idLibro').val(''); // Limpiar el campo oculto
            $('#btnEliminar').hide(); // Ocultar el botón de eliminar
        },
        error: function() {
            alert('Error al eliminar el libro');
        }
    });
}

// Función para abrir el PDF del libro en el modal
function abrirPdf(idLibro) {
    const pdfUrl = `/api/libros/pdf/${idLibro}`;
    $('#iframePdf').attr('src', pdfUrl);
    $('#modalPdf').show(); // Mostrar el modal
}

// Función para cerrar el modal
function cerrarModalPdf() {
    $('#modalPdf').hide();
    $('#iframePdf').attr('src', ''); 
}
