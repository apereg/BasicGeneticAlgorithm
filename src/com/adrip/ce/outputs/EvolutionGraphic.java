package com.adrip.ce.outputs;

import com.adrip.ce.Main;
import com.adrip.ce.basicgeneticalgorithm.GenerationValues;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class EvolutionGraphic extends JFrame {

    public EvolutionGraphic(List<GenerationValues> historical) {
        /* Se crea el dataset con todos los datos extraidos del historico. */
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < historical.size(); i++) {
            dataset.addValue(historical.get(i).getBest(), "Mejor", "" + i);
            dataset.addValue(historical.get(i).getMean(), "Media", "" + i);
        }

        /* Se crea el objeto grafico. */
        JFreeChart chart = ChartFactory.createLineChart("Aptitudes / Generación",
                "Generacion", "Aptitud", dataset, PlotOrientation.VERTICAL,
                true, true, false);

        /* Se añade el titulo y los subtitulos el grafico. */
        if (Main.getMastermind())
            this.setTitle("Resultados Mastermind");
        else
            this.setTitle("Resultados algoritmo genetico");
        Date date = Date.from(Instant.now());
        chart.addSubtitle(new TextTitle(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date)));
        chart.addSubtitle(new TextTitle(Main.getNumChromosomes() + " cromosomas"));
        ChartPanel panel = new ChartPanel(chart);

        /* Si se activa la opcion en el Main se guarda el grafico a un archivo png. */
        if (Main.getSaveGraphic()) {
            try {
                /* Se intenta crear el directorio graphics en la raiz del proyecto si no existe. */
                File f = new File("graphics/");
                if(!f.exists())
                    if(!f.mkdir())
                        throw new IOException();
                f = new File("graphics/" + (Main.getMastermind() ? "Mastermind" : "AAGG") + new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(date) + ".png");
                ChartUtils.saveChartAsPNG(f, chart, 1920, 1080);
            } catch (IOException e) {
                System.out.println("No se pudo guardar el grafico");
            }
        }

        /* Se cambian las opciones de ventana como tamaño y logo. */
        this.getContentPane().add(panel);
        try {
            this.setIconImage(ImageIO.read(new File("./resources/logo.png")));
        } catch (IOException ignored) {
        }
        this.pack();
        this.setSize(1920, 1080);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Se muestra el grafico. */
        this.setVisible(true);
    }

}