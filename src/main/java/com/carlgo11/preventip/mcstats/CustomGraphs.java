package com.carlgo11.preventip.mcstats;

import com.carlgo11.preventip.Main;

public class CustomGraphs {

    public static void graphs(Metrics metrics, Main main)
    {
        graph0(metrics, main);
        graph1(metrics, main);
        metrics.start();
    }

    static void graph0(Metrics metrics, Main main)
    {
        Metrics.Graph graph = metrics.createGraph("auto-update");
        boolean au = main.getConfig().getBoolean("auto-update");
        if (au) {
            graph.addPlotter(new SimplePlotter("enabled"));
        } else {
            graph.addPlotter(new SimplePlotter("disabled"));
        }
        main.debug("graph0 sent");
    }
    
    static void graph1(Metrics metrics, Main main){
        Metrics.Graph graph = metrics.createGraph("auto-update");
        boolean au = main.getConfig().getBoolean("auto-update");
        if (au) {
            graph.addPlotter(new SimplePlotter("enabled"));
        } else {
            graph.addPlotter(new SimplePlotter("disabled"));
        }
        main.debug("graph1 sent");
    }
}
