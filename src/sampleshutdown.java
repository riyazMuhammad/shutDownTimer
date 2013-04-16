import java.awt.AWTException;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class sampleshutdown extends JFrame implements ActionListener, ItemListener{
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	Choice op;
	Choice tim;
	Label oplab,timlab,modelab,bottom_mode_lab,bottom_time_lab;
	Button ok;
	Image imageico;
	TrayIcon trayico;
	Timer t1;
	CheckboxGroup cbg;
	Checkbox mins,hrs;
	int mode=0;    //0 -> no mode selected
				   //1 -> Minutes mode selected
				   //2 -> Hours mode selected
	int i;

	public sampleshutdown() {
	super("Shutdown Timer");
	setSize(255,200);
	setResizable(false);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setLocation((d.width-getWidth())/2,(d.height-getHeight())/2);
	setLayout(new FlowLayout());
	//menubar
	MenuBar mybar = new MenuBar();
	setMenuBar(mybar);
	//file menu
	Menu file = new Menu("File");
	MenuItem min= new MenuItem("Minimize to Tray");
	MenuItem exit= new MenuItem("Exit");
	file.add(min);
	file.add(exit);
	mybar.add(file);
	min.addActionListener(this);
	exit.addActionListener(this);
	
	//about menu
	Menu hlp = new Menu("Help");
	mybar.add(hlp);
	MenuItem how = new MenuItem("How to Run");
	hlp.add(how);
	MenuItem athr= new MenuItem("Author");
	hlp.add(athr);
	how.addActionListener(this);
	athr.addActionListener(this);
	
	oplab= new Label("Select the Operation:");
	add(oplab);
	op = new Choice();
	op.add("Shutdown");
	op.add("Restart");
	add(op);
	modelab=new Label("Mode : ");
	add(modelab);
	cbg= new CheckboxGroup();
	mins = new Checkbox("Minutes", cbg, false);
	hrs= new Checkbox("Hours", cbg, false);
	add(mins);
	add(hrs);
	timlab = new Label("Select The Delay : 						");
	add(timlab);
	tim = new Choice();
	tim.add("Null");
	add(tim);
	bottom_mode_lab=new Label("Mode : Not Selected ");	
	add(bottom_mode_lab);
	bottom_time_lab=new Label("Time Left : Null	");
	add(bottom_time_lab);
	
	ok = new Button("OK");
	add(ok);
	ok.addActionListener(this);
	mins.addItemListener(this);
	hrs.addItemListener(this);
	imageico =Toolkit.getDefaultToolkit().getImage(getClass().getResource("resources/trayicon.JPG"));
	trayico = new TrayIcon(imageico,"Shutdown timer Active");
	
	
	}
	public static void main(String[] args) {
	sampleshutdown s1 = new sampleshutdown();
	s1.setVisible(true);
	}
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("OK")){
			final int delay;
			if(mode==0){
			JOptionPane.showMessageDialog(this, "Select a Mode\n" +
					"Minutes Or Hours");
			}
			else{
				op.disable();
				tim.disable();
				mins.disable();
				hrs.disable();
				remove(ok);
				Scanner s1 = new Scanner(tim.getSelectedItem());
				delay=s1.nextInt();
				
		if(op.getSelectedItem().toString().equals("Shutdown")){			
			if(mode==1){
				i=delay*60;
			}
			else{
				i=delay*3600;
			}
			t1=new Timer(1000, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(i==0){
						t1.stop();
					try {
						Runtime.getRuntime().exec("shutdown /s");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					}
					else{
						i--;
						bottom_time_lab.setText("Time Left : "+i);
						
					}
				}
			});
			t1.start();
		}
		if(op.getSelectedItem().toString().equals("Restart")){
			if(mode==1){
				i=delay*60;
			}
			else{
				i=delay;
			}
			t1=new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(i==0){
						t1.stop();
					try {
						Runtime.getRuntime().exec("shutdown /r");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					}
					else{
						i--;
						bottom_time_lab.setText("Time Left : "+i);
						
					}
				}
			});
			t1.start();
		}
		}
		}
		if(e.getActionCommand().equals("Minimize to Tray")){
			setVisible(false);
			try {
				SystemTray.getSystemTray().add(trayico);
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
			trayico.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				if(me.getButton()==1){
					if(me.getClickCount()==2){
						setVisible(true);
						SystemTray.getSystemTray().remove(trayico);
					}
				}
			}
			});
		}
		if(e.getActionCommand().equals("How to Run")){
			JOptionPane.showMessageDialog(this, "1.Select the operation\n" +
					"a.Shutdown\n" +
					"b.Restart\n" +
					"2.Select input mode(usefull in next step)\n" +
					"a.Minutes\n" +
					"b.Hours\n" +
					"3.Based on Step2, selected value from the list is evaluated\n" +
					"Example: if Mode=Hours and Delay=1, then delay=3600 seconds\n" +
					"The value of delay is 'Time left value'\n" +
					"4.press OK\n"+
					"this Application can also be Minimized to Tray");
		}
		if(e.getActionCommand().equals("Author")){
			JOptionPane.showMessageDialog(this, "Hi Guys, my name is Riyaz.\n" +
					"If you like this App,\n" +
					"Contact me : riyaz291990@gmail.com\n" +
					"I would be glad to help");
		}
		if(e.getActionCommand().equals("Exit")){
			System.exit(0);
		}
		}
	@Override
	public void itemStateChanged(ItemEvent ie) {
		if(ie.getItem()=="Minutes"){
			tim.removeAll();
			
			for(int i=1;i<=60;i++){
				tim.add(""+i);
			}
			mode=1;
			bottom_mode_lab.setText("Mode : Minutes");
		}
		if(ie.getItem()=="Hours"){
			tim.removeAll();
			for(int i=1;i<=24;i++){
				tim.add(""+i);
			}
			mode=2;
			bottom_mode_lab.setText("Mode : Hours");
		}
		
	}

}
