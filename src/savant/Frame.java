package savant;

/**
 * Created by Arnav_Gudibande on 4/29/17.
 */

import org.jfree.chart.ChartPanel;
import org.jfree.ui.RefineryUtilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.server.ExportException;

public class Frame implements ActionListener {

    public static JFrame frame;
    public static JPanel basePanel, topButtons, centerGrid, centerGrid3;
    public static final JActivityIndicator act2 = new JActivityIndicator(1);

    public Frame(String title, int h, int w)
    {
        frame = new JFrame(title);
        frame.setSize(h,w);
    }

    public void drawScene()
    {
        basePanel = new JPanel();
        //basePanel.setLayout(new BorderLayout());
        basePanel.setLayout(new BorderLayout());
        frame.add(basePanel);
        frame.setVisible(true);

        topButtons = new JPanel();


        JButton stock = new JButton("Stock");
        topButtons.add(stock);

        JButton graphs = new JButton("Graphs");
        topButtons.add(graphs);

        JButton analytics = new JButton("Analytics");
        topButtons.add(analytics);
        basePanel.add(topButtons, BorderLayout.NORTH);

        frame.setVisible(true);

        stock.addActionListener(this);
        graphs.addActionListener(this);
        analytics.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if("Stock".equals(command)){

            if(Graph.chartPanel != null) {
                Graph.chartPanel.setVisible(false);
            }
            if(centerGrid3 != null) {
                centerGrid3.setVisible(false);
            }


            centerGrid = new JPanel();
            centerGrid.setLayout(new GridBagLayout());
            centerGrid.setBackground(new Color(217, 217, 217));

            JTextField textField = new JTextField(10);

            centerGrid.add( textField, new GridBagConstraints( 0, 0, 1, 1, 1.0,
                    1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                     new Insets( 0, 0, 0, 0 ), 0, 0 ) );

            try {
                BufferedImage pick = ImageIO.read(new File("src/assets/ticker.png"));
                JLabel pL = new JLabel(new ImageIcon(pick));

                centerGrid.add( pL, new GridBagConstraints( 0, 0, 1, 1, 1.0,
                        1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                        new Insets( 0, 0, 75, 0 ), 0, 0 ) );

            } catch (IOException d) {
                System.out.println(d);
            }

            try {
                BufferedImage myPicture = ImageIO.read(new File("src/assets/savant.png"));
                JLabel picLabel = new JLabel(new ImageIcon(myPicture));

                centerGrid.add( picLabel, new GridBagConstraints( 0, 0, 1, 1, 1.0,
                        1.0, GridBagConstraints.NORTH, GridBagConstraints.NORTH,
                        new Insets( 20, 0, 0, 0 ), 0, 0 ) );

            } catch (IOException d) {
                System.out.println(d);
            }

            JLabel price = new JLabel("");
            price.setFont(new Font("Serif", Font.PLAIN, 40));
            price.setForeground(new Color(0, 137, 53));

            centerGrid.add( price, new GridBagConstraints( 0, 0, 1, 1, 1.0,
                    1.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE,
                    new Insets( 0, 225, 250, 0 ), 0, 0 ) );

            try {
                BufferedImage myPicture = ImageIO.read(new File("src/assets/price.png"));
                JLabel picLabel = new JLabel(new ImageIcon(myPicture));

                centerGrid.add( picLabel, new GridBagConstraints( 0, 0, 1, 1, 1.0,
                        1.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE,
                        new Insets( 0, 238, 300, 0 ), 0, 0 ) );

            } catch (IOException d) {
                System.out.println(d);
            }

            JLabel exchange = new JLabel("");
            exchange.setFont(new Font("Serif", Font.PLAIN, 30));
            exchange.setForeground(new Color(0, 137, 53));

            centerGrid.add( exchange, new GridBagConstraints( 0, 0, 1, 1, 1.0,
                    1.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE,
                    new Insets( 0, 220, 125, 0 ), 0, 0 ) );

            try {
                BufferedImage myPicture = ImageIO.read(new File("src/assets/exchange.png"));
                JLabel picLabel = new JLabel(new ImageIcon(myPicture));

                centerGrid.add( picLabel, new GridBagConstraints( 0, 0, 1, 1, 1.0,
                        1.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE,
                        new Insets( 0, 210, 175, 0 ), 0, 0 ) );

            } catch (IOException d) {
                System.out.println(d);
            }

            JLabel timestamp = new JLabel("");
            timestamp.setFont(new Font("Serif", Font.PLAIN, 30));
            timestamp.setForeground(new Color(0, 137, 53));

            centerGrid.add( timestamp, new GridBagConstraints( 0, 0, 1, 1, 1.0,
                    1.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE,
                    new Insets( 0, 0, 250, 220 ), 0, 0 ) );

            try {
                BufferedImage myPicture = ImageIO.read(new File("src/assets/timestamp.png"));
                JLabel picLabel = new JLabel(new ImageIcon(myPicture));

                centerGrid.add( picLabel, new GridBagConstraints( 0, 0, 1, 1, 1.0,
                        1.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE,
                        new Insets( 0, 0, 300, 220 ), 0, 0 ) );

            } catch (IOException d) {
                System.out.println(d);
            }

            JLabel trend = new JLabel("");
            trend.setFont(new Font("Serif", Font.PLAIN, 30));
            trend.setForeground(new Color(0, 137, 53));

            centerGrid.add( trend, new GridBagConstraints( 0, 0, 1, 1, 1.0,
                    1.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE,
                    new Insets( 0, 0, 125, 250 ), 0, 0 ) );

            try {
                BufferedImage myPicture = ImageIO.read(new File("src/assets/trend.png"));
                JLabel picLabel = new JLabel(new ImageIcon(myPicture));

                centerGrid.add( picLabel, new GridBagConstraints( 0, 0, 1, 1, 1.0,
                        1.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE,
                        new Insets( 0, 0, 175, 250 ), 0, 0 ) );

            } catch (IOException d) {
                System.out.println(d);
            }

            Action action = new AbstractAction()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    Main.TICKER = textField.getText();

                    try {
                        Main.loadCurrentData();
                        price.setText("$" + Main.loadPrice());
                        exchange.setText(Main.loadExchange());
                        timestamp.setText(Main.loadTime());
                        trend.setText(Main.loadTrend());
                        if(Main.loadTrend().substring(0,1).equals("+")) {
                            trend.setForeground(new Color(0, 137, 53));
                        } else {
                            trend.setForeground(new Color(137, 2, 10));
                        }
                    } catch (Exception q) {
                        System.out.println(q);
                    }



                    try {
                        Main.loadData();
                    } catch (Exception p) {
                        System.out.println(p);
                    }
                }
            };

            textField.addActionListener( action );

            basePanel.add(centerGrid);
            frame.setVisible(true);
        }

        else if("Graphs".equals(command)){
            if(centerGrid != null) {
                centerGrid.setVisible(false);
            }
            if(centerGrid3 != null) {
                centerGrid3.setVisible(false);
            }

            JPanel jPanel1 = new JPanel();
            jPanel1.setLayout(new java.awt.BorderLayout());

            Graph chart = new Graph(
                    "Savant Analytics" ,
                    Main.TICKER + " Price v. VWAP");
        }

        else if("Analytics".equals(command)){

            if(centerGrid != null) {
                centerGrid.setVisible(false);
            }
            if(Graph.chartPanel != null) {
                Graph.chartPanel.setVisible(false);
            }

            centerGrid3 = new JPanel();
            centerGrid3.setLayout(new GridBagLayout());
            centerGrid3.setBackground(new Color(217, 217, 217));

            JButton analyze = new JButton("Run Network");

            centerGrid3.add( analyze, new GridBagConstraints( 0, 0, 1, 1, 1.0,
                    1.0, GridBagConstraints.NORTH, GridBagConstraints.NORTH,
                    new Insets( 200, 0, 0, 0 ), 0, 0 ) );

            centerGrid3.add( act2, new GridBagConstraints( 0, 0, 1, 1, 1.0,
                    1.0, GridBagConstraints.NORTH, GridBagConstraints.NORTH,
                    new Insets( 250, 0, 0, 0 ), 0, 0 ) );

            act2.setVisible(false);

            try {
                BufferedImage myPicture = ImageIO.read(new File("src/assets/analytics.png"));
                JLabel picLabel = new JLabel(new ImageIcon(myPicture));

                centerGrid3.add( picLabel, new GridBagConstraints( 0, 0, 1, 1, 1.0,
                        1.0, GridBagConstraints.NORTH, GridBagConstraints.NONE,
                        new Insets( 50, 0, 0, 0 ), 0, 0 ) );

            } catch (IOException d) {
                System.out.println(d);
            }

            try {
                BufferedImage myPicture = ImageIO.read(new File("src/assets/certainty.png"));
                JLabel picLabel = new JLabel(new ImageIcon(myPicture));

                centerGrid3.add( picLabel, new GridBagConstraints( 0, 0, 1, 1, 1.0,
                        1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                        new Insets( 0, 400, 0, 0 ), 0, 0 ) );

            } catch (IOException d) {
                System.out.println(d);
            }

            JLabel certainty = new JLabel("");
            certainty.setFont(new Font("Serif", Font.PLAIN, 50));
            certainty.setForeground(new Color(0, 46, 95));

            centerGrid3.add( certainty, new GridBagConstraints( 0, 0, 1, 1, 1.0,
                    1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                    new Insets( 125, 410, 0, 0 ), 0, 0 ) );

            certainty.setVisible(false);

            try {
                BufferedImage myPicture = ImageIO.read(new File("src/assets/verdict.png"));
                JLabel picLabel = new JLabel(new ImageIcon(myPicture));

                centerGrid3.add( picLabel, new GridBagConstraints( 0, 0, 1, 1, 1.0,
                        1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                        new Insets( 0, 0, 0, 400 ), 0, 0 ) );

            } catch (IOException d) {
                System.out.println(d);
            }

            JLabel verdict = new JLabel("");
            verdict.setFont(new Font("Serif", Font.PLAIN, 40));

            centerGrid3.add( verdict, new GridBagConstraints( 0, 0, 1, 1, 1.0,
                    1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                    new Insets( 125, 0, 0, 410 ), 0, 0 ) );

            verdict.setVisible(false);

            analyze.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    act2.setVisible(true);
                    Thread one = new Thread() {
                        public void run() {
                            runNeuralNetwork();
                            act2.toggleRotating();
                            act2.setVisible(false);
                            certainty.setText(NeuralNet.certainty.substring(0,4) + "%");
                            certainty.setVisible(true);
                            verdict.setText(determine(NeuralNet.price, NeuralNet.vwap, NeuralNet.verdict));

                            if(verdict.getText().equals("BUY")) {
                                verdict.setForeground(new Color(0, 137, 53));
                            } else if (verdict.getText().equals("SELL")) {
                                verdict.setForeground(new Color(137, 2, 10));
                            } else {
                                verdict.setForeground(new Color(0, 46, 95));
                            }

                            verdict.setVisible(true);
                        }
                    };

                    one.start();
                    act2.toggleRotating();
                }
            } );

            basePanel.add(centerGrid3);
            frame.setVisible(true);

        }
    }

    public static void runNeuralNetwork() {
        NeuralNet n = new NeuralNet();
        n.runNetwork();
    }

    public static String determine(double p, double v, boolean verdict) {
        if(verdict) {
            if(v < p) {
                return "BUY";
            } else {
                return "SELL";
            }
        } else {
            return "HOLD";
        }
    }

}




