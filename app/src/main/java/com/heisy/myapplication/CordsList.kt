package com.heisy.myapplication

/**
 * Класс, получаемый из CoordsApi
 * Нужен для превращения GeoJSON в POJO объёкт
 */
class CordsList {
    var type: String? = null
    var features: List<Feature>? = null
}

class Properties {
    var scalerank = 0
    var featurecla: String? = null
    var labelrank = 0
    var sovereignt: String? = null
    var sov_a3: String? = null
    var adm0_dif = 0
    var level = 0
    var type: String? = null
    var admin: String? = null
    var adm0_a3: String? = null
    var geou_dif = 0
    var geounit: String? = null
    var gu_a3: String? = null
    var su_dif = 0
    var subunit: String? = null
    var su_a3: String? = null
    var brk_diff = 0
    var name: String? = null
    var name_long: String? = null
    var brk_a3: String? = null
    var brk_name: String? = null
    var brk_group: Any? = null
    var abbrev: String? = null
    var postal: String? = null
    var formal_en: String? = null
    var formal_fr: Any? = null
    var note_adm0: Any? = null
    var note_brk: Any? = null
    var name_sort: String? = null
    var name_alt: Any? = null
    var mapcolor7 = 0
    var mapcolor8 = 0
    var mapcolor9 = 0
    var mapcolor13 = 0
    var pop_est = 0
    var gdp_md_est = 0
    var pop_year = 0
    var lastcensus = 0
    var gdp_year = 0
    var economy: String? = null
    var income_grp: String? = null
    var wikipedia = 0
    var fips_10_: String? = null
    var iso_a2: String? = null
    var iso_a3: String? = null
    var iso_n3: String? = null
    var un_a3: String? = null
    var wb_a2: String? = null
    var wb_a3: String? = null
    var woe_id = 0
    var woe_id_eh = 0
    var woe_note: String? = null
    var adm0_a3_is: String? = null
    var adm0_a3_us: String? = null
    var adm0_a3_un = 0
    var adm0_a3_wb = 0
    var continent: String? = null
    var region_un: String? = null
    var subregion: String? = null
    var region_wb: String? = null
    var name_len = 0
    var long_len = 0
    var abbrev_len = 0
    var tiny = 0
    var homepart = 0
    var filename: String? = null
}

/**
 * @property coordinates Список, где лежат необходимые координаты
 */
class Geometry {
    var type: String? = null

    // Нас будет интересовать именно этот ключ в получаемом GeoJSOn
    var coordinates: List<List<List<List<Double>>>>? = null
}

class Feature {
    var type: String? = null
    var properties: Properties? = null
    var geometry: Geometry? = null
}




