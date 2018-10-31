package application;
	
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.lang.model.element.ElementKind;

import org.w3c.dom.css.RGBColor;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.layout.element.Text;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs.Rgb;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.kernel.colors.*;


import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	@FXML
	private TextArea txtTexto;
	
	private Stage primaryStage;
	private AnchorPane rootLayout;
	
	// Caminho do arquivo PDF
	public static final String RESULT = "C:\\Users\\fdm30\\Desktop\\ESchool\\GerarPDF\\SegundoTestePDF\\PDF\\SegundoTeste.pdf";
	
	@FXML
    private void initialize() {
    }
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Gerar PDF");
		
		initRootLayout();
	}
	
	public void initRootLayout() {
        try {
            // Carrega o root layout do arquivo fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("GerarPDF.fxml"));
            rootLayout = (AnchorPane) loader.load();

            // Mostra a scene (cena) contendo o root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	@FXML
	private void handleGerarPDF() throws IOException {
		//Prepara o local do arquivo
		File file = new File(RESULT);
		
		
		file.getParentFile().mkdirs();
		//Chamada do metódo para criar o PDF
		if(this.createPdf(RESULT)) {
			System.out.println("Sucesso!!");
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean createPdf(String dest) throws IOException{
		//Cria o documento
		FileOutputStream fos = new FileOutputStream(dest);
		
		PdfWriter writer = new PdfWriter(fos);
		
		PdfDocument pdf = new PdfDocument(writer);
		
		Document document = new Document(pdf);
		
		
		//Criando a imagem para adicionar no documento
		Image image = new Image(ImageDataFactory.create("C:\\Users\\fdm30\\Desktop\\dk.jpg"));
		
		/*
		 * Adiciona um paragrafo ao texto.
		 * 
		 * Também pode ser feito da seguinte forma:
		 * Paragraph p = new Paragraph("TEXTO A SER ADICIONADO");
		 * document.add(p);
		 */
		
		String[] p = new String[]{"Visão Geral","Margem Liquida \n "+ new Random().nextInt(100)+"%","Margem operacional \n"+ new Random().nextInt(100)+"%","Margem bruta \n "+new Random().nextInt(100)+"%","Retorno do ativo \n"+new Random().nextInt(100)+"%","Retorno do patrimonio liquido \n "+new Random().nextInt(100)+"%"};
		String[] p2 = new String[]{"Liquidez","Liquidez corrente","Ruim por causa disso e daquilo","Liquidez seca","Media por conta disso e daquilo","Liquidez imediata","Media por conta disso daquilo","Liquidez geral","Ruim por conta disso e daquilo"};
		String[] p3 = new String[]{"Logistica e estrutura","Giro de estoque","32%","Giro do ativo","45%","Prazos de recebimento","32%"};
		String[] p4 = new String[]{"Endividamento","Endividamento a longo prazo","Bom por causa disso","Endividamento a curto prazo","Bom por causa disso"}; 
		
		CreatingFirstInformation(document, p);
		CreatingSecondInformation(document, p2);
		
		
		
		for(int i = 0; i < p3.length;i++) {
			document.add(new Paragraph(p3[i]).setFontColor(ColorConstants.BLUE));
		}
		
		document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
		
		for(int i = 0; i < p4.length;i++) {
			document.add(new Paragraph(p4[i]).setFontColor(ColorConstants.BLUE));
		}
		
		document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

		//Adicionando uma nova pagina ao documento
		document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
		//Adicionando uma imagem a nova pagina
		document.add(image);
		
		//--------------------------------------------------------------------------------------------------------------
		
		//Criação do gráfico
		
		final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");
        
        //Criando o gráfico
        LineChart<Number, Number> lineChart =
                new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setTitle("Stock Monitoring, 2010");
        
        //Definindo a série
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");
        
        //Populando a série com dados
        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        
        //Adicionando o gráfico a uma Cena Auxiliar
        new Scene(lineChart, 800, 600);
        
        //Adicionando a série ao gráfico
        lineChart.getData().add(series);
        
        //Transformando o gráfico em uma imagem
        WritableImage image2 = lineChart.snapshot(new SnapshotParameters(), null);

        //Salvando a writableimagem
        File fileA = new File("C:\\Users\\fdm30\\Desktop\\ESchool\\GerarPDF\\SegundoTestePDF\\PDF\\chart.png");
        try {
             ImageIO.write(SwingFXUtils.fromFXImage(image2, null), "png", fileA);
        }
        catch (Exception s) {
        }
        
        //----------------------------------------------------------------------------------------------------------------------------
        
        //Pegando a imagem salva do gráfico pra image do iText
        Image graph = new Image(ImageDataFactory.create("C:\\Users\\fdm30\\Desktop\\ESchool\\GerarPDF\\SegundoTestePDF\\PDF\\chart.png"));
        
        //Adicionando uma nova página ao documento
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        
        //Adicionando a imagem do gráfico a nova pagina
        document.add(graph);
        
        //Apagando o arquivo da imagem criado anteriormente
        fileA.delete();
		
		//Fechando o documento
		document.close();
		return true;
	}
	
	public void CreatingFirstInformation(Document document,String[] content) {
		Table table = new Table(2);
			
		for(int i = 0; i < content.length-1;i++) {
			Paragraph par = new Paragraph(content[i].toUpperCase());
			par.setFontSize(20);
			par.setBold();
			par.setFontColor(ColorConstants.BLUE);
			if(i == 0) {
				par.setTextAlignment(TextAlignment.LEFT);
				document.add(par);
			}else {
				par.setTextAlignment(TextAlignment.CENTER);
				Cell cell = new Cell().add(par);
				cell.setBorder(Border.NO_BORDER);
				cell.setHorizontalAlignment(HorizontalAlignment.RIGHT);
				table.addCell(cell);
			}
		}
		
		document.add(table);
		
		document.add(new Paragraph(content[content.length-1].toUpperCase())
				.setBold()
				.setFontSize(20)
				.setFontColor(ColorConstants.BLUE)
				.setTextAlignment(TextAlignment.CENTER));
		document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
	}
	
	public void CreatingSecondInformation(Document document, String[] content) {
		for(int i = 0; i < content.length;i++) {
			Paragraph par = new Paragraph(content[i].toUpperCase());
			par.setFontSize(20);
			if(content[i].equalsIgnoreCase("liquidez")) {
				content[i].toUpperCase();
				document.add(par.setFontColor(ColorConstants.BLUE));
			}
			if(content[i].equalsIgnoreCase("liquidez corrente") || content[i].equalsIgnoreCase("liquidez seca") || content[i].equalsIgnoreCase("liquidez imediata") || content[i].equalsIgnoreCase("liquidez geral")) {
				content[i].toUpperCase();
				if(content[i+1].contains("Boa")) {
					document.add(par.setFontColor(ColorConstants.GREEN));
				}else if(content[i+1].contains("Media")) {
					document.add(par.setFontColor(ColorConstants.ORANGE));
				}else {
					document.add(par.setFontColor(ColorConstants.RED));
				}
			}else {
				if(!content[i].equalsIgnoreCase("liquidez")) {
					document.add(new Paragraph(content[i]).setFontColor(ColorConstants.BLACK));
				}
			}
		}
		
		document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
