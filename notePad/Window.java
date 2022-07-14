package notePad;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTextUI;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Window<T> extends JFrame {

     JLabel statusLabel;
     JMenu edit,help,file,window,view,WindowSize,textSize;
     JMenuBar bar;
   protected  JMenuItem cut,copy,paste,selectAll,search,replace,save,New,
           newWindow,open,theme,inc,dic,goTo,zoomIn,zoomOut,wrapLine,wrapText
           ,font,statusBar,commands;
   protected   JTextArea ta;
   protected JToggleButton darkMode;
   Listeners obj;
   int MENUITEM=1;
   int MENU=0;
   int width=500;
   int height=500;
   JButton undo,redo;

boolean bt;
   Color bgColor=Color.white,textColor=Color.black;
    Window(){

        this.obj=new Listeners(this);
        darkMode=new JToggleButton("Dark mode");
        darkMode.setFocusable(false);
        darkMode.addItemListener(obj);
        darkMode.setBackground(Color.BLUE);
        darkMode.addMouseListener(obj);

            statusLabel =new JLabel();
            statusLabel.setBounds(0,height-100,width,20);
            getContentPane().setLayout(null);

            getContentPane().add(createContentPane());
            setJMenuBar((JMenuBar)createMenuBar());
            getContentPane().add(statusLabel);
try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());}catch(Exception ignored){}
            setSize(new Dimension(width,height));
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);



    }
    private JTextArea createContentPane(){
         ta= new JTextArea("",10,10);
        ta.setBackground(bgColor);
        ta.setForeground(textColor);
        ta.setLineWrap(true);
        ta.setCaret(new BasicTextUI.BasicCaret());
        ta.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        ta.addKeyListener(obj);
        ta.setBounds(0,0,width,height-100);
    return ta;
    }

    private T createMenuBar(){

       bar =new JMenuBar();
       bar.setSize(width,35);


        edit =(JMenu)create("Edit",KeyEvent.VK_E,MENU);
        help=(JMenu)create("Help",KeyEvent.VK_H,MENU);
        file=(JMenu)create("File",KeyEvent.VK_F,MENU);
        window=(JMenu)create("Window",KeyEvent.VK_W,MENU);
        view=(JMenu)create("View",KeyEvent.VK_V,MENU);


        //items of edit
         cut= (JMenuItem) create("cut",KeyEvent.VK_U,MENUITEM);
         copy=(JMenuItem) create("copy",KeyEvent.VK_P,MENUITEM);
         paste=(JMenuItem) create("paste",KeyEvent.VK_S,MENUITEM);
         selectAll = (JMenuItem) create("Select all",KeyEvent.VK_A,MENUITEM);
         theme=(JMenuItem) create("Theme",KeyEvent.VK_T,MENUITEM);

         //items of help
        search =  (JMenuItem) create("Search",KeyEvent.VK_S,MENUITEM);
        replace =  (JMenuItem) create("Replace",KeyEvent.VK_R,MENUITEM);
        commands =  (JMenuItem) create("Commands",KeyEvent.VK_C,MENUITEM);
        //items of file
        save =  (JMenuItem) create("Save",KeyEvent.VK_S,MENUITEM);
        New =  (JMenuItem) create("New",KeyEvent.VK_N,MENUITEM);
        newWindow= (JMenuItem) create("New Window",KeyEvent.VK_W,MENUITEM);
        open=(JMenuItem)create("Open",KeyEvent.VK_O,MENUITEM);
        //items of window
        WindowSize =(JMenu) create("Window Size",KeyEvent.VK_S,MENU);
        textSize =(JMenu) create("Text Size",KeyEvent.VK_T,MENU);
        inc=(JMenuItem)create("Increase",KeyEvent.VK_I,MENUITEM);
        dic=(JMenuItem)create("Decrease",KeyEvent.VK_D,MENUITEM);
        zoomIn=(JMenuItem)create("Increase",KeyEvent.VK_I,MENUITEM);
        zoomOut=(JMenuItem)create("Decrease",KeyEvent.VK_D,MENUITEM);
        goTo=(JMenuItem)create("Go To",KeyEvent.VK_G,MENUITEM);

        //items of View
        wrapLine=(JMenuItem) create("Wrap Line",KeyEvent.VK_L,MENUITEM);
        wrapText=(JMenuItem) create("Wrap Text",KeyEvent.VK_T,MENUITEM);
        font=(JMenuItem) create("Font",KeyEvent.VK_F,MENUITEM);
        statusBar=(JMenuItem) create("Status Bar",KeyEvent.VK_S,MENUITEM);

        statusBar.setSelected(false);//to diaplay icon properly
        //to get image icon perfectly
        ta.setWrapStyleWord(false);
        ta.setLineWrap(false);
        //adding items into menu
        WindowSize.add(inc);WindowSize.add(dic);
        textSize.add(zoomIn);textSize.add(zoomOut);
        edit.add(cut);edit.add(copy);edit.add(paste);edit.add(selectAll);
        help.add(search);help.add(replace);help.add(WindowSize);help.add(commands);
        file.add(New);file.add(open);file.add(newWindow);file.add(save);
        window.add(WindowSize);window.add(textSize);window.add(goTo);
        view.add(wrapLine);view.add(wrapText);view.add(font);view.add(theme);view.add(statusBar);

        undo = new JButton();
        undo.addActionListener(obj);
        undo.setFocusable(false);
        undo.setIcon(new ImageIcon("C:\\Users\\naveen\\IdeaProjects\\forSwing\\src\\notePad\\Images\\img_1.png"));
        redo = new JButton();
        redo.setFocusable(false);
        redo.addActionListener(obj);
        redo.setIcon(new ImageIcon("C:\\Users\\naveen\\IdeaProjects\\forSwing\\src\\notePad\\Images\\img.png"));
        //adding menu into bar
        bar.add(file);
        bar.add(edit);
        bar.add(window);
        bar.add(view);
        bar.add(help);
        bar.add(darkMode);
        bar.add(undo);
        bar.add(redo);
        bar.requestFocus();






        return (T)bar;
    }
    private JComponent create(String str,int key,int type){
        switch (type) {
            case 1 -> {
                JMenuItem item = new JMenuItem(str);
                item.addActionListener(obj);
                item.setMnemonic(key);

                return item;
            }
            case 0 -> {
                JMenu menu = new JMenu((str));
                menu.addMouseListener(obj);
                menu.setMnemonic(key);
                return menu;
            }
            default -> {
                return null;
            }
        }

    }

}
