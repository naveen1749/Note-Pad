package notePad;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTextUI;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Window<T> extends JFrame {

     JLabel statusLabel;
     JMenu edit,help,file,window,view;
   static  JMenuBar bar;
   protected  JMenuItem cut,copy,paste,selectAll,search,replace,save,New,newWindow,open,theme,inc,dic,goTo,zoomIn,zoomOut,setFont,wrapLine,wrapText
           ,font,statusBar;
   protected   JTextArea ta;
   protected JToggleButton darkMode;
   Listeners obj;
   int MENUITEM=1;
   int MENU=0;
   int width=500;
   int height=500;

boolean bt;
   Color bgColor=Color.white,textColor=Color.black;
    Window(){

        this.obj=new Listeners(this);
        darkMode=new JToggleButton("Dark mode");
        darkMode.setFocusable(false);
        darkMode.addActionListener(obj);
        darkMode.setSelected(true);
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
        ta.addKeyListener(obj);
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
        //items of file
        save =  (JMenuItem) create("Save",KeyEvent.VK_S,MENUITEM);
        New =  (JMenuItem) create("New",KeyEvent.VK_N,MENUITEM);
        newWindow= (JMenuItem) create("New Window",KeyEvent.VK_W,MENUITEM);
        open=(JMenuItem)create("Open",KeyEvent.VK_O,MENUITEM);
        //items of window
        inc=(JMenuItem)create("+",KeyEvent.VK_PLUS,MENUITEM);
        dic=(JMenuItem)create("-",KeyEvent.VK_MINUS,MENUITEM);
        zoomIn=(JMenuItem)create("zoom In",KeyEvent.VK_I,MENUITEM);
        zoomOut=(JMenuItem)create("zoom Out",KeyEvent.VK_O,MENUITEM);
        goTo=(JMenuItem)create("Go To",KeyEvent.VK_G,MENUITEM);
        setFont=(JMenuItem)create("Go To",KeyEvent.VK_G,MENUITEM);
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
        edit.add(cut);edit.add(copy);edit.add(paste);edit.add(selectAll);
        help.add(search);help.add(replace);
        file.add(New);file.add(open);file.add(newWindow);file.add(save);
        window.add(inc);window.add(dic);window.add(goTo);window.add(zoomIn);window.add(zoomOut);window.add(setFont);
        view.add(wrapLine);view.add(wrapText);view.add(font);view.add(theme);view.add(statusBar);

        //adding menu into bar
        bar.add(edit);
        bar.add(help);
        bar.add(file);
        bar.add(window);
        bar.add(view);
        bar.add(darkMode);
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
