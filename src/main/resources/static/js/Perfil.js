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

    //Ventana Modal de editar
    const btnEditar = document.getElementById("btnEditar");
    const modaleditar = document.getElementById("modalEditar");
    const btnCerrar = modaleditar.querySelector(".close");

    // Mostrar modal al hacer clic en el botón de editar
    btnEditar.addEventListener("click", () => {
        modaleditar.style.display = "flex"; // usar "flex" para centrar con flexbox
    });

    // Cerrar modal al hacer clic en la X
    btnCerrar.addEventListener("click", () => {
        modaleditar.style.display = "none";
    });

    // Cerrar modal al hacer clic fuera del contenido
    window.addEventListener("click", (event) => {
        if (event.target === modaleditar) {
            modaleditar.style.display = "none";
        }
    });

    //Validaciones De editar perfil
    const fieldEdit = {
        nombre: { regex: /^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]{3,}$/, errorMessage: "El nombre debe tener al menos 3 letras." },
        apellido: { regex: /^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]{3,}$/, errorMessage: "El apellido debe tener al menos 3 letras." },
        nameEmpresa: {regex: /^.{1,}$/,errorMessage: "El nombre de la empresa es obligatorio."},
        emailCompany: {regex: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/,errorMessage: "El correo solo puede contener letras,numeros,puntos,guiones y guion bajo."},
        niT: {regex: /^\d+$/,errorMessage: "El NIT no puede estar vacío y debe contener solo números."},
        email: { regex: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/, errorMessage: "El correo solo puede contener letras,numeros,puntos,guiones y guion bajo." },
        identificacion: { regex: /^\d{6,10}$/, errorMessage: "La cédula debe contener entre 6 y 10 dígitos." },
        address: {regex: /^(?=.*\d)(?=.*[A-Za-z])[A-Za-z0-9\s#.,-]{5,}$/,errorMessage: "Ingresa una dirección válida (ej. Calle 45 #10-23, 130002 o San Fernando, Calle 45 #10-23, 130002)."},
        phone: { regex: /^\d{1,10}$/, errorMessage: "El teléfono solo puede contener números (máx. 10)." }
    };

    const formEdit = document.querySelector(".edit__formulario");
    const advertenciaEdit = document.querySelector(".advertencia--change");
    const selectPaisEdit = document.getElementById("selectPais2");
    const selectCiudadEdit = document.getElementById("selectCiudad2");
    const errorPaisEdit = document.querySelector(".error--pais");
    const errorCiudadEdit = document.querySelector(".error--ciudad");
    const selectTipoIdentificacion = document.getElementById("tipoIdentificacion");
    const errortipoIdentificacion = document.querySelector(".error--tipoIdentificacion");

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
    [selectPaisEdit, selectCiudadEdit, selectTipoIdentificacion].forEach(select => {
        if (select) {
            select.addEventListener("change", () => {
                if (advertenciaEdit) advertenciaEdit.style.display = "none";

                if (select === selectPaisEdit && select.selectedIndex > 0 && errorPaisEdit) {
                    errorPaisEdit.style.display = "none";
                }
                if (select === selectCiudadEdit && select.selectedIndex > 0 && errorCiudadEdit) {
                    errorCiudadEdit.style.display = "none";
                }
                if (select === selectTipoIdentificacion && select.selectedIndex > 0 && errortipoIdentificacion) {
                    errortipoIdentificacion.style.display = "none";
                }
            });
        }
    });

    // Validación al enviar el formulario
    formEdit.addEventListener("submit", function (e) {
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

        const paisSeleccionado = selectPaisEdit.selectedIndex > 0;
        const ciudadSeleccionada = selectCiudadEdit.selectedIndex > 0;
        const tipoIdentificacionSeleccionada = selectTipoIdentificacion.selectedIndex > 0;

        // Caso: Inputs válidos, pero selects incompletos
        if (todosInputsValidos && (!paisSeleccionado || !ciudadSeleccionada || !tipoIdentificacionSeleccionada)) {
            if (!paisSeleccionado) errorPaisEdit.style.display = "block";
            if (!ciudadSeleccionada) errorCiudadEdit.style.display = "block";
            if (!tipoIdentificacionSeleccionada) errortipoIdentificacion.style.display = "block";
            advertenciaEdit.style.display = "block";
            return;
        }

        // Caso: Inputs o selects inválidos
        if (!formularioValido) {
            advertenciaEdit.style.display = "block";
            //  no mostramos errores de selects si inputs están mal
            return;
        }

        // Todo correcto: ocultar advertencias y errores
        advertenciaEdit.style.display = "none";
        errorPaisEdit.style.display = "none";
        errorCiudadEdit.style.display = "none";


        // Enviar formulario
        formEdit.submit();
    });


    //Validaciones cambiar contraseña
    const fields = {
        contraseñaAntigua: {
            regex: /^.{4,12}$/,
            errorMessage: "La contraseña debe tener entre 4 y 12 caracteres."
        },
        nuevaContraseña: {
            regex: /^.{4,12}$/,
            errorMessage: "La contraseña debe tener entre 4 y 12 caracteres."
        },
        CNContraseña: {
            regex: null,
            errorMessage: "Ambas contraseñas tienen que ser iguales."
        }
    };

    const advertencia = document.querySelector(".input__advertencia");

    // Validación en tiempo real
    Object.keys(fields).forEach(fieldId => {
        const input = document.getElementById(fieldId);
        if (!input) return;

        const inputBox = input.closest(".input-box");
        const checkIcon = inputBox.querySelector(".ri-check-line");
        const errorIcon = inputBox.querySelector(".ri-close-line");
        const errorMessage = inputBox.nextElementSibling;
        const label = inputBox.querySelector("label");

        input.addEventListener("input", () => {
            advertencia.style.display = "none"; // Oculta la advertencia general

            const value = input.value.trim();

            if (value === "") {
                inputBox.classList.remove("input-error");
                checkIcon.style.display = "none";
                errorIcon.style.display = "none";
                errorMessage.style.display = "none";
                input.style.border = "";
                label.style.color = "";
            } else if (fieldId === "CNContraseña") {
                const passwordValue = document.getElementById("nuevaContraseña").value.trim();
                if (value === passwordValue) {
                    checkIcon.style.display = "inline-block";
                    errorIcon.style.display = "none";
                    errorMessage.style.display = "none";
                    input.style.border = "2px solid #0034de";
                    label.style.color = "";
                    inputBox.classList.remove("input-error");
                } else {
                    checkIcon.style.display = "none";
                    errorIcon.style.display = "inline-block";
                    errorMessage.textContent = fields[fieldId].errorMessage;
                    errorMessage.style.display = "block";
                    input.style.border = "2px solid #fd1f1f";
                    label.style.color = "red";
                    inputBox.classList.add("input-error");
                }
            } else if (fields[fieldId].regex.test(value)) {
                checkIcon.style.display = "inline-block";
                errorIcon.style.display = "none";
                errorMessage.style.display = "none";
                input.style.border = "2px solid #0034de";
                label.style.color = "";
                inputBox.classList.remove("input-error");
            } else {
                checkIcon.style.display = "none";
                errorIcon.style.display = "inline-block";
                errorMessage.textContent = fields[fieldId].errorMessage;
                errorMessage.style.display = "block";
                input.style.border = "2px solid #fd1f1f";
                label.style.color = "red";
                inputBox.classList.add("input-error");
            }
        });
    });

    // Validación cruzada para confirmación
    const nuevaPasswordInput = document.getElementById("nuevaContraseña");
    const confirmarInput = document.getElementById("CNContraseña");

    nuevaPasswordInput.addEventListener("input", () => {
        confirmarInput.dispatchEvent(new Event("input"));
    });

    // Validación al enviar el formulario
    const form = document.querySelector(".formulario");

    form.addEventListener("submit", function (event) {
        let valid = true;

        Object.keys(fields).forEach(fieldId => {
            const input = document.getElementById(fieldId);
            const value = input.value.trim();

            if (
                value === "" ||
                (fieldId === "CNContraseña" && value !== document.getElementById("nuevaContraseña").value.trim()) ||
                (fieldId !== "CNContraseña" && !fields[fieldId].regex.test(value))
            ) {
                valid = false;
            }
        });

        if (!valid) {
            event.preventDefault();
            advertencia.style.display = "flex"; // Solo muestra la advertencia general
            // No se eliminan los estilos de error/success
        } else {
            advertencia.style.display = "none";
        }
    });

    // Ocultar advertencia general al escribir en cualquier input
    const allInputs = document.querySelectorAll("input");
    allInputs.forEach(input => {
        input.addEventListener("input", () => {
            advertencia.style.display = "none";
        });
    });

    //Ventana Modal Tabla
    const modal = document.getElementById("modalNotificacion");
    const closeBtn = document.getElementById("closeModal");

    const modalTipo = document.getElementById("modalTipo");
    const modalDescripcion = document.getElementById("modalDescripcion");
    const modalFecha = document.getElementById("modalFecha");
    const modalCodigo = document.getElementById("modalCodigo");
    const modalUbicacion = document.getElementById("modalUbicacion");
    const modalEstado = document.getElementById("modalEstado");

    const rows = document.querySelectorAll("#notificationBody tr");

    rows.forEach(row => {
        row.addEventListener("click", function (event) {
            if (event.target.closest('td').querySelector('input[type="checkbox"]')) {
                return;
            }

            const cells = row.querySelectorAll("td");
            modalTipo.textContent = cells[0].textContent;
            modalDescripcion.textContent = cells[1].textContent;
            modalFecha.textContent = cells[2].textContent;
            modalCodigo.textContent = cells[3].textContent;
            modalUbicacion.textContent = cells[4].textContent;
            modalEstado.textContent = cells[5].textContent;

            modal.style.display = "block";
        });
    });

    closeBtn.onclick = function () {
        modal.style.display = "none";
    };

    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    };


    //Javascript subir  Foto
    const inputFoto = document.getElementById("input-foto");
    const fotoPreview = document.getElementById("foto-preview");
    const resetButton = document.querySelector(".perfil__boton--reset");

    // Guardamos la URL original de la imagen (al cargar la página)
    const imagenOriginal = fotoPreview.src;

    // cargar una nueva imagen
    inputFoto.addEventListener("change", function (event) {
        const file = event.target.files[0];

        if (file) {
            const objectURL = URL.createObjectURL(file);
            fotoPreview.src = objectURL;
        }
    });

    // restablecer la imágen
    resetButton.addEventListener("click", function () {
        fotoPreview.src = imagenOriginal;
        inputFoto.value = "";
    });

    // Validaciones de Completar Empresa
    const formulario = document.querySelector(".perfil__formulario");
    const mensajeAdvertencia = document.querySelector(".advertencia--changed");

    const selectPaisPerfil = document.getElementById("selectPais");
    const selectCiudadPerfil = document.getElementById("selectCiudad");
    const errorPais = document.querySelector(".error--cambio1");
    const errorCiudad = document.querySelector(".error--cambio5");

    // Validaciones de Completar Empresa
    const perfilFields = {
        nit: {
            regex: /^\d+$/,
            errorMessage: "El NIT no puede estar vacío y debe contener solo números."
        },
        nombreEmpresa: {
            regex: /^.{1,}$/,
            errorMessage: "El nombre de la empresa es obligatorio."
        },
        direccion: {
            regex: /^(?=.*\d)(?=.*[A-Za-z])[A-Za-z0-9\s#.,-]{5,}$/,
            errorMessage: "Ingresa una dirección válida (ej. Calle 45 #10-23, 130002 o San Fernando, Calle 45 #10-23, 130002)."
        },
        emailEmpresa: {
            regex: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/,
            errorMessage: "El correo solo puede contener letras, números, puntos, guiones y guion bajo."
        },
        telefono: {
            regex: /^\d{1,10}$/,
            errorMessage: "El teléfono solo puede contener números y el máximo son 10 dígitos."
        }
    };

    // Validar en tiempo real los inputs
    Object.keys(perfilFields).forEach(fieldId => {
        const input = document.getElementById(fieldId);
        if (!input) return;

        const inputBox = input.closest(".input-box");
        const checkIcon = inputBox.querySelector(".ri-check-line");
        const errorIcon = inputBox.querySelector(".ri-close-line");
        const errorMessage = inputBox.nextElementSibling;
        const label = inputBox.querySelector("label");

        input.addEventListener("input", () => {
            mensajeAdvertencia.style.display = "none";

            const value = input.value.trim();
            if (value === "") {
                checkIcon.style.display = "none";
                errorIcon.style.display = "none";
                errorMessage.style.display = "none";
                input.style.border = "";
                label.style.color = "";
                inputBox.classList.remove("input-error");
            } else if (perfilFields[fieldId].regex.test(value)) {
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
    [selectPaisPerfil, selectCiudadPerfil].forEach(select => {
        select.addEventListener("change", () => {
            mensajeAdvertencia.style.display = "none";

            if (select === selectPaisPerfil && select.selectedIndex > 0) {
                errorPais.style.display = "none";
            }
            if (select === selectCiudadPerfil && select.selectedIndex > 0) {
                errorCiudad.style.display = "none";
            }
        });
    });

    // Validación al enviar el formulario
    formulario.addEventListener("submit", function (e) {
        e.preventDefault();

        let formularioValido = true;
        let todosInputsValidos = true;

        // Validar todos los inputs
        Object.keys(perfilFields).forEach(fieldId => {
            const input = document.getElementById(fieldId);
            const regex = perfilFields[fieldId].regex;

            if (!regex.test(input.value.trim())) {
                formularioValido = false;
                todosInputsValidos = false;
            }
        });

        const paisSeleccionado = selectPaisPerfil.selectedIndex > 0;
        const ciudadSeleccionada = selectCiudadPerfil.selectedIndex > 0;

        // Caso: Inputs válidos, pero selects incompletos
        if (todosInputsValidos && (!paisSeleccionado || !ciudadSeleccionada)) {
            if (!paisSeleccionado) errorPais.style.display = "block";
            if (!ciudadSeleccionada) errorCiudad.style.display = "block";
            mensajeAdvertencia.style.display = "block";
            return;
        }

        // Caso: Inputs o selects inválidos
        if (!formularioValido) {
            mensajeAdvertencia.style.display = "block";
            // NOTA: no mostramos errores de selects si inputs están mal
            return;
        }

        // Todo correcto: ocultar advertencias y errores
        mensajeAdvertencia.style.display = "none";
        errorPais.style.display = "none";
        errorCiudad.style.display = "none";

        // Enviar formulario
        formulario.submit();
    });

    // Api de pais y ciudad que barbaro
    // Función para cargar países en un select específico
    function cargarPaises(selectElement) {
        if (!selectElement) return;

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
        if (!paisSelect || !ciudadSelect) return;

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

    // Obtener todos los selects
    const selectPais = document.getElementById("selectPais");
    const selectCiudad = document.getElementById("selectCiudad");
    const selectPais2 = document.getElementById("selectPais2");
    const selectCiudad2 = document.getElementById("selectCiudad2");

    // Cargar países en ambos select de país
    cargarPaises(selectPais);
    cargarPaises(selectPais2);

    // Activar carga de ciudades en ambos casos
    cargarCiudades(selectPais, selectCiudad);
    cargarCiudades(selectPais2, selectCiudad2);

});