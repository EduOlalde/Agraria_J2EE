// Muestra la sección seleccionada y oculta las demás
function showSection(sectionId) {
    document.querySelectorAll('.section-content').forEach(section => {
        section.style.display = 'none';
    });
    document.getElementById(sectionId).style.display = 'block';

    if (sectionId === 'sec_maquinista') {
        showSubSection('maquinista_trabajos');
    }
    if (sectionId === 'sec_usuario') {
        showSubSection('usuario_modDatos');
    }
}

// Muestra la subsección seleccionada y oculta las demás
function showSubSection(subSectionId) {
    document.querySelectorAll('.sub-section-content').forEach(subSection => {
        subSection.style.display = 'none';
    });
    document.getElementById(subSectionId).style.display = 'block';
}

// Guardar la sección y subsección activas en el almacenamiento de sesión.
function guardarSeccionActiva(seccion, subSeccion) {
    if (seccion !== '' && seccion !== null) {
        sessionStorage.setItem('seccionActiva', seccion);
    }
    if (subSeccion !== '' && subSeccion !== null) {
        sessionStorage.setItem('subSeccionActiva', subSeccion);
    }

    sessionStorage.setItem('posicionScroll', window.scrollY);
}


// Muestra la sección y subsección activas desde el almacenamiento de sesión.
function mostrarSeccionActiva() {
    if (sessionStorage.getItem('seccionActiva')) {
        showSection(sessionStorage.getItem('seccionActiva'));
        sessionStorage.removeItem('seccionActiva');
    }
    if (sessionStorage.getItem('subSeccionActiva')) {
        showSubSection(sessionStorage.getItem('subSeccionActiva'));
        sessionStorage.removeItem('subSeccionActiva');
    }
    if (sessionStorage.getItem('posicionScroll')) {
        setTimeout(() => {
            const posicionScroll = sessionStorage.getItem("posicionScroll");
            window.scrollTo({ top: parseInt(posicionScroll), behavior: "smooth" });
            sessionStorage.removeItem('posicionScroll');
        }, 1200);

    }

}

// Elimina parámetros GET de la URL sin recargar la página
function limpiarParametros() {
    if (window.history.replaceState) {
        window.history.replaceState(null, null, window.location.pathname);
    }
}


function inicio() {
    mostrarSeccionActiva();
    limpiarParametros();

    document.querySelectorAll(".selectEstadoMaquinas").forEach(select => {
        select.addEventListener("change", (ev) => {
            const form = select.closest("form");
            if (form) {
                guardarSeccionActiva('sec_administrador', 'admin_maquinas');
                form.submit();
            }
        });
    });

}

document.addEventListener("DOMContentLoaded", inicio);