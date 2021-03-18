package com.adrip.ce.graphics;

import com.adrip.ce.basicgeneticalgorithm.Population;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class EvolutionGraphic extends JFrame {

    JPanel pane;

    public EvolutionGraphic(Population population) {
        DefaultCategoryDataset aptitudes = new DefaultCategoryDataset();
        //DefaultCategoryDataset best = new DefaultCategoryDataset();
        for (int i = 0; i < population.getGeneration(); i++) {
            aptitudes.addValue(population.getBest(i), "Mejor", "" + i);
            aptitudes.addValue(population.getMean(i), "Media", "" + i);
        }
        JFreeChart chart = ChartFactory.createLineChart("Aptitudes / Generación",
                "Generacion", "Aptitudes", aptitudes, PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel panel = new ChartPanel(chart);

        JFrame window = new JFrame("Resultados AA.GG");
        //Image icon = new ImageIcon(this.getClass().getResource("./../../../../resources/logo.png")).getImage();
        //this.setIconImage(icon);

        //Image image = new ImageIcon(getClass().getResource("File:///" + System.getProperty("java.class.path") + "/../../../../resources/logo.png")).getImage();
        //this.setIconImage(image);

        window.getContentPane().add(panel);
        window.pack();
        window.setSize(1920, 1080);
        window.setExtendedState(window.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

}