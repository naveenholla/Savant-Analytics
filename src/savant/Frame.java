package savant;

/**
 * Created by Arnav_Gudibande on 4/29/17.
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Dimension;

public class Frame implements ActionListener
{

    JFrame frame;
    JPanel basePanel;
    public Frame(String title, int h, int w)
    {
        frame = new JFrame(title);
        frame.setSize(h,w);
    }

    public void drawScene()
    {
        basePanel = new JPanel();
        basePanel.setLayout(new BorderLayout());
        frame.add(basePanel);
        frame.setVisible(true);

        JPanel topButtons = new JPanel();


        JButton stock = new JButton("stock");
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
    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();
        System.out.println(command);
        if("stock".equals(command)){
            System.out.println("stock");
            JPanel centerGrid = new JPanel();
            centerGrid.setLayout(new GridLayout(2,1));
            JTextField textField1 = new JTextField();
            textField1.setColumns(1);
            centerGrid.add(textField1);

            centerGrid.setVisible(true);

            JPanel lowerHalfLabels = new JPanel();
            lowerHalfLabels.setLayout(new GridLayout(2,2));
            lowerHalfLabels.add(new JLabel("Price: "));
            lowerHalfLabels.add(new JLabel("VWAP: "));
            lowerHalfLabels.add(new JLabel("TWAP: "));
            lowerHalfLabels.add(new JLabel("Trend: "));
            lowerHalfLabels.setVisible(true);
            centerGrid.add(lowerHalfLabels);
            basePanel.add(centerGrid, BorderLayout.CENTER);
            frame.setVisible(true);
        }

        else if("Graphs".equals(command)){
            System.out.println("graphs");
            JPanel centerPiece = new JPanel();
            centerPiece.setLayout(new GridLayout(1,3));
            centerPiece.add(new JLabel("VWAP graph here"));
            JTextField textField2 = new JTextField();
            textField2.setPreferredSize(new Dimension(20,20));
            centerPiece.add(textField2);
            centerPiece.add(new JLabel("TWAP graph here"));
            centerPiece.setVisible(true);
            basePanel.add(centerPiece, BorderLayout.CENTER);

            frame.setVisible(true);
        }

        else if("Analytics".equals(command)){
            System.out.println("analytics");

            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new GridLayout(3,3));

            JButton analyzeStock = new JButton("Analyze Stock");
            analyzeStock.setPreferredSize(new Dimension(20,20));
            centerPanel.add(analyzeStock);

            centerPanel.add(new JLabel("Neural Net Recommendations"));
            centerPanel.add(new JLabel("Buy/Sell"));
            centerPanel.add(new JLabel("Certainty: "));
            centerPanel.add(new JLabel("Volume: "));
            centerPanel.setVisible(true);

            basePanel.add(centerPanel, BorderLayout.CENTER);
            frame.setVisible(true);


        }
    }
}




