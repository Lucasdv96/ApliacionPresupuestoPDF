package com.mrcerramiento.presupuesto.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "budgets")
data class Budget(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val clientName: String,
    val clientPhone: String,
    val clientEmail: String,
    val clientAddress: String,
    val budgetNumber: String,
    val createdDate: Long = System.currentTimeMillis(),
    val contract: String = getDefaultContract(),
    val notes: String = ""
)

@Entity(tableName = "budget_items")
data class BudgetItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val budgetId: Long,
    val description: String,
    val widthMm: Float,
    val heightMm: Float,
    val material: String,
    val specifications: String = "",
    val quantity: Int = 1,
    val imagePath: String? = null,
    val order: Int = 0
)

data class BudgetWithItems(
    val budget: Budget,
    val items: List<BudgetItem>
) {
    fun getTotalSurfaceM2(): Float {
        return items.sumOf {
            ((it.widthMm * it.heightMm) / 1_000_000.0) * it.quantity
        }.toFloat()
    }
}

fun getDefaultContract(): String {
    return """
En caso que el día de la instalación llueva, se reprogramará la fecha de colocación.
La colocación no incluye tizado de carpinterías a pisos superiores. En caso de aberturas por sus medidas excesivas no pudieran ser ingresadas, se entregarán los marcos por partes y se armará en obra.

En caso de recidídos/remodelaciones, por cambio de carpinterías sobre marcos existentes, se evaluará a luego de la medición los siguientes aspectos por posible adicional.

En el caso que sea necesario se deberá contar con andamios, escalera o grúas.

Estos trabajos mencionados podrán ocurrir durante la medición, bajo la visita de un representante de ABERTURAS ABRAHAM.

NOTA IMPORTANTE SOBRE INSTALACION!! EN EL CASO QUE LA OBRA NO SE PUEDA FINALIZAR EN EL DÍA, Y LLEVE MAS TIEMPO, EL CLIENTE DEBERÁ PROVEER A LA EMPRESA UN LUGAR PARA EL GUARDADO DE LAS CARPINTERÍAS / DVH QUE QUEDAN POR INSTALAR, SIENDO EL CLIENTE EL RESPONSABLE DEL ALOJAMIENTO DE LAS MISMAS. TANTO PREPARADO UN GUARDAVIDAS SEGURO O CONTRATANDO ALGÚN PERSONAL DE SEGURIDAD NOCTURNO PARA EL CUIDADO DE LAS MISMAS HASTA QUE SE FINALICE EL TRABAJO.

Para casos de vidrios que excedan las dimensiones, se recomiendan vidrios de seguridad (laminados o templados) sin cámara de aire.

Todo el personal que disponga la Empresa en la obra, contará con la cobertura de los seguros exigidos por ley.

Una vez colocadas las aberturas deberán retirar el film de protección ya que puede quedar adherido y dañar los perfiles por la exposición al sol o complicar el retiro del mismo con el paso del tiempo.

PLAZOS DE FABRICACIÓN Y ENTREGA
Una vez que se acuerde la totalidad de los detalles necesarios que un técnico de ABERTURAS ABRAHAM apruebe para poder generar la orden de trabajo y comenzar la producción, empezará a correr el plazo de entrega de 90 DÍAS PARA COLOR BLANCO Y FOLIADOS, EXCEPTO COLOR JET BLACK 90/120 DÍAS desde la fecha de firma de confirmación de la orden de producción, posterior a medición.

Posibles 30 días de ampliación de la fabricación de la obra, y al por causas ajenas a la empresa no se pudiera entregar la mercadería al cliente, la empresa cobrará un adicional en concepto de guarda y de acuerdo a los valores del mercado.

Una vez entregadas las carpinterías se solicitar á al cliente la firma un remito.

GARANTÍA
Los vidrios, así como tampoco los componentes de las aberturas NO poseen garantía de rotura luego de colocadas en obra. La empresa no será responsable en el caso de que las medidas fueren superiores a las máximas indicadas, quedando sin efecto la garantía tanto del izaje como del montaje y de los materiales.

La colocación cuenta con garantía de 12 (doce) meses, respecto al sellado, siempre y cuando la condición de obra sean las necesarias y las terminaciones exteriores sean hidrófugas.

La garantía no contempla roturas por personal ajeno, roturas o problemas por uso erróneo, golpes, etc. Los Servicios Técnicos serán sin cargo por un plazo de 12 (doce) meses.

DVH: GARANTÍA 1 AÑO
HERRAJES: GARANTÍA 3 AÑOS
PERFILERÍA TECNOPERFILES: 10 AÑOS

CONTRATACIÓN Y FORMAS DE PAGO
Este presupuesto NO es válido como factura.

* VALORES EXPRESADOS EN DÓLARES ESTADOUNIDENSES
* TIPO DE CAMBIO OFICIAL VENDEDOR BANCO NACIÓN DEL DÍA DE CADA PAGO
* FORMA DE PAGO A CONVENIR
* PLAZO DE ENTREGA: A CONVENIR

*CONSULTAR PRECIOS PARA PAGO CON TARJETA DE CRÉDITO.

Si opta a realizar una transferencia, una vez obtenido el comprobante, deberá ser enviado por email a la dirección administracion@aberturasbrabam.com.ar, indicando el número de presupuesto.

Los presupuestos que sean confirmados de manera no presencial, serán considerados aceptados al momento de efectualizarse el pago por cualquier medio electrónico comercialmente válido y habilitado por ABERTURAS ABRAHAM AA SAS Conociendo y aceptando las condiciones comerciales y de trabajo de la empresa.

VENTAS Y ATENCIÓN AL CLIENTE
Para cualquier consulta nos encontramos a disposición.
    """.trimIndent()
}
