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


    am5.ready(function () {
        var root = am5.Root.new("chartdiv");
        root._logo.dispose();

        root.setThemes([
            am5themes_Animated.new(root)
        ]);

        var mapWrapper = root.container.children.push(
            am5.Container.new(root, {
                width: am5.percent(100),
                height: am5.percent(100),
                scale: 0.9, // escala personalizada
                centerX: am5.percent(50),
                centerY: am5.percent(50),
                x: am5.percent(50),
                y: am5.percent(50)
            })
        );

        var chart = mapWrapper.children.push(
            am5map.MapChart.new(root, {
                panX: "translateX",
                panY: "translateY",
                wheelY: "zoom",
                projection: am5map.geoMercator()
            })
        );

        // Control de zoom
        var zoomControl = am5map.ZoomControl.new(root, {
            x: am5.p0,
            y: am5.p100,
            centerX: am5.p0,
            centerY: am5.p100
        });

        zoomControl.setAll({
            layout: root.verticalLayout,
            scale: 1,
            width: 40,
            height: 80,
            marginLeft: 10,
            marginBottom: 10
        });

        zoomControl.plusButton.setAll({
            width: 40,
            height: 40
        });
        zoomControl.minusButton.setAll({
            width: 40,
            height: 40
        });
        chart.set("zoomControl", zoomControl);
        chart.children.push(zoomControl);

        // Mapa base
        var polygonSeries = chart.series.push(
            am5map.MapPolygonSeries.new(root, {
                geoJSON: am5geodata_worldLow,
                exclude: ["AQ"]
            })
        );

        polygonSeries.mapPolygons.template.setAll({
            tooltipText: "{name}",
            interactive: true,
            fill: am5.color(0xcccccc),
            stroke: am5.color(0xffffff),
            strokeWidth: 1
        });

        polygonSeries.mapPolygons.template.states.remove("hover");

        // Lista de países con visitas
        const countries = [
            { name: "Estados Unidos", latitude: 38.0, longitude: -97.0, color: 0xff0000, visitas: 10 },
            { name: "China", latitude: 35.8617, longitude: 104.1954, color: 0x0000ff, visitas: 5 },
            { name: "Japón", latitude: 36.2048, longitude: 138.2529, color: 0x00ff00, visitas: 7 },
            { name: "España", latitude: 40.4637, longitude: -3.7492, color: 0x800080, visitas: 3 },
            { name: "Brasil", latitude: -14.2350, longitude: -51.9253, color: 0xffa500, visitas: 8 },
            { name: "Rusia", latitude: 61.5240, longitude: 105.3188, color: 0xffff00, visitas: 4 }
        ];

        // Serie de puntos
        var pointSeries = chart.series.push(
            am5map.MapPointSeries.new(root, {
                latitudeField: "latitude",
                longitudeField: "longitude"
            })
        );

        pointSeries.bullets.push(function (root, series, dataItem) {
            return am5.Bullet.new(root, {
                sprite: am5.Circle.new(root, {
                    radius: 8,
                    tooltipText: `[bold]{name}[/]\nVisitas: {visitas}`,
                    cursorOverStyle: "pointer",
                    fill: am5.color(dataItem.dataContext.color),
                    stroke: am5.color(0xffffff),
                    strokeWidth: 2
                })
            });
        });



        pointSeries.data.setAll(countries);
    });

    am5.ready(function () {
        // Crear el root element
        var root = am5.Root.new("chartdiv2");
        root._logo.dispose();
    
        // Aplicar tema animado
        root.setThemes([am5themes_Animated.new(root)]);
    
        // Crear el gráfico de pastel
        var chart = root.container.children.push(
            am5percent.PieChart.new(root, {
                endAngle: 270,
                radius: am5.percent(60),
                y: -15 
            })
        );
    
        // Crear la serie
        var series = chart.series.push(
            am5percent.PieSeries.new(root, {
                valueField: "value",
                categoryField: "category",
                endAngle: 270
            })
        );
    
        // Estilo de los segmentos
        series.slices.template.setAll({
            stroke: am5.color(0xFFFFFF),
            strokeWidth: 2,
            strokeOpacity: 1
        });
    
        // Colores personalizados
        series.set("colors", am5.ColorSet.new(root, {
            colors: [
                am5.color(0x1E3A8A),
                am5.color(0x2563EB),
                am5.color(0x3B82F6),
                am5.color(0x60A5FA),
                am5.color(0x93C5FD),
                am5.color(0xBFDBFE),
                am5.color(0xDBEAFE),
                am5.color(0xEFF6FF)
            ],
            reuse: false
        }));
    
        // Ocultar etiquetas y líneas
        series.labels.template.set("visible", false);
        series.ticks.template.set("visible", false);
    
        // Estado oculto
        series.states.create("hidden", {
            endAngle: -90
        });
    
        // Datos por tipo de buques
        var dataBuques = [
            { category: "Carga General", value: 40 },
            { category: "Portacontenedores", value: 30 },
            { category: "Graneleros", value: 20 },
            { category: "Petroleros", value: 10 },
            { category: "Gaseros", value: 40 },
            { category: "Frigoríficos", value: 30 },
            { category: "Cargamento Rodado", value: 20 },
            { category: "Ganado", value: 10 }
        ];
    
        // Datos por tipo de carga
        var dataCarga = [
            { category: "RO (Carga RORO)", value: 35 },
            { category: "CS (Carga Suelta)", value: 25 },
            { category: "CN (Contenedores)", value: 15 },
            { category: "GL (Granel Limpio)", value: 25 },
            { category: "GS (Granel Sucio)", value: 25 },
            { category: "LQ (Liquido)", value: 25 }
        ];
    
        // Estado inicial
        var currentType = 'buques';
    
        // Cargar datos iniciales
        series.data.setAll(dataBuques);
        updateTitle();
    
        // Cambiar entre buques o cargas
        window.changeChart = function (type) {
            currentType = type;
    
            if (type === 'buques') {
                series.data.setAll(dataBuques);
            } else {
                series.data.setAll(dataCarga);
            }
    
            updateTitle();
        };
    
        // Actualizar título
        function updateTitle() {
            const title = document.getElementById("title__change");
    
            let base = currentType === 'buques'
                ? "Distribución Tipo De Buques"
                : "Distribución Tipo De Cargas";
    
            title.textContent = base;
        }
    
        // Animar entrada
        series.appear(1000, 100);
    });

});

