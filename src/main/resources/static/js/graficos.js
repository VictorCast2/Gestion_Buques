document.addEventListener("DOMContentLoaded", function () {

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

        // Crear título fuera del gráfico, arriba del pastel
        var chartTitle = root.container.children.unshift(
            am5.Label.new(root, {
                text: "Distribución Tipo De Buques",
                fontSize: 20,
                fontWeight: "500",
                x: am5.percent(0),        // Alineado más a la izquierda
                y: 20,                    // Más abajo (ajusta el número si quieres bajarlo más)
                centerX: am5.percent(0),
                centerY: am5.percent(0),
                paddingLeft: 20          // Un poco de espacio a la izquierda
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

        // Estilo de los segmentos del pastel
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

        // Estado oculto (animación)
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

        // Datos por semana
        var dataByWeek = [
            { category: "Semana 1", value: 50 },
            { category: "Semana 2", value: 30 },
            { category: "Semana 3", value: 15 },
            { category: "Semana 4", value: 5 }
        ];

        // Datos por mes
        var dataByMonth = [
            { category: "Mes 1", value: 40 },
            { category: "Mes 2", value: 30 },
            { category: "Mes 3", value: 20 },
            { category: "Mes 4", value: 10 }
        ];

        // Datos por año
        var dataByYear = [
            { category: "Año 2021", value: 70 },
            { category: "Año 2022", value: 20 },
            { category: "Año 2023", value: 10 }
        ];

        // Establecer datos iniciales (tipo buques)
        series.data.setAll(dataBuques);

        // Función para cambiar el tipo de gráfico
        window.changeChart = function (type) {
            if (type === 'buques') {
                series.data.setAll(dataBuques);
                chartTitle.set("text", "Distribución Tipo De Buques");
            } else {
                series.data.setAll(dataCarga);
                chartTitle.set("text", "Distribución Tipo De Carga");
            }
        };

        // Función para cambiar el periodo de tiempo
        window.changeTimeframe = function (timeframe) {
            if (timeframe === 'week') {
                series.data.setAll(dataByWeek);
                chartTitle.set("text", "Distribución por semana");
            } else if (timeframe === 'month') {
                series.data.setAll(dataByMonth);
                chartTitle.set("text", "Distribución por mes");
            } else {
                series.data.setAll(dataByYear);
                chartTitle.set("text", "Distribución por año");
            }
        };

        // Animación inicial
        series.appear(1000, 100);
    });
});