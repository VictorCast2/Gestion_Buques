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

    const modalOperacion = document.getElementById("modalOperacion");
    const modalCarga = document.getElementById("modalCarga");
    const modalProducto = document.getElementById("modalProducto");
    const modalPeso = document.getElementById("modalPeso");
    const modalDescripcion = document.getElementById("modalDescripcion");

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
            if (celdas.length >= 6) {
                modalOperacion.textContent = celdas[0].textContent.trim();
                modalCarga.textContent = celdas[1].textContent.trim();
                modalProducto.textContent = celdas[2].textContent.trim();
                modalPeso.textContent = celdas[3].textContent.trim();
                modalDescripcion.textContent = celdas[4].textContent.trim();

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

    //Ventana Modal de add new Registro
    const modaladd = document.getElementById("modalnewadd");
    const btnAbrirModal = document.querySelector(".button__add");
    const btnCerrarModal = document.querySelector(".modal__close");

    // Mostrar el modal cuando el botón sea clickeado
    btnAbrirModal.addEventListener("click", function () {
        modaladd.classList.remove("newadd--hidden");
        modaladd.classList.add("newadd--visible");
    });

    // Cerrar el modal cuando se haga clic en el botón de cerrar
    btnCerrarModal.addEventListener("click", function () {
        modaladd.classList.add("newadd--hidden");
        modaladd.classList.remove("newadd--visible");
    });

    // Cerrar el modal cuando se haga clic fuera del contenido del modal
    modaladd.addEventListener("click", function (event) {
        if (event.target === modaladd) {  // Si el clic ocurrió en la capa de fondo del modal
            modaladd.classList.add("newadd--hidden");
            modaladd.classList.remove("newadd--visible");
        }
    });

    // Función para validar los formularios
    function validarFormulario(formulario) {
        const fieldAdd = {
            cantidad: { regex: /^(?:50|[5-9][0-9]|[1-9][0-9]{2}|10000)$/, errorMessage: "La cantidad minima es de 50 y el maximo de 1000." },
            descripcion: { regex: /^.{1,}$/, errorMessage: "Por favor, ingrese una descripción." }
        };

        const advertencia = formulario.querySelector(".input__advertencia");
        const selectTipoOperacion = formulario.querySelector("#tipoOperaciones");
        const errortipoOperacion = formulario.querySelector(".error--tipoOperaciones");
        const selecttipoCarga = formulario.querySelector("#tipoCarga");
        const errortipoCarga = formulario.querySelector(".error--tipoCarga");
        const selecttipoProducto = formulario.querySelector("#tipoProducto");
        const errortipoProducto = formulario.querySelector(".error--tipoProducto");


        // Validar en tiempo real los inputs
        Object.keys(fieldAdd).forEach(fieldId => {
            const input = formulario.querySelector(`#${fieldId}`);
            if (!input) return;

            const inputBox = input.closest(".input-box");
            const checkIcon = inputBox.querySelector(".ri-check-line");
            const errorIcon = inputBox.querySelector(".ri-close-line");
            const errorMessage = inputBox.nextElementSibling;
            const label = inputBox.querySelector("label");

            input.addEventListener("input", () => {
                advertencia.style.display = "none";

                const value = input.value.trim();
                if (value === "") {
                    checkIcon.style.display = "none";
                    errorIcon.style.display = "none";
                    errorMessage.style.display = "none";
                    input.style.border = "";
                    label.style.color = "";
                    inputBox.classList.remove("input-error");
                } else if (fieldAdd[fieldId].regex.test(value)) {
                    checkIcon.style.display = "inline-block";
                    errorIcon.style.display = "none";
                    errorMessage.style.display = "none";
                    input.style.border = "2px solid #0034de";
                    label.style.color = "";
                    inputBox.classList.remove("input-error");
                } else {
                    checkIcon.style.display = "none";
                    errorIcon.style.display = "inline-block";
                    errorMessage.style.display = "block";
                    input.style.border = "2px solid #fd1f1f";
                    label.style.color = "red";
                    inputBox.classList.add("input-error");
                }
            });
        });

        // Ocultar advertencias y errores de select al interactuar
        [selectTipoOperacion, selecttipoCarga, selecttipoProducto].forEach(select => {
            select.addEventListener("change", () => {
                advertencia.style.display = "none";

                if (select.selectedIndex > 0) {
                    select.style.border = "2px solid #0034de";
                } else {
                    select.style.border = "";
                }

                if (select === selectTipoOperacion && select.selectedIndex > 0) {
                    errortipoOperacion.style.display = "none";
                }
                if (select === selecttipoCarga && select.selectedIndex > 0) {
                    errortipoCarga.style.display = "none";
                }

                if (select === selecttipoProducto && select.selectedIndex > 0) {
                    errortipoProducto.style.display = "none";
                }

            });
        });

        // Validación al enviar el formulario
        formulario.addEventListener("submit", function (e) {
            e.preventDefault();

            let formularioValido = true;
            let todosInputsValidos = true;

            // Validar todos los inputs
            Object.keys(fieldAdd).forEach(fieldId => {
                const input = formulario.querySelector(`#${fieldId}`);
                const regex = fieldAdd[fieldId].regex;

                if (!regex.test(input.value.trim())) {
                    formularioValido = false;
                    todosInputsValidos = false;
                }
            });

            const tipoOperacionSeleccionada = selectTipoOperacion.selectedIndex > 0;
            const tipoCargaSeleccionada = selecttipoCarga.selectedIndex > 0;
            const tipoProductoSeleccionada = selecttipoProducto.selectedIndex > 0;


            // Caso: Inputs válidos, pero selects incompletos
            if (todosInputsValidos && (!tipoOperacionSeleccionada || !tipoCargaSeleccionada || !tipoProductoSeleccionada)) {
                if (!tipoOperacionSeleccionada) {
                    errortipoOperacion.style.display = "block";
                    selectTipoOperacion.style.border = "2px solid #fd1f1f";
                }
                if (!tipoCargaSeleccionada) {
                    errortipoCarga.style.display = "block";
                    selecttipoCarga.style.border = "2px solid #fd1f1f";
                }
                if (!tipoProductoSeleccionada) {
                    errortipoProducto.style.display = "block";
                    selecttipoProducto.style.border = "2px solid #fd1f1f";
                }


                advertencia.style.display = "block";
                return;
            }

            // Caso: Inputs o selects inválidos
            if (!formularioValido) {
                advertencia.style.display = "block";
                return;
            }

            // Todo correcto: ocultar advertencias y errores
            advertencia.style.display = "none";
            errortipoOperacion.style.display = "none";
            errortipoCarga.style.display = "none";
            errortipoProducto.style.display = "none";

            // Enviar formulario
            formulario.submit();
        });
    }

    // Llamar a la función de validación para ambos formularios
    document.querySelectorAll('.add__formulario').forEach(form => {
        validarFormulario(form);
    });

    //Ventana Modal de editar y eliminar
    const botonesEditar = document.querySelectorAll('.icon--edit');
    const botonesEliminar = document.querySelectorAll('.icon--delete');

    // Modales
    const modalEditar = document.getElementById('modalConfirmacion');
    const modalEliminar = document.getElementById('modalConfirmacion2');
    const modalEditRegistro = document.getElementById('modalnewadd2');

    // Formularios
    const formEditar = document.getElementById('formEditar');
    const formEliminar = document.getElementById('formEliminar');

    // Botones "Sí" y "No"
    const botonSiEditar = modalEditar.querySelector('.boton-si');
    const botonNoEditar = modalEditar.querySelector('.boton-no');
    const botonNoEliminar = modalEliminar.querySelector('.boton-no2');

    // Funciones para abrir y cerrar modales
    function abrirModal(modal) {
        modal.classList.remove('confirmacion--hidden');
        modal.classList.add('confirmacion--visible');
    }

    function cerrarModal(modal) {
        modal.classList.remove('confirmacion--visible');
        modal.classList.add('confirmacion--hidden');
    }

    // Variable global para guardar el id de la solicitud que se va a editar
    let idEditarSeleccionado = null;

    // Eventos abrir modal editar
    botonesEditar.forEach(boton => {
        boton.addEventListener('click', () => {
            idEditarSeleccionado = boton.getAttribute('data-id'); // Guardamos el id
            abrirModal(modalEditar);
        });
    });

    // Eventos abrir modal eliminar
    botonesEliminar.forEach(boton => {
        boton.addEventListener('click', () => {
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

    // Evento cuando hacen click en "Sí" en editar
    botonSiEditar.addEventListener('click', async () => {
        cerrarModal(modalEditar);   // Cerramos el modal de confirmación
        // Establecer la acción del formulario con el ID seleccionado
        formEditar.setAttribute('action', `/buques/Procesos/actualizar-proceso/${idEditarSeleccionado}`);

        // Abrimos el modal de edición
        modalEditRegistro.classList.remove('newadd--hidden');
        modalEditRegistro.classList.add('newadd--visible');
    });

    // Evento cuando hace click en "Si" en eliminar
    botonesEliminar.forEach(boton => {
        boton.addEventListener('click', () => {
            const id = boton.getAttribute('data-id');
            formEliminar.setAttribute('action', `/buques/Procesos/eliminar-factura/${id}`);
            abrirModal(modalEliminar);
        });
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

    const botonCerrarModalEdit = modalEditRegistro.querySelector('.modal__close');

    botonCerrarModalEdit.addEventListener('click', () => {
        modalEditRegistro.classList.remove('newadd--visible');
        modalEditRegistro.classList.add('newadd--hidden');
    });

    modalEditRegistro.addEventListener('click', (e) => {
        if (e.target === modalEditRegistro) {
            modalEditRegistro.classList.remove('newadd--visible');
            modalEditRegistro.classList.add('newadd--hidden');
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
            .filter((header, index) => index !== 5)  // Excluir el "Action" (índice 5)
            .map(header => header.innerText);

        // Extraer las filas de la tabla, asegurándonos de excluir la columna "Action"
        const rows = Array.from(table.querySelectorAll('tbody tr')).map(row => {
            return Array.from(row.querySelectorAll('td'))
                .filter((cell, index) => index !== 5)  // Excluir la columna "Action" (índice 5)
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
            .filter((header, index) => index !== 5)  // Excluir el "Action" (índice 5)
            .map(header => header.innerText);

        // Extraer las filas de la tabla, asegurándonos de excluir la columna "Action"
        const rows = Array.from(table.querySelectorAll('tbody tr')).map(row => {
            const rowData = Array.from(row.querySelectorAll('td'))
                .filter((cell, index) => index !== 5)  // Excluir la columna "Action" (índice 5)
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
            .filter((header, index) => index !== 5) // Excluir columna "Action"
            .map(header => `"${header.innerText.trim()}"`); // Poner en comillas por seguridad

        // Extraer las filas de la tabla, excluyendo la columna "Action"
        const rows = Array.from(table.querySelectorAll('tbody tr')).map(row => {
            const rowData = Array.from(row.querySelectorAll('td'))
                .filter((cell, index) => index !== 5) // Excluir columna "Action"
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
            .filter((header, index) => index !== 5)  // Excluir el "Action" (índice 5)
            .map(header => header.innerText);

        // Extraer las filas de la tabla, asegurándonos de excluir la columna "Action"
        const rows = Array.from(table.querySelectorAll('tbody tr')).map(row => {
            const rowData = Array.from(row.querySelectorAll('td'))
                .filter((cell, index) => index !== 5)  // Excluir la columna "Action" (índice 5)
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
    })

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

    document.querySelectorAll('.sidebar__item').forEach(item => {
        item.addEventListener('click', () => {
            const url = item.getAttribute('data-url');
            if (url) {
                window.location.href = url;
            }
        });
    });

    
});
