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
        const itemHeight = items[0].offsetHeight + 8; // altura del ítem más el margen
        const offset = index * itemHeight;
        indicator.style.transform = `translateY(${offset}px)`;
    }

    // Inicializamos la posición del indicador
    moveIndicatorTo(1);

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


    //Ventana Modal solicitud atraque
    const filas = document.querySelectorAll("#notificationBody tr");
    const modal = document.getElementById("modalNotificacion");
    const closeModal = document.getElementById("closeModal");

    const modalMatricula = document.getElementById("modalMatricula");
    const modalNombre = document.getElementById("modalNombre");
    const modalTipoBuque = document.getElementById("modalTipoBuque");
    const modalProcedencia = document.getElementById("modalProcedencia");
    const modalDestino = document.getElementById("modalDestino");
    const modalFechaSalida = document.getElementById("modalFechaSalida");
    const modalFechaEntrada = document.getElementById("modalFechaEntrada");


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
                modalMatricula.textContent = celdas[0].textContent.trim();
                modalNombre.textContent = celdas[1].textContent.trim();
                modalTipoBuque.textContent = celdas[2].textContent.trim();
                modalProcedencia.textContent = celdas[3].textContent.trim();
                modalDestino.textContent = celdas[4].textContent.trim();
                modalFechaSalida.textContent = celdas[5].textContent.trim();
                modalFechaEntrada.textContent = celdas[6].textContent.trim();

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

    //Validaciones de solicitud Atraque de registro
    const fieldAdd = {
        matricula: { regex: /^[A-Z]{2}-[A-Z]{3}-\d{5}$/, errorMessage: "La matrícula ingresada no es válida.Asegúrese de que esté en el formato correcto.(Ejemplo: PC-PAN-22388)" },
        nombreBuque: { regex: /^[A-Za-z\s\-\.]+$/, errorMessage: " El nombre del buque es obligatorio." },
        ancho: { regex: /^(?:50|[5-9][0-9]|[1-9][0-9]{2}|[1-4][0-9]{3}|5000)$/, errorMessage: "El ancho minima es de 50 y el maximo de 5000." },
        altura: { regex: /^(?:50|[5-9][0-9]|[1-9][0-9]{2}|[1-4][0-9]{3}|5000)$/, errorMessage: "La altura minima es de 50 y el maximo de 5000." },
        peso: { regex: /^(?:50|[5-9][0-9]|[1-9][0-9]{2}|[1-4][0-9]{3}|5000)$/, errorMessage: "El peso minimo es de 50 y el maximo de 1000." },
        puertoProcedencia: { regex: /^(?:[A-Za-zÁÉÍÓÚáéíóúüÜñÑ\s]+|[A-Z]{4,5})$/, errorMessage: "Puerto no válido. Ingrese un nombre valido (ej. Puerto de Barcelona) o un código válido(ej. ESBCN)." },
        puertoDestino: { regex: /^(?:[A-Za-zÁÉÍÓÚáéíóúüÜñÑ\s]+|[A-Z]{4,5})$/, errorMessage: "Puerto no válido. Ingrese un nombre valido (ej. Puerto de Barcelona) o un código válido(ej. ESBCN)." },
        fechaSalida: { regex: /^(0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[0-2])\/\d{4}$/, errorMessage: "Fecha no válida. Ingrese una fecha en formato dd/mm/yyyy (ej. 03/05/2025)" },
        fechaEntrada: { regex: /^(0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[0-2])\/\d{4}$/, errorMessage: "Fecha no válida. Ingrese una fecha en formato dd/mm/yyyy (ej. 04/05/2025)" },

    };

    // variables 
    const formulario = document.querySelector(".add__formulario");
    const advertencia = document.querySelector(".input__advertencia");
    const selectTipoBuque = document.getElementById("tipoBuque");
    const errorTipoBuque = document.querySelector(".error--tipoBuque");
    const selectPaisProcedencia = document.getElementById("selectPaisProcedencia");
    const errorPaisProcedencia = document.querySelector(".error--PaisProcedencia");
    const selectCiudadProcedencia = document.getElementById("selectCiudadProcedencia");
    const errorCiudadProcedencia = document.querySelector(".error--CiudadProcedencia");
    const selectPaisDestino = document.getElementById("selectPaisDestino");
    const errorPaisDestino = document.querySelector(".error--PaisDestino");
    const selectCiudadDestino = document.getElementById("selectCiudadDestino");
    const errorCiudadDestino = document.querySelector(".error--CiudadDestino");

    // Validar en tiempo real los inputs
    Object.keys(fieldAdd).forEach(fieldId => {
        const input = document.getElementById(fieldId);
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
    [selectTipoBuque, selectPaisProcedencia, selectCiudadProcedencia, selectPaisDestino, selectCiudadDestino].forEach(select => {
        select.addEventListener("change", () => {
            advertencia.style.display = "none";

            if (select.selectedIndex > 0) {
                select.style.border = "2px solid #0034de";
            } else {
                select.style.border = "";
            }

            if (select === selectTipoBuque && select.selectedIndex > 0) {
                errorTipoBuque.style.display = "none";
            }
            if (select === selectPaisProcedencia && select.selectedIndex > 0) {
                errorPaisProcedencia.style.display = "none";
            }

            if (select === selectCiudadProcedencia && select.selectedIndex > 0) {
                errorCiudadProcedencia.style.display = "none";
            }

            if (select === selectPaisDestino && select.selectedIndex > 0) {
                errorPaisDestino.style.display = "none";
            }

            if (select === selectCiudadDestino && select.selectedIndex > 0) {
                errorCiudadDestino.style.display = "none";
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
            const input = document.getElementById(fieldId);
            const regex = fieldAdd[fieldId].regex;

            if (!regex.test(input.value.trim())) {
                formularioValido = false;
                todosInputsValidos = false;
            }
        });


        const tipoBuqueSeleccionada = selectTipoBuque.selectedIndex > 0;
        const paisProcedenciaSeleccionada = selectPaisProcedencia.selectedIndex > 0;
        const ciudadProcedenciaSeleccionada = selectCiudadProcedencia.selectedIndex > 0;
        const paisDestinoSeleccionada = selectPaisDestino.selectedIndex > 0;
        const ciudadDestinoSeleccionada = selectCiudadDestino.selectedIndex > 0;


        // Caso: Inputs válidos, pero selects incompletos
        if (todosInputsValidos && (!tipoBuqueSeleccionada || !paisProcedenciaSeleccionada || !ciudadProcedenciaSeleccionada || !paisDestinoSeleccionada || !ciudadDestinoSeleccionada)) {
            if (!tipoBuqueSeleccionada) {
                errorTipoBuque.style.display = "block";
                selectTipoBuque.style.border = "2px solid #fd1f1f";
            }
            if (!paisProcedenciaSeleccionada) {
                errorPaisProcedencia.style.display = "block";
                selectPaisProcedencia.style.border = "2px solid #fd1f1f";
            }
            if (!ciudadProcedenciaSeleccionada) {
                errorCiudadProcedencia.style.display = "block";
                selectCiudadProcedencia.style.border = "2px solid #fd1f1f";
            }

            if (!paisDestinoSeleccionada) {
                errorPaisDestino.style.display = "block";
                selectPaisDestino.style.border = "2px solid #fd1f1f";
            }
            if (!ciudadDestinoSeleccionada) {
                errorCiudadDestino.style.display = "block";
                selectCiudadDestino.style.border = "2px solid #fd1f1f";
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
        errorTipoBuque.style.display = "none";
        errorPaisProcedencia.style.display = "none";
        errorCiudadProcedencia.style.display = "none";
        errorPaisDestino.style.display = "none";
        errorCiudadDestino.style.display = "none";

        // Enviar formulario
        formulario.submit();
    });

    // Api de pais y ciudad que barbaro
    // Función para cargar países en un select específico
    function cargarPaises(selectElement) {
        fetch("https://restcountries.com/v3.1/all")
            .then(res => res.json())
            .then(data => {
                const sortedCountries = data.sort((a, b) =>
                    a.name.common.localeCompare(b.name.common)
                );

                const countriesSet = new Set();

                sortedCountries.forEach(country => {
                    if (!countriesSet.has(country.name.common)) {
                        countriesSet.add(country.name.common);
                        const option = document.createElement("option");
                        option.value = country.name.common;
                        option.textContent = country.name.common;
                        selectElement.appendChild(option);
                    }
                });
            })
            .catch(error => console.error("Error al cargar países:", error));
    }

    // Función para cargar ciudades en un select según país
    function cargarCiudades(paisSelect, ciudadSelect) {
        paisSelect.addEventListener("change", () => {
            const selectedCountry = paisSelect.value;
            ciudadSelect.innerHTML = '<option disabled selected>Ciudad</option>';

            fetch("https://countriesnow.space/api/v0.1/countries/cities", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ country: selectedCountry })
            })
                .then(res => res.json())
                .then(data => {
                    const cities = data.data;
                    const citiesSet = new Set();

                    if (Array.isArray(cities) && cities.length > 0) {
                        cities.forEach(city => {
                            if (!citiesSet.has(city)) {
                                citiesSet.add(city);
                                const option = document.createElement("option");
                                option.value = city;
                                option.textContent = city;
                                ciudadSelect.appendChild(option);
                            }
                        });
                    } else {
                        const option = document.createElement("option");
                        option.disabled = true;
                        option.textContent = "No se encontraron ciudades.";
                        ciudadSelect.appendChild(option);
                    }
                })
                .catch(err => {
                    console.error("Error al obtener ciudades:", err);
                });
        });
    }

    // Cargar países en ambos select de país
    cargarPaises(selectPaisProcedencia);
    cargarPaises(selectPaisDestino);

    // Activar carga de ciudades en ambos casos
    cargarCiudades(selectPaisProcedencia, selectCiudadProcedencia);
    cargarCiudades(selectPaisDestino, selectCiudadDestino);

    //Validaciones de solicitud Atraque 
    const fieldEdit = {
        tuition: { regex: /^[A-Z]{2}-[A-Z]{3}-\d{5}$/, errorMessage: "La matrícula ingresada no es válida.Asegúrese de que esté en el formato correcto.(Ejemplo: PC-PAN-22388)" },
        nameVessel: { regex: /^[A-Za-z\s\-\.]+$/, errorMessage: " El nombre del buque es obligatorio." },
        ancho2: { regex: /^(?:50|[5-9][0-9]|[1-9][0-9]{2}|[1-4][0-9]{3}|5000)$/, errorMessage: "El ancho minima es de 50 y el maximo de 5000." },
        altura2: { regex: /^(?:50|[5-9][0-9]|[1-9][0-9]{2}|[1-4][0-9]{3}|5000)$/, errorMessage: "La altura minima es de 50 y el maximo de 5000." },
        peso2: { regex: /^(?:50|[5-9][0-9]|[1-9][0-9]{2}|[1-4][0-9]{3}|5000)$/, errorMessage: "El peso minimo es de 50 y el maximo de 1000." },
        portOrigin: { regex: /^(?:[A-Za-zÁÉÍÓÚáéíóúüÜñÑ\s]+|[A-Z]{4,5})$/, errorMessage: "Puerto no válido. Ingrese un nombre valido (ej. Puerto de Barcelona) o un código válido(ej. ESBCN)." },
        portDestination: { regex: /^(?:[A-Za-zÁÉÍÓÚáéíóúüÜñÑ\s]+|[A-Z]{4,5})$/, errorMessage: "Puerto no válido. Ingrese un nombre valido (ej. Puerto de Barcelona) o un código válido(ej. ESBCN)." },
        fechaSalida2: { regex: /^(0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[0-2])\/\d{4}$/, errorMessage: "Fecha no válida. Ingrese una fecha en formato dd/mm/yyyy (ej. 03/05/2025)" },
        fechaEntrada2: { regex: /^(0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[0-2])\/\d{4}$/, errorMessage: "Fecha no válida. Ingrese una fecha en formato dd/mm/yyyy (ej. 04/05/2025)" }

    };

    // variables 
    const formulario2 = document.querySelector(".edit__formulario");
    const advertenciaEdit = document.querySelector(".advertencia--change");
    const selectTipoBuque2 = document.getElementById("tipoBuque2");
    const errorTipoBuque2 = document.querySelector(".error--tipoBuque2");
    const selectPaisProcedencia2 = document.getElementById("selectPaisProcedencia2");
    const errorPaisProcedencia2 = document.querySelector(".error--PaisProcedencia2");
    const selectCiudadProcedencia2 = document.getElementById("selectCiudadProcedencia2");
    const errorCiudadProcedencia2 = document.querySelector(".error--CiudadProcedencia2");
    const selectPaisDestino2 = document.getElementById("selectPaisDestino2");
    const errorPaisDestino2 = document.querySelector(".error--PaisDestino2");
    const selectCiudadDestino2 = document.getElementById("selectCiudadDestino2");
    const errorCiudadDestino2 = document.querySelector(".error--CiudadDestino2");

    // Validar en tiempo real los inputs
    Object.keys(fieldEdit).forEach(fieldId => {
        const input = document.getElementById(fieldId);
        if (!input) return;

        const inputBox = input.closest(".input-box");
        const checkIcon = inputBox.querySelector(".ri-check-line");
        const errorIcon = inputBox.querySelector(".ri-close-line");
        const errorMessage = inputBox.nextElementSibling;
        const label = inputBox.querySelector("label");

        input.addEventListener("input", () => {
            advertenciaEdit.style.display = "none";

            const value = input.value.trim();
            if (value === "") {
                checkIcon.style.display = "none";
                errorIcon.style.display = "none";
                errorMessage.style.display = "none";
                input.style.border = "";
                label.style.color = "";
                inputBox.classList.remove("input-error");
            } else if (fieldEdit[fieldId].regex.test(value)) {
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
    [selectTipoBuque2, selectPaisProcedencia2, selectCiudadProcedencia2, selectPaisDestino2, selectCiudadDestino2].forEach(select => {
        select.addEventListener("change", () => {
            advertenciaEdit.style.display = "none";

            if (select.selectedIndex > 0) {
                select.style.border = "2px solid #0034de";
            } else {
                select.style.border = "";
            }

            if (select === selectTipoBuque2 && select.selectedIndex > 0) {
                errorTipoBuque2.style.display = "none";
            }
            if (select === selectPaisProcedencia2 && select.selectedIndex > 0) {
                errorPaisProcedencia2.style.display = "none";
            }

            if (select === selectCiudadProcedencia2 && select.selectedIndex > 0) {
                errorCiudadProcedencia2.style.display = "none";
            }

            if (select === selectPaisDestino2 && select.selectedIndex > 0) {
                errorPaisDestino2.style.display = "none";
            }

            if (select === selectCiudadDestino2 && select.selectedIndex > 0) {
                errorCiudadDestino2.style.display = "none";
            }


        });
    });

    // Validación al enviar el formulario2
    formulario2.addEventListener("submit", function (e) {
        e.preventDefault();

        let formularioValido = true;
        let todosInputsValidos = true;

        // Validar todos los inputs
        Object.keys(fieldEdit).forEach(fieldId => {
            const input = document.getElementById(fieldId);
            const regex = fieldEdit[fieldId].regex;

            if (!regex.test(input.value.trim())) {
                formularioValido = false;
                todosInputsValidos = false;
            }
        });


        const tipoBuqueSeleccionada2 = selectTipoBuque2.selectedIndex > 0;
        const paisProcedenciaSeleccionada2 = selectPaisProcedencia2.selectedIndex > 0;
        const ciudadProcedenciaSeleccionada2 = selectCiudadProcedencia2.selectedIndex > 0;
        const paisDestinoSeleccionada2 = selectPaisDestino2.selectedIndex > 0;
        const ciudadDestinoSeleccionada2 = selectCiudadDestino2.selectedIndex > 0;


        // Caso: Inputs válidos, pero selects incompletos
        if (todosInputsValidos && (!tipoBuqueSeleccionada2 || !paisProcedenciaSeleccionada2 || !ciudadProcedenciaSeleccionada2 || !paisDestinoSeleccionada2 || !ciudadDestinoSeleccionada2)) {
            if (!tipoBuqueSeleccionada2) {
                errorTipoBuque2.style.display = "block";
                selectTipoBuque2.style.border = "2px solid #fd1f1f";
            }
            if (!paisProcedenciaSeleccionada2) {
                errorPaisProcedencia2.style.display = "block";
                selectPaisProcedencia2.style.border = "2px solid #fd1f1f";
            }
            if (!ciudadProcedenciaSeleccionada2) {
                errorCiudadProcedencia2.style.display = "block";
                selectCiudadProcedencia2.style.border = "2px solid #fd1f1f";
            }

            if (!paisDestinoSeleccionada2) {
                errorPaisDestino2.style.display = "block";
                selectPaisDestino2.style.border = "2px solid #fd1f1f";
            }
            if (!ciudadDestinoSeleccionada2) {
                errorCiudadDestino2.style.display = "block";
                selectCiudadDestino2.style.border = "2px solid #fd1f1f";
            }


            advertenciaEdit.style.display = "block";
            return;
        }

        // Caso: Inputs o selects inválidos
        if (!formularioValido) {
            advertenciaEdit.style.display = "block";
            return;
        }

        // Todo correcto: ocultar advertencias y errores
        advertenciaEdit.style.display = "none";
        errorTipoBuque2.style.display = "none";
        errorPaisProcedencia2.style.display = "none";
        errorCiudadProcedencia2.style.display = "none";
        errorPaisDestino2.style.display = "none";
        errorCiudadDestino2.style.display = "none";

        // Enviar formulario
        formulario2.submit();
    });

    // Api de pais y ciudad que barbaro
    // Función para cargar países en un select específico
    function cargarPaises(selectElement) {
        fetch("https://restcountries.com/v3.1/all")
            .then(res => res.json())
            .then(data => {
                const sortedCountries = data.sort((a, b) =>
                    a.name.common.localeCompare(b.name.common)
                );

                const countriesSet = new Set();

                sortedCountries.forEach(country => {
                    if (!countriesSet.has(country.name.common)) {
                        countriesSet.add(country.name.common);
                        const option = document.createElement("option");
                        option.value = country.name.common;
                        option.textContent = country.name.common;
                        selectElement.appendChild(option);
                    }
                });
            })
            .catch(error => console.error("Error al cargar países:", error));
    }

    // Función para cargar ciudades en un select según país
    function cargarCiudades(paisSelect, ciudadSelect) {
        paisSelect.addEventListener("change", () => {
            const selectedCountry = paisSelect.value;
            ciudadSelect.innerHTML = '<option disabled selected>Ciudad</option>';

            fetch("https://countriesnow.space/api/v0.1/countries/cities", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ country: selectedCountry })
            })
                .then(res => res.json())
                .then(data => {
                    const cities = data.data;
                    const citiesSet = new Set();

                    if (Array.isArray(cities) && cities.length > 0) {
                        cities.forEach(city => {
                            if (!citiesSet.has(city)) {
                                citiesSet.add(city);
                                const option = document.createElement("option");
                                option.value = city;
                                option.textContent = city;
                                ciudadSelect.appendChild(option);
                            }
                        });
                    } else {
                        const option = document.createElement("option");
                        option.disabled = true;
                        option.textContent = "No se encontraron ciudades.";
                        ciudadSelect.appendChild(option);
                    }
                })
                .catch(err => {
                    console.error("Error al obtener ciudades:", err);
                });
        });
    }

    // Cargar países en ambos select de país
    cargarPaises(selectPaisProcedencia2);
    cargarPaises(selectPaisDestino2);

    // Activar carga de ciudades en ambos casos
    cargarCiudades(selectPaisProcedencia2, selectCiudadProcedencia2);
    cargarCiudades(selectPaisDestino2, selectCiudadDestino2);

    //Ventana Modal de editar y eliminar
    const botonesEditar = document.querySelectorAll('.icon--edit');
    const botonesEliminar = document.querySelectorAll('.icon--delete');

    // Modales
    const modalEditar = document.getElementById('modalConfirmacion');
    const modalEliminar = document.getElementById('modalConfirmacion2');
    const modalEditRegistro = document.getElementById('modalnewadd2');

    // Botones "Sí" y "No"
    const botonSiEditar = modalEditar.querySelector('.boton-si');
    const botonNoEditar = modalEditar.querySelector('.boton-no');
    const botonSiEliminar = modalEliminar.querySelector('.boton-si2');
    const botonNoEliminar = modalEliminar.querySelector('.boton-no2');

    // Id del formulario de eliminar una solicitud de atraque
    const formEliminar = document.getElementById('formEliminar');

    // Funciones para abrir y cerrar modales
    function abrirModal(modal) {
        modal.classList.remove('confirmacion--hidden');
        modal.classList.add('confirmacion--visible');
    }

    function cerrarModal(modal) {
        modal.classList.remove('confirmacion--visible');
        modal.classList.add('confirmacion--hidden');
    }

    // Eventos abrir modal editar
    botonesEditar.forEach(boton => {
        boton.addEventListener('click', () => {
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
    botonSiEditar.addEventListener('click', () => {
        cerrarModal(modalEditar);     // Cerramos el modal de confirmación
        modalEditRegistro.classList.remove('newadd--hidden');
        modalEditRegistro.classList.add('newadd--visible');
    });

    // Evento cuando hace click en "Si" en eliminar
    botonesEliminar.forEach(boton => {
        boton.addEventListener('click', () => {
            const id = boton.getAttribute('data-id');
            formEliminar.setAttribute('action', `/buques/solicitud-atraque/eliminar-solicitud/${id}`);
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
                doc.text('Tabla De Solicitud Atraque', data.settings.margin.left, 15);
            }
        });

        // Guardar el archivo PDF
        doc.save('Tabla__SolicitudAtraque.pdf');
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
        a.download = 'Tabla_SolicitudAtraque.json'; // Nombre del archivo JSON
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
        a.download = 'Tabla_SolicitudAtraque.csv'; // Nombre del archivo
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
            .filter((header, index) => index !== 7)  // Excluir el "Action" (índice 7)
            .map(header => header.innerText);

        // Extraer las filas de la tabla, asegurándonos de excluir la columna "Action"
        const rows = Array.from(table.querySelectorAll('tbody tr')).map(row => {
            const rowData = Array.from(row.querySelectorAll('td'))
                .filter((cell, index) => index !== 7)  // Excluir la columna "Action" (índice 7)
                .map(cell => cell.innerText);
            return rowData;
        });

        // Crear un libro de trabajo de Excel
        const ws = XLSX.utils.aoa_to_sheet([headers, ...rows]);  // Combina los encabezados con las filas
        const wb = XLSX.utils.book_new();  // Crea un libro nuevo
        XLSX.utils.book_append_sheet(wb, ws, "Datos");  // Agrega la hoja con los datos

        // Descargar el archivo Excel
        XLSX.writeFile(wb, "Tabla__SolicitudAtraque.xlsx");
    }

    // Escuchar el clic para exportar a Excel
    document.querySelector('.exportar-option:nth-child(4)').addEventListener('click', exportToExcel);

});

