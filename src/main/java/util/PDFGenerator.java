package util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import model.OrdemServico;

public class PDFGenerator {

    public static void gerarPDFPadrao(File arquivoPDF, OrdemServico ordem) throws Exception {
        Document doc = new Document(PageSize.A4, 50, 50, 30, 30);
        PdfWriter.getInstance(doc, new FileOutputStream(arquivoPDF));
        doc.open();

        Image logo = Image.getInstance(PDFGenerator.class.getResource("/imagens/icone_ordem_servico_mecanica.png"));
        logo.scaleToFit(100, 50);

        String caminho = PDFGenerator.class
                .getResource("/Fonts/arial.ttf").toString();
        BaseFont baseArial = BaseFont.createFont(caminho, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font arialFont = new Font(baseArial, 8, Font.BOLD, BaseColor.DARK_GRAY);

        Paragraph empInfo = new Paragraph(
                "Nome da empresa\n"
                + "Endereço\n"
                + "Número\n", arialFont);
        empInfo.setAlignment(Element.ALIGN_RIGHT);

        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{1, 2});

        PdfPCell logoCell = new PdfPCell(logo, false);
        logoCell.setBorder(Rectangle.NO_BORDER);
        logoCell.setVerticalAlignment(Element.ALIGN_TOP);

        PdfPCell infoCell = new PdfPCell(empInfo);
        infoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        infoCell.setBorder(Rectangle.NO_BORDER);
        infoCell.setVerticalAlignment(Element.ALIGN_TOP);
        headerTable.addCell(logoCell);
        headerTable.addCell(infoCell);

        doc.add(headerTable);

        final BaseColor CINZA333 = new BaseColor(0x33, 0x33, 0x33);

        Font titleFont = new Font(baseArial, 12, Font.BOLD, CINZA333);
        Font lineFont = new Font(baseArial, 5, Font.NORMAL, CINZA333);

        Paragraph line1 = new Paragraph("", lineFont);
        line1.setAlignment(Element.ALIGN_CENTER);
        line1.setSpacingAfter(0f);
        doc.add(line1);

        LineSeparator lsCima = new LineSeparator();
        lsCima.setLineColor(new BaseColor(180, 180, 180));
        lsCima.setLineWidth(1f);

        Paragraph linhaSuperior = new Paragraph(new Chunk(lsCima));
        linhaSuperior.setSpacingAfter(-2f);
        doc.add(linhaSuperior);

        Paragraph title = new Paragraph("ORDEM DE SERVIÇO", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingBefore(0f);
        title.setSpacingAfter(-1f);
        doc.add(title);

        LineSeparator lsBaixo = new LineSeparator();
        lsBaixo.setLineColor(new BaseColor(180, 180, 180));
        lsBaixo.setLineWidth(1f);

        Paragraph linhaInferior = new Paragraph(new Chunk(lsBaixo));
        linhaInferior.setSpacingBefore(-10f);
        linhaInferior.setSpacingAfter(5f);
        doc.add(linhaInferior);

        Font labelFont = new Font(baseArial, 8, Font.BOLD, CINZA333);
        Font valueFont = new Font(baseArial, 8, Font.NORMAL, BaseColor.BLACK);
        Font labelDesc = new Font(baseArial, 10, Font.NORMAL, BaseColor.BLACK);
        Font fontValor = new Font(baseArial, 9, Font.BOLD, BaseColor.BLACK);

        float sectionSpacing = 5f;
        PdfPTable info1 = new PdfPTable(3);
        info1.setWidthPercentage(100);
        info1.setWidths(new float[]{5f, 7f, 4f});
        addLabelValue(info1, "NÚMERO DA ORDEM", safe(ordem.getId() != null ? ordem.getId().toString() : ""), labelFont, valueFont);
        addLabelValue(info1, "NOME", safe(ordem.getClienteNome()), labelFont, valueFont);
        addLabelValue(info1, "TELEFONE", safe(ordem.getCliente() != null ? ordem.getCliente().getTelefone() : ""), labelFont, valueFont);
        info1.setSpacingAfter(sectionSpacing);
        doc.add(info1);

        PdfPTable info2 = new PdfPTable(3);
        info2.setWidthPercentage(100);
        info2.setWidths(new float[]{5f, 7f, 4f});
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataOS = ordem.getDate_abertura_servico() != null ? ordem.getDate_abertura_servico().format(df) : "";
        String dataSvc = ordem.getDate_fechamento_servico() != null ? ordem.getDate_fechamento_servico().format(df) : "";
        String situacao = ordem.getSituacao() != null ? ordem.getSituacao().name() : "";
        addLabelValue(info2, "DATA DA ORDEM", dataOS, labelFont, valueFont);
        addLabelValue(info2, "DATA CONCLUSÃO DO SERVIÇO", dataSvc, labelFont, valueFont);
        addLabelValue(info2, "SITUAÇÃO DA ORDEM", situacao, labelFont, valueFont);
        info2.setSpacingAfter(-10f);
        doc.add(info2);

        doc.add(linhaSuperior);

        Paragraph vehHeader = new Paragraph("INFORMAÇÕES DO VEÍCULO", labelDesc);
        vehHeader.setAlignment(Element.ALIGN_CENTER);
        doc.add(vehHeader);

        Paragraph linhaInferior2 = new Paragraph(new Chunk(lsBaixo));
        linhaInferior2.setSpacingBefore(-12f);
        linhaInferior2.setSpacingAfter(0f);
        doc.add(linhaInferior2);

        PdfPTable info4 = new PdfPTable(3);
        info4.setWidthPercentage(100);
        info4.setWidths(new float[]{5f, 7f, 4f});
        addLabelValue(info4, "PLACA DO VEÍCULO", safe(ordem.getPlaca()), labelFont, valueFont);
        addLabelValue(info4, "MODELO DO VEÍCULO", safe(ordem.getModelo()), labelFont, valueFont);
        addLabelValue(info4, "COR DO VEÍCULO", safe(ordem.getCor()), labelFont, valueFont);
        info4.setSpacingAfter(-10f);
        doc.add(info4);

        doc.add(linhaSuperior);

        Paragraph servHeader = new Paragraph("DESCRIÇÃO DOS SERVIÇOS E PRODUTOS", labelDesc);
        servHeader.setAlignment(Element.ALIGN_CENTER);
        doc.add(servHeader);

        doc.add(linhaInferior2);

        String desc = safe(ordem.getDescricao_servico());
        if (desc.isEmpty()) {
            desc = "\n\n\n\n\n";
        }

        Paragraph servDesc = new Paragraph(desc, valueFont);
        servDesc.setSpacingBefore(5f);
        servDesc.setSpacingAfter(20);
        doc.add(servDesc);

        doc.add(linhaSuperior);

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String valorFormatado = formatter.format(ordem.getValor_servico());
       
        Paragraph infoVal = new Paragraph("VALOR TOTAL", labelFont);
        infoVal.setAlignment(Element.ALIGN_RIGHT);
        doc.add(infoVal);

        Paragraph infoValor = new Paragraph(valorFormatado, fontValor);
        infoValor.setAlignment(Element.ALIGN_RIGHT); 
        infoValor.setSpacingAfter(50); 
        doc.add(infoValor);
                
        PdfPTable assin = new PdfPTable(3);
        assin.setWidthPercentage(100);
        assin.setWidths(new float[]{2f, 1f, 2f});
        assin.setSpacingBefore(35);

        PdfPCell assinaturaLinha1 = new PdfPCell();
        assinaturaLinha1.setBorder(Rectangle.BOTTOM);
        assinaturaLinha1.setBorderWidthBottom(1f);
        assinaturaLinha1.setBorderColor(new BaseColor(0, 51, 102));
        assinaturaLinha1.setFixedHeight(20);
        assin.addCell(line1);

        PdfPCell spacer = new PdfPCell();
        spacer.setBorder(Rectangle.NO_BORDER);
        assin.addCell(spacer);

        Paragraph line2 = new Paragraph("", lineFont);
        line2.setAlignment(Element.ALIGN_CENTER);
        line2.setSpacingAfter(10);
        doc.add(line2);

        PdfPCell assinaturaLinha2 = new PdfPCell();
        assinaturaLinha2.setBorder(Rectangle.BOTTOM);
        assinaturaLinha2.setBorderWidthBottom(1f);
        assinaturaLinha2.setBorderColor(new BaseColor(0, 51, 102));
        assinaturaLinha2.setFixedHeight(20);
        assin.addCell(line2);

        PdfPCell lbl2 = new PdfPCell(new Paragraph("ASSINATURA DO RESPONSÁVEL", labelFont));
        lbl2.setBorder(Rectangle.NO_BORDER);
        lbl2.setHorizontalAlignment(Element.ALIGN_CENTER);
        assin.addCell(lbl2);

        PdfPCell spacer2 = new PdfPCell();
        spacer2.setBorder(Rectangle.NO_BORDER);
        assin.addCell(spacer2);

        PdfPCell lbl1 = new PdfPCell(new Paragraph("ASSINATURA CLIENTE", labelFont));
        lbl1.setBorder(Rectangle.NO_BORDER);
        lbl1.setHorizontalAlignment(Element.ALIGN_CENTER);
        assin.addCell(lbl1);

        assin.setSpacingBefore(20);
        doc.add(assin);

        doc.close();
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(arquivoPDF);
        }

    }

    private static void addLabelValue(PdfPTable table, String label, String value, Font labelFont, Font valFont) {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setMinimumHeight(30);
        Paragraph pLabel = new Paragraph(label, labelFont);
        String safeValue = value.isEmpty() ? " " : value;
        Paragraph pValue = new Paragraph(safeValue, valFont);
        pLabel.setSpacingAfter(3);
        pValue.setSpacingAfter(10);
        cell.addElement(pLabel);
        cell.addElement(pValue);
        table.addCell(cell);
    }

    private static String safe(String s) {
        return s == null ? "" : s;
    }

}
