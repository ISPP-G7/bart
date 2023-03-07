<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<bart:layout pageName="Formulario de oferta">
    <h2>Formulario de oferta</h2>
    <form:form modelAttribute="anuncio" class="form-horizontal" id="add-anuncio-form">
        <div class="form-group has-feedback">
            <idus_martii:inputField label="Título" name="anuncio.titulo"/>
            <idus_martii:inputField label="Ubicación" name="anuncio.ubicacion"/>
            <idus_martii:inputField label="Precio" name="anuncio.precio"/>
            <idus_martii:inputField label="Especificaciones" name="anuncio.especificaciones"/>
            <idus_martii:inputField label="Estilo" name="anuncio.estilo"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                        <button class="btn btn-default" type="submit">Publicar anuncio</button>
                    
            </div>
        </div>
    </form:form>
</bart:layout>