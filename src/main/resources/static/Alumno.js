$(document).ready(function() {
    cargarLibros();

    $("#searchInput").on("input", function() {
        const nombre = $(this).val();
        if (nombre) {
            buscarLibros(nombre);
        } else {
            cargarLibros(); 
        }
    });

    function cargarLibros() {
        $.ajax({
            url: "/api/libros/listar-todos",
            type: "GET",
            dataType: "json",
            success: function(data) {
                mostrarResultados(data);
            },
            error: function(error) {
                console.error("Error al cargar libros:", error);
            }
        });
    }

    function buscarLibros(nombre) {
        $.ajax({
            url: `/api/libros/buscar?nombre=${nombre}`,
            type: "GET",
            dataType: "json",
            success: function(data) {
                mostrarResultados(data);
            },
            error: function(error) {
                console.error("Error al buscar libros:", error);
            }
        });
    }

    function mostrarResultados(libros) {
        const $tbody = $("#librosTable tbody");
        $tbody.empty(); 

        libros.forEach(libro => {
            const $tr = $("<tr></tr>");

            
            const $tdNombre = $("<td></td>").text(libro.nombre); 
            const $tdAutor = $("<td></td>").text(libro.autor);   
            const $tdGenero = $("<td></td>").text(libro.genero); 

            const $tdVistaPrevia = $("<td></td>");
            const $btnVistaPrevia = $("<button>Vista Previa</button>").click(function() {
                const pdfUrl = libro.ruta || `/api/libros/pdf/${libro.id_libro}`; 
                verLibro(pdfUrl, libro.archivo_pdf); 
            });
            $tdVistaPrevia.append($btnVistaPrevia);

            $tr.append($tdNombre, $tdAutor, $tdGenero, $tdVistaPrevia);
            $tbody.append($tr);
        });
    }

    // Función para abrir el modal con el PDF completo en un iframe
    function verLibro(pdfUrl, archivoLocal) {
        
        $("#pdfViewer").attr("src", pdfUrl); 
        $("#modal").show();

        $("#pdfViewer").on("error", function() {
         
            if (archivoLocal) {
                $("#pdfViewer").attr("src", archivoLocal); 
            } else {
                alert("No se pudo cargar el PDF.");
            }
        });
    }
    
    $("#closeModal").click(function() {
        cerrarModal();
    });

    $(window).click(function(event) {
        if (event.target.id === "modal") {
            cerrarModal();
        }
    });

    function cerrarModal() {
        $("#modal").hide();
        $("#pdfViewer").attr("src", ""); 
    }
});
