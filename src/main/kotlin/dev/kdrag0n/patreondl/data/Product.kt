package dev.kdrag0n.patreondl.data

import io.ktor.auth.*
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.CurrentTimestamp
import org.jetbrains.exposed.sql.`java-time`.timestamp
import kotlin.math.roundToInt

object Products : IntIdTable("products") {
    val path = text("path").index()
    val name = text("name")
    val priceCents = integer("price_cents").nullable()
    val creationTime = timestamp("creation_time").defaultExpression(CurrentTimestamp())
    val updateTime = timestamp("update_time").defaultExpression(CurrentTimestamp())
    val active = bool("active").default(true)
    val publicUrl = text("public_url").nullable().default(null)
}

class Product(id: EntityID<Int>) : IntEntity(id), Principal {
    companion object : IntEntityClass<Product>(Products)

    var path by Products.path
    var name by Products.name
    var priceCents by Products.priceCents
    var creationTime by Products.creationTime
    var updateTime by Products.updateTime
    var active by Products.active
    var publicUrl by Products.publicUrl

    val formattedPrice: String
        get() {
            val price = priceCents!!.toDouble() / 100
            // Always round down
            val intPart = price.toInt()
            val fracPart = (price.mod(1.0) * 100).toInt()

            return "$intPart.$fracPart"
        }
}