package com.onetwo.estateservice.common

class GeomUtil {
    companion object {
        fun createPolygonWKT(minX: Double, minY: Double, maxX: Double, maxY: Double): String {
            return "POLYGON(($minX $minY, $maxX $minY, $maxX $maxY, $minX $maxY, $minX $minY))"
        }

    }
}