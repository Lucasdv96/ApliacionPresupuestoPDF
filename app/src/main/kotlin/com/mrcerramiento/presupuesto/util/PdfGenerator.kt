package com.mrcerramiento.presupuesto.util

import android.content.Context
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.PageSize
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.mrcerramiento.presupuesto.data.models.BudgetWithItems
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PdfGenerator(private val context: Context) {

    fun generatePdf(budgetData: BudgetWithItems): File? {
        return try {
            val file = File(
                context.getExternalFilesDir(null),
                "Presupuesto_${budgetData.budget.budgetNumber}.pdf"
            )

            val document = Document(PageSize.A4)
            PdfWriter.getInstance(document, FileOutputStream(file))
            document.open()

            val colorScheme = mapOf(
                "primary" to BaseColor(164, 198, 57),
                "text" to BaseColor(51, 51, 51),
                "lightGray" to BaseColor(245, 245, 245)
            )

            // Header
            addHeader(document, budgetData, colorScheme)

            // Client Info
            addClientInfo(document, budgetData, colorScheme)

            // Items Table
            addItemsTable(document, budgetData, colorScheme)

            // Summary
            addSummary(document, budgetData, colorScheme)

            // Contract
            addContract(document, budgetData, colorScheme)

            document.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun addHeader(
        document: Document,
        budgetData: BudgetWithItems,
        colorScheme: Map<String, BaseColor>
    ) {
        val headerTable = PdfPTable(2)
        headerTable.widthPercentage = 100f
        headerTable.setWidths(floatArrayOf(70f, 30f))

        val titleCell = PdfPCell()
        titleCell.border = Rectangle.NO_BORDER
        titleCell.paddingBottom = 10f

        val titleFont = Font(Font.FontFamily.HELVETICA, 28f, Font.BOLD, colorScheme["primary"])
        titleCell.addElement(Paragraph("MR CERRAMIENTOS", titleFont))

        val dateFont = Font(Font.FontFamily.HELVETICA, 11f, Font.NORMAL, colorScheme["text"])
        val dateCell = PdfPCell()
        dateCell.border = Rectangle.NO_BORDER
        dateCell.paddingBottom = 10f
        dateCell.horizontalAlignment = Element.ALIGN_RIGHT
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateCell.addElement(Paragraph("Fecha: ${dateFormat.format(Date(budgetData.budget.createdDate))}", dateFont))

        headerTable.addCell(titleCell)
        headerTable.addCell(dateCell)

        document.add(headerTable)

        val budgetNumberFont = Font(Font.FontFamily.HELVETICA, 14f, Font.BOLD, colorScheme["primary"])
        document.add(Paragraph("Presupuesto #${budgetData.budget.budgetNumber}", budgetNumberFont))
        document.add(Paragraph(" "))
    }

    private fun addClientInfo(
        document: Document,
        budgetData: BudgetWithItems,
        colorScheme: Map<String, BaseColor>
    ) {
        val font = Font(Font.FontFamily.HELVETICA, 10f, Font.NORMAL, colorScheme["text"])
        val boldFont = Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD, colorScheme["text"])

        document.add(Paragraph("INFORMACIÓN DEL CLIENTE", boldFont))
        document.add(Paragraph("Nombre: ${budgetData.budget.clientName}", font))
        document.add(Paragraph("Teléfono: ${budgetData.budget.clientPhone}", font))
        document.add(Paragraph("Email: ${budgetData.budget.clientEmail}", font))
        document.add(Paragraph("Dirección: ${budgetData.budget.clientAddress}", font))
        document.add(Paragraph(" "))
    }

    private fun addItemsTable(
        document: Document,
        budgetData: BudgetWithItems,
        colorScheme: Map<String, BaseColor>
    ) {
        val table = PdfPTable(5)
        table.widthPercentage = 100f
        table.setWidths(floatArrayOf(20f, 20f, 20f, 20f, 20f))

        val headerFont = Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD, BaseColor.WHITE)
        val headerColor = colorScheme["primary"]

        val headers = listOf("Descripción", "Dimensiones", "Material", "Especificaciones", "Cant.")
        headers.forEach { header ->
            val cell = PdfPCell(Paragraph(header, headerFont))
            cell.backgroundColor = headerColor
            cell.padding = 8f
            cell.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(cell)
        }

        val rowFont = Font(Font.FontFamily.HELVETICA, 9f, Font.NORMAL, colorScheme["text"])
        budgetData.items.forEach { item ->
            table.addCell(createCell(item.description, rowFont, Element.ALIGN_LEFT))
            table.addCell(createCell("${item.widthMm}x${item.heightMm}mm", rowFont, Element.ALIGN_CENTER))
            table.addCell(createCell(item.material, rowFont, Element.ALIGN_LEFT))
            table.addCell(createCell(item.specifications, rowFont, Element.ALIGN_LEFT))
            table.addCell(createCell(item.quantity.toString(), rowFont, Element.ALIGN_CENTER))
        }

        document.add(table)
        document.add(Paragraph(" "))
    }

    private fun addSummary(
        document: Document,
        budgetData: BudgetWithItems,
        colorScheme: Map<String, BaseColor>
    ) {
        val boldFont = Font(Font.FontFamily.HELVETICA, 11f, Font.BOLD, colorScheme["text"])
        val font = Font(Font.FontFamily.HELVETICA, 10f, Font.NORMAL, colorScheme["text"])

        document.add(Paragraph("RESUMEN", boldFont))
        document.add(Paragraph("Total de items: ${budgetData.items.size}", font))
        document.add(Paragraph("Superficie total: ${String.format("%.2f", budgetData.getTotalSurfaceM2())} m²", font))
        document.add(Paragraph(" "))
    }

    private fun addContract(
        document: Document,
        budgetData: BudgetWithItems,
        colorScheme: Map<String, BaseColor>
    ) {
        val titleFont = Font(Font.FontFamily.HELVETICA, 11f, Font.BOLD, colorScheme["primary"])
        val contractFont = Font(Font.FontFamily.HELVETICA, 8f, Font.NORMAL, colorScheme["text"])

        document.add(Paragraph("TÉRMINOS Y CONDICIONES", titleFont))
        document.add(Paragraph(budgetData.budget.contract, contractFont))
    }

    private fun createCell(text: String, font: Font, alignment: Int): PdfPCell {
        val cell = PdfPCell(Paragraph(text, font))
        cell.padding = 6f
        cell.horizontalAlignment = alignment
        return cell
    }
}
