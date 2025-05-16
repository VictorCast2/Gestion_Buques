document.addEventListener("DOMContentLoaded", function () {


    /* Menú desplegable del perfil */
    const subMenu = document.getElementById("SubMenu");
    const profileImage = document.querySelector(".nav__img");

    if (subMenu && profileImage) {
        profileImage.addEventListener("click", function () {
            subMenu.classList.toggle("open__menu");
        });

        // Opcional: cerrar menú al hacer clic fuera
        document.addEventListener("click", function (e) {
            if (!subMenu.contains(e.target) && !profileImage.contains(e.target)) {
                subMenu.classList.remove("open__menu");
            }
        });
    }

    //Sidebar
    const items = document.querySelectorAll('.sidebar__item');
    const indicator = document.querySelector('.sidebar__indicator');

    function moveIndicatorTo(index) {
        const item = items[index];
        const offsetTop = item.offsetTop;
        indicator.style.transform = `translateY(${offsetTop}px)`;
    }

    // Detectar cuál item tiene la clase 'active'
    let activeIndex = Array.from(items).findIndex(item => item.classList.contains('active'));
    if (activeIndex === -1) activeIndex = 0;

    // Mover el indicador al cargar la página
    moveIndicatorTo(activeIndex);

    // Manejo de clics para mover el indicador dinámicamente
    items.forEach((item, index) => {
        item.addEventListener('click', () => {
            items.forEach(el => el.classList.remove('active'));
            item.classList.add('active');
            moveIndicatorTo(index);
        });
    });

    //Lenguaje 
    const languageMenu = document.getElementById("languageMenu");
    const languageSelector = document.querySelector(".language-selector");
    const currentFlag = document.getElementById("currentFlag");

    if (languageMenu && languageSelector && currentFlag) {
        languageSelector.addEventListener("click", function () {
            languageMenu.style.display = languageMenu.style.display === "block" ? "none" : "block";
        });

        document.querySelectorAll(".language-option").forEach(option => {
            option.addEventListener("click", function () {
                const lang = this.dataset.lang;
                const flag = this.dataset.flag;

                currentFlag.src = flag;
                currentFlag.alt = this.querySelector("span").innerText;
                languageMenu.style.display = "none";
            });
        });

        document.addEventListener("click", function (event) {
            if (!languageMenu.contains(event.target) && !languageSelector.contains(event.target)) {
                languageMenu.style.display = "none";
            }
        });
    }

    //Pagination de la tabla
    const rows = Array.from(document.querySelectorAll("#notificationBody tr"));
    const paginationContainer = document.querySelector(".notification__pagination");
    const paginationButtons = paginationContainer.querySelectorAll(".pagination__btn");
    const flechas = paginationContainer.querySelectorAll(".pagination__flec");
    const paginationText = paginationContainer.querySelector(".pagination__text");
    const searchInput = document.getElementById("notificationSearch");

    const rowsPerPage = 4;
    let currentPage = 1;
    let filteredRows = [...rows];

    function showPage(page, dataRows) {
        currentPage = page;
        const start = (page - 1) * rowsPerPage;
        const end = start + rowsPerPage;

        // Ocultar todas
        rows.forEach(row => row.style.display = "none");

        // Mostrar solo las necesarias
        dataRows.slice(start, end).forEach(row => row.style.display = "");

        updatePagination(dataRows.length);
    }

    function updatePagination(totalItems) {
        const start = (currentPage - 1) * rowsPerPage + 1;
        const end = Math.min(currentPage * rowsPerPage, totalItems);
        paginationText.textContent = `Mostrando ${start}-${end} De ${totalItems}`;

        paginationButtons.forEach((btn, index) => {
            btn.classList.toggle("active", index + 1 === currentPage);
        });
    }

    paginationButtons.forEach((btn, index) => {
        btn.addEventListener("click", () => {
            showPage(index + 1, filteredRows);
        });
    });

    flechas[0].addEventListener("click", () => {
        if (currentPage > 1) {
            showPage(currentPage - 1, filteredRows);
        }
    });

    flechas[1].addEventListener("click", () => {
        const totalPages = Math.ceil(filteredRows.length / rowsPerPage);
        if (currentPage < totalPages) {
            showPage(currentPage + 1, filteredRows);
        }
    });

    // Búsqueda
    if (searchInput) {
        searchInput.addEventListener("input", () => {
            const searchValue = searchInput.value.toLowerCase();
            filteredRows = rows.filter(row => row.textContent.toLowerCase().includes(searchValue));

            // Reiniciar a la página 1 al buscar
            showPage(1, filteredRows);
        });
    }

    // Mostrar primera página al cargar
    showPage(1, filteredRows);

    //Ventana Modal Procesos
    const filas = document.querySelectorAll("#notificationBody tr");
    const modal = document.getElementById("modalNotificacion");
    const closeModal = document.getElementById("closeModal");

    const modalNumeroIdentificaccion = document.getElementById("modalNumeroIdentificaccion");
    const modalNombreAgente = document.getElementById("modalNombreAgente");
    const modalCorreoAgente = document.getElementById("modalCorreoAgente");
    const modalNit = document.getElementById("modalNit");
    const modalNombreEmpresa = document.getElementById("modalNombreEmpresa");
    const modalPais = document.getElementById("modalPais");
    const modalCiudad = document.getElementById("modalCiudad");
    const modalDireccion = document.getElementById("modalDireccion");
    const modalEmail = document.getElementById("modalEmail");
    const modalTelefono = document.getElementById("modalTelefono");

    filas.forEach(fila => {
        fila.addEventListener("click", (e) => {
            // Verifica si el clic fue en un icono de editar o eliminar
            const clickedElement = e.target;
            if (clickedElement.closest('.content__icon')) {
                // Detiene la propagación si fue en un icono de editar o eliminar
                e.stopPropagation();
                return;
            }

            // Si no fue un clic en un icono, abre el modal
            const celdas = fila.querySelectorAll("td");
            if (celdas.length >= 10) {
                modalNumeroIdentificaccion.textContent = celdas[0].textContent.trim();
                modalNombreAgente.textContent = celdas[1].textContent.trim();
                modalCorreoAgente.textContent = celdas[2].textContent.trim();
                modalNit.textContent = celdas[3].textContent.trim();
                modalNombreEmpresa.textContent = celdas[4].textContent.trim();
                modalPais.textContent = celdas[5].textContent.trim();
                modalCiudad.textContent = celdas[6].textContent.trim();
                modalDireccion.textContent = celdas[7].textContent.trim();
                modalEmail.textContent = celdas[8].textContent.trim();
                modalTelefono.textContent = celdas[9].textContent.trim();

                modal.classList.remove("hidden");
                modal.style.display = "block";
            }
        });
    });

    closeModal.addEventListener("click", () => {
        modal.style.display = "none";
    });

    window.addEventListener("click", e => {
        if (e.target === modal) {
            modal.style.display = "none";
        }
    });


    //cambiar modo oscuro
    const themeToggle = document.querySelector('.theme-toggle');
    const themeIcon = document.getElementById('theme-icon');

    themeToggle.addEventListener('click', () => {
        document.body.classList.toggle('dark-mode');

        // Cambiar el ícono
        if (document.body.classList.contains('dark-mode')) {
            themeIcon.classList.remove('ri-moon-fill');
            themeIcon.classList.add('ri-sun-line');
        } else {
            themeIcon.classList.remove('ri-sun-line');
            themeIcon.classList.add('ri-moon-fill');
        }
    });

    //Abrir Notificaciones
    const icon = document.getElementById('notificationIcon');
    const box = document.getElementById('notificationBox');

    icon.addEventListener('click', function (e) {
        e.stopPropagation(); // Evita que el clic se propague
        box.style.display = box.style.display === 'block' ? 'none' : 'block';
    });

    // Cerrar si se hace clic fuera
    document.addEventListener('click', function () {
        box.style.display = 'none';
    });

    // Evita cerrar al hacer clic dentro de la caja
    box.addEventListener('click', function (e) {
        e.stopPropagation();
    });


    //retornar a las paginas
    document.querySelectorAll('.sidebar__item').forEach(item => {
        item.addEventListener('click', () => {
            const url = item.getAttribute('data-url');
            if (url) {
                window.location.href = url;
            }
        });
    });

    //Ventana Modal de editar y eliminar
    const botonesEditar = document.querySelectorAll('.icon--si');
    const botonesEliminar = document.querySelectorAll('.icon--no');

    // Modales
    const modalEditar = document.getElementById('modalConfirmacion');
    const modalEliminar = document.getElementById('modalConfirmacion2');
    const modalEditRegistro = document.getElementById('modalnewadd2');

    // Botones "Sí" y "No"
    const botonSiEditar = modalEditar.querySelector('.boton-si');
    const botonNoEditar = modalEditar.querySelector('.boton-no');
    const botonSiEliminar = modalEliminar.querySelector('.boton-si2');
    const botonNoEliminar = modalEliminar.querySelector('.boton-no2');

    // Formularios
    const formEditar = document.getElementById('formAprobar')
    const formEliminar = document.getElementById('formRechazar');

    // Funciones para abrir y cerrar modales
    function abrirModal(modal) {
        modal.classList.remove('confirmacion--hidden');
        modal.classList.add('confirmacion--visible');
    }

    function cerrarModal(modal) {
        modal.classList.remove('confirmacion--visible');
        modal.classList.add('confirmacion--hidden');
    }

    // Variable global para guardar el id y la acción (estado aprobado o rechazado)
    let usuarioIdSeleccionado = null;
    let accionSeleccionada = null;

    // Eventos abrir modal aprobar (sí)
    botonesEditar.forEach(boton => {
        boton.addEventListener('click', () => {
            usuarioIdSeleccionado = boton.getAttribute('data-id');
            accionSeleccionada = true; // acción de aprobación
            abrirModal(modalEditar);
        });
    });

    // Eventos abrir modal rechazar (no)
    botonesEliminar.forEach(boton => {
        boton.addEventListener('click', () => {
            usuarioIdSeleccionado = boton.getAttribute('data-id');
            accionSeleccionada = false; // acción de rechazo
            abrirModal(modalEliminar);
        });
    });

    // Eventos cerrar modales al hacer click en "No"
    botonNoEditar.addEventListener('click', () => {
        cerrarModal(modalEditar);
    });

    botonNoEliminar.addEventListener('click', () => {
        cerrarModal(modalEliminar);
    });

    // Cerrar modal al hacer click fuera del contenido
    modalEditar.addEventListener('click', (e) => {
        if (e.target === modalEditar) {
            cerrarModal(modalEditar);
        }
    });

    modalEliminar.addEventListener('click', (e) => {
        if (e.target === modalEliminar) {
            cerrarModal(modalEliminar);
        }
    });

    // Evento para enviar formulario aprobar
    formEditar.addEventListener('submit', (e) => {
        e.preventDefault(); // Evita recarga completa
        if (usuarioIdSeleccionado !== null) {
            formEditar.action = `/buques/gestion-solicitud/validar-empresa/${usuarioIdSeleccionado}?aprovada=true`;
            formEditar.submit(); // Enviar formulario con método POST
        }
    });

    // Evento para enviar formulario rechazar
    formEliminar.addEventListener('submit', (e) => {
        e.preventDefault(); // Evita recarga completa
        if (usuarioIdSeleccionado !== null) {
            formEliminar.action = `/buques/gestion-solicitud/validar-empresa/${usuarioIdSeleccionado}?aprovada=false`;
            formEliminar.submit(); // Enviar formulario con método POST
        }
    });

    //Apertura de la exportacion
    const iconoDescarga = document.querySelector('.content__descarga i');
    const exportarDiv = document.querySelector('.exportar');

    iconoDescarga.addEventListener('click', () => {
        exportarDiv.classList.toggle('active');
    });

    // Función para exportar a PDF
    function exportToPDF() {
        const { jsPDF } = window.jspdf;
        const doc = new jsPDF();

        // Obtener la tabla
        const table = document.querySelector('.notification__table');

        // Extraer los encabezados de la tabla (sin incluir "Action")
        const headers = Array.from(table.querySelectorAll('th'))
            .filter((header, index) => index !== 7)  // Excluir el "Action" (índice 7)
            .map(header => header.innerText);

        // Extraer las filas de la tabla, asegurándonos de excluir la columna "Action"
        const rows = Array.from(table.querySelectorAll('tbody tr')).map(row => {
            return Array.from(row.querySelectorAll('td'))
                .filter((cell, index) => index !== 7)  // Excluir la columna "Action" (índice 7)
                .map(cell => cell.innerText);
        });

        // Usar autoTable para agregar la tabla al PDF con estilos mejorados
        doc.autoTable({
            head: [headers],  // Encabezados de la tabla
            body: rows,      // Datos de las filas
            startY: 20,      // Iniciar la tabla después de un margen superior
            theme: 'striped', // Aplicar el tema de filas alternas
            headStyles: {
                fillColor: [70, 130, 180], // Azul marino cielo para los encabezados
                textColor: 255, // Color del texto (blanco)
                fontStyle: 'bold', // Estilo de la fuente en negrita
                halign: 'center', // Alinear el texto de los encabezados al centro
            },
            bodyStyles: {
                fontSize: 10, // Tamaño de la fuente para las filas
                halign: 'center', // Alinear el texto al centro
            },
            alternateRowStyles: {
                fillColor: [245, 245, 245], // Color de fondo para las filas alternas
            },
            margin: { top: 20 }, // Margen superior
            didDrawPage: function (data) {
                // Agregar un título o encabezado personalizado en la parte superior
                doc.setFontSize(18);
                doc.text('Tabla De Procesos', data.settings.margin.left, 15);
            }
        });

        // Guardar el archivo PDF
        doc.save('Tabla__Procesos.pdf');
    }

    document.querySelector('.exportar-option:nth-child(1)').addEventListener('click', exportToPDF);

    // Función para exportar a JSON
    function exportToJSON() {
        // Obtener la tabla
        const table = document.querySelector('.notification__table');

        // Extraer los encabezados de la tabla (sin incluir "Action")
        const headers = Array.from(table.querySelectorAll('th'))
            .filter((header, index) => index !== 7)  // Excluir el "Action" (índice 7)
            .map(header => header.innerText);

        // Extraer las filas de la tabla, asegurándonos de excluir la columna "Action"
        const rows = Array.from(table.querySelectorAll('tbody tr')).map(row => {
            const rowData = Array.from(row.querySelectorAll('td'))
                .filter((cell, index) => index !== 7)  // Excluir la columna "Action" (índice 7)
                .map(cell => cell.innerText);
            let rowObj = {};

            // Asociar los valores con los encabezados
            headers.forEach((header, index) => {
                rowObj[header] = rowData[index];
            });

            return rowObj;
        });

        // Crear un objeto JSON con los datos
        const jsonData = JSON.stringify(rows, null, 2); // Formato bonito con sangrías de 2 espacios

        // Crear un enlace para descargar el archivo JSON
        const blob = new Blob([jsonData], { type: 'application/json' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'Tabla_Procesos.json'; // Nombre del archivo JSON
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a); // Limpiar el DOM
    }

    // Escuchar el clic para exportar a JSON
    document.querySelector('.exportar-option:nth-child(2)').addEventListener('click', exportToJSON);

    // Función para exportar a CSV
    function exportToCSV() {
        // Obtener la tabla
        const table = document.querySelector('.notification__table');

        // Extraer los encabezados de la tabla (sin incluir "Action")
        const headers = Array.from(table.querySelectorAll('th'))
            .filter((header, index) => index !== 7) // Excluir columna "Action"
            .map(header => `"${header.innerText.trim()}"`); // Poner en comillas por seguridad

        // Extraer las filas de la tabla, excluyendo la columna "Action"
        const rows = Array.from(table.querySelectorAll('tbody tr')).map(row => {
            const rowData = Array.from(row.querySelectorAll('td'))
                .filter((cell, index) => index !== 7) // Excluir columna "Action"
                .map(cell => `"${cell.innerText.trim().replace(/"/g, '""')}"`); // Escapar comillas
            return rowData.join(',');
        });

        // Combinar encabezados y filas
        const csvContent = [headers.join(','), ...rows].join('\n');

        // Crear un Blob y generar el enlace de descarga
        const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
        const url = URL.createObjectURL(blob);

        const a = document.createElement('a');
        a.href = url;
        a.download = 'Tabla_Procesos.csv'; // Nombre del archivo
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a); // Limpiar el DOM
    }

    // Escuchar el clic en el botón de exportar CSV
    document.querySelector('.exportar-option:nth-child(3)').addEventListener('click', exportToCSV);

    //Exportar A Excel
    function exportToExcel() {
        // Obtener la tabla
        const table = document.querySelector('.notification__table');

        // Extraer los encabezados de la tabla (sin incluir "Action")
        const headers = Array.from(table.querySelectorAll('th'))
            .filter((header, index) => index !== 7)  // Excluir el "Action" (índice 5)
            .map(header => header.innerText);

        // Extraer las filas de la tabla, asegurándonos de excluir la columna "Action"
        const rows = Array.from(table.querySelectorAll('tbody tr')).map(row => {
            const rowData = Array.from(row.querySelectorAll('td'))
                .filter((cell, index) => index !== 7)  // Excluir la columna "Action" (índice 5)
                .map(cell => cell.innerText);
            return rowData;
        });

        // Crear un libro de trabajo de Excel
        const ws = XLSX.utils.aoa_to_sheet([headers, ...rows]);  // Combina los encabezados con las filas
        const wb = XLSX.utils.book_new();  // Crea un libro nuevo
        XLSX.utils.book_append_sheet(wb, ws, "Datos");  // Agrega la hoja con los datos

        // Descargar el archivo Excel
        XLSX.writeFile(wb, "Tabla__Procesos.xlsx");
    }

    // Escuchar el clic para exportar a Excel
    document.querySelector('.exportar-option:nth-child(4)').addEventListener('click', exportToExcel);

    document.querySelectorAll('.button__add').forEach(button => {
        button.addEventListener('click', () => {
            const url = button.getAttribute('data-url');
            if (url) {
                window.location.href = url;
            }
        });
    });

});