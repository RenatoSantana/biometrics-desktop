/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometrics.teste;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import java.awt.print.PrinterJob;
import java.io.*;
public class ShowPrinters implements Doc {
  

    public static void main(String[] args) {
        ShowPrinters showPrinters = new ShowPrinters();
        /*boolean available = isPrinterAvailable(PRINTER_NAME);
        System.out.println(available);
        printerTest();*/
        showPrinters.printerTest();
    }

    private void test2() {
        PrintService ps = PrinterJob.getPrinterJob().getPrintService();

        DocFlavor[] myFlavors = ps.getSupportedDocFlavors();

        DocPrintJob docJob = ps.createPrintJob();
        try {
            docJob.print(ShowPrinters.this, null);
        } catch (PrintException e) {
            System.out.println("err");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static boolean isPrinterAvailable() {
        DocFlavor myFormat = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();

        PrintService[] printers = PrintServiceLookup.lookupPrintServices(myFormat, aset);

        for (PrintService printService : printers) {
          
                Attribute attribute =
                        printService.getAttribute(PrinterIsAcceptingJobs.class);

                if (attribute.toString().equals("accepting-jobs")) {
                    return true;
                }
            
        }

        return false;
    }


    public static boolean isPrinterAvailable2() {
        DocFlavor myFormat = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();

        PrintService[] printers = PrintServiceLookup.lookupPrintServices(myFormat, aset);

        for (PrintService printService : printers) {
      
                System.out.println(printService.getAttribute(PrinterIsAcceptingJobs.class));
                System.out.println(printService.getAttribute(PrinterLocation.class));
                System.out.println(printService.getAttribute(PrinterMakeAndModel.class));
                System.out.println(printService.getAttribute(PrinterMessageFromOperator.class));
                System.out.println(printService.getAttribute(PrinterMoreInfo.class));
                System.out.println(printService.getAttribute(PrinterMoreInfoManufacturer.class));
                System.out.println(printService.getAttribute(PrinterState.class));
                System.out.println(printService.getAttribute(PrinterStateReasons.class));
                System.out.println(printService.getAttribute(PrinterURI.class));
            }
        

        return false;
    }

    public static void printerTest() {
        DocFlavor myFormat = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();

        PrintService[] printers = PrintServiceLookup.lookupPrintServices(myFormat, aset);

        for (PrintService printService : printers) {
            PrintServiceAttributeSet attributes = printService.getAttributes();
            String printerState = attributes.get(PrinterState.class).toString();
            String printerStateReason = attributes.get(PrinterStateReason.class).toString();

            System.out.println("printerState = " + printerState); // May be IDLE, PROCESSING, STOPPED or UNKNOWN
            System.out.println("printerStateReason = " + printerStateReason); // If your jna state returns STOPPED, for example, you can identify the reason

            if (printerState.equals(PrinterState.STOPPED.toString())) {

                if (printerStateReason.equals(PrinterStateReason.TONER_LOW.toString())) {

                    System.out.println("Toner level is low.");
                }
            }
        }
    }

    @Override
    public DocFlavor getDocFlavor() {
        return null;
    }

    @Override
    public Object getPrintData() throws IOException {
        return null;
    }

    @Override
    public DocAttributeSet getAttributes() {
        return null;
    }

    @Override
    public Reader getReaderForText() throws IOException {
        return null;
    }

    @Override
    public InputStream getStreamForBytes() throws IOException {
        return null;
    }
}