package com.example.daniel.cartaspokemon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.pdf.PrintedPdfDocument;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;

public class MyPrintDocumentAdapter extends PrintDocumentAdapter {

    private ImageView imageView;
    private TextView consultaNombre, consultaTipo, consultaPs, consultaAtaque, consultaDefensa,
            consultaPeso, consultaAltura;
    Context context;
    private int pageHeight;
    private int pageWidth;
    public PdfDocument myPdfDocument;
    public int totalpages = 1;

    //También tenemos onStart() y onFinish():  son opcionales.


    public MyPrintDocumentAdapter(Context context, ImageView imageView, TextView consultaNombre, TextView consultaTipo,
                                  TextView consultaPs, TextView consultaAtaque,
                                  TextView consultaDefensa, TextView consultaPeso, TextView consultaAltura) {
        this.context = context;
        this.imageView = imageView;
        this.consultaNombre = consultaNombre;
        this.consultaTipo = consultaTipo;
        this.consultaPs = consultaPs;
        this.consultaAtaque = consultaAtaque;
        this.consultaDefensa = consultaDefensa;
        this.consultaPeso = consultaPeso;
        this.consultaAltura = consultaAltura;
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes,
                         PrintAttributes newAttributes,
                         CancellationSignal cancellationSignal,
                         LayoutResultCallback callback, //retrollamada
                         Bundle metadata) {

        //PrintePDFDocument, subclase especializada en imprimir pdfs.
        //newAttributes:
        myPdfDocument = new PrintedPdfDocument(context, newAttributes);

        //Esto nos viene del tamaño seleccionado por el usuario a la hora de imprimir.
        //De este modo podemos coger el resto de atributos.
        //Esto no es obligatorio.
        pageHeight = newAttributes.getMediaSize().getHeightMils()/1000 * 72;
        pageWidth = newAttributes.getMediaSize().getWidthMils()/1000 * 72;

        if (cancellationSignal.isCanceled()) {

            callback.onLayoutCancelled(); //Si el usuario cancela la impresión.

            return;

        }

        //Importante (si el layout no controla esto)

        if (totalpages > 0) {

            /* Creamos un documento info, siempre será así. Este llevará los metadatos del archivo. */

            PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                    .Builder("print_output.pdf") //Nombre del documento con el que hemos decidio guardarlo
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT) //Se trata de un documento
                    .setPageCount(totalpages); //Numpáginas

            PrintDocumentInfo info = builder.build();

            callback.onLayoutFinished(info, true); //Metadatos + true (ha habido cambios)

        } else {

            callback.onLayoutFailed("Page count is zero.");

        }
    }

    @Override
    public void onWrite(final PageRange[] pageRanges,
                        final ParcelFileDescriptor destination,
                        final CancellationSignal cancellationSignal,
                        final WriteResultCallback callback) {

        for (int i = 0; i < totalpages; i++) {

            if (pageInRange(pageRanges, i)){ //Controlar el rango de páginas

                PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, i).create(); //Esto no es obligatorio, se puede hacer de otro modo
                //Ver web de android

                PdfDocument.Page page = myPdfDocument.startPage(newPage);

                if (cancellationSignal.isCanceled()) {

                    callback.onWriteCancelled();
                    myPdfDocument.close();
                    myPdfDocument = null;

                    return;
                }

                drawPage(page, i);
                myPdfDocument.finishPage(page);
            }
        }

        try {

            myPdfDocument.writeTo(new FileOutputStream(destination.getFileDescriptor()));

        } catch (IOException e) {

            callback.onWriteFailed(e.toString());
            return;

        } finally {

            myPdfDocument.close();
            myPdfDocument = null;

        }

        callback.onWriteFinished(pageRanges);
    }

    private boolean pageInRange(PageRange[] pageRanges, int page){

        for (int i = 0; i<pageRanges.length; i++) {

            if ((page >= pageRanges[i].getStart()) && (page <= pageRanges[i].getEnd()))

                return true;
        }

        return false;
    }

    //Metodo utilizado normalmente para pintar cosas en java/android
    private void drawPage(PdfDocument.Page page, int pagenumber) {

        Canvas canvas = page.getCanvas(); //Un objeto canvas para cada página

        /*Canvas tiene muchos métodos que nos permiten pintar*/

        pagenumber++; // Make sure page numbers start at 1

        //Origen: esquina superior izquieda.
        int titleBaseLine = 72;
        int leftMargin = 54;

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);

        canvas.drawText(
                "Test Print Document Page " + pagenumber,
                leftMargin,
                titleBaseLine,
                paint);

        paint.setTextSize(14);

        //para pintar texto
        //canvas.drawText("This is some test content to verify that custom document printing works", leftMargin, titleBaseLine + 35, paint);
        
        //para pintar drawBitMap();

        /*if (pagenumber % 2 == 0)
            paint.setColor(Color.RED);
        else
            paint.setColor(Color.GREEN);

        PdfDocument.PageInfo pageInfo = page.getInfo();

        canvas.drawCircle(pageInfo.getPageWidth()/2,
                pageInfo.getPageHeight()/2,
                150,
                paint);*/
    }
}

