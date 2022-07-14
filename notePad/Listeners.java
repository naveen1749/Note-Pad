package notePad;

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

public class Listeners implements PropertyChangeListener, KeyListener, MenuListener, MouseListener,ActionListener,ItemListener{
    JComboBox<String> font,style;
    JComboBox<Integer> size;
    JButton button;
    JFrame frame;
    final JLabel label=new JLabel("sample TExt");

    Window window;
    boolean mouseOver,mouseOnItem=true;
    Integer[] Int = new Integer[]{20,22,24,26,28,36,48,62,72};
    Listeners(Window obj){
        this.window=obj;

    }
    public void propertyChange(PropertyChangeEvent e){


    }

    public void keyPressed(KeyEvent e){
        if(e.isControlDown() && !e.isShiftDown()){
            switch(e.getKeyCode()){
                case  KeyEvent.VK_M: window.edit.doClick(1);break;
                case KeyEvent.VK_H: window.help.doClick();break;
                case KeyEvent.VK_F: window.file.doClick();break;
                case KeyEvent.VK_N: new Window<>();break;
                case KeyEvent.VK_Q: window.dispose();break;
                case KeyEvent.VK_O: open();break;
                case KeyEvent.VK_T: theme();break;
                case KeyEvent.VK_D: window.darkMode.setText("Dark");
                                    window.ta.setBackground(Color.black);
                                    window.ta.setForeground(Color.white);
                                    break;
                case KeyEvent.VK_EQUALS:inc();break;
                case KeyEvent.VK_MINUS:dic();break;


            }


        }
        if(e.isShiftDown() && e.isControlDown()){
            switch (e.getKeyChar()) {
                case '+' -> {
                   zoomIn();
                    break;
                }
                case '_'->{ zoomOut();break;}
            }
        }

    }

    public void keyTyped(KeyEvent e){

        JTextArea obj=(JTextArea)e.getSource();
        int l=obj.getLineCount();
        int col=0;


       String text= obj.getText();
       int words=text.codePointCount(0,text.length());

       window.statusLabel.setText("Total Words : "+words+"  "+"Lines : "+l+"column : "+col);

    }

    public void keyReleased(KeyEvent e){

    }

    public void menuSelected(MenuEvent e){
       // Window.edit.requestFocus();
    }
    public void menuDeselected(MenuEvent e){

    }

    public void menuCanceled(MenuEvent e){

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource().equals(window.edit))
            window.edit.doClick();
        if(e.getSource().equals(window.help))
            window.help.doClick();
        if(e.getSource().equals(window.file))
            window.file.doClick();
        if(e.getSource().equals(window.view))
            window.view.doClick();
        if(e.getSource().equals(window.darkMode))
           mouseOver=true;
            prevDarkMode();









    }

    @Override
    public void mouseExited(MouseEvent e) {


        if(e.getSource().equals(window.darkMode)) {
            mouseOver = false;
            prevDarkMode();

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //events for edit menu
        if(e.getSource().equals(window.cut)){
            window.ta.cut();
        }
        if(e.getSource().equals(window.copy)){
            window.ta.copy();
        }
        if(e.getSource().equals(window.paste)){
            window.ta.paste();
        }
        if(e.getSource().equals(window.selectAll))
            window.ta.selectAll();

        //events for help menu
        if(e.getSource().equals(window.replace)){
            replace();
        }

        if(e.getSource().equals(window.search)){
            search();
        }
        if(e.getSource().equals(window.New)){
            try {
                New();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if(e.getSource().equals(window.newWindow))
            new Window();

        if(e.getSource().equals(window.save)){
            try {
                save();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if(e.getSource().equals(window.open))
            open();
        if(e.getSource().equals(window.theme))
            theme();
        if(e.getSource().equals(window.darkMode))
            darkMode();
        if(e.getSource().equals(window.inc))
         inc();
        if(e.getSource().equals(window.dic))
           dic();
        if(e.getSource().equals(window.zoomIn))
           zoomIn();
        if(e.getSource().equals(window.zoomOut))
            zoomOut();
        if(e.getSource().equals(window.wrapLine))
            wrapLine();
        if(e.getSource().equals(window.wrapText))
            wrapText();
        if(e.getSource().equals(window.font))
            font();
        if(e.getSource().equals(button))
            apply();
        if(e.getSource().equals(window.statusBar))
            statusBar();
    }
    public  void itemStateChanged(ItemEvent e){
        label.setFont(new Font((String)font.getSelectedItem(),style.getSelectedIndex(),Int[size.getSelectedIndex()]));
    }


    private void replace(){

        search();
        if(!window.ta.getText().isEmpty() && window.ta.getSelectedText() != null) {
            String replceText = JOptionPane.showInputDialog(null, "replace with ");

            int deccision = JOptionPane.showConfirmDialog(null, "are you sure to replace ");
            if (deccision == 0) {
                System.out.println("replaced");
                window.ta.replaceSelection(replceText);
            }
        }


    }

    private void search(){
        String search=JOptionPane.showInputDialog("enter what u need : ");
        String data = window.ta.getText();

        if(data.contains(search)){
            int offset=data.indexOf(search);
            window.ta.select(offset,offset+search.length());
        }else{
            JOptionPane.showMessageDialog(null,"data not found","error",JOptionPane.ERROR_MESSAGE);
            return;
        }

    }

    private void save() throws IOException {
        JFileChooser fc= new JFileChooser("C:\\Users\\naveen\\OneDrive\\Desktop");

        int option=fc.showSaveDialog(window);
        if(option == 0){
            String path=fc.getSelectedFile().getPath();
            String name = fc.getName();

                FileWriter fw= new FileWriter(path+name);

                fw.write(window.ta.getText().toCharArray());
                fw.flush();
                fw.close();

                System.out.println("data written");


        }
    }
    private void New() throws IOException {
        if(!window.ta.getText().equals("")) {


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

        //opeing file chooser
        JFileChooser fc = new JFileChooser();
        int option=fc.showOpenDialog(window);

        if(option == 0){
            Window newWindow= new Window();
            //getting creditionals
            String path=fc.getSelectedFile().getAbsolutePath();

            //reading file  data and stroing it into new window
            System.out.println(path);
            try {
            BufferedReader reader =new BufferedReader( new FileReader(path));

            String str="";
                while ((str = reader.readLine()) != null) {

                    newWindow.ta.setText(newWindow.ta.getText() + str);

                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }


    }

    private void theme(){
        window.bgColor=JColorChooser.showDialog(window,"choose background color",null);
        window.textColor=JColorChooser.showDialog(window,"choose text color",null);
        window.ta.setBackground(window.bgColor);
        window.ta.setForeground(window.textColor);
    }
    private void darkMode(){
        if(window.darkMode.isSelected()){
            window.darkMode.setText("Dark");
            window.ta.setBackground(window.bgColor);
            window.ta.setForeground(window.textColor);
        }else{
            window.darkMode.setText("Default");
            window.ta.setBackground(Color.BLACK);
            window.ta.setForeground(Color.white);
        }

    }
    private void inc(){
        window.width+=25;
        window.height+=25;
        window.setSize(window.width,window.height);
        window.ta.setBounds(0,0,window.width,window.height-100);
        window.statusLabel.setBounds(0,window.height-100,window.width,20);
    }
    private void dic(){
        window.width-=25;
        window.height-=25;
        window.setSize(window.width,window.height);
        window.ta.setBounds(0,0,window.width,window.height-100);
        window.statusLabel.setBounds(0,window.height-100,window.width,20);
    }
    void Goto(){
        int i=window.ta.getLineCount();

        window.ta.setCaret(new DefaultCaret());


        System.out.println("applied");

    }
    private void zoomIn(){
        window.ta.setFont(new Font(window.ta.getFont().getName(),Font.PLAIN,window.ta.getFont().getSize()+2));
    }
    private void zoomOut(){
        window.ta.setFont(new Font(window.ta.getFont().getName(),Font.PLAIN,window.ta.getFont().getSize()-2));
    }
    private void wrapLine(){
        if(!window.ta.getLineWrap()){
            window.ta.setLineWrap(true);
            Icon icon=new ImageIcon("C:\\Users\\naveen\\IdeaProjects\\forSwing\\src\\images\\stop.png");
           window.wrapLine.setIcon(icon);
        }else{
            window.wrapLine.setIcon(null);
            window.ta.setLineWrap(false);
        }


    }
    private void wrapText(){
        if(!window.ta.getWrapStyleWord()){
            Icon icon=new ImageIcon("C:\\Users\\naveen\\IdeaProjects\\forSwing\\src\\images\\stop.png");
            window.wrapText.setIcon(icon);
            window.ta.setWrapStyleWord(true);
        }else {
            window.wrapText.setIcon(null);
            window.ta.setWrapStyleWord(false);
        }
    }
    private void font(){
        label.setBounds(10,150,300,100);
        label.setFont(new Font("times new roman",Font.PLAIN,20));
         frame = new JFrame("select required styles");
        frame.setSize(300,600);

        Container pane=frame.getContentPane();
        pane.setLayout(null);

        String[] str=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        font = new JComboBox<>(str);
        font.setBounds(10,10,100,100);
        font.addItemListener(this);


        style=new JComboBox<>(new String[]{"Plain","Bold","Italic"});
        style.setBounds(120,10,100,100);
        style.addItemListener(this);

        size = new JComboBox<>(Int);
        size.setBounds(230,10,50,100);
        size.addItemListener(this);


        button=new JButton("okay");
        button.setBounds(frame.getWidth()-200,frame.getHeight()-200,100,100);
        button.addActionListener(this);



        frame.add(button);
        frame.add(font);
        frame.add(style);
        frame.add(size);
        frame.add(label);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

    }
    private void apply(){

        window.ta.setFont(new Font((String)font.getSelectedItem(),style.getSelectedIndex(),Int[size.getSelectedIndex()]));
        frame.dispose();
    }
    private void statusBar(){


            if(window.statusBar.getIcon() == null){
                Icon icon=new ImageIcon("C:\\Users\\naveen\\IdeaProjects\\forSwing\\src\\images\\stop.png");
                window.statusBar.setIcon(icon);
                window.statusLabel.setVisible(true);
            }else{
                window.statusBar.setIcon(null);
                window.statusLabel.setVisible(false);
            }

    }
    private void prevDarkMode(){
        if(mouseOver){
            window.ta.setBackground(Color.black);
            window.ta.setForeground(Color.white);
        }else{
            if(window.darkMode.isSelected()){
                window.ta.setBackground(Color.black);
                window.ta.setForeground(Color.white);
            }else{
                window.ta.setBackground(window.bgColor);
                window.ta.setForeground(window.textColor);
            }
        }

    }

}
