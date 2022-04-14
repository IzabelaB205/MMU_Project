package main.java.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class CacheUnitClientFrame extends JFrame implements ActionListener {
    private final JPanel buttonsPanel;
    private JPanel contentPanel;
    private final JButton loadRequestButton;
    private final JButton statisticsButton;
    private JLabel cacheAlgoLabel;
    private JLabel requestsLabel;
    private JLabel dataModelsLabel;
    private JLabel capacityLabel;
    private String cacheAlgorithm;
    private int capacity;
    private int requestsCount;
    private int dataModelsCount;
    private CacheUnitView view;

    public CacheUnitClientFrame(CacheUnitView view) {
        this.view = view;

        //JFrame = a GUI to add components to
        //create and set up the GUI window
        this.setTitle("Memory Management Unit"); //sets title of frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit out of application
        this.setResizable(false); //prevent frame from been resizable
        this.setSize(300, 400); //sets the x-dimension, and y-dimension of frame
        this.setLayout(new FlowLayout());

        //create and set up the panels
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 30));
        buttonsPanel.setPreferredSize(new Dimension(300, 100));
        buttonsPanel.setBackground(Color.BLUE);

        contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout());
        contentPanel.setPreferredSize(new Dimension(300, 300));

        //buttons creation
        loadRequestButton = new JButton(); //create button
        //loadRequestButton.setBounds(250, 100, 100, 50); //sets the x and y dimensions, width and  height of button
        loadRequestButton.addActionListener(this);
        loadRequestButton.setText("Load Request"); //sets text on button
        loadRequestButton.setFocusable(false);
        loadRequestButton.setBorder(BorderFactory.createEtchedBorder()); //sets button's border

        statisticsButton = new JButton();
        //statisticsButton.setBounds(50, 100, 100, 50);
        statisticsButton.addActionListener(this);
        statisticsButton.setText("Statistics");
        statisticsButton.setFocusable(false);
        statisticsButton.setBorder(BorderFactory.createEtchedBorder());

        //labels creation
        capacityLabel = new JLabel(); //create a label
        capacityLabel.setText("Cache capacity:" + capacity); //set text of label

        cacheAlgoLabel = new JLabel();
        cacheAlgoLabel.setText("Cache algorithm: " + cacheAlgorithm);

        requestsLabel = new JLabel();
        requestsLabel.setText("Total number of requests: " + requestsCount);

        dataModelsLabel = new JLabel();
        dataModelsLabel.setText("Total number of DataModels: " + dataModelsCount);

        //add buttons to ButtonsPanel
        buttonsPanel.add(loadRequestButton);
        buttonsPanel.add(statisticsButton);

        //add labels to CacheUnitPanel
        contentPanel.add(capacityLabel);
        contentPanel.add(cacheAlgoLabel);
        contentPanel.add(requestsLabel);
        contentPanel.add(dataModelsLabel);

        //add panels to frame
        this.add(buttonsPanel, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.SOUTH);

        //display the GUI window
        this.setVisible(true); //make frame visible
    }

    public void updateUIData(String content) {
        JOptionPane.showMessageDialog(null, content);
    }

    public void updateStatisticPanel(String payload) {
        contentPanel.removeAll();
        String[] dataLines = payload.split("\n");

        for(String line : dataLines) {
            JLabel label = new JLabel(line);
            contentPanel.add(label);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == loadRequestButton) {
            onLoadRequestClick();
        }

        else if(actionEvent.getSource() == statisticsButton) {
            onStatisticsClick();
        }
    }

    private void onLoadRequestClick() {
        JFileChooser fileChooser = new JFileChooser(); //file chooser declaration
        int response = fileChooser.showOpenDialog(null); //select a file

        if(response == JFileChooser.APPROVE_OPTION) { //successfully select a file
            try {
                File file = fileChooser.getSelectedFile();
                view.notifyObservers(new ActionPayload<String>("LOAD_REQUEST", file.getAbsolutePath()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void onStatisticsClick() {
        view.notifyObservers(new ActionPayload<String>("SHOW_STATISTIC", null));
    }
}
