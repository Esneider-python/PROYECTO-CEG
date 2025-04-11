$(document).ready(function () {
    $('#id-ver-info').click(function () {
        $("#div-info").toggleClass("hidden")
        $("#linea-opcion").addClass("hidden");
    });

    $('#boton-opciones').click(function () {
        $("#linea-opcion").toggleClass("hidden")
    });

    //  funcion para mostrar mensaje informativo button eliminar
    $('#deleteButton').hover(function (event) {
        $('#delete-e').css({
            display: 'block',
            top: event.pageY + 10 + 'px',
            left: event.pageX + 10 + 'px'
        });
    }, function () {
        $('#delete-e').css('display', 'none');
    });

    //  funcion para mostrar mensaje informativo button modificar
    $('#modifyButton').hover(function (event) {
        $('#modify-e').css({
            display: 'block',
            top: event.pageY + 10 + 'px',
            left: event.pageX + 10 + 'px'
        });
    }, function () {
        $('#modify-e').css('display', 'none');
    });

    //  funcion para mostrar mensaje informativo button agregar registro identificador 
    $('#moverButton').hover(function (event) {
        $('#move-e').css({
            display: 'block',
            top: event.pageY + 10 + 'px',
            left: event.pageX + 10 + 'px'
        });

    }, function () {
        $('#move-e').css('display', 'none');
    });

    //  funcion para mostrar mensaje informativo button agregar registro identificador 
    $('#agregarRegistroButton').hover(function (event) {
        $('#add-e').css({
            display: 'block',
            top: event.pageY + 10 + 'px',
            left: event.pageX + 10 + 'px'
        });
    }, function () {
        $('#add-e').css('display', 'none');
    });

    //mostrar mensaje actualizar elemento
    $('#btnActualizar').hover(function (event) {
        $('#update-e').css({
            display: 'block',
            top: event.pageY + 10 + 'px',
            left: event.pageX + 10 + 'px'
        });
    }, function () {
        $('#update-e').css('display', 'none');
    });

    //mostrar informacion boton hacer reporte
    $('#btnReportar').hover(function (event) {
        $('#report-e').css({
            display: 'block',
            top: event.pageY + 10 + 'px',
            left: event.pageX + 10 + 'px'
        });
    }, function () {
        $('#report-e').css('display', 'none');
    });


    // ------------------------------------------------------------------
    // CONFIGURACION BOTON ELIMINAR ELEMENTO

    // Mostrar tarjeta de conformacion eliminacion
    const BotonDelete = document.getElementById("deleteButton");
    const card = document.querySelector(".superpuesto")
    const cardOverlay = document.getElementById("card-overlay");


    function MostrarCardEliminacion() {
        cardOverlay.classList.add("overlay");
        card.classList.remove("hidden");
    };

    // Eliminar fila elemento
    const botonConfirmar = document.querySelector("#confirmarBtn");
    function EliminarFilaElemento(e) {
        let elementoPadre = card.closest(".row")
        if (elementoPadre) {
            elementoPadre.remove();
        }
    }
    //  cancelar eliminacion
    const botonCancelar = document.querySelector("#rechazarBtn");

    function Cancelar() {
    card.classList.add("hidden")
        cardOverlay.classList.remove("overlay");
    };

    BotonDelete.addEventListener("click", MostrarCardEliminacion)
    botonConfirmar.addEventListener("click", EliminarFilaElemento)
    botonCancelar.addEventListener("click", Cancelar)


    
    const formAggIdentificador = document.querySelector(".superpuesto-agg-id");
    const btnAggId = document.querySelector("#agregarRegistroButton");
    const btnCancelarAgg = document.querySelector("#btn-cancelar-agregar");
    
    const cardOverlay1 = document.querySelector("#card-overlay1");
    
    function MostrarCardestado() {
        cardOverlay1.classList.add("overlay")
        cardEstado.classList.remove("hidden")
    };
    function OcultarCardEstado() {
        cardOverlay1.classList.remove("overlay");
        cardEstado.classList.add("hidden");
    };
    
    
    const cancelarMoverE = document.querySelector("#btn-cancelar-mover");
    const formularioMoverElemento = document.querySelector(".superpuesto-mover-elemento");
    const botonMoverElemn = document.querySelector("#moverButton");
        

    // CONFIGURACION BOTON MOVER ELEMENTO

    const cardOverlay2 = document.querySelector("#card-overlay2");
    function MostarFormMoverElemento() {
        cardOverlay2.classList.add("overlay")
        formularioMoverElemento.classList.remove("hidden");
        let elems = document.querySelectorAll("select");
        M.FormSelect.init(elems);
    };

    function OcultarFormularioMover() {
        formularioMoverElemento.classList.add("hidden")
        cardOverlay2.classList.remove("overlay")
    };

    // CONFIGURACION BOTON AGREGAR IDENTIFICADOR
    const cardOverlay3 = document.querySelector("#card-overlay3");

    function AgregarIdentificador() {
        cardOverlay3.classList.add("overlay")
        formAggIdentificador.classList.remove("hidden");
    };

    function OcultarFormId() {
        formAggIdentificador.classList.add("hidden");
        cardOverlay3.classList.remove("overlay")
    };

    botonEstado.addEventListener('click', MostrarCardestado);
    btnEstado.addEventListener("click", OcultarCardEstado);
    botonMoverElemn.addEventListener("click", MostarFormMoverElemento);
    cancelarMoverE.addEventListener("click", OcultarFormularioMover);
    btnAggId.addEventListener("click", AgregarIdentificador);
    btnCancelarAgg.addEventListener("click", OcultarFormId);
});

