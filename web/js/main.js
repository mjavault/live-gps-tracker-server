var viewer = new Cesium.Viewer('cesiumContainer', {
    imageryProvider : new Cesium.BingMapsImageryProvider({
        url: '//dev.virtualearth.net',
        mapStyle: Cesium.BingMapsStyle.AERIAL_WITH_LABELS
    }),
    terrainProvider: new Cesium.CesiumTerrainProvider({
        url : '//assets.agi.com/stk-terrain/world',
        requestWaterMask : true,
        requestVertexNormals : true
    }),
    baseLayerPicker : false
});
var values = {};
var entities = {};
var refreshRequestPending = false;
var windowFocus = true;

$(document).ready(function () {
    //create cesium objects
    entities["flight-path"] = viewer.entities.add({
        name: 'Predicted flight path',
        polyline: {
            positions: Cesium.Cartesian3.fromDegreesArrayHeights([]),
            width: 5,
            material: new Cesium.PolylineOutlineMaterialProperty({
                color: Cesium.Color.BLUE,
                outlineWidth: 2,
                outlineColor: Cesium.Color.BLACK
            })
        }
    });
    entities.track = viewer.entities.add({
        name: 'Current balloon track',
        polyline: {
            positions: Cesium.Cartesian3.fromDegreesArrayHeights([]),
            width: 5,
            material: new Cesium.PolylineOutlineMaterialProperty({
                color: Cesium.Color.ORANGE,
                outlineWidth: 2,
                outlineColor: Cesium.Color.BLACK
            })
        }
    });

    //detect loss of focus and stop refreshing
    $(window).focus(function () {
        windowFocus = true;
        console.log("#Focus in");
    }).blur(function () {
        windowFocus = false;
        console.log("#Focus lost");
    });

    refreshDataFull("flight-path", function() {
        viewer.zoomTo(entities["flight-path"]);
        refreshDataTimer();
    });
});

function refreshDataTimer() {
    if (!refreshRequestPending && windowFocus) {
        refreshRequestPending = true;
        if (values["track"] && values["track"].length > 0) {
            refreshDataDiff("track");
        } else {
            refreshDataFull("track");
        }
    }
    setTimeout(refreshDataTimer, 10000);
}

function refreshDataFull(key, after) {
    $.ajax({
        url: "api/full",
        dataType: "json",
        data: {key: key},
        success: function (data) {
            //get values
            var v = data.values;
            if (v && v.length) {
                //update map
                values[key] = v;
                entities[key].polyline.positions = Cesium.Cartesian3.fromDegreesArrayHeights(values[key]);
            }
            refreshRequestPending = false;
            if(after) {
                after();
            }
        }
    });
}

function refreshDataDiff(key, after) {
    $.ajax({
        url: "api/diff",
        dataType: "json",
        data: {key: key},
        success: function (data) {
            //get values
            var v = data.diff;
            if (v && v.length) {
                //update map
                if (values[key]) {
                    values[key] = values[key].concat(v);
                } else {
                    values[key] = v;
                }
                entities[key].polyline.positions = Cesium.Cartesian3.fromDegreesArrayHeights(values[key]);
            }
            refreshRequestPending = false;
            if(after) {
                after();
            }
        }
    });
}