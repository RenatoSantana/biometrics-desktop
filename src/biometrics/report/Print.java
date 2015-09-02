/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometrics.report;

import biometrics.Biometrics;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;

/**
 *
 * @author 205406813
 */
public class Print {

  
    
  
    
    public static void gerarVia(HashMap<String, Object> parameters, String impressora) throws MalformedURLException, URISyntaxException, ClassNotFoundException{
      	File reportFile = null;
        String mimeType = "";
        ByteArrayOutputStream  output = new ByteArrayOutputStream();
        JasperPrint print = null;

        System.out.println(System.getProperty("user.dir"));
        String url = System.getProperty("user.dir");
        reportFile = new File(url+"\\report\\index.jasper");
        
        	try 
		{
                    DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		    print = JasperFillManager.fillReport(reportFile.getPath(),parameters, DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_identificacao_pm", "root", "root"));
					
		}catch (JRException e) 
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
        
        printDirectlyToPrinter(impressora,print);
    }
    
    public static void printDirectlyToPrinter(String printerName,JasperPrint jasperPrint)
	{	
		try {
			//Lista todas impressoras até encontrar a enviada por parametro
		        PrintService serviceFound = null;
			PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
			for(PrintService service:services){
				if(service.getName().trim().equals(printerName.trim()))
					serviceFound = service;
			}
			
			if (serviceFound == null)
				throw new Exception("Impressora não encontrada !");
			
			JRExporter exporter = new JRPrintServiceExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, serviceFound.getAttributes());
			exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
			exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
			exporter.exportReport();
			
			             //    JasperPrintManager.printPage(jasperPrint, 0, false);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
