package notePad;

import javax.print.event.PrintJobAttributeListener;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Listeners implements PropertyChangeListener, KeyListener, MenuListener, MouseListener, ActionListener, ItemListener {
    JComboBox<String> font, style;
    ArrayList<String> words;
    JComboBox<Integer> size;
    JButton button;
    JFrame frame, commandsFrame;
    final JLabel label = new JLabel("sample TExt");

    Window window;
    boolean mouseOver, mouseOnItem = true;
    String O = "o", Q = "Q", N = "n", T = "t", D = "d", PLUS = "plus", MINUS = "minus", Z = "z", SPLUS = "SPLUS", SMINUS = "SMINUS",
            SZ = "SZ";
    Integer[] Int = new Integer[]{20, 22, 24, 26, 28, 36, 48, 62, 72};

    Listeners(Window obj) {
        this.window = obj;
        words = new ArrayList<>();
        commandsFrame = new JFrame();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception ignored){

        }
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(Color.BLACK);
        String ctrlo = "<html><font color='green'><font color='red'> CTRL+O </font> to open a File</font></html>";
        String ctrlQ = "<html><font color='green'><font color='red'> CTRL+Q </font> close the file</font></html>";
        String ctrlT = "<html><font color='green'><font color='red'> CTRL+T </font> change theme</font></html>";
        String ctrlD = "<html><font color='green'><font color='red'> CTRL+D </font> Dark mode</font></html>";
        String ctrlPlus = "<html><font color='green'><font color='red'> CTRL+Plus </font> Increase Font Size</font></html>";

        String ctrlMinus = "<html><font color='green'><font color='red'> CTRL+MINUS </font> Decrease Font Size</font></html>";
        String ctrlZ = "<html><font color='green'><font color='red'> CTRL+Z </font> Undo</font></html>";
        String shiftPlus = "<html><font color='green'><font color='red'> CTRL+SHIFT+Plus </font> Increase Window size</font></html>";
        String shiftMinus = "<html><font color='green'><font color='red'> CTRL+SHIFT+MINUS </font> Decrease Wndow Size</font></html>";
        String shiftZ = "<html><font color='green'><font color='red'> CTRL+SHIFT+Z </font> redo </font></html>";
        panel.add(createJLabel(ctrlo, O));
        panel.add(createJLabel(ctrlQ, Q));
        panel.add(createJLabel(ctrlT, T));
        panel.add(createJLabel(ctrlD, D));
        panel.add(createJLabel(ctrlPlus, PLUS));
        panel.add(createJLabel(ctrlMinus, MINUS));
        panel.add(createJLabel(ctrlZ, Z));
        panel.add(createJLabel(shiftZ, SZ));
        panel.add(createJLabel(shiftMinus, SMINUS));
        panel.add(createJLabel(shiftPlus, SPLUS));
//        panel.add(createJLabel(ctrlo));
        commandsFrame.add(panel);
        commandsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        commandsFrame.setSize(400, 500);
    }

    public void propertyChange(PropertyChangeEvent e) {


    }

    String word = "";
    ArrayList<String> redoWords = new ArrayList<>();

    private String undo() {

        word = "";
        if (words.size() != 0) {
            redoWords.add(words.get(words.size() - 1));
            words.remove(words.size() - 1);
            for (String s : words) {
                word += s;
            }

            return word;
        }
        return null;
    }

    private String redo() {
        word = "";
        if (redoWords.size() > 0) {

            for (String s : words) {
                word += s;
            }
            words.add(redoWords.get(redoWords.size() - 1));
            word += (redoWords.get(redoWords.size() - 1));
            redoWords.remove(redoWords.size() - 1);
            return word;
        }

        return null;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            words.add(word);
            word = "";
            return;
        }

        if (e.isControlDown() && !e.isShiftDown()) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_M:
                    window.edit.doClick(1);
                    break;
                case KeyEvent.VK_H:
                    window.help.doClick();
                    break;
                case KeyEvent.VK_F:
                    window.file.doClick();
                    break;
                case KeyEvent.VK_N:
                    new Window<>();
                    break;
                case KeyEvent.VK_Q:
                    window.dispose();
                    break;
                case KeyEvent.VK_O:
                    open();
                    break;
                case KeyEvent.VK_T:
                    theme();
                    break;
                case KeyEvent.VK_D:
                    darkMode1();
                    break;
                case KeyEvent.VK_EQUALS:
                    inc();
                    break;
                case KeyEvent.VK_MINUS:
                    dic();
                    break;
                case KeyEvent.VK_Z:
                    String text = undo();
                    if (text != null) {
                        window.ta.setText(text);
                    }
                    break;


            }


        }
        if (e.isShiftDown() && e.isControlDown()) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_EQUALS -> {
                    zoomIn();
                    break;
                }
                case KeyEvent.VK_MINUS -> {
                    zoomOut();
                    break;
                }
                case KeyEvent.VK_Z -> {
                    String text = redo();
                    if (text != null) {
                        window.ta.setText(text);
                    }
                    break;
                }
                default -> {
                    System.out.println(e.getKeyChar());
                }
            }
        }

    }

    private void darkMode1() {
        window.darkMode.setText("Dark");
        window.ta.setBackground(Color.black);
        window.ta.setForeground(Color.white);
    }

    public void keyTyped(KeyEvent e) {

        if (!e.isControlDown()) {
            word = word.concat("" + e.getKeyChar());
        }

        JTextArea obj = (JTextArea) e.getSource();
        int l = obj.getLineCount();
        int col = 0;


        String text = obj.getText();
        int words = text.codePointCount(0, text.length());

        window.statusLabel.setText("  Total Words : " + words + "  " + "Lines : " + l + "column : " + col);

    }

    public void keyReleased(KeyEvent e) {
    }

    public void menuSelected(MenuEvent e) {

    }

    public void menuDeselected(MenuEvent e) {

    }

    public void menuCanceled(MenuEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() instanceof JLabel) {
            JLabel label = (JLabel) e.getSource();
            String key = label.getToolTipText();
            if (key.equals(O)) {
                open();
            }
            if (key.equals(N)) {
                new Window<>();
            }
            if (key.equals(Q)) {
                window.dispose();
            }
            if (key.equals(T)) {
                theme();
            }
            if (key.equals(D)) {
                darkMode1();
            }
            if (key.equals(PLUS)) {
                zoomIn();
            }
            if (key.equals(MINUS)) {
                zoomOut();
            }
            if (key.equals(Z)) {
                undo();
            }
            if (key.equals(SZ)) {
                String text = redo();
                if (text != null) {
                    window.ta.setText(text);
                }
            }
            if (key.equals(SPLUS)) {
                inc();
            }
            if (key.equals(SMINUS)) {
                dic();
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource().equals(window.edit)) {
            window.edit.doClick();
        }
        if (e.getSource().equals(window.help)) {
            window.help.doClick();
        }
        if (e.getSource().equals(window.file)) {
            window.file.doClick();
        }
        if (e.getSource().equals(window.view))
            window.view.doClick();
        if (e.getSource().equals(window.darkMode)) {
            mouseOver = true;
            prevDarkMode();
        }
        if (e.getSource().equals(window.WindowSize))
            window.WindowSize.doClick();
        if (e.getSource().equals(window.textSize))
            window.textSize.doClick();
        if (e.getSource().equals(window.window)) {
            window.window.doClick();
        }


    }

    @Override
    public void mouseExited(MouseEvent e) {

        if (e.getSource().equals(window.darkMode)) {
            mouseOver = false;
            prevDarkMode();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //events for edit menu
        if (e.getSource().equals(window.cut)) {
            window.ta.cut();
        }
        if (e.getSource().equals(window.copy)) {
            window.ta.copy();
        }
        if (e.getSource().equals(window.paste)) {
            window.ta.paste();
        }
        if (e.getSource().equals(window.selectAll))
            window.ta.selectAll();

        //events for help menu
        if (e.getSource().equals(window.replace)) {
            replace();
        }

        if (e.getSource().equals(window.search)) {
            search();
        }
        if (e.getSource().equals(window.New)) {
            try {
                New();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource().equals(window.newWindow))
            new Window();

        if (e.getSource().equals(window.save)) {
            try {
                save();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource().equals(window.open))
            open();
        if (e.getSource().equals(window.theme))
            theme();
        if (e.getSource().equals(window.darkMode))
            darkMode();
        if (e.getSource().equals(window.inc))
            inc();
        if (e.getSource().equals(window.dic))
            dic();
        if (e.getSource().equals(window.zoomIn))
            zoomIn();
        if (e.getSource().equals(window.zoomOut))
            zoomOut();
        if (e.getSource().equals(window.wrapLine))
            wrapLine();
        if (e.getSource().equals(window.wrapText))
            wrapText();
        if (e.getSource().equals(window.font))
            font();
        if (e.getSource().equals(button))
            apply();
        if (e.getSource().equals(window.statusBar))
            statusBar();
        if (e.getSource().equals(window.commands)) {
            commands();
        }
        if (e.getSource().equals(window.undo)) {
            window.ta.setText(undo());
        }
        if (e.getSource().equals(window.redo)) {
            window.ta.setText(redo());
        }
    }

    private void commands() {
        commandsFrame.setVisible(true);
    }

    private Component createJLabel(String name, String key) {
        JLabel label = new JLabel(name);
        label.setFont(new Font("times new Roman", Font.PLAIN, 20));
        label.setBackground(Color.BLACK);
        label.setForeground(Color.black);
        label.addMouseListener(this);
        label.setToolTipText(key);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(4)));
        return label;
    }


    private void replace() {

        search();
        if (!window.ta.getText().isEmpty() && window.ta.getSelectedText() != null) {
            String replceText = JOptionPane.showInputDialog(null, "replace with ");

            int deccision = JOptionPane.showConfirmDialog(null, "are you sure to replace ");
            if (deccision == 0) {
                System.out.println("replaced");
                window.ta.replaceSelection(replceText);
            }
        }


    }

    private void search() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception ignored){

        }
        String search = JOptionPane.showInputDialog("enter what u need : ");

        String data = window.ta.getText();
        if (search != null) {

            if (data.contains(search)) {
                int offset = data.indexOf(search);
                window.ta.select(offset, offset + search.length());
            } else {
                JOptionPane.showMessageDialog(null, "data not found", "error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

    }

    private void save() throws IOException {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e){

        }
        JFileChooser fc = new JFileChooser("C:\\Users\\naveen\\OneDrive\\Desktop");

        int option = fc.showSaveDialog(window);
        if (option == 0) {
            String path = fc.getSelectedFile().getPath();
            String name = fc.getName();

            FileWriter fw = new FileWriter(path + name);

            fw.write(window.ta.getText().toCharArray());
            fw.flush();
            fw.close();

            System.out.println("data written");


        }
    }

    private void New() throws IOException {
        if (!window.ta.getText().equals("")) {
            int option = JOptionPane.showOptionDialog(null, "do you want to save ", "CAUTION",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Yes", "No", "cancel"}, 2);
            if (option == 0) {
                save();
                window.ta.setText("");
            } else {

                window.ta.setText("");
            }
        }
    }

    private void open() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception ignored){

        }

        //opeing file chooser
        JFileChooser fc = new JFileChooser();
        int option = fc.showOpenDialog(window);

        if (option == 0) {
            Window newWindow = new Window();
            //getting creditionals
            String path = fc.getSelectedFile().getAbsolutePath();

            //reading file  data and stroing it into new window
            System.out.println(path);
            try {
                BufferedReader reader = new BufferedReader(new FileReader(path));

                String str = "";
                while ((str = reader.readLine()) != null) {

                    newWindow.ta.setText(newWindow.ta.getText() + str);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void theme() {
        window.bgColor = JColorChooser.showDialog(window, "choose background color", null);
        window.textColor = JColorChooser.showDialog(window, "choose text color", null);
        window.ta.setBackground(window.bgColor);
        window.ta.setForeground(window.textColor);
    }

    private void darkMode() {
        if (window.darkMode.isSelected()) {
            window.darkMode.setText("Default");
            window.ta.setBackground(window.bgColor);
            window.ta.setForeground(window.textColor);
        } else {
            window.darkMode.setText("Dark");
            window.ta.setBackground(Color.BLACK);
            window.ta.setForeground(Color.white);
        }

    }

    private void inc() {
        window.width += 25;
        window.height += 25;
        window.setSize(window.width, window.height);
        window.ta.setBounds(0, 0, window.width, window.height - 100);
        window.statusLabel.setBounds(0, window.height - 100, window.width, 20);
    }

    private void dic() {
        window.width -= 25;
        window.height -= 25;
        window.setSize(window.width, window.height);
        window.ta.setBounds(0, 0, window.width, window.height - 100);
        window.statusLabel.setBounds(0, window.height - 100, window.width, 20);
    }

    void Goto() {
        int i = window.ta.getLineCount();

        window.ta.setCaret(new DefaultCaret());


        System.out.println("applied");

    }

    private void zoomIn() {
        window.ta.setFont(new Font(window.ta.getFont().getName(), Font.PLAIN, window.ta.getFont().getSize() + 2));
    }

    private void zoomOut() {
        window.ta.setFont(new Font(window.ta.getFont().getName(), Font.PLAIN, window.ta.getFont().getSize() - 2));
    }

    private void wrapLine() {
        if (!window.ta.getLineWrap()) {
            window.ta.setLineWrap(true);
            Icon icon = new ImageIcon("C:\\Users\\naveen\\IdeaProjects\\forSwing\\src\\images\\stop.png");
            window.wrapLine.setIcon(icon);
        } else {
            window.wrapLine.setIcon(null);
            window.ta.setLineWrap(false);
        }


    }

    private void wrapText() {
        if (!window.ta.getWrapStyleWord()) {
            Icon icon = new ImageIcon("C:\\Users\\naveen\\IdeaProjects\\forSwing\\src\\images\\stop.png");
            window.wrapText.setIcon(icon);
            window.ta.setWrapStyleWord(true);
        } else {
            window.wrapText.setIcon(null);
            window.ta.setWrapStyleWord(false);
        }
    }

    private void font() {
        label.setBounds(10, 150, 300, 100);
        label.setFont(new Font("times new roman", Font.PLAIN, 20));
        frame = new JFrame("select required styles");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {

        }

        String[] str = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        font = new JComboBox<>(str);
        font.setBounds(10, 10, 100, 100);
        font.addItemListener(this);


        style = new JComboBox<>(new String[]{"Plain", "Bold", "Italic"});
        style.setBounds(120, 10, 100, 100);
        style.addItemListener(this);

        size = new JComboBox<>(Int);
        size.setBounds(230, 10, 50, 100);
        size.addItemListener(this);


        button = new JButton("okay");
        button.setBounds(140, 165, 100, 40);
        button.addActionListener(this);

        JPanel panel1 = new JPanel(getFlowLayout());
        panel1.setAutoscrolls(true);

        JPanel panel2 = new JPanel(getFlowLayout());

        panel2.setBounds(0, 0, 300, 125);
        panel2.add(font);
        panel2.add(style);
        JPanel panel3 = new JPanel(getFlowLayout());
        panel3.setBounds(0, 125, 300, 125);
        panel3.add(size);
        panel3.add(label);

        JPanel panel4 = new JPanel(getFlowLayout());
        panel4.add(button);

        panel1.add(panel2);
        panel1.add(panel3);
        panel1.add(panel4);
        frame.setContentPane(panel1);
//        panel.add(style);
//        panel.add(size);
//        panel.add(label);
//        panel.add(button);
//
//        frame.setContentPane(panel);
        frame.setLocationRelativeTo(window);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(300, 300);

    }

    private LayoutManager getFlowLayout() {
        return new FlowLayout(FlowLayout.CENTER, 10, 10);
    }

    private void apply() {

        window.ta.setFont(new Font((String) font.getSelectedItem(), style.getSelectedIndex(), Int[size.getSelectedIndex()]));
        frame.dispose();
    }

    private void statusBar() {


        if (window.statusBar.getIcon() == null) {
            Icon icon = new ImageIcon("C:\\Users\\naveen\\IdeaProjects\\forSwing\\src\\images\\stop.png");
            window.statusBar.setIcon(icon);
            window.statusLabel.setVisible(true);
        } else {
            window.statusBar.setIcon(null);
            window.statusLabel.setVisible(false);
        }

    }

    private void prevDarkMode() {
        if (mouseOver) {
            window.ta.setBackground(Color.black);
            window.ta.setForeground(Color.white);
        } else {
            if (window.darkMode.isSelected()) {
                window.ta.setBackground(Color.black);
                window.ta.setForeground(Color.white);
                return;
            }
            window.ta.setBackground(window.bgColor);
            window.ta.setForeground(window.textColor);

        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if (e.getSource().equals(window.darkMode)) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                window.darkMode.setText("Default");
                window.ta.setBackground(window.bgColor);
                window.ta.setForeground(window.textColor);
            } else {
                window.darkMode.setText("Dark");
                window.ta.setBackground(Color.BLACK);
                window.ta.setForeground(Color.white);
            }
        }
        if (e.getSource().equals(font) || e.getSource().equals(style) || e.getSource().equals(size)) {
            label.setFont(new Font((String) font.getSelectedItem(), style.getSelectedIndex(), Int[size.getSelectedIndex()]));
        }
    }


}
