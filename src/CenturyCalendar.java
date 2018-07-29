
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import java.io.*;
import com.sun.speech.freetts.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class CenturyCalendar extends JFrame implements ItemListener, ActionListener {
    JLabel lbl[], img_lbl;
    String []day = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    int last_day, month_no, year;
    String w_day; //First week day
    int lbl_no;
    Date dt;
    DateFormat df;
    JComboBox m_cb, y_cb;
    String txt_dt = "";
    Voice voice;
    VoiceManager vm;
    JButton sp;
    
    public CenturyCalendar() {
        initComponents();
        this.setResizable(false);
        createGUI();
        dateTime();
    }

    @SuppressWarnings("unchecked")
    
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 809, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 754, Short.MAX_VALUE)
        );

        pack();
    }

    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CenturyCalendar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CenturyCalendar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CenturyCalendar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CenturyCalendar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CenturyCalendar().setVisible(true);
            }
        });
    }

    private void createGUI() 
    {
        
      img_lbl = new JLabel();
      img_lbl.setBounds(10, 10, 800, 275);
      img_lbl.setBorder(BorderFactory.createEtchedBorder());
      getContentPane().add(img_lbl);
         dt = Calendar.getInstance().getTime();
       df = new SimpleDateFormat("MM");
        month_no = Integer.parseInt(df.format(dt));
         df = new SimpleDateFormat("yyyy");
       year = Integer.parseInt(df.format(dt));
        
        m_cb = new JComboBox();
        m_cb.setBounds(10, 300, 430, 40);
         for(int i = 1; i<= 12; i++)
       {
           String t_date = "01-"+i+"-2000";
           df = new SimpleDateFormat("dd-MM-yyyy");
           try
            {
                dt = df.parse(t_date);              
            }catch(ParseException e){}
           df = new SimpleDateFormat("MMMM");  
           m_cb.addItem(df.format(dt));
       }
        m_cb.setSelectedIndex(month_no-1);
        getContentPane().add(m_cb);
        m_cb.addItemListener(this);
        
        y_cb = new JComboBox();
        y_cb.setBounds(450, 300, 320, 40);
        for(int i = 1900; i<=3000; i++)
        {
            y_cb.addItem(""+i);
        }
        y_cb.setSelectedIndex((year-1900));
        getContentPane().add(y_cb);
        y_cb.addItemListener(this);
        lbl = new JLabel[49];
        
        int x = 10, y = 345;
        for(int i = 0; i< 49; i++)   //Goes Horizontally
        {
            lbl[i] = new JLabel("");
            lbl[i].setBounds(x, y, 100, 50);
            lbl[i].setHorizontalAlignment(JLabel.CENTER);
            lbl[i].setBorder(BorderFactory.createEtchedBorder());
            lbl[i].setFont(new Font("Tahoma", Font.PLAIN, 24));
           if(i < 7){
               lbl[i].setText(day[i]);    //For setting day
               lbl[i].setBackground(Color.BLACK);
               lbl[i].setForeground(Color.WHITE);
               lbl[i].setOpaque(true);
           }
           
               if(i%7==0) //For Sundays; Goes by per week
               lbl[i].setForeground(Color.RED);
         
            getContentPane().add(lbl[i]);
            x += 110;
            if((i+1)%7==0)
            {
                y += 55;
                x = 10;
            }
        }
        getContentPane().setBackground(new Color(185, 246, 250));
        sp = new JButton("Speak");
        sp.setBounds(10, 700, 150, 25);
        getContentPane().add(sp);
        sp.addActionListener(this);
        
    }
    public void getDateText()
    {
        String yr = ""+Integer.parseInt((String)y_cb.getSelectedItem());
        String mn = (String)m_cb.getSelectedItem();
        String a1[] = {"zero", "one", "two", "three", "four","five", "six", "seven", "eight", "nine"};

        String a2[] = {"ten", "eleven", "twelve", "thirteen", "fourteen",
                          "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};

    
        String a3[] = {"", "", "twenty", "thirty", "forty", "fifty",
                             "sixty", "seventy", "eighty", "ninety"};

        String a4[] = {"hundred", "thousand"};       
        
        
        txt_dt += mn + " ";
        int x = Integer.parseInt(""+yr.charAt(0)); //Extracts the position of the array
        txt_dt += a1[x]+ " " + a4[1] + " ";
        x = Integer.parseInt(""+yr.charAt(1));
        if(x != 0)
        {
            txt_dt += a1[x] + " " + a4[0] + " ";
        }
        int tens = Integer.parseInt(""+yr.charAt(2));
        int unit = Integer.parseInt(""+yr.charAt(3));
        if(tens == 1)
        {              
           txt_dt += a2[unit];
        }
        else if(tens == 0)
        {
            txt_dt += a1[unit];
        }
        else
        {
            txt_dt += a3[tens] + " "+ a1[unit];
        }
        //System.out.println(txt_dt);
        
        
    }
    
    public void SpeakText()
    {
        vm = VoiceManager.getInstance();
        voice = vm.getVoice("kevin16");
        voice.allocate();
        try
        {
            voice.speak(txt_dt);
        }catch(Exception e){}
        voice.deallocate();
    }
    
    private void dateTime()
    {
      
      month_no = m_cb.getSelectedIndex()+1;
       switch (month_no)
       {
           case 1: case 3: case 5: case 7: case 8: case 10: case 12:
               last_day = 31;   
               break;
           case 4: case 6: case 9: case 11:
               last_day = 30;
               break;
           default:
               last_day = 28;
       }
      year = Integer.parseInt((String)y_cb.getSelectedItem());
       String t_date = "01-"+month_no+"-"+year;      //Created our own date
       df = new SimpleDateFormat("dd-MM-yyyy");
        try
        {
            dt = df.parse(t_date);               //To convert to date from string
        }catch(ParseException e){}        
       df = new SimpleDateFormat("EEE");
       w_day = df.format(dt).trim();
       if(w_day.equalsIgnoreCase("sun"))
           lbl_no = 7;
       else if(w_day.equalsIgnoreCase("mon"))
           lbl_no = 8;
       else if(w_day.equalsIgnoreCase("tue"))
           lbl_no = 9;
       else if(w_day.equalsIgnoreCase("wed"))
           lbl_no = 10;
       else if(w_day.equalsIgnoreCase("thu"))
           lbl_no = 11;
       else if(w_day.equalsIgnoreCase("fri"))
           lbl_no = 12;
       else if(w_day.equalsIgnoreCase("sat"))
           lbl_no = 13;
       for(int i = 1; i<=last_day; i++)
       {
           lbl[lbl_no].setText(""+i);
           lbl_no++;
       }
       
       getDateText();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        txt_dt = "";
        dateTime();
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SpeakText();
        
    }
}
